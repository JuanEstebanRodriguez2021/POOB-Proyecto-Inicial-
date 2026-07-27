package silkroad;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SilkRoadTest {

    private SilkRoad road3x3;
    private int pathLen3x3;

    @Before
    public void setUp() {
        road3x3 = new SilkRoad(3);
        pathLen3x3 = extractPathLength(road3x3);
        Assert.assertTrue("La ruta inicial debe quedar en estado OK", road3x3.ok());
        // Para n=3, la longitud del camino es 4n-4 = 8
        Assert.assertEquals("Para size=3 el perímetro debe ser 4n-4 = 8", 8, pathLen3x3);
    }

    @Test
    public void shouldCreateSilkRoadWithValidSize() {
        SilkRoad r = new SilkRoad(4);
        Assert.assertTrue(r.ok());
        int len = extractPathLength(r);
        // Para n=4, 4n-4 = 12
        Assert.assertEquals("Perímetro 4n-4 con n=4", 12, len);
    }

    @Test
    public void shouldCreateSilkRoadFromMarathonEntries() {
        int[][] entries = new int[][]{
                {1, 0},
                {2, 1, 10}
        };
        SilkRoad r = new SilkRoad(entries);
        Assert.assertTrue(r.ok());
        int[][] stores = r.stores();
        int[][] robots = r.robots();
        Assert.assertEquals(1, stores.length);
        Assert.assertEquals(1, robots.length);
        Assert.assertEquals(1, stores[0][0]);
        Assert.assertEquals(0, robots[0][0]);
    }

    @Test
    public void shouldNotCreateSilkRoadWithNullMarathonEntries() {
        SilkRoad r = new SilkRoad((int[][]) null);
        Assert.assertFalse("entries == null => ok=false", r.ok());
    }

    @Test
    public void shouldPlaceStoreAtValidLocation() {
        road3x3.placeStore(0, 10);
        Assert.assertTrue(road3x3.ok());
        int[][] stores = road3x3.stores();
        Assert.assertEquals(1, stores.length);
        Assert.assertEquals(0, stores[0][0]);
        Assert.assertEquals(10, stores[0][1]);
    }

    @Test
    public void shouldNotPlaceStoreWithInvalidLocation() {
        road3x3.placeStore(pathLen3x3 + 5, 10);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(0, road3x3.stores().length);
    }

    @Test
    public void shouldNotPlaceStoreWithNonPositiveTenges() {
        road3x3.placeStore(0, 0);
        Assert.assertFalse("No debe permitir tenges <= 0", road3x3.ok());
        Assert.assertEquals(0, road3x3.stores().length);

        road3x3.placeStore(0, -5);
        Assert.assertFalse("No debe permitir tenges negativos", road3x3.ok());
        Assert.assertEquals(0, road3x3.stores().length);
    }

    @Test
    public void shouldNotPlaceStoreWhenCellAlreadyHasStore() {
        road3x3.placeStore(0, 10);
        Assert.assertTrue(road3x3.ok());

        road3x3.placeStore(0, 5);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(1, road3x3.stores().length);
        Assert.assertEquals(10, road3x3.stores()[0][1]);
    }

    @Test
    public void shouldRemoveStoreWhenExists() {
        road3x3.placeStore(1, 10);
        Assert.assertTrue(road3x3.ok());
        road3x3.removeStore(1);
        Assert.assertTrue("removeStore exitoso", road3x3.ok());
        Assert.assertEquals(0, road3x3.stores().length);
    }

    @Test
    public void shouldNotRemoveStoreWhenNotExists() {
        road3x3.removeStore(2);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(0, road3x3.stores().length);
    }

    @Test
    public void shouldResupplyStores() {
        road3x3.placeStore(0, 8);
        Assert.assertTrue(road3x3.ok());
        road3x3.resupplyStores();
        Assert.assertTrue(road3x3.ok());
        Assert.assertEquals(1, road3x3.stores().length);
    }

    @Test
    public void shouldPlaceRobotAtValidLocation() {
        road3x3.placeRobot(0);
        Assert.assertTrue(road3x3.ok());
        int[][] robots = road3x3.robots();
        Assert.assertEquals(1, robots.length);
        Assert.assertEquals(0, robots[0][0]);
        Assert.assertEquals(0, robots[0][1]);
    }

    @Test
    public void shouldNotPlaceRobotAtInvalidLocation() {
        road3x3.placeRobot(-1);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(0, road3x3.robots().length);

        road3x3.placeRobot(pathLen3x3);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(0, road3x3.robots().length);
    }

    @Test
    public void shouldNotPlaceRobotWhenInitialCellTaken() {
        road3x3.placeRobot(0);
        Assert.assertTrue(road3x3.ok());
        road3x3.placeRobot(0);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(1, road3x3.robots().length);
    }

    @Test
    public void shouldRemoveRobotWhenExists() {
        road3x3.placeRobot(1);
        Assert.assertTrue(road3x3.ok());
        road3x3.removeRobot(1);
        Assert.assertTrue(road3x3.ok());
        Assert.assertEquals(0, road3x3.robots().length);
    }

    @Test
    public void shouldNotRemoveRobotWhenNotExists() {
        road3x3.removeRobot(5);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(0, road3x3.robots().length);
    }

    @Test
    public void shouldReturnRobotsToStart() {
        road3x3.placeRobot(0);
        road3x3.placeStore(2, 1);
        road3x3.moveRobot(0, 1);
        road3x3.returnRobots();
        Assert.assertTrue(road3x3.ok());
        int[][] robots = road3x3.robots();
        Assert.assertEquals(1, robots.length);
        Assert.assertEquals(0, robots[0][0]);
    }

    @Test
    public void shouldMoveRobotAndCollectProfitWhenOnStore() throws silkroad.SilkRoadException {
        road3x3.placeStore(0, 10);
        road3x3.placeRobot(0);
        road3x3.moveRobot(0, 0);
        Assert.assertTrue(road3x3.ok());
        Assert.assertEquals(10, road3x3.profit());
        int[][] robots = road3x3.robots();
        Assert.assertEquals(1, robots.length);
        Assert.assertEquals(0, robots[0][0]);
        Assert.assertEquals(10, robots[0][1]);
        int[][] perMove = road3x3.robotsProfitPerMove();
        Assert.assertEquals(1, perMove.length);
        Assert.assertTrue(perMove[0].length >= 2);
    }

    @Test
    public void shouldNotMoveRobotWhenNoRobotAtLocation() throws silkroad.SilkRoadException {
        road3x3.moveRobot(0, 1);
        Assert.assertFalse(road3x3.ok());
        Assert.assertEquals(0, road3x3.robots().length);
        Assert.assertEquals(0, road3x3.profit());
    }

    @Test
    public void shouldMoveRobotsAutomaticallyWhenProfitable() throws silkroad.SilkRoadException {
        road3x3.placeRobot(0);
        road3x3.placeStore(1, 10);
        road3x3.moveRobots();
        int[][] robots = road3x3.robots();
        Assert.assertEquals(1, robots.length);
        Assert.assertEquals(1, robots[0][0]);
        Assert.assertTrue(road3x3.profit() > 0);
    }

    @Test
    public void shouldReportStoresEmptied() {
        road3x3.placeStore(0, 6);
        road3x3.placeRobot(0);
        road3x3.moveRobot(0, 0);
        int[][] emptied = road3x3.storesEmptied();
        Assert.assertEquals(1, emptied.length);
        Assert.assertEquals(0, emptied[0][0]);
        Assert.assertTrue(emptied[0][1] >= 1);
    }

    @Test
    public void shouldReturnEmptyStoresEmptiedWhenNoStores() {
        int[][] emptied = road3x3.storesEmptied();
        Assert.assertEquals(0, emptied.length);
    }

    @Test
    public void shouldReturnEmptyRobotsProfitPerMoveWhenNoRobots() {
        int[][] perMove = road3x3.robotsProfitPerMove();
        Assert.assertEquals(0, perMove.length);
    }

    @Test
    public void shouldRebootAndResetProfitAndPositions() throws silkroad.SilkRoadException {
        road3x3.placeStore(0, 5);
        road3x3.placeRobot(0);
        road3x3.moveRobot(0, 0);
        Assert.assertTrue(road3x3.profit() >= 5);
        road3x3.reboot();
        Assert.assertTrue(road3x3.ok());
        Assert.assertEquals(0, road3x3.profit());
        int[][] robots = road3x3.robots();
        if (robots.length > 0) {
            Assert.assertEquals(0, robots[0][0]);
        }
    }

    @Test
    public void shouldConsultSilkRoadReturnInformativeSummary() {
        road3x3.placeStore(0, 10);
        road3x3.placeRobot(1);
        String info = road3x3.consultSilkRoad();
        Assert.assertTrue(info.contains("Ruta de la Seda 3x3"));
        Assert.assertTrue(info.contains("Longitud: " + pathLen3x3 + " celdas"));
        Assert.assertTrue(info.contains("Tiendas: 1"));
        Assert.assertTrue(info.contains("Robots: 1"));
        Assert.assertTrue(info.contains("Ganancias: 0 tenges"));
    }

    private int extractPathLength(SilkRoad r) {
        String info = r.consultSilkRoad();
        String marker = "Longitud: ";
        int idx = info.indexOf(marker);
        if (idx < 0) return -1;
        int start = idx + marker.length();
        int end = info.indexOf(" celdas", start);
        return Integer.parseInt(info.substring(start, end));
        }
}