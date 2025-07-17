package com.sudha.ecommerce.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class ProductBrowsingPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public ProductBrowsingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Web Elements ---
    @FindBy(xpath = "//a[text()='Phones']")
    private WebElement phonesCategory;

    @FindBy(xpath = "//a[text()='Laptops']")
    private WebElement laptopsCategory;

    @FindBy(xpath = "//a[text()='Monitors']")
    private WebElement monitorsCategory;

    @FindBy(css = "#tbodyid .hrefch")
    private List<WebElement> productTitles;

    @FindBy(css = "#tbodyid .card a")
    private List<WebElement> productLinks;

    @FindBy(xpath = "//a[text()='Home']")
    private WebElement homeLink;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    @FindBy(xpath = "//a[text()='Contact']")
    private WebElement contactLink;

    @FindBy(css = "#tbodyid .card-title")
    private List<WebElement> productCards;

    @FindBy(css = ".name")
    private WebElement productDetailTitle;

    @FindBy(css = "h3.price-container")
    private WebElement productDetailPrice;

    @FindBy(css = "#more-information")
    private WebElement productDetailDescription; // optional if available

    // --- Actions ---
    public void clickCategory(String categoryName) {
        switch (categoryName.toLowerCase()) {
            case "phones":
                phonesCategory.click();
                break;
            case "laptops":
                laptopsCategory.click();
                break;
            case "monitors":
                monitorsCategory.click();
                break;
            default:
                throw new IllegalArgumentException("Unknown category: " + categoryName);
        }
        wait.until(ExpectedConditions.visibilityOfAllElements(productTitles));
    }

    public List<String> getDisplayedProductNames() {
        return productTitles.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public void clickOnFirstProduct() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
        productLinks.get(0).click();
        wait.until(ExpectedConditions.visibilityOf(productDetailTitle));
    }

    public String getProductDetailTitle() {
        return productDetailTitle.getText().trim();
    }

    public String getProductDetailPrice() {
        return productDetailPrice.getText().replace("$", "").trim();
    }

    public void clickHomeLink() {
        homeLink.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(productTitles));
    }

    public void clickCartLink() {
        cartLink.click();
    }

    public void clickContactLink() {
        contactLink.click();
    }
    
    public boolean isContactModalVisible() {
        try {
            WebElement contactModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal")));
            return contactModal.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}