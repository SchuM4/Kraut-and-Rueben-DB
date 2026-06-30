# Testdaten für das Schema „krautUndRueben“

Dieses Paket enthält deutschsprachige Testdaten für die in `init_migration.sql`
definierte Datenbankstruktur sowie ein Python-Skript, das sie lädt.

## Inhalt

- **18 CSV-Dateien** (eine pro Tabelle, in Ladereihenfolge unten) mit realistischen
  Testdaten: 10 Städte/19 PLZ, 8 Lieferanten, 39 Zutaten (inkl. Nährwerten je
  100 g/100 ml), 15 Rezepte, 18 Kunden, 25 Bestellungen samt Positionen sowie
  21 Einkaufsaufträge.
- **`populate_db.py`** – lädt alle CSV-Dateien in der richtigen Reihenfolge in die
  PostgreSQL-Datenbank.
- **`requirements.txt`** – einzige Abhängigkeit ist `psycopg2-binary`.

## Funktionsweise

Die `GENERATED ALWAYS AS IDENTITY`-Spalten (z. B. `stadt_nr`, `zutatennr`,
`bestellungsnr`, …) werden ausschließlich von PostgreSQL vergeben. Damit die CSV-
Dateien lesbar bleiben und unabhängig von der internen ID-Vergabe gepflegt werden
können, referenzieren sie verwandte Datensätze über sprechende Schlüssel statt über
Zahlen-IDs, zum Beispiel:

- `ort.csv` verweist per `stadtname` auf `stadt.csv`
- `zutat.csv` verweist per `lieferantenname` und `einheit` auf `lieferant.csv` bzw.
  `bezugseinheit.csv`
- `bestellung_zutat.csv` / `bestellung_rezept.csv` verweisen per `bestell_ref`
  (eine reine CSV-interne Kennung wie `B0001`, keine echte Spalte) auf die
  zugehörige Zeile in `bestellung.csv`

Das Skript löst diese Verweise beim Einfügen auf: Für jede „Stammdaten“-Tabelle
wird die von PostgreSQL per `RETURNING` zurückgegebene ID in einem Dictionary
gemerkt (`name -> id`) und bei nachgelagerten Tabellen wiederverwendet.

## Ladereihenfolge

`stadt → ort → bezugseinheit → ernaehrungskategorie → allergen → kunde →
lieferant → zutat → rezept → zutat_ernaehrungskategorie → zutat_allergen →
adresse → zahlungsinfo → bestellung → bestellung_zutat → bestellung_rezept →
einkaufsauftrag → rezept_zutat`

## Verwendung

```bash
pip install -r requirements.txt --break-system-packages

# Schema muss bereits existieren (init_migration.sql vorher ausführen)
python3 populate_db.py \
    --host localhost --port 5432 \
    --dbname krautAndRueben \
    --user krautAndRueben --password krautAndRueben
```

Alternativ über Umgebungsvariablen `PGHOST`, `PGPORT`, `PGDATABASE`, `PGUSER`,
`PGPASSWORD` statt CLI-Argumenten.

Mit `--reset` leert das Skript vorher alle 18 Tabellen
(`TRUNCATE ... RESTART IDENTITY CASCADE`), sodass es gefahrlos mehrfach
ausgeführt werden kann, ohne doppelte Daten oder Unique-Constraint-Fehler zu
erzeugen.

Das Skript wurde gegen eine echte PostgreSQL-16-Instanz mit der mitgelieferten
`init_migration.sql` getestet und läuft fehlerfrei durch (inkl. `--reset`).

## Hinweise zu den Daten

- Nährwertangaben in `zutat.csv` sind typische Referenzwerte je 100 g bzw.
  100 ml (branchenübliche Lebensmittel-Nährwerttabellen), unabhängig von der
  tatsächlichen `bezugsmenge`/`einheit` der jeweiligen Zutat. Da `natrium_g`,
  `gesaett_fettsaeuren_g` etc. als `NUMERIC(8,2)` definiert sind, rundet
  PostgreSQL beim Einfügen automatisch auf 2 Nachkommastellen (z. B. werden
  0,566 g Natrium zu 0,57 g) – das ist Schemaverhalten, kein Fehler im Skript.
- E-Mail-Adressen und Domains sind frei erfunden (Muster `vorname.nachname@example.de`).
- Alle Bezeichnungen (Zutaten, Rezepte, Lieferanten, Städte, Einheiten,
  Kategorien, Allergene) sind innerhalb ihrer jeweiligen CSV-Datei eindeutig,
  da sie als Lookup-Schlüssel dienen.
