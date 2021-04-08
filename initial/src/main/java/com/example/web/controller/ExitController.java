package com.example.web.controller;

import com.example.web.form.ProfileForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("exit")
@SessionAttributes("profileForm")
public class ExitController {

    @ModelAttribute("profileForm")
    public ProfileForm setProfileForm() {
        return new ProfileForm();
    }

    @GetMapping("/init")
    public String exitMethod() {
        System.out.println("call : ExitController#exit");
        return "exit";
    }

}
