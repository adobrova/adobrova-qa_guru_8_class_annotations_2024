import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class TextBoxTests {

    @BeforeAll
    static void beforeAll(){
        Configuration.browserSize = "1980x1080";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

        // перед коммитом удалить/закомментировать/поменять на false
        // при удалённом запуске тестов - браузер останется висеть открытым и будет занимать ресурсы
        //Configuration.holdBrowserOpen = true;
    }


    @Test
    void fillFormTest() {
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


     // нижеописанный метод рабочий, но на странице баг и при очищении поля календаря
     // возникает белый экране
     // здесь поле очищаем
     //   $("#dateOfBirthInput").sendKeys(Keys.CONTROL + "A");
     //   $("#dateOfBirthInput").sendKeys(Keys.BACK_SPACE);
     // а здесь ставим свое значение
     //   $("#dateOfBirthInput").sendKeys(Keys."02 Jan 1986");

        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("January");
        $(".react-datepicker__year-select").selectOption("1986");
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





      //  $("#city").setValue("Del").pressEnter();        //выбор города в выбранном штате в индии

        $("#submit").click();

        //проверка на все заполненные поля в финальной таблице
        //проверим, что полей всего 10
        $$("[class~=table] tbody tr").shouldHave(size(10));

        //проверка на все заполненные поля в финальной таблице
        $(".modal-content").shouldHave(text("Albina Dobrova"));
        $(".modal-content").shouldHave(text("rezolventa86@rambler.ru"));
        $(".modal-content").shouldHave(text("Female"));
        $(".modal-content").shouldHave(text("9872552206"));
        $(".modal-content").shouldHave(text("02 January,1986"));
        $(".modal-content").shouldHave(text("Computer Science"));
        $(".modal-content").shouldHave(text("Sports, Reading"));
        $(".modal-content").shouldHave(text("cat.jpg"));
        $(".modal-content").shouldHave(text("Ufa, Russia"));
        $(".modal-content").shouldHave(text("NCR Delhi"));






    }


}
