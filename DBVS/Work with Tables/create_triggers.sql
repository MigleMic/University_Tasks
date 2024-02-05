CREATE FUNCTION mimi7678.UzsakymuTikrinimas() RETURNS TRIGGER AS $$ 
BEGIN 
	IF
		((SELECT COUNT(*) 
		FROM mimi7678.Uzsakymai
		WHERE mimi7678.Uzsakymai.Pirkejas = NEW.Pirkejas
		AND Uzsakymai.Uzsisakymo_data = NEW.Uzsisakymo_data) >= 1)
		THEN 
			RAISE EXCEPTION 'Dienos uzsakymu limitas pirkejui ne daugiau kaip 1';
	END IF;
	RETURN NEW;		
END; $$  
LANGUAGE plpgsql;

CREATE TRIGGER UzsakymuLimitas
	BEFORE INSERT ON mimi7678.Uzsakymai
	FOR EACH ROW 
	EXECUTE PROCEDURE UzsakymuTikrinimas();

CREATE FUNCTION mimi7678.BalduTikrinimas() RETURNS TRIGGER AS $$
BEGIN
   IF
	((SELECT SUM(kiekis) 
	FROM mimi7678.Uzsakymai
	WHERE mimi7678.Uzsakymai.Preke = NEW.Preke
	AND NEW.Kiekis >= 6
	GROUP BY mimi7678.Uzsakymai.Preke) >= 6)
   THEN
	RAISE EXCEPTION 'Negali buti parduota daugiau nei 6 vieno tipo baldai';
	END IF;
	RETURN NEW;
END; $$ 
LANGUAGE plpgsql;

CREATE TRIGGER BalduLimitas
	BEFORE INSERT ON mimi7678.uzsakymai
	FOR EACH ROW
	EXECUTE PROCEDURE mimi7678.BalduTikrinimas();

