import com.napier.sem.Population;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class PopulationIntegrationTests {
    /**
     * Verifies:
     * - The connection is not null.
     * - The connection is valid for a small timeout period.
     */
    @Test
    void testDatabaseConnectionIntegration() {
        Population app = new Population(); // Create an instance of Population

        try {
            // Attempt to connect to the database
            String[] args = {"debug"}; // Pass "debug" to simulate local connection
            app.connect(args);

            // Assert that the connection is successfully established
            assertNotNull(app.getDatabaseConnection(), "Database connection should not be null.");

            // Optionally, you can add a test query to verify the connection works
            assertTrue(app.getDatabaseConnection().isValid(5), "Database connection should be valid.");

        } catch (Exception e) {
            // If any exception occurs, fail the test and log the error
            fail("Connection test failed due to an exception: " + e.getMessage());
        } finally {
            // Disconnect from the database after the test
            app.disconnect();
        }
    }

    /**
     * Verifies: The method runs without throwing any exceptions.
     */
    @Test
    void testGetCountriesByPopulation() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Call method to generate report
            app.getCountriesByPopulation();

            // No exception means success
            assertTrue(true);
        } finally {
            app.disconnect();
        }
    }

    /**
     * Verifies:
     * - The method runs without throwing any exceptions.
     * - Specifically checks the functionality for a known country -> "Ukraine".
     */
    @Test
    void testGetCitiesByCountryPopulation() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Call method to fetch cities by population in Ukraine
            app.getCitiesByCountryPopulation("Ukraine");

            // No exception means success
            assertTrue(true);
        } finally {
            app.disconnect();
        }
    }

    /**
     * This test verifies:
     * - The method runs without throwing any exceptions.
     * - Specifically checks functionality for a known region -> "Western Europe".
     */
    @Test
    void testGetCountriesByRegionPopulation() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Call the method to fetch countries by region population
            app.getCountriesByRegionPopulation("Western Europe");

            // No exception means success
            assertTrue(true);
        } finally {
            app.disconnect();
        }
    }
    /**
     * This test verifies:
     * - The method runs without throwing any exceptions.
     * - Specifically checks functionality for the top 5 cities globally.
     */
    @Test
    void testGetTopNCitiesByPopulation() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Call the method to fetch top N cities
            app.getTopNCitiesByPopulation(5);

            // No exception means success
            assertTrue(true);
        } finally {
            app.disconnect();
        }
    }

    /**
     * Verifies:
     * - The method runs without throwing any exceptions.
     * - Specifically checks functionality for a known district -> "California"
     */
    @Test
    void testGetCitiesByDistrictPopulation() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Call the method to fetch cities by district
            app.getCitiesByDistrictPopulation("California");

            // No exception means success
            assertTrue(true);
        } finally {
            app.disconnect();
        }
    }

    /**
     * Verifies:
     * - Method execution for a valid continent and limit.
     * - Graceful handling of nonexistent continents (e.g., invalid input).
     */
    @Test
    void testGetTopNPopulatedCountriesByContinent() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Test with a valid continent ("Asia") and limit
            app.getTopNPopulatedCountriesByContinent("Asia", 5);
            assertTrue(true, "The method should execute without exceptions for valid input.");

            // Test with a nonexistent continent
            app.getTopNPopulatedCountriesByContinent("NonexistentContinent", 5);
            assertTrue(true, "The method should handle invalid continent gracefully.");
        } finally {
            // Ensure the database connection is closed
            app.disconnect();
        }
    }

    /**
     * Verifies:
     * - Execution for a valid continent.
     * - Handling of nonexistent continents or invalid input.
     */
    @Test
    void testGetCitiesByContinentPopulation() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Test with a valid continent
            app.getCitiesByContinentPopulation("Asia");
            assertTrue(true, "The method should execute successfully for valid input.");

            // Test with a nonexistent continent
            app.getCitiesByContinentPopulation("InvalidContinent");
            assertTrue(true, "The method should handle invalid input gracefully.");
        } finally {
            // Ensure the database connection is closed
            app.disconnect();
        }
    }

    /**
     * Verifies: Methods should handle empty tables without throwing exceptions.
     */
    @Test
    void testEmptyDatabase() {
        Population app = new Population();
        try {
            // Connect to the database
            app.connect(new String[]{"debug"});

            // Assume the database has no data; these methods should not throw exceptions
            app.getCountriesByPopulation();
            app.getCitiesByCountryPopulation("NonexistentCountry");
            app.getTopNPopulatedCountries(10);

            // If no exception is thrown, the test passes
            assertTrue(true, "Methods should handle empty database gracefully.");
        } finally {
            // Ensure the database connection is closed
            app.disconnect();
        }
    }


}
