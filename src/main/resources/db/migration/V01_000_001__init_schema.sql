CREATE SCHEMA IF NOT EXISTS krautUndRueben;

SET
search_path TO krautUndRueben;

-- Independent Lookup Tables

CREATE TABLE stadt
(
    stadt_nr  INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    stadtname VARCHAR(255) NOT NULL
);

CREATE TABLE ort
(
    plz      VARCHAR(10) PRIMARY KEY,
    stadt_nr INTEGER NOT NULL REFERENCES stadt (stadt_nr)
);

CREATE TABLE bezugseinheit
(
    bezugseinheitnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    einheit         VARCHAR(20) NOT NULL
);

CREATE TABLE ernaehrungskategorie
(
    ernaehrungskategorienr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bezeichnung            VARCHAR(100) NOT NULL
);

CREATE TABLE allergen
(
    allergenenr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bezeichnung VARCHAR(100) NOT NULL
);

CREATE TABLE kunde
(
    kundennr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vorname VARCHAR(100) NOT NULL,
    nachname VARCHAR(150) NOT NULL,
    geburtsdatum DATE,
    emailadresse VARCHAR(255) NOT NULL UNIQUE,
    telefonnummer VARCHAR(30)
);

-- Supplier side

CREATE TABLE lieferant
(
    lieferantennr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lieferantenname VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    telefon VARCHAR(30),
    strasse VARCHAR(100) NOT NULL,
    hausnummer VARCHAR(10) NOT NULL,
    plz VARCHAR(10) NOT NULL REFERENCES ort(plz)
);

-- Ingredients/Recipes

CREATE TABLE zutat
(
    zutatennr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lieferantennr INTEGER NOT NULL REFERENCES lieferant(lieferantennr),
    bezeichnung VARCHAR(255) NOT NULL,
    bestand NUMERIC(10,2) NOT NULL DEFAULT 0,
    protein_g             NUMERIC(8, 2),
    kohlenhydrate_g       NUMERIC(8, 2),
    zucker_g              NUMERIC(8, 2),
    fett_g                NUMERIC(8, 2),
    gesaett_fettsaeuren_g NUMERIC(8, 2),
    ballaststoffe_g       NUMERIC(8, 2),
    kalorien_kcal         NUMERIC(8, 2),
    kalorien_kj           NUMERIC(8, 2),
    natrium_g             NUMERIC(8, 2),
    bezugsmenge           NUMERIC(8, 2),
    bezugseinheitnr       INTEGER NOT NULL REFERENCES bezugseinheit (bezugseinheitnr)
);

CREATE TABLE rezept
(
    rezeptnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bezeichnung VARCHAR(150) NOT NULL,
    beschreibung TEXT,
    portionen INTEGER NOT NULL DEFAULT 1,
    bild VARCHAR(255)
);

CREATE TABLE zutat_ernaehrungskategorie
(
    zutatennr INTEGER NOT NULL REFERENCES zutat(zutatennr),
    ernaehrungskategorienr INTEGER NOT NULL REFERENCES ernaehrungskategorie(ernaehrungskategorienr),
    PRIMARY KEY(zutatennr, ernaehrungskategorienr)
);

CREATE TABLE zutat_allergen
(
    zutatennr INTEGER NOT NULL REFERENCES zutat(zutatennr),
    allergennr INTEGER NOT NULL REFERENCES allergen(allergenenr),
    PRIMARY KEY(zutatennr, allergennr)
);

CREATE TABLE adresse
(
    lieferadressenr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    strasse VARCHAR(100) NOT NULL,
    hausnummer VARCHAR(10) NOT NULL,
    plz VARCHAR(10) NOT NULL REFERENCES ort(plz),
    kundennr INTEGER NOT NULL REFERENCES kunde(kundennr) ON DELETE CASCADE,
    is_rechnungsadresse BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE zahlungsinfo
(
    zahlungsinfonr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    zahlungsart VARCHAR(100) NOT NULL,
    kundennr INTEGER NOT NULL REFERENCES kunde(kundennr) ON DELETE CASCADE
);

-- Orders/Pyments

CREATE TABLE bestellung
(
    bestellungsnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    kundennr INTEGER NOT NULL REFERENCES kunde(kundennr),
    bestelldatum DATE NOT NULL DEFAULT CURRENT_DATE,
    rerechnungsbetrag NUMERIC(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE bestellung_zutat
(
    bestellungsnr INTEGER NOT NULL REFERENCES bestellung(bestellungsnr) ON DELETE CASCADE,
    zutatnr INTEGER NOT NULL REFERENCES zutat(zutatennr),
    menge NUMERIC(10,2) NOT NULL,
    PRIMARY KEY (bestellungsnr, zutatnr)
);

CREATE TABLE bestellung_rezept
(
    bestellungsnr INTEGER NOT NULL REFERENCES bestellung(bestellungsnr) ON DELETE CASCADE,
    rezeptnr INTEGER NOT NULL REFERENCES rezept(rezeptnr),
    menge INTEGER NOT NULL,
    PRIMARY KEY (bestellungsnr, rezeptnr)
);

-- Purchasing

CREATE TABLE einkaufsauftrag
(
    einkaufsauftragnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lieferantennr INTEGER NOT NULL REFERENCES lieferant(lieferantennr),
    zutatennr INTEGER NOT NULL REFERENCES zutat(zutatennr),
    menge NUMERIC(10,2) NOT NULL,
    nettopreis NUMERIC(10,2) NOT NULL
);

-- Customers

CREATE TABLE rezept_zutat
(
    rezeptnr INTEGER NOT NULL REFERENCES rezept(rezeptnr),
    zutatnr INTEGER NOT NULL REFERENCES zutat(zutatennr),
    menge NUMERIC(10,2) NOT NULL,
    einheit VARCHAR(20),
    PRIMARY KEY(rezeptnr, zutatnr)
);

CREATE INDEX idx_ort_stadtnr ON ort(stadt_nr);
CREATE INDEX idx_lieferant_plz ON lieferant(plz);
CREATE INDEX idx_zutat_lieferantennr ON zutat(lieferantennr);
CREATE INDEX idx_zutat_bezugseinheitnr ON zutat(bezugseinheitnr);
CREATE INDEX idx_zutat_ernaehrungskategorie_kategorienr ON zutat_ernaehrungskategorie(ernaehrungskategorienr);
CREATE INDEX idx_zutat_allergen_allergennr ON zutat_allergen(allergennr);
CREATE INDEX idx_adresse_plz ON adresse(plz);
CREATE INDEX idx_adresse_kundennr ON adresse(kundennr);
CREATE INDEX idx_zahlungsinfo_kundennr ON zahlungsinfo(kundennr);
CREATE INDEX idx_bestellung_kundennr ON bestellung(kundennr);
CREATE INDEX idx_bestellung_zutat_zutatnr ON bestellung_zutat(zutatnr);
CREATE INDEX idx_bestellung_rezept_rezeptnr ON bestellung_rezept(rezeptnr);
CREATE INDEX idx_einkaufsauftrag_lieferantennr ON einkaufsauftrag(lieferantennr);
CREATE INDEX idx_einkaufsauftrag_zutatennr ON einkaufsauftrag(zutatennr);
CREATE INDEX idx_rezept_zutat_zutatnr ON rezept_zutat(zutatnr);
CREATE INDEX idx_kunde_nachname ON kunde(nachname);

COMMIT;
