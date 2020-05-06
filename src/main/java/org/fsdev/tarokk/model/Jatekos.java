package org.fsdev.tarokk.model;

import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Jatekos {
    private String nev;
    private SortedSet<Lap> lapok = new TreeSet<>();
    private SortedSet<Lap> elvitt = new TreeSet<>();


    public Jatekos(String nev) {
        this.nev = StringUtils.capitalize(nev);
    }

    @Override
    public String toString() {
        return nev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jatekos jatekos = (Jatekos) o;
        return nev.equals(jatekos.nev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nev);
    }

    public void kap(Lap lap) {
        this.lapok.add(lap);
    }

    public void kap(List<Lap> utes) {
        this.lapok.addAll(utes);
    }

    public void elvisz(List<Lap> utes) {
        this.elvitt.addAll(utes);
    }

    public void elvesz(Lap lap) {
        this.lapok.remove(lap);
    }

    public Collection<Lap> kirakhatoLapok(Szin szin) {
        if (szin == null) {
            return lapok;
        }

        Collection<Lap> olyanSzinu = lapok.stream().filter(lap -> lap.szin == szin).collect(Collectors.toList());
        if (olyanSzinu.size() != 0) {
            return olyanSzinu;
        }

        Collection<Lap> tarokkok = lapok.stream().filter(lap -> lap.szin == Szin.TAROKK).collect(Collectors.toList());
        if (tarokkok.size() != 0) {
            return tarokkok;
        }

        return lapok;
    }

    public SortedSet<Lap> getLapok() {
        return lapok;
    }

    public List<String> getLapKepek() {
        return lapok.stream().map(Lap::getImage).collect(Collectors.toList());
    }

    public SortedSet<Lap> getElvitt() {
        return elvitt;
    }

    public String getNev() {
        return nev;
    }

    public int elvittLapokErteke() {
        return elvitt.stream().mapToInt(lap -> lap.figura.getPontertek()).sum();
    }
}
