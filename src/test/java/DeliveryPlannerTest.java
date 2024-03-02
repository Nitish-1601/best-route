import com.nitish.DeliveryPlanner;
import com.nitish.DeliveryPlanner.Location;
import com.nitish.DeliveryPlanner.Route;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryPlannerTest {

    @Test
    public void testFindBestRouteScenario1Faster() {
        // Arrange
        Location amanLocation = new Location(12.9716, 77.5946);
        Location restaurant1Location = new Location(12.9321, 77.6101);
        Location restaurant2Location = new Location(12.9343, 77.6214);
        Location consumer1Location = new Location(12.9352, 77.6245);
        Location consumer2Location = new Location(12.9279, 77.6271);
        double preparationTime1 = 15;
        double preparationTime2 = 20;
        double travelSpeed = 20;

        // Act
        DeliveryPlanner planner = new DeliveryPlanner(amanLocation, restaurant1Location, restaurant2Location,
                consumer1Location, consumer2Location, preparationTime1, preparationTime2, travelSpeed);
        Route bestRoute = planner.findBestRoute();

        // Assert
        assertEquals("C1 first, then C2", bestRoute.getDeliveryOrder());
    }

    @Test
    public void testFindBestRouteScenario2Faster() {
        // Arrange
        Location amanLocation = new Location(12.9716, 77.5946);
        Location restaurant1Location = new Location(12.9343, 77.6214);
        Location restaurant2Location = new Location(12.9321, 77.6101);
        Location consumer1Location = new Location(12.9500, 77.6000); // Farther from restaurant 1
        Location consumer2Location = new Location(12.9279, 77.6271);
        double preparationTime1 = 15;
        double preparationTime2 = 20;
        double travelSpeed = 20;

        // Act
        DeliveryPlanner planner = new DeliveryPlanner(amanLocation, restaurant1Location, restaurant2Location,
                consumer1Location, consumer2Location, preparationTime1, preparationTime2, travelSpeed);
        Route bestRoute = planner.findBestRoute();

        // Assert
        assertEquals("C2 first, then C1", bestRoute.getDeliveryOrder());
    }

    @Test
    public void testCalculateDistance() {
        // Arrange
        Location loc1 = new Location(12.9716, 77.5946);
        Location loc2 = new Location(12.9343, 77.6214);

        // Act
        double distance = DeliveryPlanner.calculateDistance(loc1, loc2);

        // Assert
        Assertions.assertTrue(distance > 0); // Expect a positive distance
    }
}
