package silkroad;

/**
 * Robot que nunca retrocede: solo puede avanzar hacia adelante.
 * No puede moverse a posiciones menores a su posición actual.
 */
public class NeverBackRobot extends RobotEntity {
    
    /**
     * Constructor de robot que nunca retrocede.
     * @param pathIndex Posición inicial.
     */
    public NeverBackRobot(int pathIndex) {
        super(pathIndex);
        this.type = "neverback";
        this.baseColor = "magenta";
        robotShape.changeColor(baseColor);
    }
    
    /**
     * Solo puede moverse hacia adelante (índices mayores).
     * @param newIndex Nueva posición propuesta.
     * @return true si newIndex >= posición actual.
     */
    @Override
    public boolean canMoveTo(int newIndex) {
        return newIndex >= currentPathIndex;
    }
    
    /**
     * Recolecta normalmente de la tienda.
     * @param store Tienda a visitar.
     * @return Cantidad recolectada.
     */
    @Override
    public int collectFromStore(Store store) {
        return store.collectTenges(this);
    }
}