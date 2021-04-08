package com.example.web.controller;

import com.example.web.form.ProfileForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
public class ProfileControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // spring-mvc.xmlなどで定義したSpring MVC の設定を読み込み、
        // WebApplicationContextを生成することで、デプロイ時とほぼ同じ状態でテストすることができる。
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void name() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/profile/name"))

                // HTTP ステータスの検証
                // isOk()……200
                .andExpect(status().isOk())

                // 指定のviewを返却しているか
                // .andExpect(view().name(テンプレート名))
                .andExpect(view().name("name"));
    }

    @Test
    public void age() throws Exception {
        String name = "test name";

        // POST する Form
        ProfileForm postForm = new ProfileForm();
        postForm.setName(name);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/profile/age")

                        // リクエストのパラメータに値を設定する。
                        // フラッシュスコープにオブジェクトを設定するメソッド。
                        // flashAttr()はオブジェクトを設定できる。
                        .flashAttr("profileForm", postForm)
        )
                .andExpect(status().isOk())
                .andExpect(view().name("age"))

                // Controller が返却した model の値を検証。
                .andExpect(model().attribute("profileForm", postForm))
                .andExpect(model().attribute("profileForm",
                        // profileForm のプロパティ "name" の値が name("test name") であることを検証。
                        hasProperty("name", is(name))
                ));
    }

    @Test
    public void not_found() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/profile/none"))
                .andExpect(status().isNotFound());
    }
}
