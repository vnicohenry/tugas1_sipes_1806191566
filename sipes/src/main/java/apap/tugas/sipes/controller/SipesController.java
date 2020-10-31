package apap.tugas.sipes.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SipesController {

    @GetMapping("/")
    public String landingPage(Model model){

        return "home";
    }
}
