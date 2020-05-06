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

@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailsService manager;

    @Autowired
    private GameController gameController;

    @GetMapping("/login/{username}")
    public String login(@PathVariable String username) {
        logger.info("login attempt for {}", username);
        UserDetails userDetails = manager.loadUserByUsername(username);

        Jatekos jatekos = new Jatekos(username);
        if (gameController.getJatekosok().contains(jatekos)) {
            return "mar itt vagy " + jatekos;
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        gameController.leul(jatekos);
        return "hello " + username;
    }
}
