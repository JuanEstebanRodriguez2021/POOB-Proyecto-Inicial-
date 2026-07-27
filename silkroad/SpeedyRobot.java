package silkroad;

/**
 * TIPO PROPUESTO: Robot veloz.
 * Se mueve al doble de velocidad (costo de movimiento es la mitad).
 * Representa un robot más eficiente en sus desplazamientos.
 */
public class SpeedyRobot extends RobotEntity {
    
    /**
     * Constructor de robot veloz.
     * @param pathIndex Posición inicial.
     */
    public SpeedyRobot(int pathIndex) {
        super(pathIndex);
        this.type = "speedy";
        this.baseColor = "yellow";
        robotShape.changeColor(baseColor);
    }
    
    /**
     * Puede moverse a cualquier posición.
     * @param newIndex Nueva posición.
     * @return Siempre true.
     */
    @Override
    public boolean canMoveTo(int newIndex) {
        return true;
    }
    
    /**
     * Recolecta normalmente de la tienda.
     * La ventaja del SpeedyRobot está en el costo reducido de movimiento,
     * que se maneja en SilkRoad al calcular la ganancia.
     * @param store Tienda a visitar.
     * @return Cantidad recolectada.
     */
    @Override
    public int collectFromStore(Store store) {
        return store.collectTenges(this);
    }
    
    /**
     * Calcula el costo de movimiento para este robot.
     * @param distance Distancia a recorrer.
     * @return La mitad del costo normal.
     */
    public int getMovementCost(int distance) {
        return distance / 2; // Cuesta la mitad
    }
}