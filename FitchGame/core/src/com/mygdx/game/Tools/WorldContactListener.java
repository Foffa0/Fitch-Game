package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Platforms.LiftPlatform;
import com.mygdx.game.Sprites.TileObjects.InteractiveTileObject; 
import com.mygdx.game.Sprites.TileObjects.PressurePlate; 

public class WorldContactListener implements ContactListener {
	FitchGame game;
	
    public WorldContactListener(FitchGame game) {
		this.game = game;
	}

	@Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        
        switch (cDef){
            case FitchGame.MARIO_HEAD_BIT | FitchGame.BRICK_BIT:
            case FitchGame.MARIO_HEAD_BIT | FitchGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
                break;
            case FitchGame.MARIO_FEET_BIT | FitchGame.COIN_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.MARIO_FEET_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onFeetHit((Player) fixA.getUserData());
                else
                    ((InteractiveTileObject) fixA.getUserData()).onFeetHit((Player) fixB.getUserData());
                break;
            case FitchGame.MARIO_FEET_BIT | FitchGame.PressurePlate_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.MARIO_FEET_BIT) {
                	if (((PressurePlate) fixB.getUserData()).getPlayerId() == ((Player) fixA.getUserData()).getPlayerId()) {
                		((PressurePlate) fixB.getUserData()).onFeetHit((Player) fixA.getUserData());
                		for (LiftPlatform platform : game.getCreator().getEnemies()) {
    	                	if (((PressurePlate) fixB.getUserData()).getPlayerId() == ((Player) fixA.getUserData()).getPlayerId()) {
    	                		platform.moveUp();
    	                	}
    	                }
                	}
	                
                }
                else {
                	if (((PressurePlate) fixA.getUserData()).getPlayerId() == ((Player) fixB.getUserData()).getPlayerId()) {
                		((PressurePlate) fixA.getUserData()).onFeetHit((Player) fixB.getUserData());
                		for (LiftPlatform platform : game.getCreator().getEnemies()) {
    	                	if (((PressurePlate) fixA.getUserData()).getPlayerId() == ((Player) fixB.getUserData()).getPlayerId()) {
    	                		platform.moveUp();
    	                	}
    	                }
                	}
	                
                }
                break;
           /* case FitchGame.ENEMY_HEAD_BIT | FitchGame.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.ENEMY_HEAD_BIT)
                    ((Enemy)fixA.getUserData()).hitOnHead((Player) fixB.getUserData());
                else
                    ((Enemy)fixB.getUserData()).hitOnHead((Player) fixA.getUserData());
                break;
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if(fixA.getFilterData().FitchGame == FitchGame.ENEMY_BIT)
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                break;*/
            /*case FitchGame.MARIO_BIT | FitchGame.ENEMY_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.MARIO_BIT)
                    ((Player) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                else
                    ((Player) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                break;*/
            /*case FitchGame.ENEMY_BIT | FitchGame.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).hitByEnemy((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).hitByEnemy((Enemy)fixA.getUserData());
                break;*/
            /*case FitchGame.ITEM_BIT | FitchGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case FitchGame.ITEM_BIT | FitchGame.MARIO_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Player) fixB.getUserData());
                else
                    ((Item)fixB.getUserData()).use((Player) fixA.getUserData());
                break;
            case FitchGame.FIREBALL_BIT | FitchGame.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.FIREBALL_BIT)
                    ((FireBall)fixA.getUserData()).setToDestroy();
                else
                    ((FireBall)fixB.getUserData()).setToDestroy();
                break;*/
        }
    }

    @Override
    public void endContact(Contact contact) {
    	Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        System.out.println(cDef);

        switch (cDef){
	        case FitchGame.MARIO_HEAD_BIT | FitchGame.BRICK_BIT:
	        case FitchGame.MARIO_HEAD_BIT | FitchGame.COIN_BIT:
	        	
	            if(fixA.getFilterData().categoryBits == FitchGame.MARIO_HEAD_BIT)
	                ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Player) fixA.getUserData());
	            else
	                ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Player) fixB.getUserData());
	            break;
	        case FitchGame.MARIO_FEET_BIT | FitchGame.COIN_BIT:
	            if(fixA.getFilterData().categoryBits == FitchGame.MARIO_FEET_BIT)
	                ((InteractiveTileObject) fixB.getUserData()).onFeetHitResolve((Player) fixA.getUserData());
	            else
	                ((InteractiveTileObject) fixA.getUserData()).onFeetHitResolve((Player) fixB.getUserData());
	            break;
	        case FitchGame.MARIO_FEET_BIT | FitchGame.PressurePlate_BIT:
                if(fixA.getFilterData().categoryBits == FitchGame.MARIO_FEET_BIT) {
                	if (((PressurePlate) fixB.getUserData()).getPlayerId() == ((Player) fixA.getUserData()).getPlayerId()) {
                		((PressurePlate) fixB.getUserData()).onFeetHit((Player) fixA.getUserData());
                		for (LiftPlatform platform : game.getCreator().getEnemies())
                       	   platform.moveDown();
                	}
                }
                else
                	if (((PressurePlate) fixA.getUserData()).getPlayerId() == ((Player) fixB.getUserData()).getPlayerId()) {
                		((PressurePlate) fixA.getUserData()).onFeetHit((Player) fixB.getUserData());
                		for (LiftPlatform platform : game.getCreator().getEnemies())
                       	   platform.moveDown();
                	}
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
