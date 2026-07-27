package figures;


/**
 * Un rectángulo que puede ser manipulado y dibujarse en un canvas.
 * Refactorizado para usar herencia de Shape.
 */
public class Rectangle extends Figure {
    public static final int EDGES = 4;
    private int height;
    private int width;

    /**
     * Constructor por defecto.
     */
    public Rectangle() {
        super();
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
    }
    
    /**
     * Constructor con parámetros.
     * @param color Color del rectángulo.
     * @param h Altura.
     * @param w Ancho.
     * @param x Posición X.
     * @param y Posición Y.
     */
    public Rectangle(String color, int h, int w, int x, int y) {
        super();
        height = h;
        width = w;
        xPosition = x;
        yPosition = y;
        this.color = color;
    }
    
    /**
     * Cambia el tamaño del rectángulo.
     * @param newHeight Nueva altura (debe ser >= 0).
     * @param newWidth Nuevo ancho (debe ser >= 0).
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }
    
    /**
     * Dibuja el rectángulo.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }
    
    /**
     * Borra el rectángulo.
     */
    @Override
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}