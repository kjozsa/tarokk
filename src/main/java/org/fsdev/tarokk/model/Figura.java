package org.fsdev.tarokk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public enum Figura {
    Asz(1, 1),
    Bunkos(2, 2),
    Lovas(3, 3),
    Dama(4, 4),
    Kiraly(5, 5),

    I(1, 5),
    II(2, 1),
    III(3, 1),
    IV(4, 1),
    V(5, 1),
    VI(6, 1),
    VII(7, 1),
    VIII(8, 1),
    IX(9, 1),
    X(10, 1),
    XI(11, 1),
    XII(12, 1),
    XIII(13, 1),
    XIV(14, 1),
    XV(15, 1),
    XVI(16, 1),
    XVII(17, 1),
    XVIII(18, 1),
    XIX(19, 1),
    XX(20, 1),
    XXI(21, 5),
    Skiz(22, 5);

    static Collection<Figura> szinesek = Arrays.asList(Asz, Bunkos, Lovas, Dama, Kiraly);
    static Collection<Figura> tarokkok;

    static {
        tarokkok = new ArrayList<>(Arrays.asList(values()));
        tarokkok.removeAll(szinesek);
    }

    private int erosseg;
    private int pontertek;

    Figura(int erosseg, int pontertek) {
        this.erosseg = erosseg;
        this.pontertek = pontertek;
    }

    public int getErosseg() {
        return erosseg;
    }

    public int getPontertek() {
        return pontertek;
    }
}
