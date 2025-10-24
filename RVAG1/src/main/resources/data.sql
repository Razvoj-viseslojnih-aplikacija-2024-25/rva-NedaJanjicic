-- =========================
-- Sekvence
-- =========================
CREATE SEQUENCE IF NOT EXISTS BIOSKOP_SEQ START 1;
CREATE SEQUENCE IF NOT EXISTS SALA_SEQ START 1;
CREATE SEQUENCE IF NOT EXISTS FILM_SEQ START 1;
CREATE SEQUENCE IF NOT EXISTS REZERVACIJA_SEQ START 1;

-- =========================
-- Tabele
-- =========================
CREATE TABLE IF NOT EXISTS bioskop (
    id INT PRIMARY KEY DEFAULT nextval('BIOSKOP_SEQ'),
    naziv VARCHAR(255),
    adresa VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS sala (
    id INT PRIMARY KEY DEFAULT nextval('SALA_SEQ'),
    kapacitet INT,
    br_redova INT,
    bioskop INT REFERENCES bioskop(id)
);

CREATE TABLE IF NOT EXISTS film (
    id INT PRIMARY KEY DEFAULT nextval('FILM_SEQ'),
    naziv VARCHAR(255),
    recenzija TEXT,
    trajanje INT,
    zanr VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS rezervacija (
    id INT PRIMARY KEY DEFAULT nextval('REZERVACIJA_SEQ'),
    br_osoba INT,
    cena_karte NUMERIC(10,2),
    datum TIMESTAMP,
    placeno BOOLEAN,
    film INT REFERENCES film(id),
    sala INT REFERENCES sala(id)
);

-- =========================
-- Primeri podataka
-- =========================

-- Bioskopi
INSERT INTO bioskop (id, naziv, adresa) VALUES (1, 'CineStar', 'Bulevar Kralja Aleksandra 73');
INSERT INTO bioskop (id, naziv, adresa) VALUES (2, 'Kino Balkan', 'Terazije 29');

-- Sale
INSERT INTO sala (id, kapacitet, broj_redova, bioskop) VALUES (1, 100, 10, 1);
INSERT INTO sala (id, kapacitet, broj_redova, bioskop) VALUES (2, 80, 8, 1);
INSERT INTO sala (id, kapacitet, broj_redova, bioskop) VALUES (3, 120, 12, 2);

-- Filmovi
INSERT INTO film (id, naziv, recenzija, trajanje, zanr) VALUES (1, 'Inception', 'Odličan film', 148, 'Sci-Fi');
INSERT INTO film (id, naziv, recenzija, trajanje, zanr) VALUES (2, 'Titanic', 'Romantična drama', 195, 'Drama');
INSERT INTO film (id, naziv, recenzija, trajanje, zanr) VALUES (3, 'Joker', 'Psihološki triler', 122, 'Thriller');

-- Rezervacije
INSERT INTO rezervacija (id, broj_osoba, cena_karti, datum, placeno, sala, film) 
VALUES (1, 2, 1200.00, TO_DATE('2025-10-15','YYYY-MM-DD'), true, 1, 1);

INSERT INTO rezervacija (id, broj_osoba, cena_karti, datum, placeno, sala, film) 
VALUES (2, 4, 2400.00, TO_DATE('2025-10-16','YYYY-MM-DD'), false, 2, 2);

INSERT INTO rezervacija (id, broj_osoba, cena_karti, datum, placeno, sala, film) 
VALUES (3, 1, 600.00, TO_DATE('2025-10-17','YYYY-MM-DD'), true, 3, 3);
