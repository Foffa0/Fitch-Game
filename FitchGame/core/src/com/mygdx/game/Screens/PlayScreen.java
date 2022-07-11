package com.mygdx.game.Screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Platforms.LiftPlatform;
import com.mygdx.game.Sprites.Platforms.Platform;

public class PlayScreen implements Screen{
	private FitchGame game;
	private Player player;
	private Player player1;
	
	public PlayScreen(FitchGame game){
		this.game = game;
		this.player = game.getPlayer();
		this.player1 = game.getPlayer1();
	}
	
	
	@Override
    public void show() {
    }

    @Override
    public void render(float delta) {
    	//separate our update logic from render
        game.update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render our game map
        game.getRenderer().render();
        
      //renderer our Box2DDebugLines
        game.getB2dr().render(game.getWorld(), game.getGamecam().combined);

        game.batch.setProjectionMatrix(game.getGamecam().combined);
        game.batch.begin();
        player.draw(game.batch); 
        player1.draw(game.batch); 
	       for (LiftPlatform platform : game.getCreator().getEnemies())
	    	   platform.draw(game.batch);
	        	//game.batch.draw(new TextureRegion(game.getAtlas().findRegion("goomba"), 16, 0, 16, 16), platform.position.x, platform.position.y, 16 / FitchGame.PPM, 0.5f);*/
        game.batch.end();
        
        game.batch.setProjectionMatrix(game.getHud().stage.getCamera().combined);
        game.getHud().stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    	game.getGamePort().update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    	game.getMap().dispose();
        game.getRenderer().dispose();
        game.getWorld().dispose();
        game.getB2dr().dispose();
        game.getHud().dispose();
    }
 
    
}
