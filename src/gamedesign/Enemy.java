package gamedesign;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

class Enemy
{
	int x, y, number, velX, velY, height, width, speed, maxhp, hp, bFreq, actCount;
	ArrayList<Bullet> bulletList;
	Color color;
	Image img;
	boolean pause = false;
	
	Enemy(int x, int y, int number, int speed, int maxhp, int bFreq, Color color, Image img)
	{
		this.x=x;
		this.y=y;
		this.number=number;
		this.speed=speed;
		this.color=color;
		this.maxhp = maxhp;
		this.bFreq = bFreq;
		this.img=img;
		this.width=60;
		this.height=80;
		hp = maxhp;
	}
	
	Enemy(int x, int number, Color color, Image img)
	{
		this(x,50,number,3,100,88,color,img);
	}
	
	void draw(Graphics g)
	{
		g.setColor(color);
		//g.drawImage(img,x,y,null);
		g.drawImage(img, x, y, width, height, null);
		//g.drawImage(img, x, y, x+width, y+height, 0, 0, 300, 400, null);
		g.setColor(Color.red);
		g.fillRect(x, y+height, hp/2, 10);
	}
	
	void act(ArrayList<Bullet> bulletList, ArrayList<Enemy> enemyList, Game g)
	{//happens to all enemies at all times
		
		if(pause==false)
		{
			actCount++; 
			if(actCount==bFreq) //movement of enemy
			{
				velX = (int) ((Math.random()*speed*2)-speed);
				velY = (int) ((Math.random()*speed*2)-speed);	
			}
			if(x<0)
				velX=speed/2;
			if(x>g.sWidth)
				velX=-speed/2;
			if(y>=g.sHeight/4)
				velY = -speed/2;
			if(y<=0)
				velY = speed/2;
			
			x+=velX;
			y+=velY;
		}
		
		
		for(Bullet b:bulletList)//checks if there is a bullet to damage it
		{
			if(b.x<x+width&&b.x>x&&b.y<y+height/2&&b.y>y) 
			{
				hp=hp-b.getDamage();
			}
		}
		
		if(actCount>=bFreq) //shooting bullets
		{
			shoot(bulletList);
			actCount=0;
		}
		
		if(hp<0) //checks if dead, removes from list if dead
		{
			color=Color.black;
			x=-150;
			enemyList.remove(this);
			g.killCount++;
		}
	}
	
	public void shoot(ArrayList<Bullet> bulletList)
	{
		EnemyBullet eb = new EnemyBullet(x,y+height,color,this);
		bulletList.add(eb);
	}
	
	public void pause()
	{
		pause=true;
	}
	public void unpause()
	{
		pause=false;
	}
}


