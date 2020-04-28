package org.fsdev.tarokk.model;

public class Lap implements Comparable<Lap> {
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

    @Override
    public int compareTo(Lap lap) {
        if (this.szin == lap.szin) {
            return Integer.valueOf(figura.getErosseg()).compareTo(lap.figura.getErosseg());
        } else {
            return Integer.valueOf(szin.getSorrend()).compareTo(lap.szin.getSorrend());
        }
    }

    public boolean elviszi(Lap masik) {
        if (szin == masik.szin) {
            return figura.getErosseg() > masik.figura.getErosseg();
        }

        return (szin == Szin.TAROKK);
    }
}