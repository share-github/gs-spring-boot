package com.example.service;

import com.example.constant.ProfileConstant;
import com.example.web.form.ProfileForm;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    /**
     * helloのフォームを設定する。
     *
     * @param profileForm
     * @return
     */
    public ProfileForm initHello(ProfileForm profileForm) {
        Integer untilRetire = calcYearsUntilRetire(profileForm.getAge());
        profileForm.setUntilRetire(untilRetire);
        return profileForm;
    }


    /**
     * 定年までの年数を計算する。
     *
     * @param age 年齢
     * @return
     */
    private Integer calcYearsUntilRetire(Integer age) {
        if (age == null) return null;
        return ProfileConstant.Age.RETIRE - age;
    }
}
