/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package sanke;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author yair_
 */
public class Snake {

    public static void main(String[] args) {

      JFrame f=new JFrame();
      Pantalla p=new Pantalla();
      f.setTitle("Culebra");
      f.setBounds(10,10,905,700);
      f.setResizable(false);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setBackground(Color.DARK_GRAY);      
      f.setVisible(true);f.add(p);
      p.setFocusable(true);
    }
}
