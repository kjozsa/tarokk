package org.fsdev.tarokk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

public enum Figura {
    Asz(1, 1),
    Bunkos(2, 2),
    Lovas(3, 3),
    Dama(4, 4),
    Kiraly(5, 5),

    I(11, 5),
    II(12, 1),
    III(13, 1),
    IV(14, 1),
    V(15, 1),
    VI(16, 1),
    VII(17, 1),
    VIII(18, 1),
    IX(19, 1),
    X(20, 1),
    XI(21, 1),
    XII(22, 1),
    XIII(23, 1),
    XIV(24, 1),
    XV(25, 1),
    XVI(26, 1),
    XVII(27, 1),
    XVIII(28, 1),
    XIX(29, 1),
    XX(30, 1),
    XXI(31, 5),
    Skiz(32, 5);

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
