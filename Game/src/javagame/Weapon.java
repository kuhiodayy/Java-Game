package javagame;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;



   class Bullet
   {
      int startX = 0;
      int startY = 0;
      int destX = 0;
      int destY = 0;
      Point location = new Point(0,0);
      float speed = 10f; //how fast this moves.
      float deg;
      float dx;
      float dy;
      
      
      public Bullet(int startX, int startY, int destX, int destY)
      {
         this.startX = startX;
         this.startY = startY;
         location.setLocation(startX, startY);
         this.destX = destX;
         this.destY = destY;
         recalculateVector(destX, destY);
        

      }
      
      /**
       * Calculates a new vector based on the input destination X and Y.
       * @param destX
       * @param destY
       */
      public void recalculateVector(int destX, int destY)
      {
         float rad = (float)(Math.atan2(destX - startX, startY - destY));
         
         //Can set different speeds here, if you wanted.
         speed = 2 * Play.DeltaS; 
         
         this.dx = (float) Math.sin(rad) * speed;
         this.dy = -(float) Math.cos(rad) * speed;
      }
      
      /**
       * Recalculates the vector for this bullet based on the current destination.
       */
      public void recalculateVector()
      {
         recalculateVector(destX, destY);
      }
      
      /**
       * Moves this bullet.
       */
      public void move()
      {
         float x = location.getX();
         float y = location.getY();
         
         x += dx;
         y += dy;
         
         location.setLocation(x, y);
      }
      
   }

