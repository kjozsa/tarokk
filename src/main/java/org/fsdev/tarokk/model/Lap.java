package org.fsdev.tarokk.model;

public class Lap implements Comparable<Lap> {
    Szin szin;
    Figura figura;
    boolean rejtett;

    private Lap() {
        // for json deserialization
    }

    public Lap(Szin szin, Figura figura) {
        this.szin = szin;
        this.figura = figura;
    }

    @Override
    public String toString() {
        return (rejtett ? " Rejtett " : "") + (szin == Szin.TAROKK ? figura.name() : szin.name() + " " + figura.name());
    }

    public String getImage() {
        if (rejtett) {
            return "hatlap";
        } else {
            return (szin == Szin.TAROKK ? "tarokk" : szin.name().toLowerCase()) +
                    String.format("%02d", figura.getErosseg());
        }
    }

    public Szin getSzin() {
        return rejtett ? null : szin;
    }

    public Figura getFigura() {
        return rejtett ? null : figura;
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

    public void setRejtett(boolean rejtett) {
        this.rejtett = rejtett;
    }

    public Lap copy() {
        return new Lap(szin, figura);
    }
}
