package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Asztal;
import org.fsdev.tarokk.model.GameLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class SessionController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GameController gameController;

    @Autowired
    private GameLogger gameLogger;

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        gameLogger.log("%s csatlakozott", event.getUser().getName());
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        gameLogger.log("%s kiesett a jatekbol", event.getUser().getName());
    }

    // TODO this never gets called
    @SubscribeMapping("/game/asztal")
    public Asztal clientSubscribed() throws Exception {
        logger.info("## sending asztal on subscribe");
        return gameController.getAsztal();
    }
}
