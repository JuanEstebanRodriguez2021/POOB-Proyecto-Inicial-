package silkroad;

/**
 * Clase base abstracta para representar una tienda en la Ruta de la Seda.
 * Ciclo 4: Implementa herencia para diferentes tipos de tiendas.
 */
public abstract class Store {
    protected int location;
    protected int initialValue;
    protected int currentValue;
    protected int timesEmptied;
    protected String color;
    protected String type;
    
    /**
     * Constructor base para todas las tiendas.
     * @param location Ubicación de la tienda en la ruta.
     * @param initialValue Valor inicial en tenges.
     */
    public Store(int location, int initialValue) {
        this.location = location;
        this.initialValue = initialValue;
        this.currentValue = initialValue;
        this.timesEmptied = 0;
        this.color = "green";
        this.type = "normal";
    }
    
    /**
     * Permite que un robot recolecte dinero de la tienda.
     * Comportamiento específico según el tipo de tienda y robot.
     * @param robot El robot que intenta recolectar.
     * @return La cantidad de tenges recolectados.
     */
    public abstract int collectTenges(RobotEntity robot);
    
    /**
     * Reabastece la tienda a su valor inicial.
     */
    public void resupply() {
        this.currentValue = this.initialValue;
    }
    
    /**
     * Obtiene el valor actual de la tienda.
     * @return Valor actual en tenges.
     */
    public int getCurrentValue() {
        return currentValue;
    }
    
    /**
     * Obtiene el valor inicial de la tienda.
     * @return Valor inicial en tenges.
     */
    public int getInitialValue() {
        return initialValue;
    }
    
    /**
     * Obtiene la ubicación de la tienda.
     * @return Ubicación en la ruta.
     */
    public int getLocation() {
        return location;
    }
    
    /**
     * Establece la ubicación de la tienda (usado por autonomous).
     * @param newLocation Nueva ubicación.
     */
    public void setLocation(int newLocation) {
        this.location = newLocation;
    }
    
    /**
     * Obtiene las veces que ha sido vaciada.
     * @return Número de veces vaciada.
     */
    public int getTimesEmptied() {
        return timesEmptied;
    }
    
    /**
     * Obtiene el color de la tienda.
     * @return Color como String.
     */
    public String getColor() {
        return currentValue > 0 ? color : "gray";
    }
    
    /**
     * Obtiene el tipo de tienda.
     * @return Tipo como String.
     */
    public String getType() {
        return type;
    }
    
    /**
     * Verifica si la tienda está vacía.
     * @return true si no tiene tenges.
     */
    public boolean isEmpty() {
        return currentValue <= 0;
    }
}