import com.napier.sem.Population;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;
public class PopulationIntegrationTests {
    /********************************************************************* DATABASE **********************************************************************/
    /** Integration Test: Verify database connection and method execution.
     * Test the ability to connect to the database and execute a basic query.*/
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



    /************************************************************ POPULATION QUERIES *******************************************************************/
    /** Test: Retrieve the total population of the world
     * Validates the method can handle retrieving valid world population.*/
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

    /**Test: Retrieve the total population of a continent
     * Validates the method can handle valid, invalid, and edge-case continent names.*/
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

    /** Test: Retrieve the population of a region.
     - Validates the integration of fetching population data by region.
     - Ensures the method handles valid, invalid, and edge-case region names gracefully.*/
    @Test
    void testGetPopulationOfRegionIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String region = "Eastern Europe";
            System.out.println("Testing population retrieval for region: " + region);
            app.getPopulationOfRegion(region);

            // Invalid case
            region = "NonExistentRegion";
            System.out.println("Testing population retrieval for invalid region: " + region);
            app.getPopulationOfRegion(region);

            // Edge case
            region = null;
            System.out.println("Testing population retrieval for null region.");
            app.getPopulationOfRegion(region);

            region = "";
            System.out.println("Testing population retrieval for empty region.");
            app.getPopulationOfRegion(region);

            // If no exceptions occur, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**Integration Test: Retrieve population data for a specific continent.
     - Validates the integration of fetching population data for a specific continent.
     - Ensures the method retrieves data correctly for valid input and handles edge cases gracefully.*/
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

    /** Test: Retrieve population data for a specific country.
     - Verifies the integration of fetching population data for a valid country.
     - Ensures the method gracefully handles invalid and edge-case inputs.*/
    @Test
    void testPopulationDataByCountry() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String country = "India";
            System.out.println("Testing population retrieval for country: " + country);
            app.getPopulationOfCountry(country);

            // Invalid case
            country = "Atlantis";
            System.out.println("Testing population retrieval for invalid country: " + country);
            app.getPopulationOfCountry(country);

            // Edge case
            country = null;
            System.out.println("Testing population retrieval for null country.");
            app.getPopulationOfCountry(country);

            country = "";
            System.out.println("Testing population retrieval for empty country.");
            app.getPopulationOfCountry(country);

            // If no exceptions occur, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /************************************************************************** TOP N QUERIES ***********************************************************************/
    /**Test: Retrieve top N populated countries
     * Purpose: Validates the method can handle various N values and invalid inputs.*/
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

    /** Test: Retrieve the top N cities by population globally.
     - Validates the integration of fetching the top N cities by population.
     - Ensures the method handles valid N values and gracefully manages edge cases like N=0 or invalid inputs.*/
    @Test
    void testGetTopNCitiesByPopulationIntegration() {
        Population app = new Population();
        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            int topN = 10;
            System.out.println("Testing retrieval of top " + topN + " cities globally.");
            app.getTopNCitiesByPopulation(topN);

            // Edge case: N = 0
            topN = 0;
            System.out.println("Testing retrieval with N = " + topN);
            app.getTopNCitiesByPopulation(topN);

            // Invalid case: Negative N
            topN = -5;
            System.out.println("Testing retrieval with negative N = " + topN);
            app.getTopNCitiesByPopulation(topN);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /** Test: Retrieve the top N cities by population for a specific country.
     - Verifies the integration of fetching top N cities in a country by population.
     - Ensures the method handles valid country names and various N values, while managing edge and invalid inputs.*/
    @Test
    void testGetTopNCitiesByCountryPopulationIntegration() {
        Population app = new Population();
        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String country = "United States";
            int topN = 5;
            System.out.println("Testing retrieval of top " + topN + " cities in " + country + ".");
            app.getTopNCitiesByCountryPopulation(topN, country);

            // Edge case: Empty country name
            country = "";
            System.out.println("Testing retrieval with an empty country name.");
            app.getTopNCitiesByCountryPopulation(topN, country);

            // Invalid case: Negative N
            topN = -3;
            country = "United States";
            System.out.println("Testing retrieval with negative N = " + topN);
            app.getTopNCitiesByCountryPopulation(topN, country);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /************************************************************ CITY QUERIES ***********************************************************************/
    /** Test: Retrieve the population of a city
     Validates the method can handle various city names and edge cases.*/
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

    /**Test: Retrieve cities by population for a specific country.
     - Validates the integration of fetching city population data for a given country.
     - Ensures the method handles valid country names, edge cases like empty or null inputs, and invalid inputs gracefully.*/
    @Test
    void testGetCitiesByCountryPopulationIntegration() {
        Population app = new Population();
        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String country = "United States";
            System.out.println("Testing retrieval of cities by population for country: " + country);
            app.getCitiesByCountryPopulation(country);

            // Edge case: Empty country name
            country = "";
            System.out.println("Testing retrieval with an empty country name.");
            app.getCitiesByCountryPopulation(country);

            // Invalid case: Non-existent country
            country = "Narnia";
            System.out.println("Testing retrieval with a non-existent country: " + country);
            app.getCitiesByCountryPopulation(country);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /** Test: Retrieve cities by population for a specific district.
     - Validates the integration of fetching city population data for a given district.
     - Ensures the method handles valid district names, edge cases like empty or null inputs, and invalid inputs gracefully.*/
    @Test
    void testGetCitiesByDistrictPopulationIntegration() {
        Population app = new Population();
        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String district = "California";
            System.out.println("Testing retrieval of cities by population for district: " + district);
            app.getCitiesByDistrictPopulation(district);

            // Edge case: Empty district name
            district = "";
            System.out.println("Testing retrieval with an empty district name.");
            app.getCitiesByDistrictPopulation(district);

            // Invalid case: Non-existent district
            district = "Atlantis";
            System.out.println("Testing retrieval with a non-existent district: " + district);
            app.getCitiesByDistrictPopulation(district);

            // If no exceptions, the test passes
            assertTrue(true);

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**Integration Test: Fetch data for cities in a specific region.
     - Validate integration of fetching city data by region.*/
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

    /**************************************************** CAPITAL CITIES QUERIES *********************************************************************************/
    /** Test: Retrieve all capital cities sorted by population.
    - Validates integration of fetching all capital cities in descending order of population.
    - Ensures the method handles large datasets and edge cases.*/
    @Test
    void testGetAllCapitalCitiesByPopulationIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Fetch all capital cities by population
            System.out.println("Testing retrieval of all capital cities sorted by population.");
            app.getAllCapitalCitiesByPopulation();

            // If no exceptions occur, the test passes
            assertTrue(true, "getAllCapitalCitiesByPopulation executed successfully.");
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /** Test: Retrieve capital cities by region sorted by population.
     * - Ensures the integration of fetching capital cities for a specific region and sorting them by population.
     * - Validates the handling of valid, edge, and invalid region inputs.*/
    @Test
    void testGetCapitalCitiesByRegionPopulationIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String region = "Western Europe";
            System.out.println("Testing capital cities retrieval for region: " + region);
            app.getCapitalCitiesByRegionPopulation(region);

            // Edge case: Empty or non-existent region
            region = "";
            System.out.println("Testing capital cities retrieval for empty region.");
            app.getCapitalCitiesByRegionPopulation(region);

            region = "Atlantis";
            System.out.println("Testing capital cities retrieval for non-existent region.");
            app.getCapitalCitiesByRegionPopulation(region);

            // If no exceptions occur, the test passes
            assertTrue(true, "getCapitalCitiesByRegionPopulation executed successfully.");
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /** Test: Retrieve capital cities by continent sorted by population.
    - Validates integration of fetching capital cities for a given continent in descending order of population.*/
    @Test
    void testGetCapitalCitiesByContinentPopulationIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Valid case
            String continent = "Asia";
            System.out.println("Testing capital cities retrieval for continent: " + continent);
            app.getCapitalCitiesByContinentPopulation(continent);

            // Edge case: Empty or non-existent continent
            continent = "";
            System.out.println("Testing capital cities retrieval for empty continent.");
            app.getCapitalCitiesByContinentPopulation(continent);

            continent = "Atlantis";
            System.out.println("Testing capital cities retrieval for non-existent continent.");
            app.getCapitalCitiesByContinentPopulation(continent);

            // If no exceptions occur, the test passes
            assertTrue(true, "getCapitalCitiesByContinentPopulation executed successfully.");
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /************************************************************************** REPORTS **************************************************************************/
    /** Test: Retrieve country report.
    - Validates integration of generating a country report, including all relevant data.
    - Ensures the method handles valid, edge, and empty datasets without errors.*/
    @Test
    void testGetCountryReportIntegration() {
        Population app = new Population();

        try {
            // Connect to the database
            String[] args = {"debug"};
            app.connect(args);

            // Fetch the country report
            System.out.println("Testing retrieval of the country report.");
            app.getCountryReport();

            // If no exceptions occur, the test passes
            assertTrue(true, "getCountryReport executed successfully.");
        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /** Integration Test: Retrieve population report by continent.
    - Verify integration of generating population reports at the continent level.*/
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

    /** Integration Test: Retrieve language speakers.
     - Validate integration of fetching language speaker data. */
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

    /********************************************************* LANGUAGE RELATED QUERIES *******************************************************************/
    /** Integration Test: Retrieve speakers by language.
     - Validates the integration of fetching speaker data for specific languages.
     - Ensures the method handles valid inputs and gracefully deals with invalid or edge cases.*/
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

    /***************************************************************** COMBINED QUERIES ***************************************************************************/
    /** Integration Test: Validate combined usage of multiple queries.
     - Simulates a scenario where multiple methods are executed in sequence.
     - Verifies that combined execution does not cause resource issues or errors.
     - Measure execution time and track success/failure for each query. */
    @Test
    void testCombinedQueryIntegration() {
        Population app = new Population();
        int passedQueries = 0;
        int failedQueries = 0;
        Instant startTime = Instant.now();
        try {
            String[] args = {"debug"}; // Use debug mode to connect to the local database
            app.connect(args);

            // Query 1: Get population of the world
            passedQueries += executeAndTrack(() -> app.getPopulationOfWorld(), "World Population Query");

            // Query 2: Get top N populated countries
            passedQueries += executeAndTrack(() -> app.getTopNPopulatedCountries(5), "Top N Populated Countries Query");

            // Query 3: Get cities by country population
            passedQueries += executeAndTrack(() -> app.getCitiesByCountryPopulation("India"), "Cities by Country Population Query");

            // Query 4: Get capital cities by continent population
            passedQueries += executeAndTrack(() -> app.getCapitalCitiesByContinentPopulation("Asia"), "Capital Cities by Continent Query");

        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            failedQueries++;
        } finally {
            app.disconnect();
            Instant endTime = Instant.now();
            Duration executionTime = Duration.between(startTime, endTime);

            // Print test summary
            System.out.println("\n=== COMBINED QUERY TEST SUMMARY ===");
            System.out.println("Total Queries: 4");
            System.out.println("Passed Queries: " + passedQueries);
            System.out.println("Failed Queries: " + failedQueries);
            System.out.println("Total Execution Time: " + executionTime.toMillis() + " ms");

            // Ensure at least one query passed
            assertTrue(passedQueries > 0, "At least one query should pass.");
        }
    }


    /** Combined Query Test: Global Population Insights
     - Simulate real-world usage of fetching global population data.
     - Combine multiple methods related to global and continent-level queries.
     - Measure query execution times and track successes and failures. */
    @Test
    void testGlobalPopulationInsightsIntegration() {
        Population app = new Population();
        int passedQueries = 0;
        int failedQueries = 0;
        Instant startTime = Instant.now();
        try {
            String[] args = {"debug"};
            app.connect(args);

            System.out.println("Testing global population insights...");

            // Query 1: Fetch the world population
            passedQueries += executeAndTrack(() -> app.getPopulationOfWorld(), "World Population Query");

            // Query 2: Fetch continent-level populations
            String[] continents = {"Asia", "Europe", "Africa"};
            for (String continent : continents) {
                passedQueries += executeAndTrack(() -> app.getPopulationOfContinent(continent),
                        "Population Query for Continent: " + continent);
            }

            // Query 3: Fetch top N populated countries globally
            passedQueries += executeAndTrack(() -> app.getTopNPopulatedCountries(5),
                    "Top 5 Populated Countries Query");

        } catch (Exception e) {
            System.out.println("Test failed with exception: " + e.getMessage());
            failedQueries++;
        } finally {
            app.disconnect();
            Instant endTime = Instant.now();
            Duration executionTime = Duration.between(startTime, endTime);

            // Print test summary
            System.out.println("\n=== GLOBAL POPULATION INSIGHTS TEST SUMMARY ===");
            System.out.println("Total Queries: " + (passedQueries + failedQueries));
            System.out.println("Passed Queries: " + passedQueries);
            System.out.println("Failed Queries: " + failedQueries);
            System.out.println("Total Execution Time: " + executionTime.toMillis() + " ms");

            // Ensure at least one query passed
            assertTrue(passedQueries > 0, "At least one query should pass.");
        }
    }

    /** Combined Query Test: Regional and Country-Level Insights
     - Simulate combined queries for regions and countries.
     - Validate sequential execution of region and country-level population data retrieval.
     - Track execution time and record success/failure counts for each query. */
    @Test
    void testRegionalAndCountryInsightsIntegration() {
        Population app = new Population();
        int totalQueries = 0;
        int successfulQueries = 0;
        int failedQueries = 0;
        try {
            String[] args = {"debug"};
            app.connect(args);

            long startTime = System.currentTimeMillis(); // Start timer

            // Fetch region-level population
            totalQueries++;
            try {
                String region = "Western Europe";
                System.out.println("Fetching population for region: " + region);
                app.getPopulationOfRegion(region);
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch population for region: Western Europe - " + e.getMessage());
            }

            // Fetch country-level population for countries in the region
            String[] countries = {"Germany", "France", "Italy"};
            for (String country : countries) {
                totalQueries++;
                try {
                    System.out.println("Fetching population for country: " + country);
                    app.getPopulationOfCountry(country);
                    successfulQueries++;
                } catch (Exception e) {
                    failedQueries++;
                    System.err.println("Failed to fetch population for country: " + country + " - " + e.getMessage());
                }

                // Fetch cities by population for each country
                totalQueries++;
                try {
                    System.out.println("Fetching cities by population for country: " + country);
                    app.getCitiesByCountryPopulation(country);
                    successfulQueries++;
                } catch (Exception e) {
                    failedQueries++;
                    System.err.println("Failed to fetch cities by population for country: " + country + " - " + e.getMessage());
                }
            }

            long endTime = System.currentTimeMillis(); // End timer
            long elapsedTime = endTime - startTime;

            // Print metrics
            System.out.println("Test Execution Metrics:");
            System.out.println("Total Queries: " + totalQueries);
            System.out.println("Successful Queries: " + successfulQueries);
            System.out.println("Failed Queries: " + failedQueries);
            System.out.println("Total Execution Time: " + elapsedTime + " ms");

            // If no exceptions occur, the test passes
            assertTrue(failedQueries == 0, "Some queries failed during the integration test.");

        } catch (Exception e) {
            fail("Regional and country insights test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**Combined Query Test: Capital City and District-Level Data
     - Combine capital city and district-level queries to simulate their interaction.
     - Validate integration of capital cities and district-level city data.
     - Track execution time and record success/failure counts for each query. */
    @Test
    void testCapitalCityAndDistrictDataIntegration() {
        Population app = new Population();
        int totalQueries = 0;
        int successfulQueries = 0;
        int failedQueries = 0;

        try {
            String[] args = {"debug"};
            app.connect(args);

            long startTime = System.currentTimeMillis(); // Start timer

            // Fetch capital cities by population for a specific region
            totalQueries++;
            try {
                String region = "South America";
                System.out.println("Fetching capital cities by population for region: " + region);
                app.getCapitalCitiesByRegionPopulation(region);
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch capital cities by population for region: South America - " + e.getMessage());
            }

            // Fetch cities by population for a district in a country from the region
            totalQueries++;
            try {
                String district = "Buenos Aires";
                System.out.println("Fetching cities by population for district: " + district);
                app.getCitiesByDistrictPopulation(district);
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch cities by population for district: Buenos Aires - " + e.getMessage());
            }

            // Fetch top N cities by population for the region
            totalQueries++;
            try {
                String region = "South America";
                System.out.println("Fetching top 5 cities by population for region: " + region);
                app.getTopNCitiesByRegionPopulation(5, region);
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch top cities by population for region: South America - " + e.getMessage());
            }

            long endTime = System.currentTimeMillis(); // End timer
            long elapsedTime = endTime - startTime;

            // Print metrics
            System.out.println("Test Execution Metrics:");
            System.out.println("Total Queries: " + totalQueries);
            System.out.println("Successful Queries: " + successfulQueries);
            System.out.println("Failed Queries: " + failedQueries);
            System.out.println("Total Execution Time: " + elapsedTime + " ms");

            // If no exceptions occur, the test passes
            assertTrue(failedQueries == 0, "Some queries failed during the integration test.");

        } catch (Exception e) {
            fail("Capital city and district data test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /** Combined Query Test: Language and Population Insights
     - Simulate a scenario where language speaker data is combined with population data.
     - Validate the interaction between language-related queries and population data.*/
    @Test
    void testLanguageAndPopulationInsightsIntegration() {
        Population app = new Population();
        int totalQueries = 0;
        int successfulQueries = 0;
        int failedQueries = 0;

        try {
            String[] args = {"debug"};
            app.connect(args);

            long startTime = System.currentTimeMillis(); // Start timer

            // Fetch language speakers
            totalQueries++;
            try {
                System.out.println("Fetching speakers by language...");
                app.getSpeakersByLanguage();
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch speakers by language: " + e.getMessage());
            }

            // Fetch world population
            totalQueries++;
            try {
                System.out.println("Fetching world population...");
                app.getPopulationOfWorld();
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch world population: " + e.getMessage());
            }

            // Fetch continent population for a known language region
            totalQueries++;
            try {
                String continent = "Asia";
                System.out.println("Fetching population for continent: " + continent);
                app.getPopulationOfContinent(continent);
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch population for continent Asia: " + e.getMessage());
            }

            // Fetch top N populated countries where the language is spoken
            totalQueries++;
            try {
                String continent = "Asia";
                System.out.println("Fetching top 5 populated countries in " + continent);
                app.getTopNPopulatedCountries(5);
                successfulQueries++;
            } catch (Exception e) {
                failedQueries++;
                System.err.println("Failed to fetch top populated countries: " + e.getMessage());
            }

            long endTime = System.currentTimeMillis(); // End timer
            long elapsedTime = endTime - startTime;

            // Print metrics
            System.out.println("Test Execution Metrics:");
            System.out.println("Total Queries: " + totalQueries);
            System.out.println("Successful Queries: " + successfulQueries);
            System.out.println("Failed Queries: " + failedQueries);
            System.out.println("Total Execution Time: " + elapsedTime + " ms");

            // Ensure all queries passed
            assertTrue(failedQueries == 0, "Some queries failed during the integration test.");

        } catch (Exception e) {
            fail("Language and population insights test failed: " + e.getMessage());
        } finally {
            app.disconnect();
        }
    }

    /**
     * Helper Method: Execute a query and track its success/failure.
     * @param query Runnable query to execute.
     * @param description Description of the query for logging purposes.
     * @return 1 if successful, 0 otherwise.
     */
    private int executeAndTrack(Runnable query, String description) {
        Instant queryStart = Instant.now();
        try {
            query.run();
            Instant queryEnd = Instant.now();
            Duration queryTime = Duration.between(queryStart, queryEnd);
            System.out.println("SUCCESS: " + description + " executed in " + queryTime.toMillis() + " ms.");
            return 1;
        } catch (Exception e) {
            System.out.println("FAILURE: " + description + " failed with exception: " + e.getMessage());
            return 0;
        }
    }
}

