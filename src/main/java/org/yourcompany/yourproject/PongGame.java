/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.yourcompany.yourproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PongGame extends JPanel implements KeyListener, ActionListener {
    private int ballX = 160, ballY = 100, ballVelX = 2, ballVelY = 2;
    private int playerY = 100, aiY = 100;
    private final int PADDLE_HEIGHT = 60, PADDLE_WIDTH = 10;
    private Timer timer;
    private int scoreRed = 0;   // Equipo Rojo (jugador)
    private int scoreBlue = 0;  // Equipo Azul (IA)
    private int panelWidth = 400;
    private int panelHeight = 300;

    public PongGame() {
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(10, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Actualiza el tamaño del panel
        panelWidth = getWidth();
        panelHeight = getHeight();
        // Background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        // Ball
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, 15, 15);
        // Player paddle (Red team)
        g.setColor(Color.RED);
        g.fillRect(10, playerY, PADDLE_WIDTH, PADDLE_HEIGHT);
        // AI paddle (Blue team)
        g.setColor(Color.BLUE);
        g.fillRect(getWidth() - 20, aiY, PADDLE_WIDTH, PADDLE_HEIGHT);
        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Rojo: " + scoreRed, 30, 25);
        g.drawString("Azul: " + scoreBlue, panelWidth - 120, 25);
    }

    public void actionPerformed(ActionEvent e) {
        // Ball movement
        ballX += ballVelX;
        ballY += ballVelY;

        // Ball collision with top/bottom
        if (ballY <= 0 || ballY >= panelHeight - 15) ballVelY = -ballVelY;

        // Ball collision with paddles
        if (ballX <= 20 && ballY + 15 >= playerY && ballY <= playerY + PADDLE_HEIGHT) ballVelX = -ballVelX;
        if (ballX >= panelWidth - 35 && ballY + 15 >= aiY && ballY <= aiY + PADDLE_HEIGHT) ballVelX = -ballVelX;

        // AI movement
        if (aiY + PADDLE_HEIGHT / 2 < ballY) aiY += 2;
        if (aiY + PADDLE_HEIGHT / 2 > ballY) aiY -= 2;

        // Ball out of bounds (score and reset)
        if (ballX < 0) {
            scoreBlue++; // Punto para Azul (IA)
            resetBall();
        }
        if (ballX > panelWidth) {
            scoreRed++; // Punto para Rojo (jugador)
            resetBall();
        }

        repaint();
    }

    private void resetBall() {
        // Usa panelWidth y panelHeight en vez de getWidth/getHeight
        ballX = panelWidth / 2;
        ballY = panelHeight / 2;
        ballVelX = -ballVelX;
        ballVelY = 2 * (Math.random() > 0.5 ? 1 : -1);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP && playerY > 0) playerY -= 10;
        if (e.getKeyCode() == KeyEvent.VK_DOWN && playerY < getHeight() - PADDLE_HEIGHT) playerY += 10;
    }
    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pong Game");
        PongGame pong = new PongGame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pong); // Añade el panel al frame
        frame.setVisible(true); // Haz visible la ventana
    }
}