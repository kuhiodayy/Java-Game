package javagame;

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
	public Image Hue;
	public float HueX = 200;
	public float HueY = 200;
	public float hyp = 0;
	public float scale = 1;
	public float BX = 0;
	public float BY = 0;
	public boolean Fired = false;
    public boolean BulletInit = false; 
    public float rotation = 0;
	
	public Play(int state){
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Hue = new Image("res/TestPlane.png");
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException{
		Hue.draw(HueX, HueY);
		g.drawRect(BX, BY, 10, 10);
		g.drawString("HueX = " + HueX, 100, 650);
		g.drawString("HueY = " + HueY, 100, 600);
		
		
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException{
		Input input = gc.getInput();
		if(input.isKeyDown(Input.KEY_UP)){

		hyp = 1f * delta;
	
		float rotation = Hue.getRotation();
		
		HueX += hyp * Math.sin(Math.toRadians(rotation));
		HueY -= hyp * Math.cos(Math.toRadians(rotation));
		
			if(HueY > 610){
				HueY = 610;
			}
			
			
			if(HueY < 0){
				HueY += hyp * Math.cos(Math.toRadians(rotation));
			}
			
			if(HueX > 900){
				HueX = 900;
				
			}
			
			if(HueX < 0){
				HueX = 0;
			}
			
			
			
		
		}
		
		if(input.isKeyDown(Input.KEY_LEFT)){Hue.rotate(-0.4f * delta);}
		if(input.isKeyDown(Input.KEY_RIGHT)){Hue.rotate(0.4f * delta);}
		if(input.isKeyDown(Input.KEY_SPACE)){ Fired = true;

		}
		if(Fired == true){

			hyp = 1f * delta;
		
			
			if(BulletInit == false){
			BX =  HueX; 
			BY = HueY;
			rotation = Hue.getRotation();
			BulletInit = true;
			}
			BX += 2 * hyp * Math.sin(Math.toRadians(rotation));
			BY -= 2 * hyp * Math.cos(Math.toRadians(rotation));
			
			
		}
		
		if(BX > 990 || BX < 0 || BY > 700 || BY < 0){
			Fired = false;
			BulletInit = false;
		}
		
		
}
	
	public int getID(){
		return 1;
	}
	
}
