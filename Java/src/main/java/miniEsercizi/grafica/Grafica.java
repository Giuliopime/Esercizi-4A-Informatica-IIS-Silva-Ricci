package miniEsercizi.grafica;

import javax.swing.*;
import java.awt.*;

public class Grafica extends JFrame {
    public Grafica() {
        super("Frame 1");
        this.setSize(400, 300);
        this.setLocation(1000, 500);

        Container c = this.getContentPane();

        JButton button = new JButton("bottone");
        c.add(button);

        this.setVisible(true);
    }
}
