package ru.netology;

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


    @Test
    void formSubmission()  {

        LocalDate date = LocalDate.now(); // получаем текущую дату
        date = date.plusDays(3); // добавляем 3 дня к текущей дате
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // необходимый формат даты для заяки
        String data = dateFormatter.format(date);

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        SelenideElement city = $("[data-test-id='city']");
        city.$("[class='input__control']").setValue("Йошкар-Ола");
        SelenideElement dateApp = $("[data-test-id='date']");
        dateApp.$("[class='input__control']").sendKeys(Keys.CONTROL + "a", Keys.DELETE); // очистка поля с датой
        dateApp.$("[class='input__control']").setValue(data);
        SelenideElement name = $("[data-test-id='name']");
        name.$("[class='input__control']").setValue("Сидоров Иван");
        SelenideElement phone = $("[data-test-id='phone']");
        phone.$("[class='input__control']").setValue("+79021234567");
        SelenideElement agreement = $("[data-test-id='agreement']");
        agreement.$("[class='checkbox__box']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        Configuration.timeout = 15_000;
        SelenideElement notification = $("[data-test-id='notification']").shouldBe(visible);
    }
}

