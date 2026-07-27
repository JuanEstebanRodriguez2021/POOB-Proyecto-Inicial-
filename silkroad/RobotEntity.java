package silkroad;
import figures.Circle;
import java.util.ArrayList;

/**
 * Clase base abstracta para representar un robot en la Ruta de la Seda.
 * Ciclo 4: Refactorización usando herencia para diferentes tipos de robots.
 */
public abstract class RobotEntity {
    protected int initialPathIndex;
    protected int currentPathIndex;
    protected int collectedTenges;
    protected Circle robotShape;
    protected ArrayList<Integer> profitHistory;
    protected boolean isBlinking;
    protected String type;
    protected String baseColor;
    
    /**
     * Constructor base para todos los robots.
     * @param pathIndex Posición inicial en el camino.
     */
    public RobotEntity(int pathIndex) {
        this.initialPathIndex = pathIndex;
        this.currentPathIndex = pathIndex;
        this.collectedTenges = 0;
        this.profitHistory = new ArrayList<>();
        this.isBlinking = false;
        this.type = "normal";
        this.baseColor = "blue";
        
        robotShape = new Circle();
        robotShape.changeSize(30);
        robotShape.changeColor(baseColor);
        robotShape.moveTo(15, 15);
    }
    
    /**
     * Determina si el robot puede moverse a una nueva posición.
     * Comportamiento específico según tipo de robot.
     * @param newIndex Nueva posición propuesta.
     * @return true si puede moverse.
     */
    public abstract boolean canMoveTo(int newIndex);
    
    /**
     * Procesa la recolección de tenges de una tienda.
     * Comportamiento específico según tipo de robot.
     * @param store La tienda a visitar.
     * @return La cantidad neta recolectada.
     */
    public abstract int collectFromStore(Store store);
    
    /**
     * Mueve el robot a una nueva posición.
     * @param newPathIndex Nueva posición en el camino.
     */
    public void moveTo(int newPathIndex) {
        this.currentPathIndex = newPathIndex;
    }
    
    /**
     * Añade tenges al robot.
     * @param amount Cantidad a añadir.
     */
    public void addTenges(int amount) {
        if (amount > 0) {
            this.collectedTenges += amount;
        }
    }
    
    /**
     * Registra la ganancia de un movimiento.
     * @param profit Ganancia del movimiento.
     */
    public void recordProfit(int profit) {
        profitHistory.add(profit);
    }
    
    /**
     * Retorna el robot a su posición inicial.
     */
    public void returnToStart() {
        currentPathIndex = initialPathIndex;
        collectedTenges = 0;
        profitHistory.clear();
    }
    
    /**
     * Actualiza la posición visual del robot.
     * @param x Coordenada X.
     * @param y Coordenada Y.
     */
    public void updatePosition(int x, int y) {
        robotShape.moveTo(x + 10, y + 10);
    }
    
    /**
     * Cambia el color del robot.
     * @param newColor Nuevo color.
     */
    public void changeColor(String newColor) {
        this.baseColor = newColor;
        robotShape.changeColor(newColor);
    }
    
    // Getters
    public int getCurrentPathIndex() { 
        return currentPathIndex; 
    }
    
    public int getInitialPathIndex() { 
        return initialPathIndex; 
    }
    
    public int getCollectedTenges() { 
        return collectedTenges; 
    }
    
    public ArrayList<Integer> getProfitHistory() { 
        return new ArrayList<>(profitHistory); 
    }
    
    public String getType() { 
        return type; 
    }
    
    public String getBaseColor() { 
        return baseColor; 
    }
    
    // Métodos de parpadeo
    public void startBlinking() { 
        isBlinking = true; 
    }
    
    public void stopBlinking() { 
        isBlinking = false; 
    }
    
    public boolean isBlinking() { 
        return isBlinking; 
    }
    
    // Métodos de visibilidad
    public void makeVisible() { 
        robotShape.makeVisible(); 
    }
    
    public void makeInvisible() { 
        robotShape.makeInvisible(); 
    }
}