Migle Miceviciute, uzd variantai - 15,18,32,42

asmuo(migle, moteris, 21, skaitymas).
asmuo(eva, moteris, 25, skaitymas).
asmuo(andrius, vyras, 31, zaidimai).
asmuo(jolanta, moteris, 49, filmai).
asmuo(edgaras, vyras, 49, filmai).
asmuo(asta, moteris, 53, skaitymas).
asmuo(albinas, vyras, 76,  sodinimas).
asmuo(ceslava, moteris, 72, sodinimas).
asmuo(olga, moteris, 82, pokalbiai).
asmuo(julius, vyras, 85, pokalbiai).
asmuo(juozas, vyras, 65, filmai).
asmuo(valia, moteris, 63, pokalbiai).
asmuo(zydrunas, vyras, 40, vaiksciojimas).
asmuo(ala, moteris, 45, skaitymas).
asmuo(vlada, moteris, 62, skaitymas).
asmuo(arvydas, vyras, 62, filmai).
asmuo(paulius, vyras, 34, vaiksciojimas).
asmuo(kristina, moteris, 35, vaiksciojimas).
asmuo(juste, moteris, 37, vaiksciojimas).
asmuo(eimantas, vyras, 33, pokalbiai).
asmuo(sima, moteris, 6, filmai).
asmuo(gabriele, moteris, 3, filmai).
asmuo(ervinas, vyras, 30, skaitymas).
asmuo(simona, moteris, 28, vaiksciojimas).
asmuo(sandra, moteris, 26, vaiksciojimas).
asmuo(osvldas, vyras, 30, filmai).
asmuo(laura, moteris, 28, vaiksciojimas).
asmuo(viktorija, moteris, 21, pokalbiai).
asmuo(eimis, vyras, 26, filmai).
asmuo(ligita, moteris, 32, vaiksciojimas).
asmuo(nijole, moteris, 54, filmai).
asmuo(rolandas, vyras, 55, pokalbiai).
asmuo(jurate, moteris, 60, vaiksciojimas).
asmuo(arunas, vyras, 60, pokalbiai).

mama(jolanta, migle).
mama(jolanta, eva).

mama(nijole, osvaldas).
mama(nijole, laura).
mama(nijole, viktorija).
mama(nijole, eimis).
mama(nijole, ligita).

mama(jurate, ervinas).
mama(jurate, simona).
mama(jurate, sandra).

mama(olga, arunas).
mama(olga, rolandas).
mama(olga, edgaras).

mama(ceslava, asta).
mama(ceslava, jolanta).

mama(valia, zydrunas).
mama(vlada, paulius).
mama(vlada, eimantas).

mama(kristina, sima).
mama(kristina, gabriele).

pora(andrius, eva).
pora(edgaras, jolanta).
pora(rolandas, nijole).
pora(arunas, jurate).
pora(julius, olga).
pora(albinas, ceslava).
pora(zydrunas, ala).
pora(arvydas, vlada).
pora(paulius, kristina).
pora(eimantas, juste).

% 15. senele(Senele, AnukasAnuke) - Pirmasis asmuo yra antrojo
% mociute.
senele(Senele, AnukasAnuke) :- asmuo(Senele, moteris, _, _),  mama(Senele,Dukra), mama(Dukra, AnukasAnuke).
senele(Senele, AnukasAnuke) :- asmuo(Senele, moteris, _, _),
    mama(Senele, Sunus), pora(Sunus, Zmona), mama(Zmona, AnukasAnuke).

/*
 ?- senele(sima, vlada). - false (vlada yra simos senele).
 ?- senele(eva, ceslava).- false (ceslava yra evos senele).
 ?- senele(olga, ligita).- true.
 ?- senele(Senele, AnukasAnuke) - visos seneles ir ju anukai
 Senele = ceslava,AnukasAnuke = migle ;
Senele = ceslava,AnukasAnuke = eva ;
Senele = olga,AnukasAnuke = ervinas ;
Senele = olga,AnukasAnuke = simona ;
Senele = olga,AnukasAnuke = sandra ;
Senele = olga,AnukasAnuke = osvaldas ;
Senele = olga,AnukasAnuke = laura ;
Senele = olga,AnukasAnuke = viktorija ;
Senele = olga,AnukasAnuke = eimis ;
Senele = olga,AnukasAnuke = ligita ;
Senele = olga,AnukasAnuke = migle ;
Senele = olga,AnukasAnuke = eva ;
Senele = vlada,AnukasAnuke = sima ;
Senele = vlada,AnukasAnuke = gabriele ;
*/

%18. uosve(Uosve, Zentas) - Pirmasis asmuo yra antrojo uosve.
uosve(Uosve, Zentas) :- pora(Zentas, Zmona), mama(Uosve, Zmona).

/*
 uosve(ceslava, edgaras). -true
 uosve(ceslava, andrius). -false
 uosve(Uosve, Zentas) - visos uosves ir zentai
 Uosve = jolanta,Zentas = andrius ;
Uosve = ceslava,Zentas = edgaras ;
 */


% 32. sv_suk(Jubiliatas) - asmuo jubiliatas ka tik atsvente apvalia
% sukakti - 10kartotinis.
sv_suk(Jubiliatas) :- asmuo(Jubiliatas, _, Amzius,_), 0 is Amzius mod 10.

/*
 ?- sv_suk(migle). -false.
 ?- sv_suk(zydrunas). - true.
 ?- sv_suk(Jubiliatas). - visi jubiliatinikai
 Jubiliatas = zydrunas ;
Jubiliatas = ervinas ;
Jubiliatas = osvldas ;
Jubiliatas = jurate ;
Jubiliatas = arunas.
 */

% 42. gera_pora(Asmuo1, Asmuo2) - asmenys yra panasaus amziaus ir
% turi ta pati pomegi. Panasus amzius - max 2 metu skirtumas.
gera_pora(Asmuo1, Asmuo2) :-
    asmuo(Asmuo1, _, Amzius1, Pomegis),
    asmuo(Asmuo2, _, Amzius2, Pomegis),
    panasus_amzius(Amzius1, Amzius2),
    Asmuo1 \= Asmuo2.

panasus_amzius(Amzius1, Amzius2) :-
    Amzius1 - Amzius2 < 2,
    Amzius1 - Amzius2 > -2.

/*
 ?- gera_pora(andrius, eva). -false.
 ?- gera_pora(edgaras, jolanta). - true.
 */