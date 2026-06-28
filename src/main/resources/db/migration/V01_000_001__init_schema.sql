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

-- CREATE TABLE bezugseinheit
-- (
--     bezugseinheitnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--     einheit         VARCHAR(20) NOT NULL
-- );

CREATE TABLE naehrstoffangaben
(
    naehrstoffnr          INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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
    bezugseinheit         VARCHAR(20) NOT NULL
--     bezugseinheitnr       INTEGER NOT NULL REFERENCES bezugseinheit (bezugseinheitnr)
);

-- CREATE TABLE ernaehrungskategorie
-- (
--     ernaehrungskategorienr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--     bezeichnung            VARCHAR(100) NOT NULL
-- );

-- CREATE TABLE allergene
-- (
--     allergenenr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--     bezeichnung VARCHAR(100) NOT NULL
-- );

-- Supplier side

CREATE TABLE lieferantenkontaktinfo
(
    lieferantenkontaktinfonr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255),
    telefon VARCHAR(30),
    strasse VARCHAR(100) NOT NULL,
    hausnummer VARCHAR(10) NOT NULL,
    plz VARCHAR(10) NOT NULL REFERENCES ort(plz)
);

CREATE TABLE lieferant
(
    lieferantennr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lieferantenname VARCHAR(255) NOT NULL,
    lieferantenkontaktinfonr INTEGER UNIQUE NOT NULL REFERENCES lieferantenkontaktinfo(lieferantenkontaktinfonr)
);

-- Ingredients/Recipes

CREATE TABLE zutat
(
    zutatennr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lieferantennr INTEGER NOT NULL REFERENCES lieferant(lieferantennr),
    bezeichnung VARCHAR(255) NOT NULL,
    bestand NUMERIC(10,2) NOT NULL DEFAULT 0,
    ernaehrungskategorie VARCHAR(50),
    allergen VARCHAR(50)
--     ernaehrungskategorienr INTEGER REFERENCES ernaehrungskategorie(ernaehrungskategorienr),
--     allergenenr INTEGER REFERENCES allergene(allergenenr),
    naehrstoffnr INTEGER UNIQUE REFERENCES naehrstoffangaben(naehrstoffnr)
);

CREATE TABLE rezept
(
    rezeptnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    bezeichnung VARCHAR(150) NOT NULL,
    beschreibung TEXT,
    portionen INTEGER NOT NULL DEFAULT 1,
    ernaehrungskategorienr INTEGER REFERENCES ernaehrungskategorie(ernaehrungskategorienr),
    allergenenr INTEGER REFERENCES allergene(allergenenr),
    naehrstoffnr INTEGER UNIQUE REFERENCES naehrstoffangaben(naehrstoffnr)
);

CREATE TABLE rezept_zutat
(
    rezeptnr INTEGER NOT NULL REFERENCES rezept(rezeptnr),
    zutatnr INTEGER NOT NULL REFERENCES zutat(zutatennr),
    menge NUMERIC(10,2) NOT NULL,
    einheit VARCHAR(20),
    PRIMARY KEY(rezeptnr, zutatnr)
);

-- Customers

CREATE TABLE kunde
(
    kundennr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    vorname VARCHAR(100) NOT NULL,
    nachname VARCHAR(150) NOT NULL,
    geburtsdatum DATE,
    emailadresse VARCHAR(255) NOT NULL UNIQUE,
    telefonnummer VARCHAR(30)
);

CREATE TABLE lieferadresse
(
    lieferadressenr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    strasse VARCHAR(100) NOT NULL,
    hausnummer VARCHAR(10) NOT NULL,
    plz VARCHAR(10) NOT NULL REFERENCES ort(plz),
    kundennr INTEGER NOT NULL REFERENCES kunde(kundennr) ON DELETE CASCADE
);

CREATE TABLE rechnungsadresse
(
    rechnungsadressenr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    strasse VARCHAR(100) NOT NULL,
    hausnummer VARCHAR(10) NOT NULL,
    plz VARCHAR(10) NOT NULL REFERENCES ort(plz),
    kundennr INTEGER UNIQUE NOT NULL REFERENCES kunde(kundennr) ON DELETE CASCADE
);

-- Orders/Pyments

CREATE TABLE bestellung
(
    bestellungsnr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    kundennr INTEGER NOT NULL REFERENCES kunde(kundennr),
    bestelldatum DATE NOT NULL DEFAULT CURRENT_DATE,
    rechnungsbetrag NUMERIC(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE zahlungsinfo
(
    zahlungsinfonr INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    zahlungsart VARCHAR(100) NOT NULL,
    zahlungsstatus VARCHAR(100) NOT NULL,
    zahlungsdatum DATE,
    bestellungsnr INTEGER NOT NULL REFERENCES bestellung(bestellungsnr) ON DELETE CASCADE
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

CREATE INDEX idx_ort_stadtnr ON ort(stadt_nr);
CREATE INDEX idx_lieferantenkontaktinfo_plz ON lieferantenkontaktinfo(plz);
-- CREATE INDEX idx_lieferant_kontaktinfonr ON lieferant(lieferantenkontaktinfonr);
CREATE INDEX idx_zutat_lieferantennr ON zutat(lieferantennr);
-- CREATE INDEX idx_zutat_ernaehrungskategorienr ON zutat(ernaehrungskategorienr);
-- CREATE INDEX idx_zutat_allergenenr ON zutat(allergenenr);
CREATE INDEX idx_zutat_naehrstoffnr ON zutat(naehrstoffnr);
-- CREATE INDEX idx_rezepte_ernaehrungskategorienr ON rezept(ernaehrungskategorienr);
-- CREATE INDEX idx_rezepte_allergenenr ON rezept(allergenenr);
CREATE INDEX idx_rezepte_naehrstoffnr ON rezept(naehrstoffnr);
CREATE INDEX idx_rezeptzutat_rezeptnr ON rezept(rezeptnr);
CREATE INDEX idx_rezeptzutat_zutatnr ON rezept_zutat(zutatnr);
CREATE INDEX idx_lieferadresse_kundennr ON lieferadresse(kundennr);
CREATE INDEX idx_lieferadresse_plz ON lieferadresse(plz);
CREATE INDEX idx_rechnungsadresse_kundennr ON rechnungsadresse(kundennr);
CREATE INDEX idx_rechnungsadresse_plz ON rechnungsadresse(plz);
CREATE INDEX idx_bestellung_kundennr ON bestellung(kundennr);
CREATE INDEX idx_zahlungsinfo_bestellungsnr ON zahlungsinfo(bestellungsnr);
CREATE INDEX idx_bestellung_zutat_zutatnr ON bestellung_zutat(zutatnr);
CREATE INDEX idx_bestellung_rezept_rezeptnr ON bestellung_rezept(rezeptnr);
CREATE INDEX idx_einkaufsauftrag_lieferantennr ON einkaufsauftrag(lieferantennr);
CREATE INDEX idx_einkaufsauftrag_zutatennr ON einkaufsauftrag(zutatennr);
-- CREATE INDEX idx_naehrstoffangaben_bezugseinheitnr ON naehrstoffangaben(bezugseinheitnr);

COMMIT;
