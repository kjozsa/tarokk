package org.fsdev.tarokk.model;

import org.slf4j.Logger;

import java.util.ArrayList;
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

    public Jatekos rak(String nev, Lap lap) {
        return rak(findJatekosByNev(nev), lap);
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

    public List<Jatekos> getJatekosok() {
        return jatekosok;
    }

    public Jatekos getKovetkezo() {
        return kovetkezo;
    }

    public Jatekos findJatekosByNev(String nev) {
        return jatekosok.stream().filter(it -> nev.equalsIgnoreCase(it.getNev())).findFirst().get();
    }
}
