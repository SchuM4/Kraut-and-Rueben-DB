#!/usr/bin/env python3
"""
populate_db.py — Lädt deutsche Testdaten aus den CSV-Dateien im selben Verzeichnis
in das PostgreSQL-Schema "krautUndRueben".

Die CSV-Dateien referenzieren verwandte Datensätze über sprechende Schlüssel
(z. B. Stadtname, Lieferantenname, Zutatenbezeichnung, Kunden-E-Mail,
Rezeptbezeichnung) statt über die automatisch generierten IDENTITY-Spalten.
Das Skript löst diese Referenzen beim Einfügen auf, indem es die von
PostgreSQL per RETURNING zurückgegebene ID merkt und für nachgelagerte
Tabellen wiederverwendet. So bleiben die CSV-Dateien lesbar und lassen sich
unabhängig von der internen ID-Vergabe erweitern.

Voraussetzungen:
    pip install -r requirements.txt --break-system-packages

Verwendung:
    python populate_db.py --host localhost --port 5432 --dbname krautundrueben \
        --user krautAndRueben --password krautAndRueben

    Alternativ über Umgebungsvariablen PGHOST, PGPORT, PGDATABASE, PGUSER, PGPASSWORD.

    --reset leert vorher alle Tabellen des Schemas (TRUNCATE ... RESTART IDENTITY CASCADE),
    damit das Skript gefahrlos mehrfach ausgeführt werden kann.
"""
import argparse
import csv
import os
import sys
from decimal import Decimal
from pathlib import Path

import psycopg2

# Der Name wird beim Anlegen des Schemas (ohne Anführungszeichen) von Postgres
# automatisch kleingeschrieben, siehe init-Migration ("CREATE SCHEMA IF NOT
# EXISTS krautUndRueben"). Daher hier ebenfalls unquoted verwenden.
SCHEMA = "krautUndRueben"
DATA_DIR = Path(__file__).resolve().parent

TABLES_IN_RESET_ORDER = [
    "rezept_zutat", "einkaufsauftrag", "bestellung_rezept", "bestellung_zutat",
    "bestellung", "zahlungsinfo", "adresse", "zutat_allergen",
    "zutat_ernaehrungskategorie", "rezept", "zutat", "lieferant",
    "kunde", "allergen", "ernaehrungskategorie", "bezugseinheit", "ort", "stadt",
]


def read_csv(filename):
    with open(DATA_DIR / filename, newline="", encoding="utf-8") as f:
        return list(csv.DictReader(f))


def dec(value):
    value = (value or "").strip()
    return Decimal(value) if value else None


def opt(value):
    value = (value or "").strip()
    return value if value else None


def boolean(value):
    return (value or "").strip().lower() in ("true", "1", "ja", "wahr")


def reset(cur):
    tables = ", ".join(TABLES_IN_RESET_ORDER)
    cur.execute(f"TRUNCATE TABLE {tables} RESTART IDENTITY CASCADE")


def populate_stadt(cur):
    ids = {}
    for row in read_csv("stadt.csv"):
        cur.execute(
            "INSERT INTO stadt (stadtname) VALUES (%s) RETURNING stadt_nr",
            (row["stadtname"],),
        )
        ids[row["stadtname"]] = cur.fetchone()[0]
    print(f"  stadt: {len(ids)} Zeilen")
    return ids


def populate_ort(cur, stadt_ids):
    rows = read_csv("ort.csv")
    for row in rows:
        cur.execute(
            "INSERT INTO ort (plz, stadt_nr) VALUES (%s, %s)",
            (row["plz"], stadt_ids[row["stadtname"]]),
        )
    print(f"  ort: {len(rows)} Zeilen")


def populate_bezugseinheit(cur):
    ids = {}
    for row in read_csv("bezugseinheit.csv"):
        cur.execute(
            "INSERT INTO bezugseinheit (einheit) VALUES (%s) RETURNING bezugseinheitnr",
            (row["einheit"],),
        )
        ids[row["einheit"]] = cur.fetchone()[0]
    print(f"  bezugseinheit: {len(ids)} Zeilen")
    return ids


def populate_ernaehrungskategorie(cur):
    ids = {}
    for row in read_csv("ernaehrungskategorie.csv"):
        cur.execute(
            "INSERT INTO ernaehrungskategorie (bezeichnung) VALUES (%s) "
            "RETURNING ernaehrungskategorienr",
            (row["bezeichnung"],),
        )
        ids[row["bezeichnung"]] = cur.fetchone()[0]
    print(f"  ernaehrungskategorie: {len(ids)} Zeilen")
    return ids


def populate_allergen(cur):
    ids = {}
    for row in read_csv("allergen.csv"):
        cur.execute(
            "INSERT INTO allergen (bezeichnung) VALUES (%s) RETURNING allergennr",
            (row["bezeichnung"],),
        )
        ids[row["bezeichnung"]] = cur.fetchone()[0]
    print(f"  allergen: {len(ids)} Zeilen")
    return ids


def populate_kunde(cur):
    ids = {}
    for row in read_csv("kunde.csv"):
        cur.execute(
            """INSERT INTO kunde (vorname, nachname, geburtsdatum, emailadresse, telefonnummer)
               VALUES (%s, %s, %s, %s, %s) RETURNING kundennr""",
            (row["vorname"], row["nachname"], opt(row["geburtsdatum"]),
             row["emailadresse"], opt(row["telefonnummer"])),
        )
        ids[row["emailadresse"]] = cur.fetchone()[0]
    print(f"  kunde: {len(ids)} Zeilen")
    return ids


def populate_lieferant(cur):
    ids = {}
    for row in read_csv("lieferant.csv"):
        cur.execute(
            """INSERT INTO lieferant (lieferantenname, email, telefon, strasse, hausnummer, plz)
               VALUES (%s, %s, %s, %s, %s, %s) RETURNING lieferantennr""",
            (row["lieferantenname"], opt(row["email"]), opt(row["telefon"]),
             row["strasse"], row["hausnummer"], row["plz"]),
        )
        ids[row["lieferantenname"]] = cur.fetchone()[0]
    print(f"  lieferant: {len(ids)} Zeilen")
    return ids


def populate_zutat(cur, lieferant_ids, einheit_ids):
    ids = {}
    for row in read_csv("zutat.csv"):
        cur.execute(
            """INSERT INTO zutat
                   (lieferantennr, bezeichnung, bestand, protein_g, kohlenhydrate_g, zucker_g,
                    fett_g, gesaett_fettsaeuren_g, ballaststoffe_g, kalorien_kcal, kalorien_kj,
                    natrium_g, bezugsmenge, bezugseinheitnr)
               VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
               RETURNING zutatennr""",
            (
                lieferant_ids[row["lieferantenname"]],
                row["bezeichnung"],
                dec(row["bestand"]),
                dec(row["protein_g"]),
                dec(row["kohlenhydrate_g"]),
                dec(row["zucker_g"]),
                dec(row["fett_g"]),
                dec(row["gesaett_fettsaeuren_g"]),
                dec(row["ballaststoffe_g"]),
                dec(row["kalorien_kcal"]),
                dec(row["kalorien_kj"]),
                dec(row["natrium_g"]),
                dec(row["bezugsmenge"]),
                einheit_ids[row["einheit"]],
            ),
        )
        ids[row["bezeichnung"]] = cur.fetchone()[0]
    print(f"  zutat: {len(ids)} Zeilen")
    return ids


def populate_rezept(cur):
    ids = {}
    for row in read_csv("rezept.csv"):
        cur.execute(
            """INSERT INTO rezept (bezeichnung, beschreibung, portionen, bild)
               VALUES (%s, %s, %s, %s) RETURNING rezeptnr""",
            (row["bezeichnung"], opt(row["beschreibung"]),
             int(row["portionen"]), opt(row["bild"])),
        )
        ids[row["bezeichnung"]] = cur.fetchone()[0]
    print(f"  rezept: {len(ids)} Zeilen")
    return ids


def populate_zutat_ernaehrungskategorie(cur, zutat_ids, kategorie_ids):
    rows = read_csv("zutat_ernaehrungskategorie.csv")
    for row in rows:
        cur.execute(
            "INSERT INTO zutat_ernaehrungskategorie (zutatennr, ernaehrungskategorienr) "
            "VALUES (%s, %s)",
            (zutat_ids[row["zutat"]], kategorie_ids[row["kategorie"]]),
        )
    print(f"  zutat_ernaehrungskategorie: {len(rows)} Zeilen")


def populate_zutat_allergen(cur, zutat_ids, allergen_ids):
    rows = read_csv("zutat_allergen.csv")
    for row in rows:
        cur.execute(
            "INSERT INTO zutat_allergen (zutatennr, allergennr) VALUES (%s, %s)",
            (zutat_ids[row["zutat"]], allergen_ids[row["allergen"]]),
        )
    print(f"  zutat_allergen: {len(rows)} Zeilen")


def populate_adresse(cur, kunde_ids):
    rows = read_csv("adresse.csv")
    for row in rows:
        cur.execute(
            """INSERT INTO adresse (strasse, hausnummer, plz, kundennr, is_rechnungsadresse)
               VALUES (%s, %s, %s, %s, %s)""",
            (row["strasse"], row["hausnummer"], row["plz"],
             kunde_ids[row["kunde_email"]], boolean(row["ist_rechnungsadresse"])),
        )
    print(f"  adresse: {len(rows)} Zeilen")


def populate_zahlungsinfo(cur, kunde_ids):
    rows = read_csv("zahlungsinfo.csv")
    for row in rows:
        cur.execute(
            "INSERT INTO zahlungsinfo (zahlungsart, kundennr) VALUES (%s, %s)",
            (row["zahlungsart"], kunde_ids[row["kunde_email"]]),
        )
    print(f"  zahlungsinfo: {len(rows)} Zeilen")


def populate_bestellung(cur, kunde_ids):
    ids = {}
    for row in read_csv("bestellung.csv"):
        cur.execute(
            """INSERT INTO bestellung (kundennr, bestelldatum, rechnungsbetrag)
               VALUES (%s, %s, %s) RETURNING bestellungsnr""",
            (kunde_ids[row["kunde_email"]], row["bestelldatum"],
             dec(row["rerechnungsbetrag"])),
        )
        ids[row["bestell_ref"]] = cur.fetchone()[0]
    print(f"  bestellung: {len(ids)} Zeilen")
    return ids


def populate_bestellung_zutat(cur, bestellung_ids, zutat_ids):
    rows = read_csv("bestellung_zutat.csv")
    for row in rows:
        cur.execute(
            "INSERT INTO bestellung_zutat (bestellungsnr, zutatnr, menge) VALUES (%s, %s, %s)",
            (bestellung_ids[row["bestell_ref"]], zutat_ids[row["zutat"]], dec(row["menge"])),
        )
    print(f"  bestellung_zutat: {len(rows)} Zeilen")


def populate_bestellung_rezept(cur, bestellung_ids, rezept_ids):
    rows = read_csv("bestellung_rezept.csv")
    for row in rows:
        cur.execute(
            "INSERT INTO bestellung_rezept (bestellungsnr, rezeptnr, menge) VALUES (%s, %s, %s)",
            (bestellung_ids[row["bestell_ref"]], rezept_ids[row["rezept"]], int(row["menge"])),
        )
    print(f"  bestellung_rezept: {len(rows)} Zeilen")


def populate_einkaufsauftrag(cur, lieferant_ids, zutat_ids):
    rows = read_csv("einkaufsauftrag.csv")
    for row in rows:
        cur.execute(
            """INSERT INTO einkaufsauftrag (lieferantennr, zutatennr, menge, nettopreis)
               VALUES (%s, %s, %s, %s)""",
            (lieferant_ids[row["lieferantenname"]], zutat_ids[row["zutat"]],
             dec(row["menge"]), dec(row["nettopreis"])),
        )
    print(f"  einkaufsauftrag: {len(rows)} Zeilen")


def populate_rezept_zutat(cur, rezept_ids, zutat_ids):
    rows = read_csv("rezept_zutat.csv")
    for row in rows:
        cur.execute(
            """INSERT INTO rezept_zutat (rezeptnr, zutatnr, menge, einheit)
               VALUES (%s, %s, %s, %s)""",
            (rezept_ids[row["rezept"]], zutat_ids[row["zutat"]],
             dec(row["menge"]), opt(row["einheit"])),
        )
    print(f"  rezept_zutat: {len(rows)} Zeilen")


def main():
    parser = argparse.ArgumentParser(description="Testdaten für krautUndRueben laden")
    parser.add_argument("--host", default=os.environ.get("PGHOST", "localhost"))
    parser.add_argument("--port", default=os.environ.get("PGPORT", "5432"))
    parser.add_argument("--dbname", default=os.environ.get("PGDATABASE", "krautundrueben"))
    parser.add_argument("--user", default=os.environ.get("PGUSER", "postgres"))
    parser.add_argument("--password", default=os.environ.get("PGPASSWORD", ""))
    parser.add_argument("--reset", action="store_true",
                         help="Alle Tabellen des Schemas vor dem Laden leeren")
    args = parser.parse_args()

    conn = psycopg2.connect(
        host=args.host, port=args.port, dbname=args.dbname,
        user=args.user, password=args.password,
    )
    conn.autocommit = False
    cur = conn.cursor()
    cur.execute(f"SET search_path TO {SCHEMA}")

    try:
        if args.reset:
            print("Setze Tabellen zurück ...")
            reset(cur)

        print("Lade Testdaten ...")
        stadt_ids = populate_stadt(cur)
        populate_ort(cur, stadt_ids)
        einheit_ids = populate_bezugseinheit(cur)
        kategorie_ids = populate_ernaehrungskategorie(cur)
        allergen_ids = populate_allergen(cur)
        kunde_ids = populate_kunde(cur)
        lieferant_ids = populate_lieferant(cur)
        zutat_ids = populate_zutat(cur, lieferant_ids, einheit_ids)
        rezept_ids = populate_rezept(cur)
        populate_zutat_ernaehrungskategorie(cur, zutat_ids, kategorie_ids)
        populate_zutat_allergen(cur, zutat_ids, allergen_ids)
        populate_adresse(cur, kunde_ids)
        populate_zahlungsinfo(cur, kunde_ids)
        bestellung_ids = populate_bestellung(cur, kunde_ids)
        populate_bestellung_zutat(cur, bestellung_ids, zutat_ids)
        populate_bestellung_rezept(cur, bestellung_ids, rezept_ids)
        populate_einkaufsauftrag(cur, lieferant_ids, zutat_ids)
        populate_rezept_zutat(cur, rezept_ids, zutat_ids)

        conn.commit()
        print("Fertig: Testdaten wurden erfolgreich geladen.")
    except Exception as exc:
        conn.rollback()
        print(f"Fehler beim Laden der Testdaten, Rollback durchgeführt: {exc}", file=sys.stderr)
        raise
    finally:
        cur.close()
        conn.close()


if __name__ == "__main__":
    main()
