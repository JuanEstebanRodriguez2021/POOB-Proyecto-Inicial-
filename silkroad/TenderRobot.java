package silkroad;

/**
 * Robot tierno: solo toma la mitad del dinero de las tiendas.
 * Deja algo para otros robots.
 */
public class TenderRobot extends RobotEntity {
    
    /**
     * Constructor de robot tierno.
     * @param pathIndex Posición inicial.
     */
    public TenderRobot(int pathIndex) {
        super(pathIndex);
        this.type = "tender";
        this.baseColor = "green";
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
     * Toma solo la mitad del dinero disponible.
     * @param store Tienda a visitar.
     * @return La mitad de lo que la tienda ofrezca.
     */
    @Override
    public int collectFromStore(Store store) {
        int fullAmount = store.collectTenges(this);
        if (fullAmount > 0) {
            int half = fullAmount / 2;
            // Devolver la otra mitad a la tienda
            store.currentValue = fullAmount - half;
            return half;
        }
        return 0;
    }
}