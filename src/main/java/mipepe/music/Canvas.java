package mipepe.music;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Canvas extends JComponent {
    public BufferedImage bufferedImage;
    public Color[] colors;
    public Canvas() {
        bufferedImage = new BufferedImage(App.width*App.cellSize, App.height*App.cellSize, BufferedImage.TYPE_INT_ARGB);
        for (int row = 0; row < bufferedImage.getHeight(); row++) {
            for (int column = 0; column < bufferedImage.getWidth(); column++) {
                bufferedImage.setRGB(column, row, new Color(0.5f, 0.5f, 0.5f).getRGB());
            }
        }
        setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
    }
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this);
    }
    public void drawCell(int row, int column) {
		int level = App.automaton.cells[row][column].level;
	    Color color = colors[level];
	    for (int y = row*App.cellSize; y < row*App.cellSize+App.cellSize; y++) {
	        for (int x = column*App.cellSize; x < column*App.cellSize+App.cellSize; x++) {
	            bufferedImage.setRGB(x, y, color.getRGB());
	        }
	    }
    }
    public void drawAutomaton() {
        for (int row = 0; row < App.height; row++) {
            for (int column = 0; column < App.width; column++) {
                drawCell(row, column);
            }
        }
        repaint();
    }
    public void calculateColorsFull() {
        int modulus = App.modulus;
        colors = new Color[modulus];
        float accumulator  = 0.0f;
        float step = 1.0f / (float) (modulus - 1);
        for (int colorIndex = 0; colorIndex < modulus; colorIndex++) {
            int c = (int)(accumulator*0xffffff);
            colors[colorIndex] = new Color(c|0xff000000);
            accumulator += step;
        }
    }
    public void calculateColors() {
        int modulus = App.modulus;
        colors = new Color[modulus];
        float first = 0.0f;
        float second = 0.0f;
        float third = 0.0f;
        float accumulator  = 0.0f;
        float step = 3.0f / (float) (modulus - 1);
        for (int colorIndex = 0; colorIndex < modulus; colorIndex++) {
            if (accumulator > 3.1f) {
                System.out.println("PANIC " + accumulator);
                System.exit(0);
            } else if (accumulator > 3.0f) {
                accumulator = 3.0f;
            }
            if (accumulator > 2.0f) {
                first = 1.0f;
                second = 1.0f;
                third = accumulator - 2.0f;
            } else if (accumulator > 1.0f) {
            	first = 1.0f;
                second = accumulator - 1.0f;
                third = 0.0f;
            } else {
            	first = accumulator;
            	second = 0.0f;
                third = 0.0f;
            }
            colors[colorIndex] = new Color(second, third, first);
            accumulator += step;
        }
    }
    
    public void calculateGrayColors() {
        int modulus = App.modulus;
        colors = new Color[modulus];
        float red = 0.0f;
        float green = 0.0f;
        float blue = 0.0f;
        float accumulator  = 0.0f;
        float step = 1.0f / (float) (modulus - 1);
        for (int colorIndex = 0; colorIndex < modulus; colorIndex++) {
            if (accumulator > 1.1f) {
                System.out.println("PANIC " + accumulator);
                System.exit(0);
            } else if (accumulator > 1.0f) {
                accumulator = 1.0f;
            }
            if (accumulator > 0.0f) {
                blue = accumulator;
                red = accumulator;
                green = accumulator;
            }
            colors[colorIndex] = new Color(red, green, blue);
            accumulator += step;
        }
    }
    public void fillColors() {
        if (App.grey) {
            calculateGrayColors();
        } else {
            calculateColors();
        }
    }
}
