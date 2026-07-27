package silkroad;
import figures.Rectangle;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Simulador de la Ruta de la Seda con robots y tiendas.
 * Ciclo 4: Soporte para diferentes tipos de robots y tiendas mediante herencia.
 */
public class SilkRoad {
    
    private int size;
    private PathCell[][] board;
    private Rectangle background;
    private ArrayList<PathCell> path;
    private ArrayList<RobotEntity> robots;
    private ArrayList<PathCell> storeCells;
    private boolean visible;
    private boolean ok;
    private int totalProfit;
    private Rectangle profitBar;
    private Rectangle profitBarBackground;
    private final int PROFITBARHEIGTH = 20;
    private Thread blinkingThread;

    /**
     * Constructor básico - usa tipos normales.
     */
    public SilkRoad(int length) {
        initializeSimulator(length);
    }
    
    /**
     * Constructor con entrada de maratón - usa solo tipos normales.
     */
    public SilkRoad(int[][] entries) {
        if (entries == null || entries.length == 0) {
            ok = false;
            return;
        }
        
        int maxLocation = 0;
        for (int[] entry : entries) {
            if (entry.length >= 2 && entry[1] > maxLocation) {
                maxLocation = entry[1];
            }
        }
        
        int calculatedSize = (int) Math.ceil(Math.sqrt(maxLocation / 4.0)) + 2;
        initializeSimulator(Math.max(3, calculatedSize));
        
        for (int[] entry : entries) {
            if (entry[0] == 1) {
                placeRobot(entry[1], "normal");
            } else if (entry[0] == 2 && entry.length >= 3) {
                try
                {
                    placeStore(entry[1], entry[2], "normal");
                }
                catch (silkroad.SilkRoadException sre)
                {
                    sre.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Inicializa el simulador.
     */
    private void initializeSimulator(int length) {
        if (length > 1) {
            this.size = length;
            this.board = new PathCell[length][length];
            this.path = new ArrayList<>();
            this.robots = new ArrayList<>();
            this.storeCells = new ArrayList<>();
            this.visible = false;
            this.ok = true;
            this.totalProfit = 0;
            
            background = new Rectangle("black", length * 55 + 5, length * 55 + 5, 0, 0);
            profitBar = new Rectangle("yellow", PROFITBARHEIGTH, 0, 5, length * 55 + 15);
            profitBarBackground = new Rectangle("black", PROFITBARHEIGTH, 0, 5, length * 55 + 15);
            
            createBoardAndPath();
        } else {
            JOptionPane.showMessageDialog(null, "No se puede crear un camino de esa longitud.");
        }
    }

    /**
     * Crea el tablero y la ruta.
     */
    private void createBoardAndPath() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new PathCell(j * 55 + 5, i * 55 + 5);
            }
        }

        // Lado superior
        for (int j = 0; j < size; j++) {
            board[0][j].setAsPath();
            path.add(board[0][j]);
        }
        // Lado derecho
        for (int i = 1; i < size; i++) {
            board[i][size - 1].setAsPath();
            path.add(board[i][size - 1]);
        }
        // Lado inferior
        if (size > 1) {
            for (int j = size - 2; j >= 0; j--) {
                board[size - 1][j].setAsPath();
                path.add(board[size - 1][j]);
            }
        }
        // Lado izquierdo
        if (size > 2) {
            for (int i = size - 2; i > 0; i--) {
                board[i][0].setAsPath();
                path.add(board[i][0]);
            }
        }
    }
    
    /**
     * Coloca una tienda (sobrecarga para compatibilidad).
     */
    public void placeStore(int location, int tenges) {
        try
        {
            placeStore(location, tenges, "normal");
        }
        catch (silkroad.SilkRoadException sre)
        {
            sre.printStackTrace();
        }
    }
    
    /**
     * Coloca una tienda de un tipo específico.
     * @param location Ubicación en la ruta.
     * @param tenges Valor inicial.
     * @param type Tipo: "normal", "autonomous", "fighter", "generous".
     */
    public void placeStore(int location, int tenges, String type) throws SilkRoadException {
        if (path == null || path.isEmpty()){
            throw new SilkRoadException(SilkRoadException.INVALID_PATH);
        }
        ok = false;
        if (location >= 0 && location < path.size() && tenges > 0) {
            PathCell cell = path.get(location);
            if (!cell.hasStore()) {
                Store newStore = createStore(location, tenges, type);
                
                // Autonomous store escoge su ubicación
                if (newStore instanceof AutonomousStore) {
                    int[] robotPositions = getRobotPositions();
                    ((AutonomousStore) newStore).chooseLocation(robotPositions, path.size());
                    location = newStore.getLocation();
                    cell = path.get(location);
                }
                
                cell.setStore(newStore);
                storeCells.add(cell);
                ok = true;
            } else if (visible) {
                JOptionPane.showMessageDialog(null, "Ya existe una tienda en esa posición.");
            }
        } else if (visible) {
            JOptionPane.showMessageDialog(null, "Posición inválida o cantidad inválida.");
        }
        updateProfitBarBackground();
    }
    
    /**
     * Crea una tienda del tipo especificado.
     */
    private Store createStore(int location, int tenges, String type) {
        switch (type.toLowerCase()) {
            case "autonomous":
                return new AutonomousStore(location, tenges);
            case "fighter":
                return new FighterStore(location, tenges);
            case "generous":
                return new GenerousStore(location, tenges);
            default:
                return new NormalStore(location, tenges);
        }
    }
    
    /**
     * Obtiene las posiciones actuales de todos los robots.
     */
    private int[] getRobotPositions() {
        int[] positions = new int[robots.size()];
        for (int i = 0; i < robots.size(); i++) {
            positions[i] = robots.get(i).getCurrentPathIndex();
        }
        return positions;
    }
    
    /**
     * Elimina una tienda.
     */
    public void removeStore(int location) {
        ok = false;
        if (location >= 0 && location < path.size()) {
            PathCell cell = path.get(location);
            if (cell.hasStore()) {
                cell.removeStore();
                storeCells.remove(cell);
                ok = true;
            } else if (visible) {
                JOptionPane.showMessageDialog(null, "No hay tienda en esa posición.");
            }
        } else if (visible) {
            JOptionPane.showMessageDialog(null, "Posición fuera de la ruta.");
        }
        updateProfitBarBackground();
    }
    
    /**
     * Reabastece todas las tiendas.
     */
    public void resupplyStores() {
        for (PathCell cell : storeCells) {
            cell.resupply();
        }
        updateProfitBarBackground();
        ok = true;
    }
    
    /**
     * Coloca un robot (sobrecarga para compatibilidad).
     */
    public void placeRobot(int location) {
        placeRobot(location, "normal");
    }
    
    /**
     * Coloca un robot de un tipo específico.
     * @param location Ubicación inicial.
     * @param type Tipo: "normal", "neverback", "tender", "speedy".
     */
    public void placeRobot(int location, String type) {
        ok = false;
        if (location >= 0 && location < path.size()) {
            boolean positionTaken = false;
            for (RobotEntity r : robots) {
                if (r.getInitialPathIndex() == location) {
                    positionTaken = true;
                    break;
                }
            }
            
            if (!positionTaken) {
                RobotEntity newRobot = createRobot(location, type);
                robots.add(newRobot);
                assignUniqueColor(newRobot);
                updateRobotPositions();
                ok = true;
                if (visible) {
                    newRobot.makeVisible();
                }
            } else if (visible) {
                JOptionPane.showMessageDialog(null, "Ya hay un robot en esa posición.");
            }
        } else if (visible) {
            JOptionPane.showMessageDialog(null, "Posición fuera de la ruta.");
        }
    }
    
    /**
     * Crea un robot del tipo especificado.
     */
    private RobotEntity createRobot(int location, String type) {
        switch (type.toLowerCase()) {
            case "neverback":
                return new NeverBackRobot(location);
            case "tender":
                return new TenderRobot(location);
            case "speedy":
                return new SpeedyRobot(location);
            default:
                return new NormalRobot(location);
        }
    }
    
    /**
     * Asigna colores únicos a robots según su tipo base.
     */
    private void assignUniqueColor(RobotEntity robot) {
        // El color base ya está asignado según el tipo
        // Aquí podríamos añadir variaciones si hay múltiples del mismo tipo
    }
    
    /**
     * Elimina un robot.
     */
    public void removeRobot(int location) {
        ok = false;
        RobotEntity robotToRemove = null;
        for (RobotEntity r : robots) {
            if (r.getCurrentPathIndex() == location) {
                robotToRemove = r;
                break;
            }
        }
        
        if (robotToRemove != null) {
            robotToRemove.makeInvisible();
            robots.remove(robotToRemove);
            ok = true;
        } else if (visible) {
            JOptionPane.showMessageDialog(null, "No hay robot en esa posición.");
        }
        updateProfitBar();
        updateBlinkingRobot();
    }
    
    /**
     * Retorna todos los robots a sus posiciones iniciales.
     */
    public void returnRobots() {
        for (RobotEntity r : robots) {
            r.returnToStart();
        }
        updateRobotPositions();
        updateProfitBar();
        updateBlinkingRobot();
        ok = true;
    }
    
    /**
     * Mueve un robot considerando sus restricciones de tipo.
     */
    public void moveRobot(int location, int meters) {
        ok = false;
        RobotEntity robotToMove = null;
        for (RobotEntity r : robots) {
            if (r.getCurrentPathIndex() == location) {
                robotToMove = r;
                break;
            }
        }

        if (robotToMove != null) {
            int initialIndex = robotToMove.getCurrentPathIndex();
            int newIndex = (initialIndex + meters) % path.size();
            if (newIndex < 0) {
                newIndex += path.size();
            }
            
            // Verificar si el robot puede moverse a esa posición
            if (!robotToMove.canMoveTo(newIndex)) {
                if (visible) {
                    JOptionPane.showMessageDialog(null, 
                        "Este robot tipo " + robotToMove.getType() + " no puede moverse a esa posición.");
                }
                return;
            }
            
            int distance = Math.abs(meters);
            int movementCost = distance;
            
            // SpeedyRobot tiene costo reducido
            if (robotToMove instanceof SpeedyRobot) {
                movementCost = ((SpeedyRobot) robotToMove).getMovementCost(distance);
            }
            
            int profitFromMove = -movementCost;
            
            robotToMove.moveTo(newIndex);
            PathCell destinationCell = path.get(newIndex);
            
            if (destinationCell.hasStore()) {
                int collected = destinationCell.collectTenges(robotToMove);
                profitFromMove = collected - movementCost;
                robotToMove.addTenges(collected);
                totalProfit += collected;
            }
            
            robotToMove.recordProfit(profitFromMove);
            updateRobotPositions();
            updateProfitBar();
            updateBlinkingRobot();
            ok = true;
        } else if (visible) {
            JOptionPane.showMessageDialog(null, "No hay un robot en la posición inicial.");
        }
    }
    
    /**
     * Mueve robots automáticamente para maximizar ganancias.
     */
    public void moveRobots() {
        ok = false;
        if (robots.isEmpty() || storeCells.isEmpty()) {
            ok = true;
            return;
        }
        
        for (RobotEntity robot : robots) {
            int bestProfit = Integer.MIN_VALUE;
            int bestStoreLocation = -1;
            int robotLocation = robot.getCurrentPathIndex();
            
            for (PathCell storeCell : storeCells) {
                Store store = storeCell.getStore();
                if (store == null || store.getCurrentValue() <= 0) continue;
                
                int storeLocation = path.indexOf(storeCell);
                if (storeLocation == -1) continue;
                
                // Verificar si el robot puede moverse a esa posición
                if (!robot.canMoveTo(storeLocation)) continue;
                
                int distance = Math.abs(storeLocation - robotLocation);
                int movementCost = distance;
                
                if (robot instanceof SpeedyRobot) {
                    movementCost = ((SpeedyRobot) robot).getMovementCost(distance);
                }
                
                int potentialProfit = store.getCurrentValue() - movementCost;
                
                // TenderRobot solo toma la mitad
                if (robot instanceof TenderRobot) {
                    potentialProfit = (store.getCurrentValue() / 2) - movementCost;
                }
                
                // FighterStore requiere más dinero
                if (store instanceof FighterStore && 
                    robot.getCollectedTenges() <= store.getCurrentValue()) {
                    continue;
                }
                
                if (potentialProfit > bestProfit) {
                    bestProfit = potentialProfit;
                    bestStoreLocation = storeLocation;
                }
            }
            
            if (bestStoreLocation != -1 && bestProfit > 0) {
                int distance = bestStoreLocation - robotLocation;
                moveRobot(robotLocation, distance);
            }
        }
        ok = true;
    }
    
    /**
     * Actualiza qué robot debe parpadear.
     */
    private void updateBlinkingRobot() {
        RobotEntity maxProfitRobot = null;
        int maxProfit = Integer.MIN_VALUE;
        
        for (RobotEntity r : robots) {
            r.stopBlinking();
            if (r.getCollectedTenges() > maxProfit) {
                maxProfit = r.getCollectedTenges();
                maxProfitRobot = r;
            }
        }
        
        if (maxProfitRobot != null && maxProfit > 0) {
            maxProfitRobot.startBlinking();
            startBlinkingEffect(maxProfitRobot);
        }
    }
    
    /**
     * Efecto visual de parpadeo.
     */
    private void startBlinkingEffect(final RobotEntity robot) {
        if (blinkingThread != null && blinkingThread.isAlive()) {
            blinkingThread.interrupt();
        }
        
        if (!visible) return;
        
        blinkingThread = new Thread(new Runnable() {
            public void run() {
                String[] colors = {"yellow", "red"};
                int colorIndex = 0;
                while (robot.isBlinking() && !Thread.interrupted()) {
                    robot.changeColor(colors[colorIndex]);
                    colorIndex = (colorIndex + 1) % colors.length;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        blinkingThread.start();
    }
    
    /**
     * Consulta tiendas vaciadas.
     */
    public int[][] storesEmptied() {
        if (storeCells.isEmpty()) {
            return new int[0][2];
        }
        
        ArrayList<int[]> result = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            PathCell cell = path.get(i);
            if (cell.hasStore()) {
                result.add(new int[]{i, cell.getTimesEmptied()});
            }
        }
        
        return result.toArray(new int[0][2]);
    }
    
    /**
     * Consulta ganancias por movimiento de robots.
     */
    public int[][] robotsProfitPerMove() {
        if (robots.isEmpty()) {
            return new int[0][0];
        }
        
        int maxMoves = 0;
        for (RobotEntity r : robots) {
            maxMoves = Math.max(maxMoves, r.getProfitHistory().size());
        }
        
        int[][] result = new int[robots.size()][maxMoves + 1];
        for (int i = 0; i < robots.size(); i++) {
            result[i][0] = i;
            ArrayList<Integer> history = robots.get(i).getProfitHistory();
            for (int j = 0; j < history.size(); j++) {
                result[i][j + 1] = history.get(j);
            }
        }
        
        return result;
    }
    
    /**
     * Reinicia la ruta.
     */
    public void reboot() {
        resupplyStores();
        returnRobots();
        totalProfit = 0;
        updateProfitBar();
        updateProfitBarBackground();
        ok = true;
    }
    
    /**
     * Consulta ganancias.
     */
    public int profit() throws SilkRoadException {
        if (totalProfit < 0) {
        throw new SilkRoadException(SilkRoadException.PROFIT_ERROR);
    }
        return totalProfit;
    }
    
    /**
     * Consulta información.
     */
    public String consultSilkRoad() {
        StringBuilder info = new StringBuilder();
        info.append("Ruta de la Seda ").append(size).append("x").append(size).append("\n");
        info.append("Longitud: ").append(path.size()).append(" celdas\n");
        info.append("Tiendas: ").append(storeCells.size()).append("\n");
        info.append("Robots: ").append(robots.size()).append("\n");
        info.append("Ganancias: ").append(totalProfit).append(" tenges\n");
        info.append("Máximo posible: ").append(getMaxPossibleProfit()).append(" tenges");
        return info.toString();
    }
    
    private int getMaxPossibleProfit() {
        int maxProfit = 0;
        for (PathCell cell : storeCells) {
            maxProfit += cell.getCurrentValue();
        }
        return maxProfit;
    }
    
    private void updateRobotPositions() {
        for (RobotEntity r : robots) {
            int pathIndex = r.getCurrentPathIndex();
            if (pathIndex >= 0 && pathIndex < path.size()) {
                PathCell cell = path.get(pathIndex);
                r.updatePosition(cell.getX(), cell.getY());
            }
        }
    }
    
    private void updateProfitBarBackground(){
        profitBarBackground.changeSize(20, getMaxPossibleProfit());    
    }
    
    private void updateProfitBar() {
        profitBar.changeSize(20, totalProfit);
    }
    
    public int[][] stores() {
        if (storeCells.isEmpty()) {
            return new int[0][2];
        }
        ArrayList<int[]> storeInfo = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            PathCell cell = path.get(i);
            if (cell.hasStore()) {
                storeInfo.add(new int[]{i, cell.getCurrentValue()});
            }
        }
        return storeInfo.toArray(new int[0][2]);
    }
    
    public int[][] robots() {
        if (robots.isEmpty()) {
            return new int[0][2];
        }
        ArrayList<int[]> robotInfo = new ArrayList<>();
        for (RobotEntity robot : robots) {
            robotInfo.add(new int[]{robot.getCurrentPathIndex(), robot.getCollectedTenges()});
        }
        robotInfo.sort((a, b) -> Integer.compare(a[0], b[0]));
        return robotInfo.toArray(new int[0][2]);
    }
    
    public void makeVisible() {
        visible = true;
        background.makeVisible();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].makeVisible();
            }
        }
        for (RobotEntity r : robots) {
            r.makeVisible();
        }
        profitBarBackground.makeVisible();
        profitBar.makeVisible();
        updateRobotPositions();
        updateBlinkingRobot();
    }
    
    public void makeInvisible() {
        visible = false;
        if (blinkingThread != null) {
            blinkingThread.interrupt();
        }
        profitBar.makeInvisible();
        profitBarBackground.makeInvisible();
        for (RobotEntity r : robots) {
            r.makeInvisible();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j].makeInvisible();
            }
        }
        background.makeInvisible();
    }
    
    public void finish() {
        if (blinkingThread != null) {
            blinkingThread.interrupt();
        }
        makeInvisible();
        robots.clear();
        storeCells.clear();
        path.clear();
        System.exit(0);
    }
    
    public boolean ok() {
        return ok;
    }
}