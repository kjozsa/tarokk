package org.fsdev.tarokk.model;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.Jatekos;
import org.fsdev.tarokk.model.Lap;
import org.fsdev.tarokk.model.Pakli;
import org.fsdev.tarokk.model.Szin;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.fsdev.tarokk.model.Figura.Asz;
import static org.fsdev.tarokk.model.Figura.Bunkos;
import static org.fsdev.tarokk.model.Figura.Dama;
import static org.fsdev.tarokk.model.Figura.Kiraly;
import static org.fsdev.tarokk.model.Figura.XXI;
import static org.fsdev.tarokk.model.Szin.KOR;
import static org.fsdev.tarokk.model.Szin.PIKK;
import static org.fsdev.tarokk.model.Szin.TAROKK;
import static org.fsdev.tarokk.model.Szin.TREFF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestModel {

    @Test
    public void testLap() {
        Lap pikkAsz = new Lap(PIKK, Asz);
        Lap pikkDama = new Lap(PIKK, Dama);
        Lap xxi = new Lap(TAROKK, XXI);

        System.out.println(pikkAsz);
        System.out.println(pikkAsz.getImage());
        System.out.println(xxi);
        System.out.println(xxi.getImage());

        assertTrue(xxi.elviszi(pikkAsz));
        assertFalse(pikkAsz.elviszi(xxi));
        assertTrue(pikkDama.elviszi(pikkAsz));
    }

    @Test
    public void testPakli() {
        Pakli pakli = new Pakli();
        assertEquals(42, pakli.getLapok().size());

        for (Lap lap : pakli.getLapok()) {
            System.out.println(lap);
        }
    }

    @Test
    public void testKirakhatoLapok() {
        Jatekos jatekos = new Jatekos("Bela");
        jatekos.kap(Arrays.asList(new Lap(TREFF, Kiraly), new Lap(TREFF, Dama), new Lap(KOR, Bunkos)));
        assertEquals(2, jatekos.kirakhatoLapok(TREFF).size());
        assertEquals(1, jatekos.kirakhatoLapok(KOR).size());
        assertEquals(3, jatekos.kirakhatoLapok(TAROKK).size());
    }

    @Test
    public void testAsztal() {
        Jatekos kristof = new Jatekos("Kristof");
        Jatekos hoba = new Jatekos("Hoba");
        Jatekos vinczeg = new Jatekos("Vinczeg");
        Jatekos attila = new Jatekos("Attila");

        List<Jatekos> jatekosok = Arrays.asList(attila, hoba, kristof, vinczeg);
        Asztal asztal = new Asztal(jatekosok);
        asztal.ujOsztas();

        assertEquals(6, asztal.getPakli().getLapok().size());
        assertEquals(9, kristof.getLapok().size());

        asztal.logLapokKezben();
        Jatekos aktualisJatekos = kristof;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                Lap hivottLap = asztal.getHivottLap();
                Szin hivottSzin = hivottLap == null ? null : hivottLap.szin;

                ArrayList<Lap> kirakhatoLapok = new ArrayList<>(aktualisJatekos.kirakhatoLapok(hivottSzin));
                Collections.shuffle(kirakhatoLapok);
                Lap lap = kirakhatoLapok.get(0);

                aktualisJatekos = asztal.rak(aktualisJatekos, lap);

                System.err.println("K lapjai: " + asztal.getLapok(kristof));
                System.err.println("Tobbiek: " + asztal.getTobbiek(kristof));
            }
        }

        assertEquals(0, kristof.getLapok().size() + hoba.getLapok().size() + attila.getLapok().size() + vinczeg.getLapok().size());
        assertEquals(36, kristof.getElvitt().size() + hoba.getElvitt().size() + attila.getElvitt().size() + vinczeg.getElvitt().size());

        for (Jatekos jatekos : jatekosok) {
            System.out.println(jatekos.getNev() + ": " + jatekos.elvittLapokErteke());
        }
    }
}
