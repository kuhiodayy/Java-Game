package javagame;
//Imports!111!!!!1oNE!
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Shape;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Play extends BasicGameState {

	int startX = 0;//I know, I know, I need to clean this crap up.
	int startY = 0;
	int destX = 0;
	int destY = 0;
	Point location = new Point(0,0);
	float speed = .01f; //how fast this moves.
	float deg;
	float dx;
	float dy;

	public float rotations = 0;
	public float HueX = 200;
	public float HueY = 200;
	public float hyp = 0;
	public float scale = 1; 
	public float rotation = 0;
	public int RotationB = 0;
	public int BX = 0;
	public int BY = 0;//I know, I know
	public int HueBX = 200;
	public int HueBY= 750;
	public int Height = 0;
	public static int DeltaS = 50;
	public boolean dec = true;
	public Image playerLocations;
	public Image playerLocation;
	public Animation CatA;
	public Animation CatAS;
	public Image[] Cat;
	public Image[] CatS;
	public float Vel = 0f;
	public int test = 0;
	public ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	public Play(int state){//there, it's over
		//this is required for the base game class
	}
	//this runs when this state is first entered
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		int count = 0;
		Cat = new Image[5];
		CatS = new Image[5];
		playerLocation = new Image("res/TestPlane.png");
		playerLocations = playerLocation.getScaledCopy(.5f);
		Height = playerLocation.getHeight();// yes all of this is for the cat
		XMLPackedSheet sheet = new XMLPackedSheet("res/NHUE.png", "res/NHUE.xml");
		Cat[0] = sheet.getSprite("nyan-cat1.png");
		Cat[1] = sheet.getSprite("nyan-cat2.png");
		Cat[2] = sheet.getSprite("nyan-cat3.png");
		Cat[3] = sheet.getSprite("nyan-cat4.png");
		Cat[4] = sheet.getSprite("nyan-cat5.png");
		CatAS = new Animation(true);
		while(count <= 4){
			CatS[count] = Cat[count].getScaledCopy(.2f); 
			CatAS.addFrame(CatS[count], 90);
			count++;
		}
		CatA = new Animation(true);
		CatA.addFrame(Cat[0], 90);
		CatA.addFrame(Cat[1], 90);
		CatA.addFrame(Cat[2], 90);
		CatA.addFrame(Cat[3], 90);
		CatA.addFrame(Cat[4], 90);
	} //end of cat stuff	

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		playerLocations.drawCentered(HueX, HueY);
		g.setColor(Color.white);
		g.drawString("HueX = test " + HueX, 100, 650); //this is the player's location in x
		g.drawString("HueY = " + HueY, 100, 600); //and in y
		g.drawString("BX = " + HueBX, 100, 550);
		g.drawString("BY = " + HueBY, 100, 500);
		g.drawString("Velocity = " + Vel, 400, 650);//Vel is the velocity, still needs work
		g.drawString("Rotation = " + rotations, 400, 600);
		g.setColor(Color.cyan);
		CatAS.draw(200, 200);
		g.fillRect(HueBX, HueBY, 10, 10);
		//Draw the bullets
		g.setColor(Color.red);
		for(int i = 0;i<bulletList.size();i++)
		{
			Bullet bullet = bulletList.get(i);

			g.fillOval(bullet.location.getX(), bullet.location.getY(), 5, 5);
		}

	}


	private void addNewBullet() //this class adds a new bullet to the array list
	{
		int x = (int)Math.round(HueX + 20 * Math.sin(Math.toRadians(rotation)));//X position of the bullet's destination
		int y = (int)Math.round(HueY - 20 * Math.cos(Math.toRadians(rotation))); //Y position for the destination of the bullet
		bulletList.add(new Bullet(x, y, HueBX, HueBY));//actual adding
	}

	public void mousePressed ( int button, int x, int y )//debugging crap
	{
		x = Math.round(HueX);
		y = Math.round(HueY);

	}

	//delta is the time in between when this next method is called. Remember that, now
	public void update(GameContainer gc, StateBasedGame sbg, int delta ) throws SlickException{

		//DeltaS is planned to affect the bullet's state based on this method
		DeltaS = delta;

		if(Vel > 0){ //some slight friction, we'll say it's space-age technology
			Vel = Vel - .0009f;
		}
		if(Vel < .1 && dec == false){//in case you need to stop
			Vel = .1f;
		}
		
		rotations = playerLocations.getRotation(); //I believe that this should be here
		
		if(rotations < 0){
			rotations += 360;
		}
		
		HueBX = (int) Math.round( 100 * Math.sin(Math.toRadians(rotations)) + HueX);// this deals with finding the ending point for the bullet's destination
		HueBY =  (int)Math.round(HueY - 100 * Math.cos(Math.toRadians(rotations))); //And this is the Y component of that
		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_DOWN)){//this is for decelerating, not fully implemented yet
			dec = true;
			Vel = Vel - .005f;
			if(Vel <= 0){
				Vel = 0;
			}
		}
		if(input.isKeyDown(Input.KEY_UP)){//where the magic happens for moving forward

			dec = false;
			Vel = Vel + .09f;
			hyp = Vel * delta;

			rotation = playerLocations.getRotation();

			if(rotation < 0){
				rotation += 360;
			}

			if(Vel > .6){ //this is important, since the screen is so small, If we figure out camera and scrolling screen, it's apt to change
				Vel = .6f;
			}

		}//if you were to move this bracket down bellow the next four lines, Velocity and physics would not work
		hyp = Vel * delta;
		HueX += hyp * Math.sin(Math.toRadians(rotation));//calculates x movement
		HueY -= hyp * Math.cos(Math.toRadians(rotation));//figure it out, I mean really.

		if(HueY >= 720){//bounds for maximum Y
			HueY = 720;

		}


		if(HueY < 0){//bounds for minimum Y
			HueY = 0;
		}

		if(HueX > 1000){//bounds for maximum X
			HueX = 1000;

		}

		if(HueX < 0){//minimum X
			HueX = 0;
		}


		if(input.isKeyDown(Input.KEY_LEFT)){//turns the ship and whatnot
			playerLocations.rotate(-0.4f * delta);}
		if(input.isKeyDown(Input.KEY_RIGHT)){
			playerLocations.rotate(0.4f * delta);}
		if(input.isKeyDown(Input.KEY_M)){ }//spawn random enemies
		if(input.isKeyPressed(Input.KEY_SPACE)){
			addNewBullet(); 

		}

		//Update the bullet's position.
		for(int i = 0;i<bulletList.size();i++)
		{
			Bullet bullet = bulletList.get(i);

			bullet.move();



			//NOTE: Will need to determine if this hit something or went off the screen. Or otherwise, the list will get filled with invalid bullets.
			//You'll have to add that yourself. This is code I copied, FYI.
		}


	}

	public int getID(){
		return 1;
	}

}
