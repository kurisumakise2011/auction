package com.auction.game.controller;

import com.auction.game.model.UserDetailsAdapter;
import com.auction.game.service.JwtTokenService;
import com.auction.game.web.JwtRequest;
import com.auction.game.web.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static com.auction.game.web.JwtRequestFilter.TOKEN;

@Slf4j
@Controller
@CrossOrigin
public class TokenController {
    public static final String ANONYMOUS = "anonymous";
    @Qualifier("userServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public String createAuthenticationToken(@ModelAttribute JwtRequest request, ModelMap model, HttpServletResponse response) {
        String errors = authenticate(request.getUsername(), request.getPassword());
        if (StringUtils.EMPTY.equals(errors)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtTokenService.generateToken(userDetails);
            response.addCookie(new Cookie(TOKEN, token));
            return "redirect:/";
        } else {
            model.addAttribute("error", errors);
            return "login";
        }
    }

    @GetMapping(path = "/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        response.addCookie(new Cookie(TOKEN, null));
        return "redirect:/login";
    }

    private String authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.error("User is banned", e);
            return "User is banned";
        } catch (BadCredentialsException e) {
            log.error("Invalid username or password", e);
            return "Invalid username or password";
        }
        return StringUtils.EMPTY;
    }

    public static String id() {
        Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .orElse(null);
        if (principal == null || (principal instanceof String && "anonymousUser".equals(principal))) {
            return ANONYMOUS;
        }
        return ((UserDetailsAdapter)principal).id();
    }
}

