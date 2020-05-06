package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.Jatekos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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

    List<Jatekos> jatekosok = new ArrayList<>();
    private Asztal asztal;


    public void ujJatek() {
        logger.info("uj jatek kezodik");
        asztal = new Asztal(jatekosok);
        asztal.ujOsztas();

        gameLog("uj jatek kezdodik");
        broker.convertAndSend("/game/asztal", asztal);
    }

    @MessageMapping("/asztal")
    @SendTo("/game/asztal")
    public Asztal asztal() {
        return asztal;
    }

    @MessageMapping("/kihiv")
    public void kihiv(String kartya, Principal principal) {
        gameLog("kihivott %s %s", kartya, principal);
    }


    public void leul(Jatekos jatekos) {
        gameLog("%s leult az asztalhoz", jatekos);
        jatekosok.add(jatekos);

        if (jatekosok.size() == 4) {
            ujJatek();
        }
    }

    public void felall(Jatekos jatekos) {
        gameLog("%s felallt az asztaltol", jatekos);
        jatekosok.remove(jatekos);
    }

    public List<Jatekos> getJatekosok() {
        return jatekosok;
    }

    public void gameLog(String message, Object... params) {
        String log = String.format(message, params);
        logger.info("LOG: " + log);
        broker.convertAndSend("/game/log", log);
    }
}
