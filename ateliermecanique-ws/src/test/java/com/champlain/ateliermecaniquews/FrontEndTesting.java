package com.champlain.ateliermecaniquews;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@SpringBootTest
public class FrontEndTesting {

    @BeforeEach()
    void setUp() {          // comment out the driver you don't use before testing

        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";

//        WebDriverManager.firefoxdriver().setup();
//        Configuration.browser = "firefox";
    }

    @Test
    public void getAllCustomers() {
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);
        $("button[value='Login']").click();
        sleep(1000);
        $("div[class='HomeOption").click();
        sleep(1000);
        $("p").shouldHave(text("CUSTOMER ACCOUNTS"));
        //Click on the docs link
        ElementsCollection links = $$("td");
        SelenideElement firstCustomer = links.get(0);
        firstCustomer.shouldHave(text("b7024d89-1a5e-4517-3gba-05178u7ar260"));
        sleep(1000);
    }

    @Test
    public void getCustomerAccountById() {
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);
        $("button[value='Login']").click();
        sleep(1000);
        $("div[class='HomeOption']").click();
        sleep(1000);

        String accountId = "b7024d89-1a5e-4517-3gba-05178u7ar260";
        SelenideElement accountLink = $$("td").findBy(text(accountId));
        accountLink.shouldBe(visible).click();
        $("p").shouldHave(text("CUSTOMER ACCOUNT DETAILS"));
    }

    @Test
    public void updateCustomerById() {
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);
        $("button[value='Login']").click();
        sleep(1000);
        $("div[class='HomeOption']").click();
        sleep(1000);

        String accountId = "b7024d89-1a5e-4517-3gba-05178u7ar260";
        SelenideElement accountLink = $$("td").findBy(text(accountId));
        accountLink.shouldBe(visible).click();
        $("p").shouldHave(text("CUSTOMER ACCOUNT DETAILS"));

        // Fill the form with new data
        $("input[name='firstName']").setValue("Jeff");
        $("input[name='lastName']").setValue("Doe");
        $("input[name='email']").setValue("john@example.com");
        $("input[name='phoneNumber']").setValue("123456789");
        $("button[type='submit']").click();

        open("http://localhost:3000/admin/customers");
        SelenideElement secondTd = $$("td").get(1);
        secondTd.shouldHave(text("Jeff"));
    }

}

