import com.napier.sem.Population;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PopulationUnitTests {
    /**Verifies:
     * - The method executes without throwing exceptions.
     * - Mocked database interaction, ensuring the query is executed correctly.
     * - That the result set is processed as expected (fetching country details).
     */
    @Test
    void testGetCountriesByPopulationFormatting() {
        // Mock the database components
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try {
            // Simulate database behavior
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
            when(mockStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one row of data
            when(mockResultSet.getString("Code")).thenReturn("USA");
            when(mockResultSet.getString("Name")).thenReturn("United States");
            when(mockResultSet.getString("Continent")).thenReturn("North America");
            when(mockResultSet.getString("Region")).thenReturn("Northern America");
            when(mockResultSet.getInt("Population")).thenReturn(331002651);
            when(mockResultSet.getInt("Capital")).thenReturn(1);

            // Create a spy of the Population class
            Population app = spy(new Population());

            // Inject mock connection into spy (use doReturn to avoid direct modification)
            doReturn(mockConnection).when(app).getDatabaseConnection();

            // Call the method under test
            app.getCountriesByPopulation();

            // Verify behavior
            verify(mockStatement, times(1)).executeQuery();
            verify(mockResultSet, times(1)).getString("Code");

        } catch (Exception e) {
            fail("Test failed with exception: " + e.getMessage());
        }
    }

    /**Verifies:
     * - The method executes correctly for a specific continent.
     * - Proper use of prepared statements with correct parameters (e.g., continent name).
     * - Correct processing of the result set (country details by continent).
     */
    @Test
    void testGetCountriesByContinentPopulation() throws Exception {
        // Mocking
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock result set behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Single result row
        when(mockResultSet.getString("Code")).thenReturn("USA");
        when(mockResultSet.getString("Name")).thenReturn("United States");
        when(mockResultSet.getString("Region")).thenReturn("North America");
        when(mockResultSet.getInt("Population")).thenReturn(331002651);
        when(mockResultSet.getInt("Capital")).thenReturn(1);

        // Create instance of Population and set mock connection
        Population app = new Population();
        app.connect(new String[]{"debug"}); // Assuming `connect` has a mock-friendly option.
        app.getDatabaseConnection = () -> mockConnection; // Mock the connection

        // Call the method
        app.getCountriesByContinentPopulation("North America");

        // Verify interactions
        verify(mockPreparedStatement, times(1)).setString(1, "North America");
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getString("Code");
        verify(mockResultSet, times(1)).getString("Name");
    }

    /**verifies:
     * - The method executes without exceptions for a specific limit.
     * - Correct parameter setting for the limit in the prepared statement.
     * - Proper processing of the result set (top N countries by population).
     */
    @Test
    void testGetTopNPopulatedCountries() throws Exception {
        // Mocking
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock result set behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Single result row
        when(mockResultSet.getString("Code")).thenReturn("CHN");
        when(mockResultSet.getString("Name")).thenReturn("China");
        when(mockResultSet.getString("Continent")).thenReturn("Asia");
        when(mockResultSet.getString("Region")).thenReturn("Eastern Asia");
        when(mockResultSet.getInt("Population")).thenReturn(1393409038);

        // Create instance of Population and set mock connection
        Population app = new Population();
        app.getDatabaseConnection = () -> mockConnection; // Mock the connection

        // Call the method
        app.getTopNPopulatedCountries(5);

        // Verify interactions
        verify(mockPreparedStatement, times(1)).setInt(1, 5);
        verify(mockResultSet, times(1)).next();
    }

    /**Verifies:
     * - The method executes correctly for a specific region.
     * - Proper use of prepared statements with correct parameters (e.g., region name).
     * - Correct processing of the result set (cities in a region).
     */
    @Test
    void testGetCitiesByRegionPopulation() throws Exception {
        // Mocking
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock result set behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Single result row
        when(mockResultSet.getInt("ID")).thenReturn(1);
        when(mockResultSet.getString("Name")).thenReturn("Kyiv");
        when(mockResultSet.getString("CountryCode")).thenReturn("UKR");
        when(mockResultSet.getString("District")).thenReturn("Kyiv");
        when(mockResultSet.getInt("Population")).thenReturn(2804000);

        // Create instance of Population and set mock connection
        Population app = new Population();
        app.getDatabaseConnection = () -> mockConnection; // Mock the connection

        // Call the method
        app.getCitiesByRegionPopulation("Eastern Europe");

        // Verify interactions
        verify(mockPreparedStatement, times(1)).setString(1, "Eastern Europe");
        verify(mockResultSet, times(1)).next();
    }

    /**Verifies:
     * - The method executes correctly for a specific district and limit.
     * - Correct parameter setting for the district name and limit in the prepared statement.
     * - Proper processing of the result set (top N cities by district population).
     */
    @Test
    void testGetTopNCitiesByDistrictPopulation() throws Exception {
        // Mocking
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock result set behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Single result row
        when(mockResultSet.getInt("ID")).thenReturn(1);
        when(mockResultSet.getString("Name")).thenReturn("San Francisco");
        when(mockResultSet.getString("CountryCode")).thenReturn("USA");
        when(mockResultSet.getString("District")).thenReturn("California");
        when(mockResultSet.getInt("Population")).thenReturn(884363);

        // Create instance of Population and set mock connection
        Population app = new Population();
        app.getDatabaseConnection = () -> mockConnection; // Mock the connection

        // Call the method
        app.getTopNCitiesByDistrictPopulation(5, "California");

        // Verify interactions
        verify(mockPreparedStatement, times(1)).setString(1, "California");
        verify(mockPreparedStatement, times(1)).setInt(2, 5);
        verify(mockResultSet, times(1)).next();
    }
}
