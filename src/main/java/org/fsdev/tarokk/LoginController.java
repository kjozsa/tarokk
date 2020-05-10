package org.fsdev.tarokk;

import org.fsdev.tarokk.model.Jatekos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsService manager;

    @Autowired
    private GameController gameController;

    @GetMapping("/login/{username}")
    public String login(@PathVariable String username) {
        logger.debug("login attempt for {}", username);
        UserDetails userDetails = manager.loadUserByUsername(username);

        Jatekos jatekos = new Jatekos(username);
        if (gameController.getJatekosok().contains(jatekos)) {
            logger.debug("already logged in {}", jatekos);
            return "mar itt vagy " + jatekos;

        } else {
            Authentication auth = new UsernamePasswordAuthenticationToken(jatekos, userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
            logger.debug("logged in {}", jatekos);
            return "hello " + username;
        }
    }

    @GetMapping("/logout")
    public String logout(Principal principal) {
        logger.info("logout {}", principal);
        gameController.felall(new Jatekos(principal.getName()));
        SecurityContextHolder.getContext().setAuthentication(null);
        return "bye";
    }

    @GetMapping("/reset")
    public String reset() {
        gameController.getJatekosok().clear();
        SecurityContextHolder.getContext().setAuthentication(null);
        return "reset done";
    }
}
