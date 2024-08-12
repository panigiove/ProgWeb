CREATE TABLE LOCALITA (
    id_localita INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    localita VARCHAR(255) NOT NULL
);

CREATE TABLE CATEGORIA (
    id_categoria INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    categoria VARCHAR(255) NOT NULL
);

CREATE TABLE EVENTI (
    id_evento INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_categoria INT,
    id_localita INT,
    nome VARCHAR(255) NOT NULL,
    inizio TIMESTAMP NOT NULL,
    fine TIMESTAMP NOT NULL,
    totale_poltrona INT NOT NULL,
    disponibilita_poltrona INT NOT NULL,
    totale_in_piedi INT NOT NULL,
    disponibilita_in_piedi INT NOT NULL,
    prezzi_poltrona DECIMAL(10, 2) NOT NULL,
    prezzi_in_piedi DECIMAL(10, 2) NOT NULL,
    n_click INT DEFAULT 0,
    FOREIGN KEY (id_localita) REFERENCES LOCALITA(id_localita),
    FOREIGN KEY (id_categoria) REFERENCES CATEGORIA(id_categoria)
);

CREATE TABLE SCONTI_EVENTO (
    id_sconto INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_evento INT,
    data_scadenza DATE NOT NULL,
    sconto DECIMAL(5, 2) NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES EVENTI(id_evento)
);

CREATE TABLE STATISTICHE_CATEGORIA (
    id_categoria INT PRIMARY KEY,
    n_click INT DEFAULT 0,
    FOREIGN KEY (id_categoria) REFERENCES CATEGORIA(id_categoria)
);

CREATE TABLE UTENTI (
    id_utente INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono CHAR(10) NOT NULL,
    n_acquisti INT DEFAULT 0,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE PRENOTAZIONE_BIGLIETTI (
    id_prenotazione INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_evento INT NOT NULL,
    id_utente INT NOT NULL,
    id_sconto INT,
    quantita INT DEFAULT 1,
    tipologia VARCHAR(10) CHECK (tipologia IN ('poltrona', 'in piedi')) NOT NULL,
    data_acquisto TIMESTAMP NOT NULL,
    prezzo DECIMAL (10, 2) NOT NULL,
    FOREIGN KEY (id_evento) REFERENCES EVENTI(id_evento) ON DELETE CASCADE,
    FOREIGN KEY (id_utente) REFERENCES UTENTI(id_utente) ON DELETE CASCADE,
    FOREIGN KEY (id_sconto) REFERENCES SCONTI_EVENTO(id_sconto) ON DELETE CASCADE
);

CREATE TABLE AMMINISTRATORI (
    id_amministratore INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO LOCALITA (localita) VALUES ('Trento');
INSERT INTO LOCALITA (localita) VALUES ('Povo');
INSERT INTO LOCALITA (localita) VALUES ('Rovereto');
INSERT INTO LOCALITA (localita) VALUES ('Riva del Garda');
INSERT INTO LOCALITA (localita) VALUES ('Arco');

INSERT INTO CATEGORIA (categoria) VALUES ('concerti');
INSERT INTO CATEGORIA (categoria) VALUES ('spettacoli teatrali');
INSERT INTO CATEGORIA (categoria) VALUES ('eventi sportivi');
INSERT INTO CATEGORIA (categoria) VALUES ('visite guidate a mostre e musei');

INSERT INTO EVENTI (
    id_categoria,
    id_localita,
    nome,
    inizio,
    fine,
    totale_poltrona,
    disponibilita_poltrona,
    totale_in_piedi,
    disponibilita_in_piedi,
    prezzi_poltrona,
    prezzi_in_piedi,
    n_click
) VALUES (
    1, -- id_categoria (concerti)
    1, -- id_localita (Trento)
    'Concerto di Rock',
    '2024-08-10 21:00:00',
    '2024-08-10 23:00:00',
    100, -- totale_poltrona
    50,  -- disponibilità_poltrona
    200, -- totale_in_piedi
    150, -- disponibilità_in_piedi
    30.00, -- prezzi_poltrona
    15.00,  -- prezzi_in_piedi
    100 -- n_click
);

INSERT INTO EVENTI (
    id_categoria,
    id_localita,
    nome,
    inizio,
    fine,
    totale_poltrona,
    disponibilita_poltrona,
    totale_in_piedi,
    disponibilita_in_piedi,
    prezzi_poltrona,
    prezzi_in_piedi,
    n_click
) VALUES (
    2, -- id_categoria (spettacoli teatrali)
    2, -- id_localita (Povo)
    'Spettacolo Teatrale',
    '2024-09-05 20:00:00',
    '2024-09-05 22:00:00',
    80,  -- totale_poltrona
    30,  -- disponibilità_poltrona
    100, -- totale_in_piedi
    80,  -- disponibilità_in_piedi
    25.00, -- prezzi_poltrona
    10.00,  -- prezzi_in_piedi
    200 -- n_click
);

INSERT INTO SCONTI_EVENTO (
    id_evento,
    data_scadenza,
    sconto
) VALUES (
    1, -- id_evento (Concerto di Rock)
    '2024-08-09',
    10.00 -- sconto (10%)
);

INSERT INTO SCONTI_EVENTO (
    id_evento,
    data_scadenza,
    sconto
) VALUES (
    2, -- id_evento (Spettacolo Teatrale)
    '2024-09-04',
    15.00 -- sconto (15%)
);

INSERT INTO STATISTICHE_CATEGORIA (
    id_categoria,
    n_click
) VALUES (
    1, -- id_categoria (concerti)
    200
);

INSERT INTO STATISTICHE_CATEGORIA (
    id_categoria,
    n_click
) VALUES (
    2, -- id_categoria (spettacoli teatrali)
    120
);

INSERT INTO UTENTI (
    nome,
    cognome,
    data_nascita,
    email,
    telefono,
    n_acquisti,
    username,
    password
) VALUES (
    'Mario',
    'Rossi',
    '1990-01-15',
    'mario.rossi@example.com',
    '3456789012',
    5,
    'mrossi',
    'password123'
);

INSERT INTO UTENTI (
    nome,
    cognome,
    data_nascita,
    email,
    telefono,
    n_acquisti,
    username,
    password
) VALUES (
    'Giulia',
    'Bianchi',
    '1985-03-22',
    'giulia.bianchi@example.com',
    '3216549870',
    9,
    'gbianchi',
    'securepass456'
);


-- Per il Concerto di Rock, prezzo per poltrona è 30.00
INSERT INTO PRENOTAZIONE_BIGLIETTI (
    id_evento,
    id_utente,
    id_sconto,
    quantita,
    tipologia,
    data_acquisto,
    prezzo
) VALUES (
    1, -- id_evento (Concerto di Rock)
    1, -- id_utente (Mario Rossi)
    1, -- id_sconto (10% sconto)
    2, -- quantita
    'poltrona',
    '2024-08-01 10:30:00',
    30.00 * 2 * (1 - 0.10) -- Prezzo dopo sconto (10% su 30.00 per 2 poltrone)
);

-- Per lo Spettacolo Teatrale, prezzo per in piedi è 10.00
INSERT INTO PRENOTAZIONE_BIGLIETTI (
    id_evento,
    id_utente,
    id_sconto,
    quantita,
    tipologia,
    data_acquisto,
    prezzo
) VALUES (
    2, -- id_evento (Spettacolo Teatrale)
    2, -- id_utente (Giulia Bianchi)
    2, -- id_sconto (15% sconto)
    1, -- quantita
    'in piedi',
    '2024-08-02 11:00:00',
    10.00 * (1 - 0.15) -- Prezzo dopo sconto (15% su 10.00)
);

INSERT INTO AMMINISTRATORI (
    username,
    password
) VALUES (
    'admin',
    '01nimda!'
);
