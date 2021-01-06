package miniEsercizi.grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraficaEventi implements ActionListener {
    private JTextArea text;

    public static void main(String[] args) {
        new GraficaEventi();
    }

    private GraficaEventi() {
        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        Container c = frame.getContentPane();
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel(new GridLayout(2, 1));
        JPanel panel3 = new JPanel(new GridLayout(1, 1));

        JButton b1 = new JButton("Bottone 1");
        JButton b2 = new JButton("Bottone 2");
        JButton b3 = new JButton("Bottone 3");

        b2.addActionListener(this);

        text = new JTextArea("Si");

        panel3.add(text);

        panel2.add(b1);
        panel2.add(b2);
        panel2.add(b3);

        panel.add(panel2);
        panel.add(panel3);

        c.add(panel);

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        text.setText("test");
    }
}
