package gamedesign;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

class Bullet
{
	int x, y, height, width, speed, damage;
	Color color;
	Ship s; Enemy e;
	
	Bullet(int x, int y, int height, int width, int speed, int damage, Color color, Ship s, Enemy e)
	{
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.speed=speed;
		this.damage=damage;
		this.color=color;
		this.s=s;
		this.e=e;
	}
	
	Bullet(int x, int y, Color color, Ship s)
	{
		this(x,y,20,8,10,10,color,s,null);
	}
	
	void draw(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
	
	void fly(ArrayList<Bullet> bulletList)
	{
		if(y>0)
		{
			y=y-speed;
			//if(this.)
		}
		else
		{
			bulletList.remove(this);
			s.subtractBullets();
		}
			
	}
	
	int getDamage()
	{
		return damage;
	}
}

class VBullet extends Bullet
{
	VBullet(int x, int y, Color color, Ship s)
	{
		super(x,y,20,15,15,102,color,s,null);
	}

}

class EnemyBullet extends Bullet
{
	EnemyBullet(int x, int y, Color color, Enemy e)
	{
		super(x,y,20,8,8,5,color,null,e);
	}
	
	@Override
	void fly(ArrayList<Bullet> bulletList)
	{
		if(y>0)
		{
			y=y+speed;
		}
		else
		{
			bulletList.remove(this);
		}
			
	}
}