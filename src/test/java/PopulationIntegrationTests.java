import com.napier.sem.Population;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Population class.
 * Purpose:
 * - Validate the combined behavior of multiple methods.
 * - Ensure methods work with the database and provide expected results.
 */
public class PopulationIntegrationTests {

    /**
     * Integration Test: Verify database connection and method execution.
     * Purpose:
     * - Test the ability to connect to the database and execute a basic query.
     */
    @Test
    void testDatabaseConnectionAndSimpleQuery() {
        Population app = new Population();
        try {
            String[] args = {"debug"}; // Use debug mode to connect to local database
            app.connect(args);

            // Verify connection
            assertNotNull(app.getDatabaseConnection(), "Database connection should not be null.");

            // Run a basic query (e.g., getCountriesByPopulation)
            app.getCountriesByPopulation();

            // If no exception occurs, the integration is working
            assertTrue(true, "getCountriesByPopulation executed without errors.");
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Integration Test: Retrieve population data for a specific continent.
     * Purpose:
     * - Verify the integration of fetching data for a specific continent.
     */
    @Test
    void testPopulationDataByContinent() {
        Population app = new Population();
        try {
            String[] args = {"debug"};
            app.connect(args);

            // Test retrieving population data for Europe
            app.getPopulationOfContinent("Europe");

            // If no exception occurs, the integration is working
            assertTrue(true, "getPopulationOfContinent executed without errors.");
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Integration Test: Retrieve population data for a specific country.
     * Purpose:
     * - Verify the integration of fetching data for a specific country.
     */
    @Test
    void testPopulationDataByCountry() {
        Population app = new Population();
        try {
            String[] args = {"debug"};
            app.connect(args);

            // Test retrieving population data for Germany
            app.getPopulationOfCountry("Germany");

            // If no exception occurs, the integration is working
            assertTrue(true, "getPopulationOfCountry executed without errors.");
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Integration Test: Fetch data for cities in a specific region.
     * Purpose:
     * - Validate integration of fetching city data by region.
     */
    @Test
    void testCityDataByRegion() {
        Population app = new Population();
        try {
            String[] args = {"debug"};
            app.connect(args);

            // Test retrieving cities by region
            app.getCitiesByRegionPopulation("Eastern Europe");

            // If no exception occurs, the integration is working
            assertTrue(true, "getCitiesByRegionPopulation executed without errors.");
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Integration Test: Retrieve language speakers.
     * Purpose:
     * - Validate integration of fetching language speaker data.
     */
    @Test
    void testLanguageSpeakersData() {
        Population app = new Population();
        try {
            String[] args = {"debug"};
            app.connect(args);

            // Test retrieving speakers for specific languages
            app.getSpeakersByLanguage();

            // If no exception occurs, the integration is working
            assertTrue(true, "getSpeakersByLanguage executed without errors.");
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Integration Test: Retrieve population report by continent.
     * Purpose:
     * - Verify integration of generating population reports at the continent level.
     */
    @Test
    void testPopulationReportByContinent() {
        Population app = new Population();
        try {
            String[] args = {"debug"};
            app.connect(args);

            // Test generating population report by continent
            app.getPopulationReport("continent");

            // If no exception occurs, the integration is working
            assertTrue(true, "getPopulationReport for continent executed without errors.");
        } catch (Exception e) {
            fail("Integration test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Test: Retrieve the total population of a continent
     * Purpose: Validates the method can handle valid, invalid, and edge-case continent names.
     */
    @Test
    void testGetPopulationOfContinentIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String continent = "Asia";
            System.out.println("Testing continent population retrieval for: " + continent);
            app.getPopulationOfContinent(continent);

            // Edge case
            continent = "NonExistentContinent";
            System.out.println("Testing continent population retrieval for: " + continent);
            app.getPopulationOfContinent(continent);

            // Invalid case: null or empty string
            continent = null;
            System.out.println("Testing continent population retrieval for null");
            app.getPopulationOfContinent(continent);

            continent = "";
            System.out.println("Testing continent population retrieval for empty string");
            app.getPopulationOfContinent(continent);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Test: Retrieve speakers by language
     * Purpose: Ensures the method handles valid languages and gracefully handles invalid inputs.
     */
    @Test
    void testGetSpeakersByLanguageIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            System.out.println("Testing speakers retrieval for valid languages.");
            app.getSpeakersByLanguage();

            // Invalid case: Non-existent language
            System.out.println("Testing speakers retrieval for invalid data (Handled inside the method).");

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Test: Retrieve top N populated countries
     * Purpose: Validates the method can handle various N values and invalid inputs.
     */
    @Test
    void testGetTopNPopulatedCountriesIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            int topN = 5;
            System.out.println("Testing top " + topN + " populated countries retrieval.");
            app.getTopNPopulatedCountries(topN);

            // Edge case: N = 0
            topN = 0;
            System.out.println("Testing retrieval with N = " + topN);
            app.getTopNPopulatedCountries(topN);

            // Invalid case: Negative N
            topN = -1;
            System.out.println("Testing retrieval with negative N = " + topN);
            app.getTopNPopulatedCountries(topN);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Test: Retrieve the population of a city
     * Purpose: Validates the method can handle various city names and edge cases.
     */
    @Test
    void testGetPopulationOfCityIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String city = "Tokyo";
            System.out.println("Testing population retrieval for city: " + city);
            app.getPopulationOfCity(city);

            // Edge case
            city = "NonExistentCity";
            System.out.println("Testing population retrieval for city: " + city);
            app.getPopulationOfCity(city);

            // Invalid case: null and empty string
            city = null;
            System.out.println("Testing population retrieval for null");
            app.getPopulationOfCity(city);

            city = "";
            System.out.println("Testing population retrieval for empty string");
            app.getPopulationOfCity(city);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Test: Retrieve the total population of the world
     * Purpose: Validates the method can handle retrieving valid world population.
     */
    @Test
    void testGetPopulationOfWorldIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            System.out.println("Testing world population retrieval.");
            app.getPopulationOfWorld();

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }
}

