package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.Jatekos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Controller
public class GameController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Jatekos kristof = new Jatekos("Kristof");
    private Jatekos hoba = new Jatekos("Hoba");
    private Jatekos vinczeg = new Jatekos("Vinczeg");
    private Jatekos attila = new Jatekos("Attila");
    private Asztal asztal;


    public GameController() {
        ujJatek();
    }

    public void ujJatek() {
        asztal = new Asztal(Arrays.asList(kristof, hoba, vinczeg, attila));
        asztal.ujOsztas();
    }

    @MessageMapping("/asztal")
    @SendTo("/game/asztal")
    public Asztal asztal() {
        return asztal;
    }

    @MessageMapping("/kihiv")
    public void kihiv(String kartya) {
        logger.info("kihivott {}", kartya);
    }




}
