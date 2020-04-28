package org.fsdev.tarokk.model;

public enum Szin {
    KOR(0),
    PIKK(1),
    TREFF(2),
    KARO(3),
    TAROKK(-1);

    private final int sorrend;

    Szin(int sorrend) {
        this.sorrend = sorrend;
    }

    public int getSorrend() {
        return sorrend;
    }
}
