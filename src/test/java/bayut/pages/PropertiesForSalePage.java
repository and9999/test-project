package bayut.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Web elements and interactions for search results page.
 */
public class PropertiesForSalePage {

    WebDriver driver;
    WebDriverWait wait;

    public PropertiesForSalePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10);
    }

    @FindBy(css = "li[class='ef447dde'] article div[class='_4b74b8bb'] div[aria-label='Location']")
    List<WebElement> propertiesLocation;

    /**
     * Returns a list of all properties locations found on the 1st page of results
     */
    public List<String> getLocationOfProperties() {
        wait.until(ExpectedConditions.visibilityOfAllElements(propertiesLocation));
        List<String> allProperties = new ArrayList<>();
        for (WebElement property : propertiesLocation) {
            allProperties.add(property.getText());
            System.out.println(property.getText());
        }
        return allProperties;
    }
}
