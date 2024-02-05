CREATE MATERIALIZED VIEW mimi7678.PirkinioKaina(Uzsakymo_numeris, Pelnas) 
AS 
	SELECT Uzsakymai.Uzsakymo_numeris, Uzsakymai.Kiekis * Preke.Kaina AS Pelnas
	FROM mimi7678.Uzsakymai, mimi7678.Preke
	WHERE Preke.Prekes_kodas = Uzsakymai.Preke;
	
REFRESH MATERIALIZED VIEW PirkinioKaina;

CREATE VIEW mimi7678.KlientoIslaidos(Pirkejas, Islaidos)
AS
	SELECT Pirkejas.Pirkejo_kodas, SUM(PirkinioKaina.Pelnas) AS Islaidos 
	FROM mimi7678.Pirkejas, mimi7678.PirkinioKaina, mimi7678.Uzsakymai
	WHERE Pirkejas.Pirkejo_kodas = Uzsakymai.Pirkejas AND Uzsakymai.Uzsakymo_numeris = PirkinioKaina.Uzsakymo_numeris
	GROUP BY Pirkejas.Pirkejo_kodas;          

CREATE VIEW mimi7678.SiandienosUzsakymai(Uzsakymas, Pirkejas)
AS
	SELECT Uzsakymo_numeris, Pirkejas
	FROM mimi7678.Uzsakymai
	WHERE Uzsisakymo_data = CURRENT_DATE;
