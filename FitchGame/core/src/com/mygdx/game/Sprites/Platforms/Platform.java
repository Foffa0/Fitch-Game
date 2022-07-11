package com.mygdx.game.Sprites.Platforms;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.FitchGame;

public abstract class Platform extends Sprite {
	protected World world;
    protected FitchGame screen;
    public Body b2body;
    public Vector2 velocity;

    public Platform(FitchGame game, float x, float y){
        this.world = game.getWorld();
        this.screen = game;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(0, 1);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();
    public abstract void update(float dt);
    /*public abstract void hitOnHead(Player player);
    public abstract void hitByEnemy(Enemy enemy);*/

    public void setVelocity(float x, float y){
            velocity.x = x;
            velocity.y = y;
    }
}
