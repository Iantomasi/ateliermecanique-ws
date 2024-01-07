package com.champlain.ateliermecaniquews.zelenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import static com.codeborne.selenide.Selenide.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthFrontendTesting {


    @BeforeEach()
    void setUp() {          // comment out the driver you don't use before testing

        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";

        //WebDriverManager.firefoxdriver().setup();
        //Configuration.browser = "firefox";
    }

    @Test()
    @Order(1)
    public void instagramLogin(){
        open("https://localhost:3000/");
        $("a[href='/login']").click();
        $("svg[data-icon='instagram']").click();
        switchTo().window(1); // Switch to the newly opened window
        sleep(5000);

        // Perform actions on the Instagram login window
        $("input[name='username']").setValue("testuseracms");
        $("input[name='password']").setValue("testUser");
        $("button[type='submit']").click();

        $("button[class=' _acan _acap _acas _aj1- _ap30']").click();
        $("div[aria-label='Allow']").click();

        sleep(3000);
        switchTo().window(0);
        sleep(3000);

        $("input[id='firstName']").setValue("John");
        $("input[id='lastName']").setValue("Doe");
        $("input[id='email']").setValue("JohnDoe@gmail.com");
        $("input[id='phoneNumber']").setValue("555-235-4323");

        $("button[type='submit']").click();
        $("h2").shouldHave(Condition.text("John Doe"));
        sleep(3000);

    }

    @Test
    @Order(2)
    public void logout(){
        open("https://localhost:3000/");
        $("a[href='/login']").click();
        $("a[href='/']").click();

        ElementsCollection h1Elements = $$("h1");
        SelenideElement secondH1 = h1Elements.get(1);
        secondH1.shouldHave(Condition.text("Revitalize your ride with expert care."));
    }
    //for some reason, on the selenium test's driver, the google div doesn't show when clicking on the google button
//    @Test
//    public void googleLogin(){
//        open("https://localhost:3000/");
//        $("a[href='/login']").click();
//        $("img[alt='Google Icon']").click();
//        sleep(10000);
//    }
    //facebook password: testUser!
    //facebook email : nipige6169@wenkuu.com

    //for some reason, this button also doesn't work when clicking on it
//    @Test
//    public void facebookLogin(){
//        open("https://localhost:3000/");
//        $("a[href='/login']").click();
//        $("svg[data-icon='facebook']").click();
//        sleep(5000);
//    }


}
