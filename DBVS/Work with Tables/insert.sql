INSERT INTO mimi7678.Firma (Imones_kodas, Pavadinimas, Miestas, Gatve) VALUES
('5435476812', 'ROM', 'Klaipeda', 'Guolio g. 24'),
('2475618500', 'MUSONAS', 'Vilnius', 'Kalvariju g. 140A'),
('7468102548', 'RejBen', 'Vilnius', 'Seimyniskiu g. 21'),
('9214742604', 'IVO', 'Kaunas', 'Didzioji g. 120'),
('4981381282', 'Plunges baldai', 'Plunge', 'Vutauto g. 45'),
('8214159682', 'Lauskva', 'Sakiai', 'P. Vaiciacio g. 13'),
('3249826981', 'Alantas', 'Kaunas', 'Tiesioji g. 14');
('2214567812', 'Ikea', DEFAULT,'Didzioji g. 15');


INSERT INTO mimi7678.Preke (Prekes_kodas, Baldo_tipas, Kaina, Imones_kodas) VALUES
('1247856472', 'Odine sofa', 675.99, 			'9214742604'),
('1179514825', 'Dvigule lova', 749.59, 		'3249826981'),
('1358429295', 'Baro kede', 93.89, 			'2475618500'),
('1495221692', 'Prieskambario spinta', 250.79, '7468102548'),
('1388459745', 'Kompiuterine kede', 93.55, 	'2475618500'),
('1375952955', 'Rasomasis stalas', 110.59, 	'2475618500'),
('1247955952', 'Fotelis', 79.99, 				'9214742604'),
('1574569951', 'Sofa-lova', 647.00, 			'4981381282'),
('1547412581', 'Komoda', 96.49,				'4981381282'),
('1761251522', 'Kampine sofa', 678.00, 		'5435476812'),
('1387459525', 'Gultas', 300.00, 				'2475618500'),
('1874126969', 'Valgomasis stalas', 130.99, 	'8214159682'),
('1247852698', 'Miegamojo spintele', 68.49, 	'9214742604'),
('1144478526', 'Viengule lova', 549.00, 		'3249826981'),
('1479521848', 'Miegamojo spinta', 299.99, 	'7468102548'),
('1751589712', 'Vaikiska lovele', 79.99, 		'5435476812'),
('1175546468', 'Vaikiska kedute', 79.99, 		'8214159682');

INSERT INTO mimi7678.Pirkejas (Pirkejo_kodas, Asmens_kodas, Vardas, Pavarde, Telefonas, El_Pastas, Miestas, Adresas)VALUES
('6125746201', '37502040000','Jonas', 	'Petrauskas',	 '860267057', NULL, 'Vilnius', 'Kalvariju g. 130'),
('6019822822', '45812142965','Simona', 	'Savickiene',	 '861457125', 'simona@gmail.com','Kaunas', 'Vilniaus g. 79'),
('6015742821', '46802310254', 'Ligita', 	'Miliuviene',	 '861547856', NULL,'Druskininkai', 'Dzuku g. 14'),
('6148259744', '48712012547','Veronika', 'Simaityte',	 '864751015', NULL,'KLaipeda', 'Palangos g. 18'),
('6147852474', '39812012547','Petras', 	'Savickas',		 '864725014', 'p.savickas@gmail.com','Utena', 'Mazeikiu g. 25'),
('6125158171', '39801021242','Mykolas', 'Jankauskas',	 '862547935', 'mjankau@gmail.com','Vilnius', 'Jeruzales g. 79'),
('6014582075', '49812021462','Rima', 	'Simonaityte',	 '864175229', 'rimSim@gmail.com','Vilnius', 'Didzioji g. 10'),
('6014782055', '48903021245','Ona', 		'Adomaite',		 '867685049', 'onaDo@gmail.com','Kaunas', 'Sventoji g. 48'),
('6185247925', '37312120212','Romas', 	'Antanavicius',	 '867614252', NULL, 'Vilnius', 'Taikos g. 142');

INSERT INTO mimi7678.Uzsakymai (Uzsisakymo_data, Gavimo_data, Preke, Kiekis, Pirkejas) VALUES
('2021-11-25', '2021-12-07', '1358429295', 6, '6015742821'),
('2021-12-06', '2021-12-28', '1179514825', 1, '6019822822'),
(DEFAULT, 	 '2022-01-03', '1144478526', 1, '6014582075'),
('2021-12-10', '2021-12-15', '1387459525', 2, '6148259744'),
('2021-12-11', '2021-12-15', '1574569951', 1, '6148259744'),
('2021-11-17', '2021-12-17', '1874126969', 1, '6015742821'),
('2021-12-05', '2022-01-03', '1751589712', 1, '6147852474'),
('2021-12-06', '2022-01-03', '1175546468', 1, '6147852474'),
(DEFAULT, 	 '2022-01-04', '1247852698', 1, '6185247925');