package silkroad;

/**
 * Robot normal: puede moverse en cualquier dirección y toma todo el dinero.
 * Comportamiento estándar de los robots.
 */
public class NormalRobot extends RobotEntity {
    
    /**
     * Constructor de robot normal.
     * @param pathIndex Posición inicial.
     */
    public NormalRobot(int pathIndex) {
        super(pathIndex);
        this.type = "normal";
        this.baseColor = "blue";
        robotShape.changeColor(baseColor);
    }
    
    /**
     * Puede moverse a cualquier posición.
     * @param newIndex Nueva posición.
     * @return Siempre true.
     */
    @Override
    public boolean canMoveTo(int newIndex) {
        return true; // Sin restricciones
    }
    
    /**
     * Recolecta todo lo que la tienda le permita.
     * @param store Tienda a visitar.
     * @return Cantidad recolectada.
     */
    @Override
    public int collectFromStore(Store store) {
        return store.collectTenges(this);
    }
}