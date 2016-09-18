package gamedesign;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Ship
{
	
	int x, y, velX, velY, height, width, number, speed, maxhp, hp, bullets;
	boolean isAlive = true;
	ArrayList<Bullet> bulletList;
	Color color;
	Image img;

	Ship(int x, int y, int number, Color color, Image img)
	{
		this(x,y,number,5,100,color,img);
	}

	Ship(int x, int y, int number, int speed, int maxhp, Color color, Image img)
	{
		
		this.x=x;
		this.y=y;
		this.number=number;
		this.width=60;//img.getWidth(null);
		this.height=80;//img.getHeight(null);
		this.speed=speed;
		this.color=color;
		this.maxhp=maxhp;
		this.img=img;
		hp = maxhp;
	}
	
	
	
	void react(int keyCode, ArrayList<Bullet> bulletList) 
	{//happens only when key is pressed
		if(keyCode==KeyEvent.VK_RIGHT)
			velX=speed;
		if(keyCode == KeyEvent.VK_LEFT)
			velX=-speed;
		if(keyCode == KeyEvent.VK_DOWN)
			velY=speed;
		if(keyCode == KeyEvent.VK_UP)
			velY=-speed;
		if(keyCode==KeyEvent.VK_F&&bullets<=3)
			shoot(bulletList);
	}
	
	void stop(int keyCode)
	{//happens only when key is released
		if(keyCode==KeyEvent.VK_RIGHT||keyCode==KeyEvent.VK_LEFT)
			velX=0;
		if(keyCode==KeyEvent.VK_UP||keyCode==KeyEvent.VK_DOWN)
			velY=0;
	}
	
	void act(ArrayList<Bullet> bulletList, ArrayList<Ship> shipList, Game g)
	{//happens to all ships at all times	
		
		x=(x+velX+g.sWidth)%g.sWidth;
		y=(y+velY+g.sHeight)%g.sHeight;
		for(Bullet b:bulletList)
		{
			if(b.x<x+width&&b.x>x&&b.y<y+height/2&&b.y>y) 
			{
				hp=hp-b.getDamage();
			}
		}
		if(hp<0)
		{
			color=Color.black;
			isAlive = false;
		}
	}
	
	boolean isAlive()
	{
		return isAlive;
	}
	
	void shoot(ArrayList<Bullet> bulletList)
	{
		//System.out.println("pew!");
		Bullet b = new Bullet(x,y,color,this);
		bullets++;
		bulletList.add(b);   
	}
	
	void draw(Graphics g)
	{
		if(isAlive)
		{
			g.setColor(color);
//			g.drawImage(img, x, y, x+width, y+height, 0, 0, 300, 400, null);
			g.drawImage(img, x, y, width, height, null);
			g.drawString(Integer.toString(number), x-3, y);
			g.setColor(Color.green);
			g.fillRect(x, y+height, hp/2, 10);
		}
	}
	
	void subtractBullets()
	{
		bullets--;
	}
	
	void refillHP()
	{
		hp=maxhp;
	}
}

class VShip extends Ship
{
	VShip(int x, int y, int number, Color color, Image img)
	{
		super(x,y,number,8,300,color,img);
	}
	
	@Override
	void shoot(ArrayList<Bullet> bulletList)
	{
		VBullet v = new VBullet(x,y,color, this);
		bulletList.add(v);
	}
}