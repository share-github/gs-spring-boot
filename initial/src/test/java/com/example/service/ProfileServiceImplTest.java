package com.example.service;

import com.example.constant.ProfileConstant;
import com.example.web.form.ProfileForm;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Spring Boot Service層のテスト
 */
@SpringBootTest // これがないと @InjectMocks が動かない。
public class ProfileServiceImplTest {

    @InjectMocks // モックオブジェクトの注入。
    private ProfileServiceImpl profileServiceImpl;

    @Test
    public void initHello() throws Exception {
        int age = 20;

        // 期待値
        ProfileForm expected = new ProfileForm();
        expected.setAge(age);
        expected.setUntilRetire(ProfileConstant.Age.RETIRE - age);

        // 実行
        ProfileForm inputForm = new ProfileForm();
        inputForm.setAge(age);
        ProfileForm actualValue = profileServiceImpl.initHello(inputForm);

        // 検証
        assertThat(actualValue, is(samePropertyValuesAs(expected))); // 全てのプロパティが等しいことをチェックする。
    }

    /**
     * ProfileServiceImpl#calcYearsUntilRetire のテスト
     * ★private メソッドはリフレクションを用いて実行すれば、呼び出しが可能。
     */
    @Test
    public void calcYearsUntilRetire() throws Exception {

        // テストパラメータ
        Integer age = 20;

        // テスト対象メソッドの情報を取得する。
        Method method = ProfileServiceImpl.class.getDeclaredMethod("calcYearsUntilRetire", Integer.class);

        // メソッドをアクセス制限を解除。
        method.setAccessible(true);

        // メソッド呼び出し
        Integer actualValue = (Integer) method.invoke(profileServiceImpl, age);

        // 期待値を算出する。
        Integer expectedValue = ProfileConstant.Age.RETIRE - age;

        // 検証
        assertEquals(actualValue, expectedValue);
    }

    /**
     * テスト実行用インナークラスを用いてテスト実行。
     */
    @Test
    public void calcYearsUntilRetire_any_params() throws Exception {
        int retire = ProfileConstant.Age.RETIRE;
        call_calcYearsUntilRetire(null, null);
        call_calcYearsUntilRetire(-1, retire - -1);
        call_calcYearsUntilRetire(0, retire);
        call_calcYearsUntilRetire(1, retire - 1);
    }

    /**
     * テスト実行用インナークラスを用いてテストするための共通メソッド。
     *
     * @param age 年齢
     */
    public void call_calcYearsUntilRetire(Integer age, Integer expected) throws Exception {

        // インナークラスのインスタンス生成
        CalcYearsUntilRetire calcYearsUntilRetire = new CalcYearsUntilRetire();

        // テストパラメータを設定
        calcYearsUntilRetire.age = age;
        calcYearsUntilRetire.expectedValue = expected;

        // テストシナリオ実行
        calcYearsUntilRetire.execute();

        // テスト結果を検証
        calcYearsUntilRetire.verify();
    }

    /**
     * テスト実行用インナークラス。<br>
     * 異なるパラメータで同じシナリオを実行しなければならないようなケースで有用。<br>
     * 1.クラスをnew<br>
     * 2.テストパラメータを設定<br>
     * └age<br>
     * └expectedValue……null(未設定)の場合、<br>
     */
    private class CalcYearsUntilRetire {

        /**
         * テストパラメータ
         */
        public Integer age;

        /**
         * テスト期待値
         */
        public Integer expectedValue;

        /**
         * テスト実行結果値
         */
        private Integer actualValue;

        /**
         * 実行シナリオ
         */
        public void execute() throws Exception {
            Method method = ProfileServiceImpl.class.getDeclaredMethod("calcYearsUntilRetire", Integer.class);
            method.setAccessible(true);
            actualValue = (Integer) method.invoke(profileServiceImpl, age);
        }

        /**
         * 検証メソッド
         */
        public void verify() {
            assertEquals(expectedValue, actualValue);
            System.out.println("param : " + age + ", expected : " + expectedValue + ", actual : " + actualValue);
        }
    }
}
