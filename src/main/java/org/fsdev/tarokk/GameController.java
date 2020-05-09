package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.GameLogger;
import org.fsdev.tarokk.model.Jatekos;
import org.fsdev.tarokk.model.Lap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Lazy
    @Autowired
    private SimpMessagingTemplate broker;

    @Autowired
    private GameLogger gameLogger;

    List<Jatekos> jatekosok = new ArrayList<>();
    private Asztal asztal;


    public void ujJatek() {
        logger.info("uj jatek kezodik");
        asztal = new Asztal(jatekosok);
        asztal.ujOsztas();

        gameLogger.log("uj jatek kezdodik");
        broadcastAsztal();
    }

    @MessageMapping("/asztal")
    public void broadcastAsztal() {
        logger.info("## broadcasting asztal");
        broker.convertAndSend("/game/asztal", asztal);
    }

    @MessageMapping("/kihiv")
    public void kihiv(Lap lap, Principal principal) {
        gameLogger.log("%s kihivott %s", principal.getName(), lap);
        asztal.rak(principal.getName(), lap);
        broadcastAsztal();
    }

    public void leul(Jatekos jatekos) {
        gameLogger.log("%s leult az asztalhoz", jatekos);
        jatekosok.add(jatekos);

        if (jatekosok.size() == 4) {
            ujJatek();
        }
    }

    public void felall(Jatekos jatekos) {
        gameLogger.log("%s felallt az asztaltol", jatekos);
        jatekosok.remove(jatekos);
    }


    public List<Jatekos> getJatekosok() {
        return jatekosok;
    }

    public Asztal getAsztal() {
        return asztal;
    }
}
