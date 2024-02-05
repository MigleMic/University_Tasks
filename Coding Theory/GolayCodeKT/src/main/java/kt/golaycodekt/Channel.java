package kt.golaycodekt;

import java.util.Random;

public class Channel {
    //Nepatikimas kanalas, kuris iskraipo vektoriu su duota tikimybe
    //Ieitis - vektorius, klaidos tikimybe, klaidu vietu masyvas, pradzioje uzpildytas 0
    public void channell(int[] code, float possibility, int[] errorPlace) {
        Random r = new Random();

        for (int i = 0; i < 23; i++) {
            //Jei mazesnis už tikimybe, bit'as apsivers
            //nextDouble paima atsitiktini skaicių intervale nuo 0 iki 1
            if (r.nextDouble() < possibility) {
                AdditionalResources.flipping(code, i + 1);
                errorPlace[i] = 1;
            }
        }
    }
}
