
package figures;

import java.awt.geom.*;

/**
 * Un círculo que puede ser manipulado y dibujarse en un canvas.
 * Refactorizado para usar herencia de Shape.
 */
public class Circle extends Figure {
    public static final double PI = 3.1416;
    private int diameter;

    /**
     * Constructor por defecto.
     */
    public Circle() {
        super();
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
    }
    
    /**
     * Constructor con área específica.
     * @param area Área del círculo.
     */
    public Circle(double area) {
        super();
        double radius = Math.sqrt(area / PI);
        int d = (int)(2 * radius);
        if (d > 0) {
            diameter = d;
            xPosition = 20;
            yPosition = 15;
            color = "red";
        }
    }
    
    /**
     * Cambia el tamaño del círculo.
     * @param newDiameter Nuevo diámetro (debe ser > 0).
     */
    public void changeSize(int newDiameter) {
        if (newDiameter > 0) {
            erase();
            diameter = newDiameter;
            draw();
        }
    }
    
    /**
     * Calcula el área del círculo.
     * @return Área del círculo.
     */
    public double area() {
        double r = diameter / 2.0;
        return PI * r * r;
    }
    
    /**
     * Aumenta el tamaño en un porcentaje.
     * @param percentage Porcentaje de aumento (0-100).
     */
    public void bigger(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            double radius = diameter / 2.0;
            double newRadius = radius * Math.sqrt(1 + (percentage / 100.0));
            changeSize((int)(newRadius * 2));
        }
    }
    
    /**
     * Reduce el círculo progresivamente.
     * @param times Número de reducciones.
     * @param areaLimit Límite de área.
     */
    public void shrink(int times, int areaLimit) {
        int reduction = diameter / times;
        for (int i = 0; i < times; i++) {
            if (area() <= areaLimit) {
                break;
            }
            int finalDiameter = diameter - reduction;
            changeSize(finalDiameter);
        }
    }
    
    /**
     * Cambia a un color aleatorio.
     */
    public void randomColor() {
        int n = (int)(Math.random() * 7);
        String[] colors = {"black", "blue", "green", "magenta", "red", "white", "yellow"};
        color = colors[n];
        draw();
    }
    
    /**
     * Dibuja el círculo.
     */
    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color, 
                new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }
    
    /**
     * Borra el círculo.
     */
    @Override
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}