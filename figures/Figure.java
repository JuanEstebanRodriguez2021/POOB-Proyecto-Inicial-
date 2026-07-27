
package figures;


/**
 * Clase base abstracta para todas las formas geométricas.
 * Ciclo 4: Refactorización usando herencia.
 */
public abstract class Figure {
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;
    
    /**
     * Constructor base.
     */
    public Figure() {
        this.xPosition = 0;
        this.yPosition = 0;
        this.color = "black";
        this.isVisible = false;
    }
    
    /**
     * Hace la forma visible.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Hace la forma invisible.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }
    
    /**
     * Mueve la forma horizontalmente.
     * @param distance Distancia en píxeles.
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }
    
    /**
     * Mueve la forma verticalmente.
     * @param distance Distancia en píxeles.
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }
    
    /**
     * Mueve la forma lentamente horizontalmente.
     * @param distance Distancia en píxeles.
     */
    public void slowMoveHorizontal(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);
        
        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }
    
    /**
     * Mueve la forma lentamente verticalmente.
     * @param distance Distancia en píxeles.
     */
    public void slowMoveVertical(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);
        
        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }
    
    /**
     * Mueve la forma a una posición específica.
     * @param newX Nueva posición X.
     * @param newY Nueva posición Y.
     */
    public void moveTo(int newX, int newY) {
        if (newX >= 0 && newY >= 0) {
            erase();
            xPosition = newX;
            yPosition = newY;
            draw();
        }
    }
    
    /**
     * Cambia el color de la forma.
     * @param newColor Nuevo color.
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }
    
    /**
     * Métodos de conveniencia para movimientos.
     */
    public void moveRight() { moveHorizontal(20); }
    public void moveLeft() { moveHorizontal(-20); }
    public void moveUp() { moveVertical(-20); }
    public void moveDown() { moveVertical(20); }
    
    /**
     * Método abstracto para dibujar (cada forma lo implementa).
     */
    protected abstract void draw();
    
    /**
     * Método abstracto para borrar (cada forma lo implementa).
     */
    protected abstract void erase();
}
