
package com.champlain.ateliermecaniquews.zelenium;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.springframework.boot.test.context.SpringBootTest;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthFrontendTesting {


    @BeforeEach()
    void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
    }

//    @Test()
//    @Order(1)
//    public void instagramLogin() {
//        open("https://localhost:3000/");
//        $("a[href='/login']").click();
//        $("svg[data-icon='instagram']").click();
//        switchTo().window(1); // Switch to the newly opened window
//        sleep(5000);
//
//        // Perform actions on the Instagram login window
//        $("input[name='username']").setValue("testuseracms");
//        $("input[name='password']").setValue("testUser");
//        $("button[type='submit']").click();
//
//        $("button[class=' _acan _acap _acas _aj1- _ap30']").click();
//        $("div[aria-label='Allow']").click();
//
//        sleep(3000);
//        switchTo().window(0);
//        sleep(3000);
//
//        $("input[id='firstName']").setValue("John");
//        $("input[id='lastName']").setValue("Doe");
//        $("input[id='email']").setValue("JohnDoe@gmail.com");
//        $("input[id='phoneNumber']").setValue("555-235-4323");
//
//        $("button[type='submit']").click();
//        $("h2").shouldHave(Condition.text("John Doe"));
//        sleep(3000);
//
//    }

    @Test
    @Order(1)
    public void logout() {
        open("https://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);

        $("input[name='email']").setValue("admin@example.com");
        $("input[type='password']").setValue("Hello!");

        $("button[type='submit']").click();
        sleep(1000);

        $("a[href='/']").click();

        ElementsCollection h1Elements = $$("h1");
        SelenideElement secondH1 = h1Elements.get(1);
        secondH1.shouldHave(Condition.text("Revitalize your ride with expert care."));
    }

    @Test
    @Order(2)
    public void signUp(){
        open("https://localhost:3000/");
        $$("button").findBy(text("Sign up")).click();
        sleep(1000);

        $("input[name='firstName']").setValue("John");
        $("input[name='lastName']").setValue("Doe");
        $("input[name='email']").setValue("john.doe@example.com");
        $("input[name='phoneNumber']").setValue("123456789");
        $("input[name='password']").setValue("password");
        $("input[name='confirmPassword']").setValue("password");

        // Submit the form
        $("button[type='submit']").click();

        Alert alert = switchTo().alert();

        // Verify the alert message (customize as needed)
        String alertText = alert.getText();
        assert(alertText.contains("User registered successfully!"));

        alert.accept();


    }
}
    //for some reason, on the selenium test's driver, the google div doesn't show when clicking on the Google button
//    @Test
//    public void googleLogin(){
//        open("https://localhost:3000/");
//        $("a[href='/login']").click();
//        $("img[alt='Google Icon']").click();
//        sleep(10000);
//    }
//
//    @Test()
//    @Order(1)
//    public void instagramLogin(){
//        open("https://localhost:3000/");
//        $("a[href='/login']").click();
//        $("svg[data-icon='instagram']").click();
//        switchTo().window(1); // Switch to the newly opened window
//        sleep(5000);
//
//        // Perform actions on the Instagram login window
//        $("input[name='username']").setValue("testuseracms");
//        $("input[name='password']").setValue("testUser");
//        $("button[type='submit']").click();
//
//        $("button[class=' _acan _acap _acas _aj1- _ap30']").click();
//        $("div[aria-label='Allow']").click();
//
//        sleep(3000);
//        switchTo().window(0);
//        sleep(3000);
//
//        $("input[id='firstName']").setValue("John");
//        $("input[id='lastName']").setValue("Doe");
//        $("input[id='email']").setValue("JohnDoe@gmail.com");
//        $("input[id='phoneNumber']").setValue("555-235-4323");
//
//        $("button[type='submit']").click();
//        $("h2").shouldHave(Condition.text("John Doe"));
//        sleep(3000);
//
//    }
//
//    @Test
//    @Order(2)
//    public void logout(){
//        open("https://localhost:3000/");
//        $("a[href='/login']").click();
//        $("a[href='/']").click();
//
//        ElementsCollection h1Elements = $$("h1");
//        SelenideElement secondH1 = h1Elements.get(1);
//        secondH1.shouldHave(Condition.text("Revitalize your ride with expert care."));
//    }
//    //for some reason, on the selenium test's driver, the google div doesn't show when clicking on the google button
////    @Test
////    public void googleLogin(){
////        open("https://localhost:3000/");
////        $("a[href='/login']").click();
////        $("img[alt='Google Icon']").click();
////        sleep(10000);
////    }
//    //facebook password: testUser!
//    //facebook email : nipige6169@wenkuu.com
//
//    //for some reason, this button also doesn't work when clicking on it
////    @Test
////    public void facebookLogin(){
////        open("https://localhost:3000/");
////        $("a[href='/login']").click();
////        $("svg[data-icon='facebook']").click();
////        sleep(5000);
////    }
//
//
//}
