package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.Jatekos;
import org.fsdev.tarokk.model.Lap;
import org.fsdev.tarokk.model.Pakli;
import org.fsdev.tarokk.model.Szin;
import org.junit.Test;

import java.util.Arrays;

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
        System.out.println(xxi);

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

        Asztal asztal = new Asztal(Arrays.asList(kristof, hoba, vinczeg, attila));
        asztal.ujOsztas();

        assertEquals(6, asztal.getPakli().getLapok().size());
        assertEquals(9, kristof.getLapok().size());

        Jatekos kovetkezo = kristof;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 4; j++) {
                Lap hivottLap = asztal.getHivottLap();
                Szin hivottSzin = hivottLap == null ? null : hivottLap.szin;
                kovetkezo.kirakhatoLapok(hivottSzin);

                Lap lap = kovetkezo.getLapok().remove(0);
                kovetkezo = asztal.rak(kovetkezo, lap);
            }
        }

        assertEquals(36, kristof.getLapok().size() + hoba.getLapok().size() + attila.getLapok().size() + vinczeg.getLapok().size());
    }
}
