package figures;


import java.awt.*;

/**
 * Un triángulo que puede ser manipulado y dibujarse en un canvas.
 * Refactorizado para usar herencia de Shape.
 */
public class Triangle extends Figure {
    public static final int VERTICES = 3;
    private int height;
    private int width;

    /**
     * Constructor por defecto.
     */
    public Triangle() {
        super();
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
    }
    
    /**
     * Cambia el tamaño del triángulo.
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
     * Dibuja el triángulo.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width/2), xPosition - (width/2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }
    
    /**
     * Borra el triángulo.
     */
    @Override
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}