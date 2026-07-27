package silkroad;

/**
 * Tienda normal: cualquier robot puede tomar todo su dinero.
 * Comportamiento estándar de las tiendas.
 */
public class NormalStore extends Store {
    
    /**
     * Constructor de tienda normal.
     * @param location Ubicación en la ruta.
     * @param initialValue Valor inicial en tenges.
     */
    public NormalStore(int location, int initialValue) {
        super(location, initialValue);
        this.color = "green";
        this.type = "normal";
    }
    
    /**
     * Permite recolectar todo el dinero disponible.
     * @param robot El robot que recolecta (no importa el tipo).
     * @return La cantidad total recolectada.
     */
    @Override
    public int collectTenges(RobotEntity robot) {
        if (currentValue > 0) {
            int collected = currentValue;
            currentValue = 0;
            timesEmptied++;
            return collected;
        }
        return 0;
    }
}