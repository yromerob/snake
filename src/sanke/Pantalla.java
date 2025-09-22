/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sanke;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author yair_
 */
public class Pantalla extends JPanel implements KeyListener, ActionListener {

    private ImageIcon titulo;
    private ImageIcon culebra;
    private ImageIcon enemigo;
    private int[] snakexlength = new int[750], snakeylength = new int[750];//maxmo de x y
    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
    private boolean[] movimientos = new boolean[]{false, false, false, false};
    private ImageIcon[] cabeza = new ImageIcon[4];
    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo

    private Timer tiempo;
    private int delay = 150;
    private int mov = 0;
    private int tam = 3; 
    private int score=0;
    //ENEMIGOS
    private int[] enemigosposx=new int[34];
    private int[] enemigosposy=new int[23];
    
    private Random r=new Random();    
    private int posx=r.nextInt(enemigosposx.length);
    private int posy=r.nextInt(enemigosposy.length);
   
    public Pantalla() {
        int i;       
        String path = "../img/";
        titulo = new ImageIcon(getClass().getResource(path + "snaketitle.jpg")); 
        cabeza[0] = new ImageIcon(getClass().getResource(path + "leftmouth.png"));
        cabeza[1] = new ImageIcon(getClass().getResource(path + "rightmouth.png"));
        cabeza[2] = new ImageIcon(getClass().getResource(path + "upmouth.png"));
        cabeza[3] = new ImageIcon(getClass().getResource(path + "downmouth.png"));
        culebra = new ImageIcon(getClass().getResource(path + "snakeimage.png"));
        enemigo=new ImageIcon(getClass().getResource(path + "enemy.png"));
        
        for(i=0;i<enemigosposx.length;i++)//se rellena el array con las posiciones de x desde 25 hasta 850
        {
         if(i==0){
            this.enemigosposx[i]=25;}
         else{
             this.enemigosposx[i]=this.enemigosposx[i-1]=this.enemigosposx[i-1]+25;
           }   
         //System.out.println(this.enemigosposx[i]);
        }
         for(i=0;i<enemigosposy.length;i++)//se rellena el array con las posiciones de y desde 75 hasta 625
        {
         if(i==0){
            this.enemigosposy[i]=75;}
         else{
             this.enemigosposy[i]=this.enemigosposy[i-1]=this.enemigosposy[i-1]+25;
           }      
          //System.out.println(this.enemigosposy[i]);
        }
         addKeyListener(this);       
         setFocusTraversalKeysEnabled(true);
         setFocusable(true); 
        tiempo = new Timer(delay, this);        
        tiempo.start();
       
       
    }

    @Override
    public void paint(Graphics g) {        
      if (mov == 0) {
            //Posicion incial de la culebra          
            this.snakexlength[0] = 100;
            this.snakexlength[1] = 75;
            this.snakexlength[2] = 50;
            this.snakeylength[0] = this.snakeylength[1] = this.snakeylength[2] = 100;
        }
        //Cabecera   
        g.setColor(Color.white);
        g.drawRect(24, 10, 851, 55);
        titulo.paintIcon(this, g, 25, 11);
        //dibujar putuacion 
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial",Font.BOLD,14));
        g.drawString("Score: "+this.score, 780, 30);
        g.drawString("Tamaño: "+this.tam, 780, 50);
        //Borde de juego
        g.setColor(Color.white);
        g.drawRect(24, 74, 851, 577);
        g.setColor(Color.BLACK);
        g.fillRect(25, 75, 850, 575);
        cabeza[1].paintIcon(this, g, this.snakexlength[0], this.snakeylength[0]);//pinta la cabeza de la culebra hacia la derecha
        for (int i = 0; i < tam; i++) {
            if (i == 0) {
                for (int j = 0; j < movimientos.length; j++) {
                    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
                    if (movimientos[j]) {
                        cabeza[j].paintIcon(this, g, this.snakexlength[i], this.snakeylength[i]);//pinta la posicion de la cabeza
                    }
                }
            } else {

                culebra.paintIcon(this, g, this.snakexlength[i], this.snakeylength[i]);//pinta el resto del cuerpo
            }

        }
        enemigo.paintIcon(this, g, enemigosposx[posx], enemigosposy[posy]);//se carga la bola enemiga
        
        if((this.enemigosposx[posx]==this.snakexlength[0])&&this.enemigosposy[posy]==this.snakeylength[0]){
            //Se come la bola y se crece la culebra
           tam++;
           score++;
           posx=r.nextInt(enemigosposx.length);
           posy=r.nextInt(enemigosposy.length);
        }
        for(int j=1;j<tam;j++)
        {
            //valida la colision con su propio cuerpo            
          if((this.snakexlength[j]==this.snakexlength[0])&&(this.snakeylength[j]==this.snakeylength[0])){
            this.movimientos[0]=this.movimientos[1]=this.movimientos[2]=this.movimientos[3]=false;
            g.setColor(Color.WHITE);
            g.setFont(new Font("arial",Font.BOLD,50));
            g.drawString("Fin de Juego! :(", 350, 350);
          }
        }
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
       // System.out.println(e.getActionCommand());
        //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
        for(int j=0;j<this.movimientos.length;j++)        
        {
           if(this.movimientos[j])
           {
            switch(j)
            {
                case 0://Izquierda
                    for (int i = tam - 1; i >= 0; i--) {
                        this.snakeylength[i + 1] = this.snakeylength[i];
                        if (i == 0) {
                            this.snakexlength[i] = this.snakexlength[i] - 25;
                        } else {
                            this.snakexlength[i] = this.snakexlength[i - 1];
                        }
                        if (this.snakexlength[i] < 25) {
                            this.snakexlength[i] = 850;
                        }
                    }
                    break;
                case 1://derecha
                    for (int i = tam - 1; i >= 0; i--) {
                        this.snakeylength[i + 1] = this.snakeylength[i];
                        if (i == 0) {
                            this.snakexlength[i] = this.snakexlength[i] + 25;
                        } else {
                            this.snakexlength[i] = this.snakexlength[i - 1];
                        }
                        if (this.snakexlength[i] > 850) {
                            this.snakexlength[i] = 25;
                        }
                    }
                    break;
                case 2://arriba
                    for (int i = tam - 1; i >= 0; i--) {
                        this.snakexlength[i + 1] = this.snakexlength[i];
                        if (i == 0) {
                            this.snakeylength[i] = this.snakeylength[i] - 25;
                        } else {
                            this.snakeylength[i] = this.snakeylength[i - 1];
                        }
                        if (this.snakeylength[i] < 75) {
                            this.snakeylength[i] = 625;
                        }
                    }
                    break;
                    case 3://abajo
                        for (int i = tam - 1; i >= 0; i--) {
                            this.snakexlength[i + 1] = this.snakexlength[i];
                            if (i == 0) {
                                this.snakeylength[i] = this.snakeylength[i] + 25;
                            } else {
                                this.snakeylength[i] = this.snakeylength[i - 1];
                            }
                            if (this.snakeylength[i] > 625) {
                                this.snakeylength[i] = 75;
                            }
                        }
                        break;

                }
               j=this.movimientos.length; //para romper el ciclo for
            }
        }
      
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
       //System.out.println(e.getKeyChar() + "\t->" + e.getKeyCode());
        //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
       switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                mov++;
                if (!movimientos[0]) {//if no Izquierda
                    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
                    movimientos[1] = true;//derecha
                } else {
                    movimientos[1] = false;//derecha
                    movimientos[0] = true;//Izquierda
                }
                movimientos[2] = false;//arriba
                movimientos[3] = false;//abajo
                break;
            case KeyEvent.VK_LEFT:
                mov++;
                if (!movimientos[1]) { //if no derecha
                    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
                    movimientos[0] = true;//Izquierda
                } else {
                    movimientos[0] = false;//Izquierda
                    movimientos[1] = true;//Derecha
                }
                movimientos[2] = false;//arriba
                movimientos[3] = false;//abajo
                break;
            case KeyEvent.VK_UP:
                mov++;
                if (!movimientos[3]) { //if no abajo
                    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
                    movimientos[2] = true;//Arriba
                } else {
                    movimientos[2] = false;//arriba
                    movimientos[3] = true;//abajo
                }
                movimientos[0] = false;//Izquierda
                movimientos[1] = false;//Derecha
                break;
            case KeyEvent.VK_DOWN:
                mov++;
                if (!movimientos[2]) { //if no arriba
                    //Los movimientos del array seran determinados de la siguiente forma 0 izquierda, 1 derecha, 2 arriba, 3 abajo
                    movimientos[3] = true;//abajo
                } else {
                    movimientos[3] = false;//abajo
                    movimientos[2] = true;//arriba
                }
                movimientos[0] = false;//Izquierda
                movimientos[1] = false;//Derecha
                break;
            case KeyEvent.VK_SPACE://FIN DEL JUEGO
                mov = 0;
                tam = 3;
                score = 0;
                repaint();
                break;  
              case KeyEvent.VK_ENTER://PAUSAR O REANUDAR EL JUEGO
                if (this.tiempo.isRunning()){this.tiempo.stop();}
                else{this.tiempo.start();}
                break;   

        }
     
    }

    @Override
    public void keyTyped(KeyEvent e) {
       }

    @Override
    public void keyReleased(KeyEvent e) {
       }


}
