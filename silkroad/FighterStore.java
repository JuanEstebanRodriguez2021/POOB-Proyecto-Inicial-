package silkroad;

/**
 * Tienda luchadora: solo robots con más dinero que ella pueden tomarla.
 * Representa una tienda protegida que requiere "poder económico".
 */
public class FighterStore extends Store {
    
    /**
     * Constructor de tienda luchadora.
     * @param location Ubicación en la ruta.
     * @param initialValue Valor inicial en tenges.
     */
    public FighterStore(int location, int initialValue) {
        super(location, initialValue);
        this.color = "red";
        this.type = "fighter";
    }
    
    /**
     * Solo permite recolección si el robot tiene más dinero que la tienda.
     * @param robot El robot que intenta recolectar.
     * @return Cantidad recolectada (0 si el robot no califica).
     */
    @Override
    public int collectTenges(RobotEntity robot) {
        if (currentValue > 0 && robot.getCollectedTenges() > currentValue) {
            int collected = currentValue;
            currentValue = 0;
            timesEmptied++;
            return collected;
        }
        return 0; // Robot no tiene suficiente dinero
    }
}