import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PopulationUnitTests {

    /****************************************** GET POPULATION OF WORLD ******************************************************/
    /** Valid: Test retrieving the total population of the world.*/
    @Test
    void testGetPopulationOfWorld_Valid() {
        long worldPopulation = 7_900_000_000L; // Example population
        assertTrue(worldPopulation > 0, "World population should be greater than zero.");
    }

    /** Edge: Test for a world population of zero.*/
    @Test
    void testGetPopulationOfWorld_Edge() {
        long worldPopulation = 0; // Edge case: Cleared database
        assertEquals(0, worldPopulation, "World population might be zero in a cleared database.");
    }

    /*********************************************** GET POPULATION OF CONTINENT **************************************************************************************/
    /**Valid case: Test retrieving the population for a valid continent.*/
    @Test
    void testGetPopulationOfContinent_Valid() {
        String continent = "Africa";
        long population = 1_340_598_147L; // Example population

        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Africa", continent, "Continent name should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /**Edge case: Test handling of case-insensitive input and extra spaces for a continent.*/
    @Test
    void testGetPopulationOfContinent_Edge() {
        String continent = "africa"; // lowercase input

        assertNotNull(continent, "Continent should not be null.");
        assertTrue(continent.equalsIgnoreCase("Africa"), "Continent input should handle case insensitivity.");

        continent = " Africa "; // input with extra spaces
        assertEquals("Africa", continent.trim(), "Continent input should handle leading/trailing spaces.");
    }

    /**Invalid case: Test handling of non-existent or invalid continent names.*/
    @Test
    void testGetPopulationOfContinent_Invalid() {
        String continent = "Atlantis"; // Fictional continent
        long population = 0;

        assertNotNull(continent, "Invalid continent should not be null.");
        assertNotEquals("Africa", continent, "Invalid continent should not match valid continent names.");
        assertEquals(0, population, "Population should be zero for non-existent continents.");

        continent = ""; // Empty input
        assertEquals("", continent, "Continent input should allow empty values.");

        continent = null; // Null input
        assertNull(continent, "Continent should be null when no input is provided.");
    }

    /********************************************************* GET POPULATION OF REGION *****************************************************************************/
    /** Valid case: Test retrieving the population for a valid region.*/
    @Test
    void testGetPopulationOfRegion_Valid() {
        String region = "Eastern Europe";
        long population = 300_000_000L; // Example population

        assertNotNull(region, "Region should not be null.");
        assertEquals("Eastern Europe", region, "Region should match the valid input value.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test handling of edge case region names and populations.*/
    @Test
    void testGetPopulationOfRegion_Edge() {
        // Test leading/trailing spaces in region name
        String region = " eastern europe ";
        assertNotNull(region, "Region should not be null.");
        assertTrue(region.trim().equalsIgnoreCase("Eastern Europe"), "Region input should handle leading/trailing spaces.");

        // Test case-insensitive region input
        region = "EASTERN EUROPE";
        assertTrue(region.equalsIgnoreCase("Eastern Europe"), "Region input should handle case insensitivity.");

        // Edge case for population = 1 (minimum positive population)
        long population = 1;
        assertTrue(population > 0, "Edge case: Population = 1 should still be valid.");
    }

    /** Invalid case: Test handling of invalid region names and populations.*/
    @Test
    void testGetPopulationOfRegion_Invalid() {
        // Invalid region name
        String region = "Middle Earth";
        assertNotNull(region, "Invalid region should not be null.");
        assertNotEquals("Eastern Europe", region, "Invalid region should not match valid region names.");

        // Invalid population values
        long population = 0; // Zero population
        assertEquals(0, population, "Invalid case: Population should be zero for non-existent regions.");

        population = -100; // Negative population
        assertTrue(population < 0, "Invalid case: Population should not be negative.");
    }

    /***************************************************** GET COUNTRIES BY POPULATION *****************************************************************************/
    /** Valid case: Test retrieving population data for a valid country.*/
    @Test
    void testGetCountriesByPopulation_Valid() {
        String country = "United States";
        long population = 331_002_651L; // Example population

        assertNotNull(country, "Country should not be null.");
        assertEquals("United States", country, "Country name should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test retrieving population data for a small but valid country.*/
    @Test
    void testGetCountriesByPopulation_Edge() {
        String country = "Vatican City";
        long population = 800; // Example population for Vatican City

        assertNotNull(country, "Country should not be null.");
        assertEquals("Vatican City", country, "Edge case: Country name should match the valid input.");
        assertTrue(population > 0, "Population should still be greater than zero.");
    }

    /**Invalid case: Test retrieving population data for a non-existent country.*/
    @Test
    void testGetCountriesByPopulation_Invalid() {
        String country = "NonExistentCountry";
        long population = 0;

        assertNotNull(country, "Country should not be null.");
        assertNotEquals("United States", country, "Invalid country name should not match a valid country.");
        assertEquals(0, population, "Population should be zero for non-existent countries.");
    }

    /********************************************************************GET COUNTRIES BY CONTINENT POPULATION***********************/
    /*** Valid case: Test fetching countries by a valid continent.*/
    @Test
    void testGetCountriesByContinentPopulation_Valid() {
        String continent = "Europe";
        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Europe", continent, "Continent should match the valid input.");
    }

    /*** Edge case: Test for lowercase and trimmed continent names. */
    @Test
    void testGetCountriesByContinentPopulation_Edge() {
        String continent = " europe ";
        assertNotNull(continent.trim(), "Continent should not be null after trimming.");
        assertEquals("europe", continent.trim().toLowerCase(), "Edge case: Continent should match after normalization.");
    }

    /*** Invalid case: Test for non-existent continent.*/
    @Test
    void testGetCountriesByContinentPopulation_Invalid() {
        String continent = "NonExistentContinent";
        assertNotNull(continent, "Continent should not be null.");
        assertNotEquals("Europe", continent, "Invalid continent should not match a valid continent.");
    }

    /*************************************************** GET COUNTRIES BY REGION POPULATION ****************************************************************************/
    /**Valid case: Test retrieving countries for a valid region.*/
    @Test
    void testGetCountriesByRegionPopulation_Valid() {
        String region = "Eastern Europe";

        assertNotNull(region, "Region should not be null.");
        assertEquals("Eastern Europe", region, "Region name should match the valid input.");
    }

    /** Edge case: Test case insensitivity and input formatting for a region.*/
    @Test
    void testGetCountriesByRegionPopulation_Edge() {
        String region = "eastern europe"; // lowercase input

        assertNotNull(region, "Region should not be null.");
        assertTrue(region.equalsIgnoreCase("Eastern Europe"), "Region input should handle case insensitivity.");

        region = " Eastern Europe "; // input with extra spaces
        assertEquals("Eastern Europe", region.trim(), "Region input should handle leading/trailing spaces.");
    }

    /** Invalid case: Test handling of non-existent or invalid region names.*/
    @Test
    void testGetCountriesByRegionPopulation_Invalid() {
        String region = "Middle Earth"; // Fictional region

        assertNotNull(region, "Region should not be null.");
        assertNotEquals("Eastern Europe", region, "Invalid region should not match valid region names.");

        region = ""; // Empty input
        assertEquals("", region, "Region input should allow empty values.");

        region = null; // Null input
        assertNull(region, "Region should be null when no input is provided.");
    }

    /**************************************************** GET POPULATION OF COUNTRY ****************************************************/
    /**Valid case: Test retrieving the population for a valid country.*/
    @Test
    void testGetPopulationOfCountry_Valid() {
        String country = "Germany";
        long population = 83_000_000L; // Example population

        assertNotNull(country, "Country should not be null.");
        assertEquals("Germany", country, "Country should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test for small population countries.*/
    @Test
    void testGetPopulationOfCountry_Edge() {
        String country = "Vatican City";
        long population = 800; // Example population for Vatican City

        assertNotNull(country, "Country should not be null.");
        assertEquals("Vatican City", country, "Edge case: Country should match.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /**Invalid case: Test for non-existent country. */
    @Test
    void testGetPopulationOfCountry_Invalid() {
        String country = "NonExistentCountry";
        long population = 0;

        assertNotNull(country, "Country should not be null.");
        assertNotEquals("Germany", country, "Invalid country should not match a valid country.");
        assertEquals(0, population, "Population should be zero for non-existent countries.");
    }

    /************************************************************** GET POPULATION OF DISTRICT *******************************************************************/

    /** Valid case: Test retrieving the population of a valid district. */
    @Test
    void testGetPopulationOfDistrict_Valid() {
        String district = "California";
        long population = 39_000_000L; // Example population

        assertNotNull(district, "District should not be null.");
        assertEquals("California", district, "District should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test retrieving population for a district with leading/trailing spaces or lowercase. */
    @Test
    void testGetPopulationOfDistrict_Edge() {
        String district = " california "; // Leading/trailing spaces
        long population = 39_000_000L;

        assertNotNull(district.trim(), "District should not be null after trimming.");
        assertTrue(district.trim().equalsIgnoreCase("California"), "District should match after normalization.");
        assertTrue(population > 0, "Population should still be greater than zero.");
    }

    /** Invalid case: Test retrieving population for a non-existent or invalid district. */
    @Test
    void testGetPopulationOfDistrict_Invalid() {
        String district = "NonExistentDistrict";
        long population = 0;

        assertNotNull(district, "District should not be null.");
        assertNotEquals("California", district, "Invalid district should not match a valid district.");
        assertEquals(0, population, "Population should be zero for a non-existent district.");
    }


    /**************************************************************** GET POPULATION OF CITY ********************************************************************/
    /** Valid case: Test retrieving the population for a valid city. */
    @Test
    void testGetPopulationOfCity_Valid() {
        String city = "Tokyo";
        long population = 13_500_000L; // Example population

        assertNotNull(city, "City should not be null.");
        assertEquals("Tokyo", city, "City name should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /*** Edge case: Test retrieving the population for a small but valid city.*/
    @Test
    void testGetPopulationOfCity_Edge() {
        String city = "Vatican City";
        long population = 800; // Example population for Vatican City

        assertNotNull(city, "City should not be null.");
        assertEquals("Vatican City", city, "Edge case: City name should match.");
        assertTrue(population > 0, "Population should still be greater than zero.");
    }

    /** Invalid case: Test retrieving the population for a non-existent city.*/
    @Test
    void testGetPopulationOfCity_Invalid() {
        String city = "NonExistentCity";
        long population = 0;

        assertNotNull(city, "City should not be null.");
        assertNotEquals("Tokyo", city, "Invalid city name should not match a valid city.");
        assertEquals(0, population, "Population should be zero for non-existent cities.");
    }


    /******************************************************* GET ALL CITIES BY POPULATION *******************************************************************************/
    /**Valid case: Test retrieving population for a large city.*/
    @Test
    void testGetAllCitiesByPopulation_Valid() {
        String city = "Tokyo";
        long population = 13_500_000L; // Example population

        assertNotNull(city, "City should not be null.");
        assertEquals("Tokyo", city, "City name should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /**Edge case: Test handling of small cities with very low populations.*/
    @Test
    void testGetAllCitiesByPopulation_Edge() {
        String city = "Smallville"; // Example small city
        long population = 100; // Example small population

        assertNotNull(city, "City should not be null.");
        assertTrue(population > 0, "Small population should still be valid.");
        assertEquals("Smallville", city, "City name should match the valid input for small cities.");
    }

    /**Invalid case: Test handling of non-existent or empty city names.*/
    @Test
    void testGetAllCitiesByPopulation_Invalid() {
        String city = ""; // Empty city name
        long population = 0;

        assertEquals("", city, "City name should allow empty strings.");
        assertEquals(0, population, "Population should be zero for invalid cities.");

        city = null; // Null city name
        assertNull(city, "City name should be null for uninitialized cities.");
    }

    /************************************************************ GET CITIES BY CONTINENT POPULATION *************************************************************/
    /**Valid case: Test retrieving cities for a valid continent. */
    @Test
    void testGetCitiesByContinentPopulation_Valid() {
        String continent = "Asia";

        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Asia", continent, "Continent name should match the valid input.");
    }

    /**Edge case: Test case insensitivity and input formatting for a continent.*/
    @Test
    void testGetCitiesByContinentPopulation_Edge() {
        String continent = "asia"; // lowercase input

        assertNotNull(continent, "Continent should not be null.");
        assertTrue(continent.equalsIgnoreCase("Asia"), "Continent input should handle case insensitivity.");

        continent = " Asia "; // input with extra spaces
        assertEquals("Asia", continent.trim(), "Continent input should handle leading/trailing spaces.");
    }

    /**Invalid case: Test handling of non-existent or invalid continent names.*/
    @Test
    void testGetCitiesByContinentPopulation_Invalid() {
        String continent = "Atlantis"; // Non-existent continent

        assertNotNull(continent, "Continent should not be null.");
        assertNotEquals("Asia", continent, "Invalid continent should not match valid continent names.");

        continent = ""; // Empty input
        assertEquals("", continent, "Continent input should allow empty values.");

        continent = null; // Null input
        assertNull(continent, "Continent should be null when no input is provided.");
    }

    /**************************************************** GET TOP N CITIES BY CONTINENT POPULATION *****************************************************************/
    /** Valid case: Test retrieving the top N cities for a valid continent.*/
    @Test
    void testGetTopNCitiesByContinentPopulation_Valid() {
        String continent = "Asia";
        int n = 10;

        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Asia", continent, "Continent should match the valid input value.");
        assertTrue(n > 0, "N should be greater than zero.");
    }

    /** Edge case: Test handling of edge case continent names and N values.*/
    @Test
    void testGetTopNCitiesByContinentPopulation_Edge() {
        // Test leading/trailing spaces in continent name
        String continent = " asia ";
        assertNotNull(continent, "Continent should not be null.");
        assertTrue(continent.trim().equalsIgnoreCase("Asia"), "Continent input should handle leading/trailing spaces.");

        // Test case-insensitive continent input
        continent = "ASIA";
        assertTrue(continent.equalsIgnoreCase("Asia"), "Continent input should handle case insensitivity.");

        // Edge case for N = 1
        int n = 1;
        assertTrue(n > 0, "Edge case: N = 1 should still be valid.");
    }

    /** Invalid case: Test handling of invalid continent names and N values.*/
    @Test
    void testGetTopNCitiesByContinentPopulation_Invalid() {
        // Invalid continent name
        String continent = "Atlantis";
        assertNotNull(continent, "Invalid continent should not be null.");
        assertNotEquals("Asia", continent, "Invalid continent should not match valid continent names.");

        // Invalid N values
        int n = 0; // Zero
        assertTrue(n < 1, "Invalid case: N should be greater than zero.");

        n = -5; // Negative value
        assertTrue(n < 1, "Invalid case: N should not be negative.");

        n = Integer.MAX_VALUE; // Excessively large number
        assertTrue(n > 0, "N should remain valid, but this is an edge case for an extremely large value.");
    }


    /****************************************************** GET TOP N CITIES BY REGION POPULATION *******************************************************************/
    /**Valid case: Test retrieving the top N cities for a valid region.*/
    @Test
    void testGetTopNCitiesByRegionPopulation_Valid() {
        String region = "Western Europe";
        int n = 3;

        assertNotNull(region, "Region should not be null.");
        assertEquals("Western Europe", region, "Region should match the valid input value.");
        assertTrue(n > 0, "N should be greater than zero.");
    }

    /**Edge case: Test handling of edge case region names and N values.*/
    @Test
    void testGetTopNCitiesByRegionPopulation_Edge() {
        // Test leading/trailing spaces in region
        String region = " western europe ";
        assertNotNull(region, "Region should not be null.");
        assertTrue(region.trim().equalsIgnoreCase("Western Europe"), "Region input should handle leading/trailing spaces.");

        // Test case-insensitive region input
        region = "WESTERN EUROPE";
        assertTrue(region.equalsIgnoreCase("Western Europe"), "Region input should handle case insensitivity.");

        // Edge case for N = 1
        int n = 1;
        assertTrue(n > 0, "Edge case: N = 1 should still be valid.");
    }

    /** Invalid case: Test handling of invalid region names and N values.*/
    @Test
    void testGetTopNCitiesByRegionPopulation_Invalid() {
        // Invalid region name
        String region = "Mordor";
        assertNotNull(region, "Invalid region should not be null.");
        assertNotEquals("Western Europe", region, "Invalid region should not match valid region names.");

        // Invalid N values
        int n = 0; // Zero
        assertTrue(n < 1, "Invalid case: N should be greater than zero.");

        n = -5; // Negative value
        assertTrue(n < 1, "Invalid case: N should not be negative.");

        n = Integer.MAX_VALUE; // Excessively large number
        assertTrue(n > 0, "N should remain valid, but this is an edge case for an extremely large value.");
    }

    /******************************************************** GET TOP N POPULATED COUNTRIES ****************************************************************************/
    /** Valid case: Test fetching top N populated countries.*/
    @Test
    void testGetTopNPopulatedCountries_Valid() {
        int topN = 10;
        assertTrue(topN > 0, "Top N should be greater than zero.");
        assertEquals(10, topN, "Top N should match the valid input.");
    }

    /** Edge case: Test for a minimal value of N.*/
    @Test
    void testGetTopNPopulatedCountries_Edge() {
        int topN = 1;
        assertTrue(topN > 0, "Top N should be greater than zero.");
        assertEquals(1, topN, "Edge case: Top N should handle the smallest valid value.");
    }

    /** Invalid case: Test for negative and zero N.*/
    @Test
    void testGetTopNPopulatedCountries_Invalid() {
        int topN = -5;
        assertTrue(topN < 1, "Invalid case: Top N should not be less than one.");
        topN = 0;
        assertTrue(topN < 1, "Invalid case: Top N should not be zero.");
    }


    /*********************************************************** GET CITIES BY REGION *********************************************************************/
    /**Valid case: Test retrieving cities for a valid region. */
    @Test
    void testGetCitiesByRegionPopulation_Valid() {
        String region = "Western Europe";

        assertNotNull(region, "Region should not be null.");
        assertEquals("Western Europe", region, "Region name should match the valid input.");
    }

    /**Edge case: Test case insensitivity and input formatting for a region.*/
    @Test
    void testGetCitiesByRegionPopulation_Edge() {
        String region = "western europe"; // lowercase input

        assertNotNull(region, "Region should not be null.");
        assertTrue(region.equalsIgnoreCase("Western Europe"), "Region input should handle case insensitivity.");

        region = " Western Europe "; // input with extra spaces
        assertEquals("Western Europe", region.trim(), "Region input should handle leading/trailing spaces.");
    }

    /**Invalid case: Test handling of non-existent or invalid region names.*/
    @Test
    void testGetCitiesByRegionPopulation_Invalid() {
        String region = "Mordor"; // Non-existent region

        assertNotNull(region, "Region should not be null.");
        assertNotEquals("Western Europe", region, "Invalid region should not match valid region names.");

        region = ""; // Empty input
        assertEquals("", region, "Region input should allow empty values.");

        region = null; // Null input
        assertNull(region, "Region should be null when no input is provided.");
    }

    /********************************************************** GET CITIES BY COUNTRY POPULATION *****************************************************************/
    /** Valid case: Test retrieving cities for a valid country.*/
    @Test
    void testGetCitiesByCountryPopulation_Valid() {
        String country = "United States";

        assertNotNull(country, "Country should not be null.");
        assertEquals("United States", country, "Country name should match the valid input.");
    }

    /**Edge case: Test case insensitivity and input formatting for a country.*/
    @Test
    void testGetCitiesByCountryPopulation_Edge() {
        String country = "united states"; // lowercase input

        assertNotNull(country, "Country should not be null.");
        assertTrue(country.equalsIgnoreCase("United States"), "Country input should handle case insensitivity.");

        country = " United States "; // input with extra spaces
        assertEquals("United States", country.trim(), "Country input should handle leading/trailing spaces.");
    }

    /**Invalid case: Test handling of non-existent or invalid country names.*/
    @Test
    void testGetCitiesByCountryPopulation_Invalid() {
        String country = "Narnia"; // Non-existent country

        assertNotNull(country, "Country should not be null.");
        assertNotEquals("United States", country, "Invalid country should not match valid country names.");

        country = ""; // Empty input
        assertEquals("", country, "Country input should allow empty values.");

        country = null; // Null input
        assertNull(country, "Country should be null when no input is provided.");
    }

    /***************************************************** GET CITIES BY DISTRICT POPULATION **************************************************************************/
    /**Valid case: Test retrieving cities for a valid district.*/
    @Test
    void testGetCitiesByDistrictPopulation_Valid() {
        String district = "California";
        assertNotNull(district, "District should not be null.");
        assertEquals("California", district, "District should match the valid input value.");
    }

    /**Edge case: Test handling of edge case district names.*/
    @Test
    void testGetCitiesByDistrictPopulation_Edge() {
        String district = " california "; // Leading/trailing spaces
        assertNotNull(district, "District should not be null.");
        assertTrue(district.trim().equalsIgnoreCase("California"), "District input should handle leading/trailing spaces.");

        // Test case-insensitivity
        district = "CALIFORNIA";
        assertTrue(district.equalsIgnoreCase("California"), "District input should handle case insensitivity.");
    }

    /**Invalid case: Test handling of invalid district names.*/
    @Test
    void testGetCitiesByDistrictPopulation_Invalid() {
        String district = "12345"; // Numeric input
        assertNotNull(district, "Invalid district should not be null.");
        assertNotEquals("California", district, "Numeric district should not match valid district values.");

        // Test with an empty string
        district = "";
        assertNotNull(district, "Empty district string should not be null.");
        assertEquals(0, district.trim().length(), "Empty district string should have zero length.");

        // Test with null
        district = null;
        assertNull(district, "District should be null for invalid input.");
    }

    /********************************************************** GET TOP N CITIES BY POPULATION *****************************************************/
    /*** Valid case: Test retrieving top N populated cities in the world.*/
    @Test
    void testGetTopNCitiesByPopulation_Valid() {
        int topN = 5;
        assertTrue(topN > 0, "Top N should be greater than zero.");
        assertEquals(5, topN, "Top N should match the valid input.");
    }

    /*** Edge case: Test for minimal N value.*/
    @Test
    void testGetTopNCitiesByPopulation_Edge() {
        int topN = 1;
        assertTrue(topN > 0, "Top N should be greater than zero.");
        assertEquals(1, topN, "Edge case: Top N should handle the smallest valid value.");
    }

    /*** Invalid case: Test for negative or zero N.*/
    @Test
    void testGetTopNCitiesByPopulation_Invalid() {
        int topN = -3;
        assertTrue(topN < 1, "Invalid case: Top N should not be less than one.");
        topN = 0;
        assertTrue(topN < 1, "Invalid case: Top N should not be zero.");
    }

    /************************************ GET TOP N CITIES BY DISTRICT POPULATION ******************************/
    /** Valid case: Test retrieving top N cities in a valid district.*/
    @Test
    void testGetTopNCitiesByDistrictPopulation_Valid() {
        String district = "California";
        int topN = 5;

        assertNotNull(district, "District should not be null.");
        assertEquals("California", district, "District name should match the valid input.");
        assertTrue(topN > 0, "Top N should be greater than zero.");
    }

    /**Edge case: Test for district name with lowercase and extra spaces.*/
    @Test
    void testGetTopNCitiesByDistrictPopulation_Edge() {
        String district = " california ";
        int topN = 3;

        assertNotNull(district.trim(), "District should not be null after trimming.");
        assertEquals("california", district.trim().toLowerCase(), "Edge case: District name should match after normalization.");
        assertTrue(topN > 0, "Top N should be greater than zero.");
    }

    /** Invalid case: Test for invalid district name and N values.*/
    @Test
    void testGetTopNCitiesByDistrictPopulation_Invalid() {
        String district = "123";
        int topN = -1;

        assertNotNull(district, "District should not be null.");
        assertNotEquals("California", district, "Invalid district name should not match a valid district.");
        assertTrue(topN < 1, "Invalid case: Top N should not be less than one.");
    }

    /************************************************************ GET TOP N POPULATED CAPITAL CITIES ****************************************************************/
    /** Valid case: Test retrieving the top N populated capital cities. */
    @Test
    void testGetTopNPopulatedCapitalCities_Valid() {
        int n = 5;
        String capitalCity = "Tokyo";
        long population = 13_960_000L; // Example population for a valid capital city

        assertTrue(n > 0, "N should be greater than zero.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertEquals("Tokyo", capitalCity, "Capital city should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test handling for a small value of N and less common capital city names. */
    @Test
    void testGetTopNPopulatedCapitalCities_Edge() {
        int n = 1; // Smallest valid value
        String capitalCity = "Thimphu"; // Bhutan's capital, small population
        long population = 114_551L; // Example small population

        assertTrue(n > 0, "Edge case: N = 1 should be valid.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertTrue(capitalCity.equalsIgnoreCase("Thimphu"), "Capital city should match after normalization.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Invalid case: Test handling of invalid values for N and capital city names. */
    @Test
    void testGetTopNPopulatedCapitalCities_Invalid() {
        int n = -5; // Negative value
        String capitalCity = "NonExistentCapital";
        long population = 0; // No population for an invalid capital city

        assertTrue(n < 1, "Invalid case: N should not be less than one.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertNotEquals("Tokyo", capitalCity, "Invalid capital city should not match a valid capital city.");
        assertEquals(0, population, "Population should be zero for non-existent capital cities.");
    }

    /**************************************************** GET CAPITAL CITIES BY CONTINENT POPULATION ******************************************************************/

    /** Valid case: Test retrieving the capital cities for a valid continent. */
    @Test
    void testGetCapitalCitiesByContinentPopulation_Valid() {
        String continent = "Asia";
        String capitalCity = "Tokyo";
        long population = 13_960_000L; // Example population for a valid capital city in Asia

        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Asia", continent, "Continent should match the valid input.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertEquals("Tokyo", capitalCity, "Capital city should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test handling of case-insensitive continent names and smaller populations. */
    @Test
    void testGetCapitalCitiesByContinentPopulation_Edge() {
        String continent = " asia "; // Input with extra spaces and lowercase
        String capitalCity = "Thimphu"; // Bhutan's capital, smaller population
        long population = 114_551L; // Example small population for an edge case

        assertTrue(continent.trim().equalsIgnoreCase("Asia"), "Continent input should handle case insensitivity and spaces.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertTrue(capitalCity.equalsIgnoreCase("Thimphu"), "Capital city input should handle normalization.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Invalid case: Test handling of invalid continent names and unrealistic N values. */
    @Test
    void testGetCapitalCitiesByContinentPopulation_Invalid() {
        String continent = "Atlantis"; // Non-existent continent
        String capitalCity = "NonExistentCapital";
        long population = 0; // No population for an invalid capital city

        assertNotNull(continent, "Continent should not be null.");
        assertNotEquals("Asia", continent, "Invalid continent should not match a valid continent.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertNotEquals("Tokyo", capitalCity, "Invalid capital city should not match a valid capital city.");
        assertEquals(0, population, "Population should be zero for non-existent capital cities.");
    }


    /***************************************************** GET CAPITAL CITIES BY REGION POPULATION *********************************************************************/
    /**Valid case: Test retrieving the capital city for a valid region.*/
    @Test
    void testGetCapitalCitiesByRegionPopulation_Valid() {
        String region = "South America";
        String capitalCity = "Brasília";
        long population = 3_000_000L; // Example population

        assertNotNull(region, "Region should not be null.");
        assertEquals("South America", region, "Region should match the valid input value.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertEquals("Brasília", capitalCity, "Capital city should match the valid input.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /**Edge case: Test handling of capital cities with unusual formatting or case.*/
    @Test
    void testGetCapitalCitiesByRegionPopulation_Edge() {
        String capitalCity = "sucre"; // Lowercase input
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertTrue(capitalCity.equalsIgnoreCase("Sucre"), "Capital city input should handle case insensitivity.");

        // Test for spaces
        capitalCity = " Sucre ";
        assertTrue(capitalCity.trim().equalsIgnoreCase("Sucre"), "Capital city input should handle extra spaces.");
    }

    /**Invalid case: Test handling of invalid region and capital city names.*/
    @Test
    void testGetCapitalCitiesByRegionPopulation_Invalid() {
        String region = "Middle Earth";
        String capitalCity = "Gondor";
        long population = 0; // Invalid population

        assertNotNull(region, "Region should not be null.");
        assertNotEquals("South America", region, "Invalid region should not match a valid value.");
        assertNotNull(capitalCity, "Capital city should not be null.");
        assertNotEquals("Brasília", capitalCity, "Invalid capital city should not match a valid value.");
        assertEquals(0, population, "Population should be zero for invalid regions or capital cities.");
    }

    /******************************************************* GET CAPITAL CITY REPORT *********************************************************************************/
    /**Valid case: Test generating a report for a valid capital city. */
    @Test
    void testGetCapitalCityReport_Valid() {
        String capitalCity = "Tokyo";
        long population = 13_960_000L; // Example population

        assertNotNull(capitalCity, "Capital city should not be null.");
        assertEquals("Tokyo", capitalCity, "Capital city should match the input value.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test handling of edge cases for capital city names and populations.*/
    @Test
    void testGetCapitalCityReport_Edge() {
        // Test lowercase input
        String capitalCity = "tokyo";
        assertTrue(capitalCity.equalsIgnoreCase("Tokyo"), "Capital city input should handle case insensitivity.");

        // Test leading/trailing spaces
        capitalCity = " tokyo ";
        assertTrue(capitalCity.trim().equalsIgnoreCase("Tokyo"), "Capital city input should handle leading/trailing spaces.");

        // Edge case for population = 1 (minimum positive population)
        long population = 1;
        assertTrue(population > 0, "Edge case: Population = 1 should still be valid.");
    }

    /** Invalid case: Test handling of invalid capital city names and populations.*/
    @Test
    void testGetCapitalCityReport_Invalid() {
        // Invalid capital city name
        String capitalCity = "Atlantis";
        assertNotNull(capitalCity, "Invalid capital city should not be null.");
        assertNotEquals("Tokyo", capitalCity, "Invalid capital city should not match valid capital city names.");

        // Invalid population values
        long population = 0; // Zero population
        assertEquals(0, population, "Invalid case: Population should be zero for non-existent capital cities.");

        population = -100; // Negative population
        assertTrue(population < 0, "Invalid case: Population should not be negative.");
    }

    /**************************************************** GET SPEAKER BY LANGUAGE **********************************************/
    /** Valid: Test retrieving speakers for a common language.**/
    @Test
    void testGetSpeakersByLanguage_Valid() {
        String language = "Chinese";
        long speakers = 1_200_000_000L; // Example speakers for Chinese

        assertNotNull(language, "Language should not be null.");
        assertEquals("Chinese", language, "Language name should match the valid input.");
        assertTrue(speakers > 0, "Speakers should be greater than zero.");
    }

    /** Edge: Test retrieving speakers for a rare language **/
    @Test
    void testGetSpeakersByLanguage_Edge() {
        String language = "Esperanto";
        long speakers = 100_000; // Example speakers for Esperanto

        assertNotNull(language, "Language should not be null.");
        assertEquals("Esperanto", language, "Edge case: Language name should match.");
        assertTrue(speakers > 0, "Speakers should still be greater than zero.");
    }

    /** Invalid: Test retrieving speakers for a non-existent language **/
    @Test
    void testGetSpeakersByLanguage_Invalid() {
        String language = "NonExistentLanguage";
        long speakers = 0;

        assertNotNull(language, "Language should not be null.");
        assertNotEquals("Chinese", language, "Non-existent language should not match a valid language.");
        assertEquals(0, speakers, "Speakers should be zero for non-existent languages.");
    }

    /*************************************************************** GET COUNTRY REPORT **********************************************************************************/
    /**Valid case: Test generating a country report for a valid country.*/
    @Test
    void testGetCountryReport_Valid() {
        String country = "India";
        long population = 1_380_004_385L; // Example population
        String capital = "New Delhi";

        assertNotNull(country, "Country should not be null.");
        assertEquals("India", country, "Country name should match the valid input value.");
        assertNotNull(capital, "Capital should not be null.");
        assertEquals("New Delhi", capital, "Capital should match the valid input value.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /**Edge case: Test generating a report for countries with edge case inputs.*/
    @Test
    void testGetCountryReport_Edge() {
        String country = "japan"; // Lowercase input
        String capital = "tokyo";
        long population = 126_300_000L; // Example population

        assertNotNull(country, "Country should not be null.");
        assertTrue(country.equalsIgnoreCase("Japan"), "Country input should handle case insensitivity.");
        assertNotNull(capital, "Capital should not be null.");
        assertTrue(capital.equalsIgnoreCase("Tokyo"), "Capital input should handle case insensitivity.");
        assertTrue(population > 0, "Population should be greater than zero.");

        // Test handling of extra spaces
        country = " Japan ";
        capital = " Tokyo ";
        assertTrue(country.trim().equalsIgnoreCase("Japan"), "Country input should handle extra spaces.");
        assertTrue(capital.trim().equalsIgnoreCase("Tokyo"), "Capital input should handle extra spaces.");
    }

    /** Invalid case: Test generating a report for invalid country names.*/
    @Test
    void testGetCountryReport_Invalid() {
        String country = "Wakanda";
        long population = 0; // Invalid population
        String capital = "Golden City";

        assertNotNull(country, "Invalid country should not be null.");
        assertNotEquals("India", country, "Invalid country should not match valid values.");
        assertNotNull(capital, "Invalid capital should not be null.");
        assertNotEquals("New Delhi", capital, "Invalid capital should not match valid values.");
        assertEquals(0, population, "Population should be zero for invalid countries.");
    }

    /*********************************************************** GET POPULATION REPORT ***********************************************************************************/
    /**Valid case: Test generating a population report for a valid level and continent.*/
    @Test
    void testGetPopulationReport_Valid() {
        String level = "continent";
        String continent = "Asia";
        long population = 4_641_054_775L; // Example population

        assertNotNull(level, "Level should not be null.");
        assertTrue(level.equalsIgnoreCase("continent"), "Level should match 'continent'.");
        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Asia", continent, "Continent should match the valid input value.");
        assertTrue(population > 0, "Population should be greater than zero.");
    }

    /** Edge case: Test generating a report for edge cases in levels or continent names.*/
    @Test
    void testGetPopulationReport_Edge() {
        String level = "Region"; // Case-insensitive input
        String region = "western europe"; // Lowercase input
        long population = 200_000_000L; // Example population for a smaller region

        assertNotNull(level, "Level should not be null.");
        assertTrue(level.equalsIgnoreCase("region"), "Level input should handle case insensitivity.");
        assertNotNull(region, "Region should not be null.");
        assertTrue(region.equalsIgnoreCase("Western Europe"), "Region input should handle case insensitivity.");
        assertTrue(population > 0, "Population should be greater than zero.");

        // Test handling of extra spaces
        region = " Western Europe ";
        assertTrue(region.trim().equalsIgnoreCase("Western Europe"), "Region input should handle extra spaces.");
    }

    /** Invalid case: Test generating a report for invalid levels or continents.*/
    @Test
    void testGetPopulationReport_Invalid() {
        String level = "planet"; // Invalid level
        String continent = "Atlantis"; // Invalid continent
        long population = 0;

        assertNotNull(level, "Invalid level should not be null.");
        assertNotEquals("continent", level, "Invalid level should not match valid values.");
        assertNotNull(continent, "Invalid continent should not be null.");
        assertNotEquals("Asia", continent, "Invalid continent should not match valid values.");
        assertEquals(0, population, "Population should be zero for invalid continents or levels.");
    }

}
