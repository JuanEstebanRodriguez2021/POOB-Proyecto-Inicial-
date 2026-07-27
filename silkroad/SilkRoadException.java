package silkroad;
/**
 * Exception class Project SilkRoad 2025
 */
public class SilkRoadException extends Exception {

    public static final String INVALID_PATH = "Ruta inválida";
    public static final String PROFIT_ERROR = "Error en obtener ganancias";

    /**
     * Constructor
     */
    public SilkRoadException(String message) {
        super(message);
    }
}