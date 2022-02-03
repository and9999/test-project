package bayut.tests.searchProperties;

import bayut.pages.HomePage;
import bayut.pages.PropertiesForSalePage;
import bayut.tests.BaseTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.*;
import org.junit.runner.RunWith;
import org.seleniumhq.jetty9.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SerenityRunner.class)
public class WhenSearchingForPropertiesTest extends BaseTest {

    HomePage home;
    PropertiesForSalePage propertiesForSalePage;

    private static final String SEARCH_LOCATION = "Dubai Marina";

    /**
     * Verifies that all displayed properties contain the selected location
     */
    @Test
    public void verifyIfDisplayedResultsAreInSelectedLocation() throws InterruptedException {
        home = new HomePage(driver);
        home.searchForPropertiesToBuy(SEARCH_LOCATION);

        propertiesForSalePage = new PropertiesForSalePage(driver);
        List<String> allProperties = propertiesForSalePage.getLocationOfProperties();
        for (String prop : allProperties) {
            Assert.assertTrue(prop.contains(SEARCH_LOCATION + ",")); //comma is in order to exclude locations containing something after Dubai Marina, ex: Dubai Marina Towers
        }
    }

    /**
     * Verifies if all the links in Popular searches under "To rent" - "Dubai Apartments" work correctly
     */
    @Test
    public void verifyIfPopularSearchesLinksInDubaiApartmentsAreValid() {
        home = new HomePage(driver);
        List<String> dubaiApartments = home.getAllLinksToRentDubaiApartments();
        Map<String, Integer> responseStatusByLink = new HashMap<>();

        RequestSpecification httpRequest = RestAssured.given();
        for (String link : dubaiApartments) {
            int response = httpRequest.get(link).getStatusCode();
            responseStatusByLink.put(link, response);
        }
        for (Map.Entry<String, Integer> entry : responseStatusByLink.entrySet()) {
            if (!entry.getValue().equals(HttpStatus.OK_200)) {
                System.out.println("invalid link: " + entry.getKey() + " - status code: " + entry.getValue());
            }
        }
        for (Map.Entry<String, Integer> entry : responseStatusByLink.entrySet()) {
            Assert.assertEquals(HttpStatus.OK_200, (int) entry.getValue());
        }
    }

    /**
     * Verifies if all the links in Popular searches under "To rent" - "Dubai Apartments" are in Dubai
     */
    @Test
    public void verifyIfPopularSearchesLinksInDubaiApartmentsAreInDubai() {
        home = new HomePage(driver);
        List<String> dubaiApartments = home.getAllLinksToRentDubaiApartments();

        for (String link : dubaiApartments) {
            if (!link.contains("dubai")) {
                System.out.println("link: " + link + " not in Dubai");
            }
            Assert.assertTrue(link.contains("dubai"));
        }
    }
}
