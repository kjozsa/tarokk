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
            return utestViszi;
        } else {
            int index = jatekosok.indexOf(jatekos);
            return index + 1 < jatekosok.size() ? jatekosok.get(index + 1) : jatekosok.get(0);
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
}
