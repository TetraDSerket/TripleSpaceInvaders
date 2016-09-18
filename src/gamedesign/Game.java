package gamedesign;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class Game extends JPanel
{
	//Global Variables
	final JFileChooser fc = new JFileChooser();
	GameEvents gameEvents = new GameEvents();
	Timer gameTimer = new Timer(10, gameEvents);
	Font font = new Font("Mysterious Mr.L", Font.PLAIN, 18);
	Font titleFont = new Font("Wat the refrance", Font.CENTER_BASELINE, 30);
	Random octet = new Random();
	int shipNumber = 1; int sWidth = 1200; int sHeight = 700;
	int numShips, numEnemy, levelNum=1, killCount=0, backPos;
	BufferedImage Igalaga = null, ISburb = null;
	BufferedImage CBack = null, CEnemy = null, CShip = null, CPause = null; 
	boolean paused = false;
	
	ArrayList<Ship> shipList = new ArrayList<Ship>();
	ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	//BufferedImage[][] themeList = new BufferedImage[12][3];
	
	
	public Game()
	{
		//reads images
		try 
		{
			ISburb = ImageIO.read(getClass().getResource("Homestuck.gif"));
			CBack = ImageIO.read(getClass().getResource("vspace.jpg"));
			CPause = ImageIO.read(getClass().getResource("PauseScreen.png"));
		} 
		catch (IOException e)
		{
			System.out.println("Pictures failed to load");
		}
		//sWidth=CBack.getWidth();
		//sHeight=700;
		backPos=CBack.getHeight()-sHeight;
		this.setSize(new Dimension(sWidth,sHeight));
		this.setVisible(true);
		//this.createBufferStrategy(2);
		this.addKeyListener(gameEvents);
		this.setBackground(Color.black);
		
		
		numShips = 3; numEnemy = 3;
		
		//checks to see if they know my passcode and get ship 0
		
		shipList.add(null);
		
		JOptionPane.showMessageDialog(null,"Choose your player.");
		CShip = this.chooseImageFromFileChooser();//"\ships");
		JOptionPane.showMessageDialog(null,"Choose the enemies you will battle.");
		CEnemy = this.chooseImageFromFileChooser();//"\enemies");
		
		String Scode = JOptionPane.showInputDialog(null, "Enter the secret passcode to use the special ship (this password will appear on the screen at level 8) Press Cancel to end this application."); 
		//if(Scode.equals("wherearewe"))
			shipList.set(0,new VShip(octet.nextInt(400)+200, octet.nextInt(250)+150,0,new Color(150,0,255),ISburb));
		
		/**
		Instructions Time!!!!!!!! You will start off with three players (You get to pick their image at the beginning. They'll all look the same.). 
		Press the corresponding player number on your keyboard to switch to that player. Ex: If the player has a number 1 floating above it, press 1 to switch to it.
		Use the arrow keys to move the player you are controlling at the moment.
		Press f to fire bullets (you can only fire upwards and the enemy can only fire downwards) Press p to pause the game.
		At the end of every level, players regain all health, but dead players cannot revive.
		Obviously, you can't use dead players. Or can you? >:)
		Objective: Kill all the enemies!!!!!!!! (You get to pick who the enemies are too! (Pick Eridan first lol))
		If the game isn't working when you run it, just try starting it again.
		Contact TetraDSerket (Varsha) for more info/resolving problems/meaning of life
		 */
		
		//creating ships
		for(int i=0; i<numShips; i++)
		{
			Color t = new Color(0, octet.nextInt(206)+50, octet.nextInt(206)+50);
			Ship temp = new Ship(octet.nextInt(sWidth-400)+200, octet.nextInt(sHeight-300)+150,i+1, t, CShip);
			shipList.add(temp);
		}
		
		//creating enemies first time, created again in act method
		Color c = new Color(octet.nextInt(56)+200, 0, octet.nextInt(100)); 
		for(int i=0; i<numEnemy; i++)
		{
			Enemy e = new Enemy(octet.nextInt(sWidth-200)+100,i,c,CEnemy);
			enemyList.add(e);
		}
		
		gameTimer.start();
	}
	
	public BufferedImage chooseImageFromFileChooser()//String location)
	{
		String location = "images/";
		BufferedImage img = null;
		fc.updateUI();
		//comment out this next line when exporting
//		fc.setCurrentDirectory(new File(location));
//		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		//uncomment this next line when exporting
		fc.setCurrentDirectory(new File(System.getProperty("user.dir")+"/images")); //"images/"
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            File file = fc.getSelectedFile();
            try
            {
            	img = ImageIO.read(file);
            	if(img==null)
            		return chooseImageFromFileChooser();
            	System.out.println("Image you opened: "+img);
            }
            catch(Exception e)
            {
            	JOptionPane.showMessageDialog(null, "Problem with the file you chose");
            	return chooseImageFromFileChooser();
            }
            //This is where a real application would open the file.
            //log.append("Opening: " + file.getName() + "." + newline);
        } else {
        	
           // log.append("Open command cancelled by user." + newline);
        }
        if(img==null)
        {
        	img = this.chooseImageFromFileChooser();
        }
		return img;
		
	}

	public void paintComponent(Graphics g)
	{
		//Graphics g = this.getBufferStrategy().getDrawGraphics();
		//draw background
		super.paintComponent(g);
		sWidth = this.getWidth();
		System.out.println(sWidth);
		sHeight = this.getHeight();
		System.out.println(sHeight);
		//g.drawImage(CBack,0,backPos,null);
		g.drawImage(CBack, -5, 0, sWidth, sHeight, 0, backPos, CBack.getWidth(), backPos+sHeight, null);
		//draw instructions only on level 1
		if(levelNum==1||levelNum==2)
		{
			g.setColor(Color.black);
			g.fillRect(0, 50, sWidth, sHeight/6);
			g.setFont(font); g.setColor(Color.white);
			g.drawString("Use the arrow keys to move your players and press the player's number on your keyboard to switch to that player.",50,130);
			g.drawString("Press f to shoot and p to pause. Your ships will regain full health after every level, but dead ships won't come back to life.",50,150);
			g.setColor(Color.red);
			g.drawString("These instructions will disappear after the first two levels",50,170);
		}
		if(levelNum==8)
		{
			g.setColor(Color.red);
			g.drawString("Secret Ship Password: wherearewe", 200, 50);
		}
		//draw level and score
		g.setColor(shipList.get(shipNumber).color); 
		g.setFont(titleFont);
		g.drawString("Level: "+levelNum+" Score: "+killCount*2, 50, 100);
		g.setFont(font);
		//draw ships, bullets, and enemies
		for(Ship s: shipList)
		{
			if(s!=null)
			s.draw(g);
		}
		for(Bullet b: bulletList)
		{
			b.draw(g);
		}
		for(Enemy e: enemyList)
		{
			e.draw(g);
		}
		if(paused)
		{
			g.drawImage(CPause, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		//this.getBufferStrategy().show();
		//Toolkit.getDefaultToolkit().sync();
		
	}
	
	public void act()
	{
		//runs each act method
		for(int i=0; i<bulletList.size(); i++)
		{
			bulletList.get(i).fly(bulletList);
		}
		for(int i=0; i<shipList.size(); i++)
		{
			if(shipList.get(i)!=null)
			shipList.get(i).act(bulletList,shipList, this);
		}
		for(int i=0; i<enemyList.size(); i++)
		{
			enemyList.get(i).act(bulletList, enemyList, this);
		}
		
		if(backPos<0)
			backPos=CBack.getHeight()-sHeight;
		else
			backPos--;
		//if level is complete
		if(enemyList.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Level "+levelNum+" Complete!");
			numEnemy++; levelNum++;
			for(Ship s: shipList)
			{
				if(s!=null&&s.isAlive())
				{
					s.refillHP(); 
				}
			
			}
			
			Color c = new Color(octet.nextInt(56)+200, 0, octet.nextInt(100));
			for(int i=0; i<numEnemy; i++)
			{
				//BufferedImage face = (themeList[levelNum][1]==null) ? themeList[0][1]:themeList[levelNum][1];
				//System.out.println(themeList[levelNum][1]+" itap "+themeList[0][1]);
				Enemy n = new Enemy(octet.nextInt(sWidth-200)+100, i, c, CEnemy);
				enemyList.add(n);
			}
			
//			CBack = (themeList[levelNum][0]==null) ? themeList[0][0]:themeList[levelNum][0];
//			CEnemy = (themeList[levelNum][1]==null) ? themeList[0][1]:themeList[levelNum][1];
//			sWidth=CBack.getWidth();
//			sHeight=CBack.getHeight();
//			this.resize(sWidth,sHeight);
		}
		
		int deadCount = 0;
		for(Ship s:shipList)
		{
			if(s==null||!s.isAlive())
			{
				deadCount++;
			}
		}
		if(deadCount==shipList.size())
		{
			JOptionPane.showMessageDialog(null, "Game Over! You died at Level "+levelNum+" and killed "+killCount+" enemies!");
//			JOptionPane.showMessageDialog(null, "Game Over! You died at Level "+levelNum+" and killed "+killCount+" enemies!");
//			JOptionPane.showMessageDialog(null, "Game Over! You died at Level "+levelNum+" and killed "+killCount+" enemies!");
				System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame();
		Game g = new Game();
		f.add(g);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.setSize(1200, 700);
		g.requestFocusInWindow();
	}
	
	public class GameEvents implements ActionListener, KeyListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if(!paused)
			{
				act();
			}
			repaint();
		}
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode()>=48&&e.getKeyCode()<=57)
			{
				if(shipList.size()>shipNumber&&shipList.get(shipNumber)!=null)
					shipList.get(shipNumber).stop(e.getKeyCode());
				shipNumber = e.getKeyCode()-48;
			}
			if(e.getKeyCode()==KeyEvent.VK_P)
			{
				paused = !paused;
			}
			if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				for(Enemy n: enemyList)
					n.unpause();
			}
			else if(shipNumber<=shipList.size())
			{
				shipList.get(shipNumber).react(e.getKeyCode(), bulletList);
			}
			else
			{
				shipList.get(1).react(e.getKeyCode(),bulletList);
			}
				
		}
		
		@Override
		public void keyReleased(KeyEvent e)
		{
			if(shipList.size()>shipNumber)
			shipList.get(shipNumber).stop(e.getKeyCode());
		}
		
		@Override
		public void keyTyped(KeyEvent e){}
		
	}
	
	public ArrayList<Bullet> getBulletList()
	{
		return bulletList;
	}
}

