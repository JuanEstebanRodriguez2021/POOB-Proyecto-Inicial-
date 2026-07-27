package silkroad;

/**
 * Tienda autónoma: escoge su propia posición en lugar de la indicada.
 * Busca posicionarse cerca de donde hay robots.
 */
public class AutonomousStore extends Store {
    
    /**
     * Constructor de tienda autónoma.
     * @param suggestedLocation Ubicación sugerida (puede cambiarla).
     * @param initialValue Valor inicial en tenges.
     */
    public AutonomousStore(int suggestedLocation, int initialValue) {
        super(suggestedLocation, initialValue);
        this.color = "blue";
        this.type = "autonomous";
    }
    
    /**
     * Determina su ubicación óptima basada en posiciones de robots.
     * @param robotPositions Array con posiciones de robots existentes.
     * @param pathSize Tamaño total de la ruta.
     */
    public void chooseLocation(int[] robotPositions, int pathSize) {
        if (robotPositions == null || robotPositions.length == 0) {
            return; // Mantiene ubicación sugerida
        }
        
        // Estrategia simple: ubicarse cerca del robot más cercano a su posición sugerida
        int closestRobot = robotPositions[0];
        int minDistance = Math.abs(location - robotPositions[0]);
        
        for (int robotPos : robotPositions) {
            int distance = Math.abs(location - robotPos);
            if (distance < minDistance) {
                minDistance = distance;
                closestRobot = robotPos;
            }
        }
        
        // Se coloca a distancia intermedia del robot más cercano
        int offset = (closestRobot > location) ? 2 : -2;
        int newLocation = location + offset;
        
        // Asegurar que está dentro del rango válido
        if (newLocation >= 0 && newLocation < pathSize) {
            this.location = newLocation;
        }
    }
    
    /**
     * Recolección normal de tenges.
     * @param robot El robot que recolecta.
     * @return Cantidad recolectada.
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