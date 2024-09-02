import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Тесты на проверку заполнения Регистрационной формы студента")

public class TextBoxTests {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1980x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

        // перед коммитом удалить/закомментировать/поменять на false
        // при удалённом запуске тестов - браузер останется висеть открытым и будет занимать ресурсы
        //   Configuration.holdBrowserOpen = true;
    }


    @ValueSource(strings = {"1986", "2010"})
    @ParameterizedTest(name = "Тест с заполнением всех полей формы для лиц {0} года рождения")
    @Tag("Regress")
    void fillFullFormTest(String yearOfBirth) {
        open("/automation-practice-form");

        //после открытия страницы скрываем рекламу и футер
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        //заполняем таблицу
        $("#firstName").setValue("Albina");
        $("#lastName").setValue("Dobrova");
        $("#userEmail").setValue("rezolventa86@rambler.ru");
        $("#genterWrapper").$(byText("Female")).click();                //здесь выбор пола
        $("#userNumber").setValue("9872552206");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("January");
        $(".react-datepicker__year-select").selectOption(yearOfBirth);
        $(".react-datepicker__day--002").click();
        $("#subjectsInput").setValue("Comp").pressEnter();
        $("#hobbiesWrapper").$(byText("Sports")).click();
        $("#hobbiesWrapper").$(byText("Reading")).click();
        $("#uploadPicture").uploadFromClasspath("cat.jpg");
        $("#currentAddress").setValue("Ufa, Russia");
        $("#state").click();
        $("#stateCity-wrapper").$(byText("NCR")).click();
        $("#city").click();
        $("#stateCity-wrapper").$(byText("Delhi")).click();
        $("#submit").click();

        //проверка на все заполненные поля в финальной таблице
        //проверим, что полей всего 10
        $$("[class~=table] tbody tr").shouldHave(size(10));

        //проверка на все заполненные поля в финальной таблице
        $(".modal-content").shouldHave(text("Albina Dobrova"));
        $(".modal-content").shouldHave(text("rezolventa86@rambler.ru"));
        $(".modal-content").shouldHave(text("Female"));
        $(".modal-content").shouldHave(text("9872552206"));
        $(".modal-content").shouldHave(text("02 January," + yearOfBirth));
        $(".modal-content").shouldHave(text("Computer Science"));
        $(".modal-content").shouldHave(text("Sports, Reading"));
        $(".modal-content").shouldHave(text("cat.jpg"));
        $(".modal-content").shouldHave(text("Ufa, Russia"));
        $(".modal-content").shouldHave(text("NCR Delhi"));
    }

    @Test
    @Tag("Regress")
    @DisplayName("Тест с заполнением только обязательных полей формы для лиц любого года рождения")
    void fillFormWIthRequiredFieldsTest() {
        open("/automation-practice-form");

        //после открытия страницы скрываем рекламу и футер
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        //заполняем таблицу
        $("#firstName").setValue("Albina");
        $("#lastName").setValue("Dobrova");
        $("#genterWrapper").$(byText("Female")).click();                //здесь выбор пола
        $("#userNumber").setValue("9872552206");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("January");
        $(".react-datepicker__year-select").selectOption("1986");
        $(".react-datepicker__day--002").click();
        $("#uploadPicture").uploadFromClasspath("cat.jpg");
        $("#submit").click();

        //проверка на все заполненные поля в финальной таблице
        //проверим, что полей всего 10
        $$("[class~=table] tbody tr").shouldHave(size(10));

        //проверка на все заполненные поля в финальной таблице
        $(".modal-content").shouldHave(text("Albina Dobrova"));
        $(".modal-content").shouldHave(text("Female"));
        $(".modal-content").shouldHave(text("9872552206"));
        $(".modal-content").shouldHave(text("02 January,1986"));
    }

    @Disabled("Bug-1")
    @Test
    @Tag("Regress")
    @DisplayName("Тест с неправильным заполнением обязательных полей формы")
    void fillFormWIthWrongFieldsTest() {
        open("/automation-practice-form");

        //после открытия страницы скрываем рекламу и футер
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");

        //заполняем таблицу
        $("#firstName").setValue("Albina");
        $("#lastName").setValue("Dobrova");
        $("#genterWrapper").$(byText("Female")).click();                //здесь выбор пола
        $("#userNumber").setValue("9872552206");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("January");
        $(".react-datepicker__year-select").selectOption("1986");
        $(".react-datepicker__day--002").click();
        $("#uploadPicture").uploadFromClasspath("cat.jpg");
        $("#submit").click();

        //проверка на все заполненные поля в финальной таблице
        //проверим, что полей всего 10
        $$("[class~=table] tbody tr").shouldHave(size(10));

        //проверка на все заполненные поля в финальной таблице
        $(".modal-content").shouldHave(text("Albina Dobrova"));
        $(".modal-content").shouldHave(text("Female"));
        $(".modal-content").shouldHave(text("9872552206"));
        $(".modal-content").shouldHave(text("02 January,1986"));
    }
}
