/* Migle Miceviciute
 *  Programu sistemos 3 kursas 3 grupe
 *  uzduoties variantai - 1.9, 2.7, 3.7 ir 4.2
 */

%1.9 - knelyg(S, K) - skaiciu saraso S nelyginiu elementu kiekis yra K.

knelyg(S, K) :-
    nelyg(S,K).
nelyg([], 0).
nelyg([E|S],K) :-
    nelyg(S,K1),
    K is K1 + (E mod 2).

/* ?- knelyg([1,8,3,7,5,2],K).
   K = 4.
   ?- knelyg([4,6,8,10,12],K).
   K = 0.
   ?- knelyg([1,3,5,7,9,11],K).
   K = 6.
   ?- knelyg([1,2,3,4,5,6,7,8,9,10],K).
   K = 5.
*/

% 2.7 posarasis(S1,S2) - sarasas S2 susideda is (gal but ne visu) saraso
% S1 elementu, isdestytu ta pacia tvarka, kaip ir sarase S1. Kitaip
% tariant, is saraso S1 ismetus tam tikrus elementus, galime gauti
% sarasa S2.

posarasis(_,[]).
posarasis([Head|Tail], [Head|Likutis]) :- posarasis(Tail,Likutis).
posarasis([_|Tail],[Head|Likutis]) :- posarasis(Tail, [Head|Likutis]), !.

/* ?- posarasis([1,8,3,5],[1,3]).
   true.
   ?- posarasis([a,b,c,d,e],[c,e]).
   true.
   ?- posarasis([1,2,3,4,5],[5,4]).
   false.
 */

% 3.7 keisti(S, K, R) - duotas sarasas S. Duotas sarasas K, nusakantis
% keitini ir susidedantis is elementu pavidalo k(KeiciamasSimbolis,
% PakeistasSimbolis). R- rezultatas, gautas pritaikius sarasui S keitini
% K.

keisti([],_,[]).
keisti([Head|Tail], K, [Head2|Tail2]) :-
    pakeitimas(Head, K, Head2),
    keisti(Tail, K, Tail2),!.

pakeitimas(Head, [], Head).
pakeitimas(KeiciamasSimbolis,
           [k(KeiciamasSimbolis, PakeistasSimbolis)|_],
           PakeistasSimbolis) :- !.
pakeitimas(KeiciamasSimbolis,
           [_|Tail],
           PakeistasSimbolis) :-
    pakeitimas(KeiciamasSimbolis, Tail, PakeistasSimbolis).


/* ?- keisti([a,c,b],[k(a,x),k(b,y)], R).
   R = [x,c,y].
   ?- keisti([1,2,3,4,5],[k(1,100),k(5,500)],R).
   R = [100,2,3,4,500].
 */

% 4.2 dvej_skaic(S,K) - dvejetainiu skaitmenu sarasas S atitinka skaiciu
% K. pasirasyti length

dvej_skaic([],0).
dvej_skaic([Head|Tail], K) :-
    ilgis(Tail, Ilgis),
    dvej_skaic(Tail, Skaicius),
    K is Skaicius + 2 ** Ilgis * Head.

ilgis(Tail, Ilgis) :- ilgis(Tail, 0, Ilgis).
ilgis([], Ilgis, Ilgis).
ilgis([_|Tail],Ilgis, Akum):-
    Ilgis1 is Ilgis+1,
    ilgis(Tail, Ilgis1, Akum).



/* ?- dvej_skaic([1,0,1,1],K)
   K = 11.
   ?- dvej_skaic([1,0,0,0,1,1],K).
   K = 35.
 */















