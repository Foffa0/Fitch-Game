package com.mygdx.game.Sprites.Platforms;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player.State;

public class LiftPlatform extends Sprite {
	public Body b2body;
	public World world;
	private float step;
	
	public LiftPlatform(FitchGame game, float x, float y){
        //initialize default values
        this.world = game.getWorld();

        //create texture region for platform
        TextureRegion marioStand = new TextureRegion(game.getAtlas().findRegion("little_mario"), 0, 0, 16, 16);

        //define platform in Box2d
        defineLiftPlatform(x, y);

        //set initial values for the platforms location, width and height. And initial frame as marioStand.
        setBounds(0, 0, 16 / FitchGame.PPM, 16 / FitchGame.PPM);
        setRegion(marioStand);
    }

    public void update(float dt){
        //update our sprite to correspond with the position of our Box2D body
    	setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    	if (b2body.getPosition().y < 0.24) {
    		b2body.setLinearVelocity(new Vector2(0, 0));
    	} else if (b2body.getPosition().y > 1) {
    		b2body.setLinearVelocity(new Vector2(0, 0));
    	}
        
    }
	
    
    public void defineLiftPlatform(float x, float y){
        BodyDef bdef = new BodyDef();
        //bdef.position.set(32 / FitchGame.PPM, 32 / FitchGame.PPM);
        bdef.position.set(x, y+8/FitchGame.PPM);
        bdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        /*CircleShape shape = new CircleShape();
        shape.setRadius(6 / FitchGame.PPM);*/
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(8/FitchGame.PPM, 8/FitchGame.PPM);
        fdef.filter.categoryBits = FitchGame.BRICK_BIT;
        fdef.filter.maskBits = FitchGame.GROUND_BIT |
        		FitchGame.COIN_BIT |
        		FitchGame.MARIO_BIT |
        		FitchGame.ENEMY_BIT |
        		FitchGame.OBJECT_BIT |
        		FitchGame.ENEMY_HEAD_BIT |
        		FitchGame.ITEM_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }
    
    public void moveUp() {
    	b2body.setLinearVelocity(new Vector2(0,(float) 0.1));
    }
    
    public void moveDown() {
    	b2body.setLinearVelocity(new Vector2(0,(float) -0.1));
    }
    
    
    public void draw(Batch batch){
        super.draw(batch);
    }
}
