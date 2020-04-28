package org.fsdev.tarokk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.fsdev.tarokk.model.Figura.szinesek;
import static org.fsdev.tarokk.model.Figura.tarokkok;
import static org.fsdev.tarokk.model.Szin.KARO;
import static org.fsdev.tarokk.model.Szin.KOR;
import static org.fsdev.tarokk.model.Szin.PIKK;
import static org.fsdev.tarokk.model.Szin.TAROKK;
import static org.fsdev.tarokk.model.Szin.TREFF;

public class Pakli {
    List<Lap> lapok = new ArrayList<>();

    public Pakli() {
        kever();
    }

    public void kever() {
        lapok.clear();
        for (Szin szin : Arrays.asList(KOR, KARO, PIKK, TREFF)) {
            for (Figura figura : szinesek) {
                lapok.add(new Lap(szin, figura));
            }
        }
        for (Figura figura : tarokkok) {
            lapok.add(new Lap(TAROKK, figura));
        }

        Collections.shuffle(lapok);
    }

    public List<Lap> getLapok() {
        return lapok;
    }
}
