package silkroad;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase para resolver el problema de la maratón.
 * NO usa SilkRoad para resolver, solo para simular.
 * Ciclo 3 - Requisitos 14 y 15
 */
public class SilkRoadContest {
    
    private int[][] entries; // Entrada del problema
    private int[] dailyProfits; // Ganancias máximas por día
    private ArrayList<Integer> robotPositions; // Posiciones de robots
    private ArrayList<StoreInfo> storesList; // Lista de tiendas
    
    /**
     * Constructor
     * @param entries Matriz de entrada del problema [tipo, ubicación, (valor)]
     */
    public SilkRoadContest(int[][] entries) {
        this.entries = entries;
        this.dailyProfits = new int[entries.length];
        this.robotPositions = new ArrayList<>();
        this.storesList = new ArrayList<>();
    }
    
    /**
     * REQUISITO 14: Resuelve el problema de la maratón
     * Calcula la máxima utilidad diaria sin usar SilkRoad
     * @return Array con las ganancias máximas de cada día
     */
    public int[] solve() {
        robotPositions.clear();
        storesList.clear();
        
        for (int day = 0; day < entries.length; day++) {
            int type = entries[day][0];
            int location = entries[day][1];
            
            if (type == 1) {
                // Añadir robot
                robotPositions.add(location);
            } else if (type == 2) {
                // Añadir tienda
                int value = entries[day][2];
                storesList.add(new StoreInfo(location, value));
            }
            
            // Reabastecer tiendas y calcular máxima ganancia
            dailyProfits[day] = calculateMaxProfit();
        }
        
        return dailyProfits;
    }
    
    /**
     * Calcula la máxima ganancia posible para el estado actual
     * usando un enfoque greedy simple
     */
    private int calculateMaxProfit() {
        if (robotPositions.isEmpty() || storesList.isEmpty()) {
            return 0;
        }
        
        // Crear copias para no modificar el estado original
        ArrayList<Integer> robots = new ArrayList<>(robotPositions);
        ArrayList<StoreInfo> stores = new ArrayList<>();
        for (StoreInfo s : storesList) {
            stores.add(new StoreInfo(s.location, s.value));
        }
        
        int totalProfit = 0;
        boolean[] storeUsed = new boolean[stores.size()];
        
        // Para cada robot, encontrar la mejor tienda disponible
        for (int robotLoc : robots) {
            int bestProfit = 0;
            int bestStoreIndex = -1;
            
            for (int i = 0; i < stores.size(); i++) {
                if (storeUsed[i]) continue;
                
                StoreInfo store = stores.get(i);
                int distance = Math.abs(store.location - robotLoc);
                int profit = store.value - distance;
                
                if (profit > bestProfit) {
                    bestProfit = profit;
                    bestStoreIndex = i;
                }
            }
            
            if (bestStoreIndex != -1 && bestProfit > 0) {
                totalProfit += bestProfit;
                storeUsed[bestStoreIndex] = true;
            }
        }
        
        return totalProfit;
    }
    
    /**
     * REQUISITO 15: Simula la solución usando SilkRoad
     * Ejecuta los movimientos óptimos en el simulador visual
     * @return El simulador SilkRoad con la simulación ejecutada
     */
    public SilkRoad simulate() {
        // Crear simulador con las entradas
        SilkRoad simulator = new SilkRoad(entries);
        
        // Simular día por día
        for (int day = 0; day < entries.length; day++) {
            if (day > 0) {
                simulator.reboot(); // Nuevo día: reabastecer y retornar robots
            }
            
            // Ejecutar movimientos óptimos para este día
            simulator.moveRobots(); // Usa la lógica automática implementada
        }
        
        return simulator;
    }
    
    /**
     * Obtiene las ganancias calculadas
     * @return Array con las ganancias de cada día
     */
    public int[] getProfits() {
        return dailyProfits;
    }
    
    /**
     * Clase interna para representar información de una tienda
     */
    private class StoreInfo {
        int location;
        int value;
        
        StoreInfo(int location, int value) {
            this.location = location;
            this.value = value;
        }
    }
    
    /**
     * Método auxiliar para imprimir resultados
     * @return String con los resultados formateados
     */
    public String getResultsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Resultados del problema de la maratón:\n");
        for (int i = 0; i < dailyProfits.length; i++) {
            sb.append("Día ").append(i + 1).append(": ").append(dailyProfits[i]).append("\n");
        }
        return sb.toString();
    }
}