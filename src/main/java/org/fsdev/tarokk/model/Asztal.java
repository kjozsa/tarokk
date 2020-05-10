package org.fsdev.tarokk.model;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Asztal {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private List<Jatekos> jatekosok;
    Pakli pakli = new Pakli();

    List<Lap> utes = new ArrayList<>();
    Lap hivottLap;
    Jatekos utestViszi;
    Jatekos kovetkezo;

    public Asztal(List<Jatekos> jatekosok) {
        this.jatekosok = jatekosok;
    }

    public void ujOsztas() {
        pakli.kever();

        for (Jatekos jatekos : jatekosok) {
            jatekos.getLapok().clear();

            for (int i = 0; i < 9; i++) {
                jatekos.kap(pakli.getLapok().remove(0));
            }
        }
        kovetkezo = jatekosok.get(0);
    }

    public void logLapokKezben() {
        for (Jatekos jatekos : jatekosok) {
            logger.info("{}: {}", jatekos, jatekos.getLapok());
        }
    }

    /**
     * @return kovetkezo Jatekos
     */
    public synchronized Jatekos rak(Jatekos jatekos, Lap lap) {
        logger.debug("{} rak {}", jatekos, lap);
        jatekos.elvesz(lap);

        if (utes.size() == 0) {
            hivottLap = lap;
            utestViszi = jatekos;
        }

        if (utes.stream().allMatch(utesLap -> lap.elviszi(utesLap))) {
            utestViszi = jatekos;
        }
        utes.add(lap);

        if (utes.size() == 4) {
            logger.info("utest vitte {} ({})", utestViszi.getNev(), utes);
            utestViszi.elvisz(utes);
            utes.clear();
            kovetkezo = utestViszi;
            return utestViszi;
        } else {
            int index = jatekosok.indexOf(jatekos);
            kovetkezo = index + 1 < jatekosok.size() ? jatekosok.get(index + 1) : jatekosok.get(0);
            return kovetkezo;
        }
    }

    public Pakli getPakli() {
        return pakli;
    }

    public Lap getHivottLap() {
        return hivottLap;
    }

//    public List<Jatekos> getJatekosok() {
//        return jatekosok;
//    }

    public Jatekos getKovetkezo() {
        return kovetkezo;
    }

    public List<Lap> getUtes() {
        return utes;
    }

    public Collection<Lap> getLapok(Jatekos jatekos) {
        return jatekosok.stream().filter(it -> jatekos.equals(it)).findFirst().get().getLapok();
    }

    public List<Jatekos> getTobbiek(Jatekos jatekos) {
        ArrayList<Jatekos> results = new ArrayList<>();
        int aktualisPozicio = 0;
        for (int i = 0; i <= 3; i++) {
            if (jatekosok.get(i).equals(jatekos)) {
                aktualisPozicio = i;
            }
        }

        for (int i = 1; i <= 3; i++) {
            int pozicio = aktualisPozicio + i;
            int korrigaltPozicio = pozicio > 3 ? pozicio - 4 : pozicio;

            Jatekos copy = jatekosok.get(korrigaltPozicio).copy();
            copy.getLapok().forEach(lap -> lap.setRejtett(true));
            results.add(copy);
        }
        return results;
    }

    public boolean szabalyosHivas(Lap lap) {
        if (hivottLap == null) return true;
        Szin szin = lap.szin;
        return szin.equals(hivottLap.szin) || szin.equals(Szin.TAROKK);
    }
}
