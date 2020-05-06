package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.Jatekos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class GameController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    List<Jatekos> jatekosok = new ArrayList<>();
    private Asztal asztal;


    public GameController() {
        ujJatek();
    }

    public void ujJatek() {
        logger.info("uj jatek kezodik");
        asztal = new Asztal(jatekosok);
        asztal.ujOsztas();
    }

    @MessageMapping("/asztal")
    @SendTo("/game/asztal")
    public Asztal asztal() {
        return asztal;
    }

    @MessageMapping("/kihiv")
    public void kihiv(String kartya, Principal principal) {
        logger.info("kihivott {} {}", kartya, principal);
    }

    
    public void leul(String username) {
        logger.info("{} leult az asztalhoz", username);
        jatekosok.add(new Jatekos(username));

        if (jatekosok.size() == 4) {
            ujJatek();
        }
    }
}
