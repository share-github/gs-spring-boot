package com.example.web.controller;

import com.example.service.ProfileService;
import com.example.web.form.ProfileForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("profile")
@SessionAttributes("profileForm")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * フィールドインジェクションは非推奨。
     * コンストラクタインジェクションが推奨されている。
     * 以下のようなものは非推奨。
     * ex) @Autowired private Service service;
     *
     * @param profileService
     */
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @ModelAttribute("profileForm")
    public ProfileForm setProfileForm() {
        return new ProfileForm();
    }

    @GetMapping("/name")
    public String name() {
        return "name";
    }

    @PostMapping("age")
    public String age(ProfileForm profileForm) {
        return "age";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String hello(ProfileForm profileForm, SessionStatus sessionStatus) {

        profileService.initHello(profileForm);

        // SessionStatusオブジェクトのsetCompleteメソッドを呼び出し、オブジェクトをセッションから削除する。
        // Modelオブジェクトに同じオブジェクトが残っているため、直接、View(JSP) の表示処理に影響は与えない。
        sessionStatus.setComplete();

        return "hello";
    }

    @GetMapping("hello")
    public String getHello(ProfileForm profileForm, SessionStatus sessionStatus) {

        profileService.initHello(profileForm);

        // SessionStatusオブジェクトのsetCompleteメソッドを呼び出し、オブジェクトをセッションから削除する。
        // Modelオブジェクトに同じオブジェクトが残っているため、直接、View(JSP) の表示処理に影響は与えない。
        sessionStatus.setComplete();

        return "hello";
    }

    @RequestMapping(value = "", method = RequestMethod.POST, params = "exit")
    public String exit(ProfileForm profileForm, SessionStatus sessionStatus) {

        // SessionStatusオブジェクトのsetCompleteメソッドを呼び出し、オブジェクトをセッションから削除する。
        // Modelオブジェクトに同じオブジェクトが残っているため、直接、View(JSP) の表示処理に影響は与えない。
        sessionStatus.setComplete();

        return "redirect:exit/init";// redirect は RequestMapping のパスを記述する。
    }

    @PostMapping(params = "home")
    public String home(ProfileForm profileForm, SessionStatus sessionStatus) {

        // SessionStatusオブジェクトのsetCompleteメソッドを呼び出し、オブジェクトをセッションから削除する。
        // Modelオブジェクトに同じオブジェクトが残っているため、直接、View(JSP) の表示処理に影響は与えない。
        sessionStatus.setComplete();

        return "redirect:/profile/name";
    }

}
