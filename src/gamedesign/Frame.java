package gamedesign;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Frame extends JFrame 
{
	GameEvents gameEvents = new GameEvents();
	Timer gameTimer = new Timer(1, gameEvents);
	int x=200, y=200, width=30, height=30;
	String str;
	int speed = 10;
	
	public Frame ()
	{
		this.setSize(new Dimension(640,480));
		this.setBackground(Color.black);
		this.setVisible(true);
		this.createBufferStrategy(2);
		this.addKeyListener(gameEvents);
		gameTimer.start();
		
	}
	
	public void paint() 
	{
		Graphics g = this.getBufferStrategy().getDrawGraphics();
		g.setColor(Color.blue);
		g.fillOval(x, y, width, height);
		g.setColor(Color.white);
		g.drawString(str, x+width, y+height);
		this.getBufferStrategy().show();
		Toolkit.getDefaultToolkit().sync();
		
	}
	
	public static void main(String[] args)
	{
		new Frame();
	}
	
	public class GameEvents implements ActionListener, KeyListener
	{
		
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			paint();
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			System.out.println(e.getKeyCode()); 
			x++;
			y++;
		}
		
		@Override
		public void keyReleased(KeyEvent arg0)
		{
			//TODO Auto-generated method stub
		}
		
		@Override
		public void keyTyped(KeyEvent arg0){
			//TODO Auto-generated method stub
		}
		
	}
	
}

