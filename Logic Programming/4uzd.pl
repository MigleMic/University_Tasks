% Migle Miceviciute
% Programu sistemos 3 kursas 3 grupe
% 1.1 uzd.

% Ieitis
perpylimas(Talpa1, Talpa2, Talpa3, NorimasKiekis) :-
    (   Talpa1 =< 0;
        Talpa2 =< 0;
        Talpa3 =< 0;
        NorimasKiekis < 0),
    format("Indo talpa negali buti <=0 ir/arba norimas kiekis negali buti neigiamas"), !.

perpylimas(Talpa1, Talpa2, Talpa3, _) :-
    Talpa1 = Talpa2,
    Talpa1 = Talpa3,
    Talpa2 = Talpa3,
    format("Indu talpos negali buti vienodos!"),!.

perpylimas(Talpa1, _, _, NorimasKiekis):-
    Talpa1 < NorimasKiekis,
    format("Norimas vandens kiekis negali buti didesnis uz indo talpa"), !.

perpylimas(Talpa1, Talpa2, Talpa3, NorimasKiekis):-
    Skirtumas is Talpa1 - NorimasKiekis,
    LikusiTalpa = Talpa2 + Talpa3,
    Skirtumas > LikusiTalpa,
    format("Neimanomas vandens kiekis, nes kituose induose per maza talpa"), !.

%Jei visi duomenys tinkami, vykdome
perpylimas(Talpa1, Talpa2, Talpa3, NorimasKiekis):-
    perpylimas(indai(Talpa1, Talpa2, Talpa3),
                     NorimasKiekis, pylimas(Talpa1,0,0),
                     _, Atsakymas),
    atspausdintiAtsakyma(Atsakymas).

perpylimas(Indai, NorimasKiekis, Perpylimas,_,Busenos) :-
    vykdytiPerpylima(Indai,NorimasKiekis, Perpylimas,[Perpylimas], Busenos).


%Paieska. ats - perpylimu seka
vykdytiPerpylima(_, NorimasKiekis, pylimas(NorimasKiekis, _, _), Perpylimai, Perpylimai).

vykdytiPerpylima(Indai, NorimasKiekis, Perpylimas, Perpylimai, Atsakymas):-
    pilstymas(Indai, Perpylimas, NaujasPerpylimas),
    not(member(NaujasPerpylimas, Perpylimai)),
    vykdytiPerpylima(Indai, NorimasKiekis, NaujasPerpylimas,[NaujasPerpylimas|Perpylimai], Atsakymas).

% Is pirmo indo i antra pilam kiek telpa
pilstymas(indai(Talpa1, Talpa2, _), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(NaujasKiekis, Talpa2,Kiekis3)):-
    Kiekis1 > 0,
    Kiekis2 < Talpa2,
    NaujasKiekis is Kiekis1 + Kiekis2 - Talpa2,
    NaujasKiekis > 0,
    NaujasKiekis =< Talpa1.

% Is pirmo i trecia kiek telpa
pilstymas(indai(Talpa1, _, Talpa3), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(NaujasKiekis, Kiekis2, Talpa3)):-
    Kiekis1 > 0,
    Kiekis3 < Talpa3,
    NaujasKiekis is Kiekis1 + Kiekis3 - Talpa3,
    NaujasKiekis > 0,
    NaujasKiekis =< Talpa1.

% Is antro i pirma kiek telpa
pilstymas(indai(Talpa1, Talpa2, _), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(Talpa1, NaujasKiekis,Kiekis3)):-
    Kiekis2 > 0,
    Kiekis1 < Talpa1,
    NaujasKiekis is Kiekis2 + Kiekis1 - Talpa1,
    NaujasKiekis > 0,
    NaujasKiekis =< Talpa2.

% Is antro i trecia kiek telpa
pilstymas(indai(_, Talpa2, Talpa3), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(Kiekis1, NaujasKiekis,Talpa3)):-
    Kiekis2 > 0,
    Kiekis3 < Talpa3,
    NaujasKiekis is Kiekis2 + Kiekis3 - Talpa3,
    NaujasKiekis > 0,
    NaujasKiekis =< Talpa2.

% Is trecio i pirma kiek telpa
pilstymas(indai(Talpa1, _, Talpa3), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(Talpa1, Kiekis2,NaujasKiekis)):-
    Kiekis3 > 0,
    Kiekis1 < Talpa1,
    NaujasKiekis is Kiekis3 + Kiekis1 - Talpa1,
    NaujasKiekis > 0,
    NaujasKiekis =< Talpa3.

% Is trecio i antra kiek telpa
pilstymas(indai(_, Talpa2, Talpa3), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(Kiekis1, Talpa2,NaujasKiekis)):-
    Kiekis3 > 0,
    Kiekis2 < Talpa2,
    NaujasKiekis is Kiekis3 + Kiekis2 - Talpa2,
    NaujasKiekis > 0,
    NaujasKiekis =< Talpa3.

% Is pirmo i antra, kad pirmas tuscias butu
pilstymas(indai(_, Talpa2, _), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(0, NaujasKiekis, Kiekis3)):-
    Kiekis1 > 0,
    NaujasKiekis is Kiekis1 + Kiekis2,
    NaujasKiekis =< Talpa2.

% Is pirmo i trecia, kad pirmas tuscias butu
pilstymas(indai(_, _, Talpa3), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(0, Kiekis2, NaujasKiekis)):-
    Kiekis1 > 0,
    NaujasKiekis is Kiekis1 + Kiekis3,
    NaujasKiekis =< Talpa3.

% Is antro i pirma, kad antras tuscias butu
pilstymas(indai(Talpa1, _, _), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(NaujasKiekis, 0, Kiekis3)):-
    Kiekis2 > 0,
    NaujasKiekis is Kiekis2 + Kiekis1,
    NaujasKiekis =< Talpa1.

% Is antro i trecia, kad antras tuscias butu
pilstymas(indai(_, _, Talpa3), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(Kiekis1, 0, NaujasKiekis)):-
    Kiekis2 > 0,
    NaujasKiekis is Kiekis2 + Kiekis3,
    NaujasKiekis =< Talpa3.

% Is trecio i pirma, kad trecias tuscias butu
pilstymas(indai(Talpa1, _, _), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(NaujasKiekis, Kiekis2, 0)):-
    Kiekis3 > 0,
    NaujasKiekis is Kiekis3 + Kiekis1,
    NaujasKiekis =< Talpa1.

% Is trecio i antra, kad trecias tuscias butu
pilstymas(indai(_, Talpa2, _), pylimas(Kiekis1, Kiekis2, Kiekis3), pylimas(Kiekis1, NaujasKiekis, 0)):-
    Kiekis3 > 0,
    NaujasKiekis is Kiekis3 + Kiekis2,
    NaujasKiekis =< Talpa2.

%atsakymas
atspausdintiAtsakyma(Perpylimai):-
    reverse(Perpylimai, Atsakymas),
    write(Atsakymas).

/*
 * ?- perpylimas(5,4,2,3).
 * [pylimas(5,0,0),pylimas(1,4,0),
 * pylimas(1,2,2), pylimas(0,3,2),
 * pylimas(0,4,1),pylimas(4,0,1),
 * pylimas(3,0,2).
 * ?- perpylimas(5,2,1,1).
 * Neimanomas vandens kiekis, nes kituose induose per maza talpa.
 */











