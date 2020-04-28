package org.fsdev.tarokk.model;

public class Lap {
    public final Szin szin;
    public final Figura figura;

    public Lap(Szin szin, Figura figura) {
        this.szin = szin;
        this.figura = figura;
    }

    @Override
    public String toString() {
        return szin == Szin.TAROKK ? figura.name() : szin.name() + " " + figura.name();
    }

    public boolean elviszi(Lap masik) {
        if (szin == masik.szin) {
            return figura.getErosseg() > masik.figura.getErosseg();
        }

        return (szin == Szin.TAROKK);
    }
}
