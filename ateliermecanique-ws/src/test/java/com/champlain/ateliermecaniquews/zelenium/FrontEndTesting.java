package com.champlain.ateliermecaniquews.zelenium;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;
import org.springframework.boot.test.context.SpringBootTest;


import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.mockito.ArgumentMatchers.contains;

@SpringBootTest
public class FrontEndTesting {

    @BeforeEach()
    void setUp() {          // comment out the driver you don't use before testing

       // WebDriverManager.chromedriver().setup();
        //Configuration.browser = "chrome";

        WebDriverManager.firefoxdriver().setup();
        Configuration.browser = "firefox";
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

        String accountId = "cdff4g82-9e8h-7532-1qws-75321v5ar963";
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
        String accountId = "yzab8cd5-3e6f-8796-2abi-96330c6bw164";
        SelenideElement accountLink = $$("td").findBy(text(accountId));
        accountLink.shouldBe(visible).click();
        $("p").shouldHave(text("CUSTOMER ACCOUNT DETAILS"));

        $("input[name='firstName']").setValue("Jeff");
        $("input[name='lastName']").setValue("Doe");
        $("input[name='email']").setValue("john@example.com");
        $("input[name='phoneNumber']").setValue("123456789");
        $("button[type='submit']").click();
    }

    @Test
    public void getVehiclesForCustomer() {
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);
        $("button[value='Login']").click();
        sleep(1000);
        $("div[class='HomeOption']").click();
        sleep(1000);
        String customerId = "lmno8p45-3q6r-8791-2abc-96325t5ar159";
        SelenideElement customerLink = $$("td").findBy(text(customerId));
        customerLink.shouldBe(visible).click();
        $("p").shouldHave(text("CUSTOMER ACCOUNT DETAILS"));

        SelenideElement vehiclesLink = $$("a[class*='sidebar-link']").findBy(text("Vehicle List"));
        vehiclesLink.shouldBe(visible).click();
        $("p").shouldHave(text("VEHICLE LIST"));
    }

    @Test
    public void getVehicleById() {
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        Selenide.sleep(1000);
        $("button[value='Login']").click();
        Selenide.sleep(1000);

        $("div[class='HomeOption']").click();
        Selenide.sleep(1000);

        String customerId = "tuvw8x45-3y6z-8794-2abf-96328w6bu162";
        String vehicleId = "0f8c5e36-9e94-4c6d-921d-29d7e2e923b5";

        $$("td").findBy(text(customerId)).click();
        Selenide.sleep(2000);

        $$("a[class*='sidebar-link']").findBy(text("Vehicle List")).click();
        Selenide.sleep(2000);

        SelenideElement vehicleLink = $$("table tbody tr td a").findBy(href(contains(vehicleId)));
        if (vehicleLink.exists()) {
            vehicleLink.click();
        } else {
            throw new NoSuchElementException("Vehicle link not found for ID: " + vehicleId);
        }

        Selenide.sleep(2000);
        $("p").shouldHave(text("VEHICLE DETAILS"));
    }

    @Test
    public void updateCustomerVehicle() {
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        Selenide.sleep(1000);
        $("button[value='Login']").click();
        Selenide.sleep(1000);
        $("div[class='HomeOption']").click();
        Selenide.sleep(1000);

        String customerId = "tuvw8x45-3y6z-8794-2abf-96328w6bu162";
        String vehicleId = "0f8c5e36-9e94-4c6d-921d-29d7e2e923b5";

        SelenideElement customerLink = $$("td").findBy(text(customerId));
        customerLink.shouldBe(visible).click();
        Selenide.sleep(1000);

        $$("a[class*='sidebar-link']").findBy(text("Vehicle List")).click();
        Selenide.sleep(1000);

        SelenideElement vehicleLink = $$("table tbody tr td a").findBy(href(contains(vehicleId)));
        if (vehicleLink.exists()) {
            vehicleLink.click();
        } else {
            throw new NoSuchElementException("Vehicle link not found for ID: " + vehicleId);
        }

        $("p").shouldHave(text("VEHICLE DETAILS"));

        $("input[name='make']").setValue("NewMake");
        $("input[name='model']").setValue("NewModel");
        $("input[name='year']").setValue("2023");

        $("select[name='transmissionType']").selectOption("Manual");
        $("input[name='mileage']").setValue("1000");

        $("button[class='save-button']").click();
        Selenide.sleep(2000);
    }
    @Test
    public void deleteCustomer(){
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);
        $("button[value='Login']").click();
        sleep(1000);
        $("div[class='HomeOption']").click();
        sleep(1000);
        String accountId = "eggh9i83-7j8k-4567-4tyu-98765z5ar741";
        SelenideElement accountLink = $$("td").findBy(text(accountId));
        accountLink.shouldBe(visible).click();
        $("p").shouldHave(text("CUSTOMER ACCOUNT DETAILS"));

        $(".delete-button").click();


        $$(".confirmation-box button").findBy(text("Yes")).click();

        sleep(1000);
        switchTo().alert().accept();

        $("p").shouldHave(text("CUSTOMER ACCOUNTS"));
    }
    @Test
    public void addVehicleToCustomer(){
        open("http://localhost:3000/");
        $("a[href='/login']").click();
        sleep(1000);
        $("button[value='Login']").click();
        sleep(1000);
        $("div[class='HomeOption']").click();
        sleep(1000);
        String customerId = "lmno8p45-3q6r-8791-2abc-96325t5ar159";
        SelenideElement customerLink = $$("td").findBy(text(customerId));
        customerLink.shouldBe(visible).click();
        $("p").shouldHave(text("CUSTOMER ACCOUNT DETAILS"));

        SelenideElement vehiclesLink = $$("a[class*='sidebar-link']").findBy(text("Vehicle List"));
        vehiclesLink.shouldBe(visible).click();
        $("p").shouldHave(text("VEHICLE LIST"));

        $("button.add-button").click();
        sleep(1000);

        $("form.vehicle-details-form").shouldBe(visible);

        $("input[name='make']").setValue("Chevrolet");
        $("input[name='model']").setValue("Cruze");
        $("input[name='year']").setValue("2016");
        $("select[name='transmissionType']").selectOption("Manual");
        $("input[name='mileage']").setValue("50000");

        $("button.save-button").click();
        sleep(1000);

    }

}








