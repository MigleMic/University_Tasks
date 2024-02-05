/* psql -h pgsql2.mif -d studentu  */

/*
1. Kaina daugiau uz 0                   ++
2. Asmens kodas lygiai 11 skaitmenu
3. Uzsakymo data anksciau gavimo 
5. 1 triggeris
6. 2 triggeris
*/

-- 1. Pavyko
INSERT INTO mimi7678.Preke (Prekes_kodas, Baldo_tipas, Kaina, Imones_kodas) VALUES
('1547851254', 'Odinis kreslas', 0 ,'9214742604' );

-- 2. Pavyko 
INSERT INTO mimi7678.Pirkejas (Pirkejo_kodas, Asmens_kodas, Vardas, Pavarde, Telefonas, El_Pastas, Miestas, Adresas)VALUES
('6142587412', '00000001', 'Ona', 'Onute', '860578512', NULL, 'K', 'Tiesioji g. 15');

-- 3. Pavyko 
INSERT INTO mimi7678.Uzsakymai (Uzsisakymo_data, Gavimo_data, Preke, Kiekis, Pirkejas) VALUES
('2021-12-10', '2021-12-01', '1179514825', 1, '6185247925');

--4. Pavyko
INSERT INTO mimi7678.Uzsakymai (Uzsisakymo_data, Gavimo_data, Preke, Kiekis, Pirkejas) VALUES
('2021-12-13', '2022-01-03', '1179514825', 1, '6185247925');

-- 5. Pavyko
INSERT INTO mimi7678.Uzsakymai (Uzsisakymo_data, Gavimo_data, Preke, Kiekis, Pirkejas) VALUES
('2021-12-16', '2021-12-30', '1358429295', 8, '6125158171');

