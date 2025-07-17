package com.sudha.ecommerce.pages;

import com.sudha.ecommerce.dto.Product;
import com.sudha.ecommerce.store.TestDataStore;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Locators ---
    private By navLinks = By.cssSelector("#navbarExample .nav-link");
    private By productLinks = By.cssSelector("#tbodyid a.hrefch");
    private By productTitle = By.className("name");
    private By productPrice = By.cssSelector("h3.price-container");
    private By productImage = By.cssSelector(".carousel-inner .item.active img");
    private By addToCartBtn = By.xpath("//*[@id='tbodyid']/div[2]/div/a");
    private By homeLink = By.xpath("//*[@id='navbarExample']/ul/li[1]/a");
    private By logoutBtn = By.id("logout2");
    private By cartLink = By.id("cartur");
    private By contactLink = By.xpath("//a[text()='Contact']");
    private By aboutLink = By.xpath("//a[text()='About us']");
    private By productRows = By.cssSelector("tr.success");

    // --- Actions ---
    public void openAndLogin() {
        new LoginPage(driver).loginWithValidUser();
    }

    public List<String> getTopNavTexts() {
        return driver.findElements(navLinks).stream()
                .filter(WebElement::isDisplayed)
                .map(e -> e.getText().replace("(current)", "").trim())
                .collect(Collectors.toList());
    }

    public List<Product> addRandomProductsToCart(int count) {
        Set<Product> selectedProducts = new LinkedHashSet<>();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));

        List<String> hrefs = driver.findElements(productLinks).stream()
                .map(el -> el.getAttribute("href"))
                .distinct()
                .collect(Collectors.toList());

        Collections.shuffle(hrefs);

        for (String href : hrefs) {
            if (selectedProducts.size() >= count) break;

            driver.navigate().to(href);
            wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));

            String name = driver.findElement(productTitle).getText().trim();
            String imageUrl = driver.findElement(productImage).getAttribute("src");
            String price = driver.findElement(productPrice).getText().split("\\*")[0].replace("$", "").trim();

            Product product = new Product(name, imageUrl, price);
            if (selectedProducts.add(product)) {
                clickAddToCart();
            }

            driver.navigate().back();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
        }

        return new ArrayList<>(selectedProducts);
    }

    public void clickAddToCart() {
        driver.findElement(addToCartBtn).click();
        wait.until(ExpectedConditions.alertIsPresent()).accept();
        clickHome();
    }

    public void clickHome() {
        driver.findElement(homeLink).click();
    }

    public void clickLogout() {
        driver.findElement(logoutBtn).click();
    }

    public void clickCartLink() {
        driver.findElement(cartLink).click();
    }

    public boolean isProductInCart(Product expectedProduct) {
        wait.until(ExpectedConditions.presenceOfElementLocated(productRows));

        for (WebElement row : driver.findElements(productRows)) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() < 3) continue;

            String name = cells.get(1).getText().trim();
            String price = cells.get(2).getText().trim();
            String image = cells.get(0).findElement(By.tagName("img")).getAttribute("src");

            if (name.equalsIgnoreCase(expectedProduct.getName()) &&
                    price.equalsIgnoreCase(expectedProduct.getPrice()) &&
                    image.contains(expectedProduct.getImageUrl())) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteProductFromCart(String productName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(productRows));
        for (WebElement row : driver.findElements(productRows)) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 4 && cells.get(1).getText().trim().equalsIgnoreCase(productName)) {
                cells.get(3).findElement(By.tagName("a")).click();
                return true;
            }
        }
        return false;
    }

    public void openContactModal() {
        By contactLinkLocator = By.xpath("//a[text()='Contact']");
        wait.until(ExpectedConditions.elementToBeClickable(contactLinkLocator)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("exampleModal"))); // Contact modal ID
    }

    public void fillContactForm(String email, String name, String message) {
        WebElement modal = driver.findElement(By.id("exampleModal"));
        modal.findElement(By.id("recipient-email")).sendKeys(email);
        modal.findElement(By.id("recipient-name")).sendKeys(name);
        modal.findElement(By.id("message-text")).sendKeys(message);
    }

    public void openAboutModal() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='About us']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("videoModal")));
    }

    public String getAboutModalPoster() {
        return driver.findElement(By.id("videoModal"))
                     .findElement(By.id("example-video_html5_api"))
                     .getAttribute("poster");
    }
}