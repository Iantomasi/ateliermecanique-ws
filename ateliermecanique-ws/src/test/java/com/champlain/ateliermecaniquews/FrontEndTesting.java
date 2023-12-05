package com.champlain.ateliermecaniquews;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

@SpringBootTest
public class FrontEndTesting {

    @BeforeEach()
    void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
    }

    @Test
    public void getAllCustomers() {
        open("http://localhost:3001/");
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
}

