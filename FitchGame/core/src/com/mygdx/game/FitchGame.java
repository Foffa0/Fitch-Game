package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Sprites.Platforms.LiftPlatform;
import com.mygdx.game.Sprites.Platforms.Platform;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.WorldContactListener;

import database.Datenbank;

public class FitchGame extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;
	
	//Box2D Collision Bits
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;
	public static final short MARIO_FEET_BIT = 1024;
	public static final short PressurePlate_BIT = 2048;
	
	//Reference to our Game, used to set Screens
    private TextureAtlas atlas;
    public static boolean alreadyDestroyed = false;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldCreator creator;

    //sprites
    private Player player;
    private Player player1;
    
    //Database
    private Datenbank datenbank;

    /*private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;*/
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		atlas = new TextureAtlas("Mario_and_Enemies.pack");
		datenbank = new Datenbank();
		datenbank.checkDb();
		System.out.println(datenbank.getHighscore(1));

        //create camera
        gamecam = new OrthographicCamera();

        //create a FitViewport to maintain virtual aspect ratio despite screen size
        gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gamecam);

        //create our game HUD for scores/timers/level info
        hud = new Hud(batch);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("chemie_1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1  / PPM);

        //initially set our gamcam to be centered correctly at the start of of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();
        
        creator = new B2WorldCreator(this);
        
        player = new Player(this, 0);
        player1 = new Player(this, 1);
        
        world.setContactListener(new WorldContactListener(this));
        
		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		//manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void update(float dt){
		handleInput(dt);

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2);

        player.update(dt);
        player1.update(dt);
        for (LiftPlatform platform : creator.getEnemies())
     	   platform.update(dt);
        
        /*for(LiftPlatform platform : creator.getEnemies()) {
        	platform.update(dt);
            /*if(platform.getX() < player.getX() + 224 / FitchGame.PPM) {
            	platform.b2body.setActive(true);
            }*/
        //}
        hud.update(dt);
        
        //attach our gamecam to our players.x coordinate
        if(player.currentState != Player.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our camera can see in our game world.
        renderer.setView(gamecam);

    }
	
	public void handleInput(float dt){
        //control our player using immediate impulses
        if(player.currentState != Player.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.jump();
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if(player1.currentState != Player.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W))
            	player1.jump();
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player1.b2body.getLinearVelocity().x <= 2)
            	player1.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player1.b2body.getWorldCenter(), true);
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player1.b2body.getLinearVelocity().x >= -2)
            	player1.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player1.b2body.getWorldCenter(), true);
        }

    }

	public OrthographicCamera getGamecam() {
		return gamecam;
	}

	public void setGamecam(OrthographicCamera gamecam) {
		this.gamecam = gamecam;
	}

	public Viewport getGamePort() {
		return gamePort;
	}

	public void setGamePort(Viewport gamePort) {
		this.gamePort = gamePort;
	}

	public TmxMapLoader getMaploader() {
		return maploader;
	}

	public void setMaploader(TmxMapLoader maploader) {
		this.maploader = maploader;
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}

	public OrthogonalTiledMapRenderer getRenderer() {
		return renderer;
	}

	public void setRenderer(OrthogonalTiledMapRenderer renderer) {
		this.renderer = renderer;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public TextureAtlas getAtlas() {
		return atlas;
	}

	public void setAtlas(TextureAtlas atlas) {
		this.atlas = atlas;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Box2DDebugRenderer getB2dr() {
		return b2dr;
	}

	public void setB2dr(Box2DDebugRenderer b2dr) {
		this.b2dr = b2dr;
	}

	public B2WorldCreator getCreator() {
		return creator;
	}

	public void setCreator(B2WorldCreator creator) {
		this.creator = creator;
	}

	public Hud getHud() {
		return hud;
	}

	public void setHud(Hud hud) {
		this.hud = hud;
	}
	
	
}
