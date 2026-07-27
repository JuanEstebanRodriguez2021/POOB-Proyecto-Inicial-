package silkroad;

/**
 * TIPO PROPUESTO: Tienda generosa.
 * Da el doble de dinero al primer robot que la visita, pero luego queda vacía.
 * Incentiva a los robots a competir por llegar primero.
 */
public class GenerousStore extends Store {
    private boolean firstVisit;
    
    /**
     * Constructor de tienda generosa.
     * @param location Ubicación en la ruta.
     * @param initialValue Valor inicial en tenges.
     */
    public GenerousStore(int location, int initialValue) {
        super(location, initialValue);
        this.color = "yellow";
        this.type = "generous";
        this.firstVisit = true;
    }
    
    /**
     * Da el doble al primer visitante, nada a los siguientes.
     * @param robot El robot que recolecta.
     * @return El doble del valor si es primera visita, 0 en caso contrario.
     */
    @Override
    public int collectTenges(RobotEntity robot) {
        if (currentValue > 0 && firstVisit) {
            int collected = currentValue * 2; // Da el doble
            currentValue = 0;
            firstVisit = false;
            timesEmptied++;
            return collected;
        }
        return 0;
    }
    
    /**
     * Reabastece y resetea la bandera de primera visita.
     */
    @Override
    public void resupply() {
        super.resupply();
        this.firstVisit = true;
    }
}