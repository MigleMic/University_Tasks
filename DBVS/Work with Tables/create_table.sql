CREATE TABLE mimi7678.Firma (
	Imones_kodas CHAR(10) 	 NOT NULL,
	Pavadinimas  VARCHAR(20) NOT NULL, 
	Miestas		 VARCHAR(20) NOT NULL DEFAULT 'Vilnius',
	Gatve		 VARCHAR(20) NOT NULL,
	
	PRIMARY KEY(Imones_kodas)
);

CREATE TABLE mimi7678.Preke (
	Prekes_kodas CHAR(10) 	  NOT NULL,
	Baldo_tipas  CHAR(20) 	  NOT NULL,
	Kaina 		 DECIMAL(6,2) NOT NULL CHECK (Kaina > 0),
	Imones_kodas		 CHAR(10)	  NOT NULL,
	
	PRIMARY KEY(Prekes_kodas),
	FOREIGN KEY(Imones_kodas) REFERENCES mimi7678.Firma(Imones_kodas) 
	ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE mimi7678.Pirkejas (
	Pirkejo_kodas 	CHAR(10) 	NOT NULL,
	Asmens_kodas    VARCHAR(11) NOT NULL CONSTRAINT kodo_ilgis CHECK (char_length(Asmens_kodas)  = 11), 
	Vardas		  	VARCHAR(14) NOT NULL,
	Pavarde       	VARCHAR(20) NOT NULL,
	Telefonas	  	CHAR(9)     NOT NULL,
	El_Pastas 		VARCHAR(20),
	Miestas    	  	VARCHAR(20) NOT NULL,
	Adresas		  	VARCHAR(20) NOT NULL,
	
	PRIMARY KEY(Pirkejo_kodas)
);

CREATE TABLE mimi7678.Uzsakymai (
	Uzsakymo_numeris SERIAL   NOT NULL CONSTRAINT Uzsakymo_nr CHECK (Uzsakymo_numeris > 0),
	Uzsisakymo_data   DATE     NOT NULL DEFAULT CURRENT_DATE CONSTRAINT uzsakymo_data CHECK (Uzsisakymo_data < Gavimo_data),
	Gavimo_data		 DATE     NOT NULL CONSTRAINT pristatymas CHECK (Gavimo_data BETWEEN Uzsisakymo_data AND Uzsisakymo_data + INTERVAL '1 month'),
	Preke			 CHAR(10) NOT NULL,
	Kiekis			 INTEGER  NOT NULL,
	Pirkejas		 CHAR(10) NOT NULL,
	
	PRIMARY KEY(Uzsakymo_numeris),
	FOREIGN KEY(Pirkejas) REFERENCES mimi7678.Pirkejas(Pirkejo_kodas)
	ON DELETE CASCADE ON UPDATE RESTRICT 
);

