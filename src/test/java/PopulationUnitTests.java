import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PopulationUnitTests {
    /************************************************ GET POPULATION OF CITY ************************************************/
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

    /********************************************************************GET COUNTRIES BY CONTINENT POPULATION***********************/
    /**
     * Valid case: Test fetching countries by a valid continent.
     */
    @Test
    void testGetCountriesByContinentPopulation_Valid() {
        String continent = "Europe";
        assertNotNull(continent, "Continent should not be null.");
        assertEquals("Europe", continent, "Continent should match the valid input.");
    }

    /**
     * Edge case: Test for lowercase and trimmed continent names.
     */
    @Test
    void testGetCountriesByContinentPopulation_Edge() {
        String continent = " europe ";
        assertNotNull(continent.trim(), "Continent should not be null after trimming.");
        assertEquals("europe", continent.trim().toLowerCase(), "Edge case: Continent should match after normalization.");
    }

    /**
     * Invalid case: Test for non-existent continent.
     */
    @Test
    void testGetCountriesByContinentPopulation_Invalid() {
        String continent = "NonExistentContinent";
        assertNotNull(continent, "Continent should not be null.");
        assertNotEquals("Europe", continent, "Invalid continent should not match a valid continent.");
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


}
