package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.Jatekos;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Controller
public class GameController {
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

}
