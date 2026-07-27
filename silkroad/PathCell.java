package silkroad;
import figures.Rectangle;

/**
 * Representa una celda en el tablero de SilkRoad.
 * Ciclo 4: Ahora usa objetos Store para manejar tiendas con herencia.
 */
public class PathCell {
    private Rectangle cellShape;
    private boolean isPath;
    private Store store; // Cambio: ahora es un objeto Store
    private int xPosition;
    private int yPosition;

    /**
     * Constructor para objetos de la clase PathCell.
     * @param x La coordenada x para dibujar la celda.
     * @param y La coordenada y para dibujar la celda.
     */
    public PathCell(int x, int y) {
        this.isPath = false;
        this.store = null;
        this.xPosition = x;
        this.yPosition = y;
        
        cellShape = new Rectangle();
        cellShape.changeSize(50, 50);
        cellShape.moveTo(x, y);
        cellShape.changeColor("black");
    }

    /**
     * Convierte esta celda en parte de la Ruta de la Seda.
     */
    public void setAsPath() {
        this.isPath = true;
        cellShape.changeColor("white");
    }
    
    /**
     * Coloca una tienda en esta celda.
     * @param newStore El objeto Store a colocar.
     */
    public void setStore(Store newStore) {
        if (isPath && newStore != null) {
            this.store = newStore;
            updateColor();
        }
    }
    
    /**
     * Remueve la tienda de esta celda.
     */
    public void removeStore() {
        if (store != null) {
            this.store = null;
            cellShape.changeColor("white");
        }
    }

    /**
     * Consulta si la celda tiene una tienda.
     * @return true si tiene tienda.
     */
    public boolean hasStore() {
        return store != null;
    }
    
    /**
     * Obtiene el objeto Store de esta celda.
     * @return El objeto Store o null.
     */
    public Store getStore() {
        return store;
    }
    
    /**
     * Consulta si la celda es parte de la ruta.
     * @return true si es parte de la ruta.
     */
    public boolean isPath() {
        return isPath;
    }
    
    /**
     * Obtiene la coordenada X de la celda.
     * @return La coordenada X.
     */
    public int getX() {
        return xPosition;
    }
    
    /**
     * Obtiene la coordenada Y de la celda.
     * @return La coordenada Y.
     */
    public int getY() {
        return yPosition;
    }
    
    /**
     * Obtiene el valor actual de la tienda.
     * @return El valor actual o 0 si no hay tienda.
     */
    public int getCurrentValue() {
        return store != null ? store.getCurrentValue() : 0;
    }
    
    /**
     * Obtiene el valor inicial de la tienda.
     * @return El valor inicial o 0 si no hay tienda.
     */
    public int getInitialValue() {
        return store != null ? store.getInitialValue() : 0;
    }
    
    /**
     * Obtiene el número de veces que la tienda ha sido vaciada.
     * @return Número de veces o 0 si no hay tienda.
     */
    public int getTimesEmptied() {
        return store != null ? store.getTimesEmptied() : 0;
    }
    
    /**
     * Un robot recolecta dinero de la tienda en esta celda.
     * @param robot El robot que recolecta.
     * @return El dinero recolectado.
     */
    public int collectTenges(RobotEntity robot) {
        if (store != null) {
            int collected = robot.collectFromStore(store);
            updateColor();
            return collected;
        }
        return 0;
    }
    
    /**
     * Reabastece la tienda.
     */
    public void resupply() {
        if (store != null) {
            store.resupply();
            updateColor();
        }
    }
    
    /**
     * Actualiza el color visual basado en el estado de la tienda.
     */
    private void updateColor() {
        if (store != null) {
            cellShape.changeColor(store.getColor());
        }
    }

    /**
     * Hace visible la celda
     */
    public void makeVisible() {
        cellShape.makeVisible();
    }
    
    /**
     * Hace invisible la celda
     */
    public void makeInvisible() {
        cellShape.makeInvisible();
    }
    
    /**
     * Compatibilidad: verifica si es tienda (alias de hasStore).
     * @return true si tiene tienda.
     */
    public boolean isStore() {
        return hasStore();
    }
}