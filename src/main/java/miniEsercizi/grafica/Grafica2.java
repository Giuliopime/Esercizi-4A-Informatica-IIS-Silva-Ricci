package miniEsercizi.grafica;

import javax.swing.*;
import java.awt.*;

public class Grafica2 extends JFrame {
    private JLabel label1, label2;

    public Grafica2() {
        super("Frame 2");
        this.setSize(500, 500);
        this.setLocation(1000, 500);
        Container c = this.getContentPane();

        label1 = new JLabel("uno");
        c.add(label1);

        this.setVisible(true);
    }
}
