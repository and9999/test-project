package bayut.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Web elements and interactions for home page.
 */
public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    private static final String HOME_PAGE = "https://www.bayut.com/";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(css = "div[class*='b9e5541a'] ul input")
    WebElement locationBox;

    @FindBy(css = "div[class*='_325092f0 '] div div")
    WebElement purposeBtn;

    @FindBy(css = "div[role='listbox'] button[aria-label='Buy']")
    WebElement buyBtn;

    @FindBy(css = "a[class='c3901770 f6d94e28']")
    WebElement findBtn;

    @FindBy(css = "div[class*='d8530318']")
    List<WebElement> rentSaleBtn;

    @FindBy(xpath = "(//div[@class='_2f838ff4 _5b112776 _29dd7f18'])[3]")
    WebElement viewAllBtn;

    @FindBy(xpath = "//a[@title='Apartments for rent in Dubai']/parent::div/following-sibling::ul/li/a")
    List<WebElement> dubaiApartmentsRentLinks;

    /**
     * Searches in the given location for properties to buy
     */
    public void searchForPropertiesToBuy(String locationToSearch) throws InterruptedException {
        driver.get(HOME_PAGE);

        locationBox.sendKeys(locationToSearch);
        wait.until(ExpectedConditions.textToBePresentInElementValue(locationBox, locationToSearch));// wait for the entire text to be typed
        locationBox.sendKeys(Keys.ENTER);

        purposeBtn.click();
        buyBtn.click();

        findBtn.click();
    }

    /**
     * Gets all the links in Popular searches under "To rent" - "Dubai Apartments"
     */
    public List<String> getAllLinksToRentDubaiApartments() {
        driver.get(HOME_PAGE);

        for (WebElement element : rentSaleBtn) {
            if (element.getText().equals("To Rent")) {
                element.click();
            }
        }

        wait.until(ExpectedConditions.visibilityOf(viewAllBtn)).click();

        List<String> dubaiApartments = new ArrayList<>();
        for (WebElement link : dubaiApartmentsRentLinks) {
            dubaiApartments.add(link.getAttribute("href"));
        }
        return dubaiApartments;
    }
}
