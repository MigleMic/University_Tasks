/* Migle Miceviciute,
 *  Programu sistemos 3 kursas 3 grupe,
 *  uzduoties variantai - 2.3 ir 5.2 */

%studentas(Vardas,Kursas).
studentas(lukas, 1).
studentas(daniele, 1).

studentas(urte, 2).
studentas(simas, 2).
studentas(jonas,2).

studentas(migle, 3).
studentas(rokas, 3).
studentas(arnas, 3).

studentas(ernestas, 4).
studentas(aivaras, 4).
studentas(veronika, 4).

%yraVyresnis(StudentoVardas1, StudentoVardas2).

yraVyresnis(ernestas, aivaras).
yraVyresnis(aivaras, rokas).
yraVyresnis(rokas, veronika).
yraVyresnis(veronika, arnas).
yraVyresnis(arnas, migle).
yraVyresnis(migle, urte).
yraVyresnis(urte, jonas).
yraVyresnis(jonas, lukas).
yraVyresnis(lukas, simas).
yraVyresnis(simas, daniele).

butiVyresniu(StudentoVardas1, StudentoVardas2) :-
    yraVyresnis(StudentoVardas1, StudentoVardas2).
butiVyresniu(StudentoVardas1, StudentoVardas2) :-
    yraVyresnis(StudentoVardas1, TarpinisStudentas),
    butiVyresniu(TarpinisStudentas, StudentoVardas2).

/* 2.3 studentas A yra vyresnis uþ kito kurso studentà B*/

vyresnis(StudentasA, StudentasB) :-
    studentas(StudentasA, KursasA),
    studentas(StudentasB, KursasB),
    KursasA \= KursasB,
    butiVyresniu(StudentasA, StudentasB).

/*
 ?- vyresnis(rokas, lukas).       - true.
 ?- vyresnis(arnas, ernestas).    - false.
 ?- vyresnis(jonas, daniele).     - true.
*/

/* 5.2 apibrëþti dalyba(div) nenaudojant daugybos ir dalybos operacijø*/
dalyba(0,_,0).
dalyba(_,0,0).

dalyba(Daliklis, Dalinys, Dalmuo) :-
    zenklas(Daliklis, Dalinys, Zenklas),
    NaujasDaliklis is abs(Daliklis),
    NaujasDalinys is abs(Dalinys),
    NaujasDaliklis > NaujasDalinys,
    dalyba2(NaujasDaliklis, NaujasDalinys, 0, Dalmuo, Zenklas).
dalyba(Daliklis, Dalinys, Dalmuo) :-
    NaujasDaliklis is abs(Daliklis),
    NaujasDalinys is abs(Dalinys),
    NaujasDaliklis < NaujasDalinys,
    Dalmuo is 0.

zenklas(Daliklis, Dalinys, Zenklas) :-
    Daliklis > 0,
    Dalinys > 0,
    Zenklas is 1.
zenklas(Daliklis, Dalinys, Zenklas) :-
    Daliklis < 0,
    Dalinys < 0,
    Zenklas is 1.
zenklas(Daliklis, Dalinys, Zenklas) :-
    Daliklis > 0,
    Dalinys < 0,
    Zenklas is -1.
zenklas(Daliklis, Dalinys, Zenklas) :-
    Daliklis < 0,
    Dalinys > 0,
    Zenklas is -1.

dalyba2(Daliklis, Dalinys, Akumuliatorius, Dalmuo, Zenklas):-
    Daliklis >= Dalinys,
    NaujasDaliklis is Daliklis - Dalinys,
    NaujasAkum is Akumuliatorius +1,
    dalyba2(NaujasDaliklis, Dalinys, NaujasAkum, Dalmuo, Zenklas).
dalyba2(Daliklis, Dalinys, Akumuliatorius, Dalmuo, Zenklas) :-
    Zenklas = 1,
    Daliklis < Dalinys,
    Dalmuo is Akumuliatorius.
dalyba2(Daliklis, Dalinys, Akumuliatorius, Dalmuo, Zenklas) :-
    Zenklas = -1,
    Daliklis < Dalinys,
    Dalmuo is -(Akumuliatorius).
/*?-
 * dalyba(10, 0, X).     - X = 0.
 * dalyba(0, 15, X).     - X = 0.
 * dalyba(25, 5, X).     - X = 5.
 * dalyba(50, -7,X).     - X = -7.
 * dalyba(-121,11,X).    - X = -11.
 * dalyba(-27, -8, X).   - X = 3.
 */










