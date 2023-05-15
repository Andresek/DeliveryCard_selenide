package ru.netology;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

class DeliveryCardTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(3, "dd.MM.yyyy");


    @Test
    void formSubmissionManualInput() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        SelenideElement city = $("[data-test-id='city']");
        city.$("[class='input__control']").setValue("Йошкар-Ола");
        SelenideElement dateApp = $("[data-test-id='date']");
        dateApp.$("[class='input__control']").sendKeys(Keys.CONTROL + "a", Keys.DELETE); // очистка поля с датой
        dateApp.$("[class='input__control']").setValue(planningDate);
        SelenideElement name = $("[data-test-id='name']");
        name.$("[class='input__control']").setValue("Сидоров Иван");
        SelenideElement phone = $("[data-test-id='phone']");
        phone.$("[class='input__control']").setValue("+79021234567");
        SelenideElement agreement = $("[data-test-id='agreement']");
        agreement.$("[class='checkbox__box']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        Configuration.timeout = 15_000;
        SelenideElement notification = $("[data-test-id='notification']").shouldBe(visible);
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

  /*  @Test
    void formSubmissionEnteringAutoCompletion() {
        LocalDate date = LocalDate.now(); // получаем текущую дату
        date = date.plusDays(7); // добавляем 3 дня к текущей дате
        int dayOfMonth = date.getDayOfMonth();


        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        SelenideElement city = $("[data-test-id='city']");
        city.$("[class='input__control']").setValue("си");
        $$("[class = 'menu-item__control']").last().click();
        SelenideElement dateApp = $("[data-test-id='date']");
        dateApp.$("[class = 'icon-button__content']").click();
        SelenideElement days = $("[class = 'calendar__day']");
        days.$("[attr = 'role']", dayOfMonth).click();
    }*/


}

