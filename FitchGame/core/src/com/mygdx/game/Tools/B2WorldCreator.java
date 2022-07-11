package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.TileObjects.Brick;
import com.mygdx.game.Sprites.TileObjects.PressurePlate;
import com.mygdx.game.Sprites.Platforms.LiftPlatform;
import com.mygdx.game.Sprites.Platforms.Platform;

public class B2WorldCreator {
	private Array<LiftPlatform> platforms;

    public B2WorldCreator(FitchGame game){
        World world = game.getWorld();
        TiledMap map = game.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
       /* for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FitchGame.PPM, (rect.getY() + rect.getHeight() / 2) / FitchGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FitchGame.PPM, rect.getHeight() / 2 / FitchGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }*/

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / FitchGame.PPM, (rect.getY() + rect.getHeight() / 2) / FitchGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / FitchGame.PPM, rect.getHeight() / 2 / FitchGame.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = FitchGame.OBJECT_BIT;
            body.createFixture(fdef);
        }

       /* //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Brick(game, object);
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){

            new PressurePlate(game, object, 0);
        }

        /*///create all goombas
        platforms = new Array<LiftPlatform>();
        /*for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            platforms.add(new LiftPlatform(game, rect.getX() / FitchGame.PPM, rect.getY() / FitchGame.PPM));
        }*/
        /*turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / MarioBros.PPM, rect.getY() / MarioBros.PPM));
        }*/
    }

    public Array<LiftPlatform> getGoombas() {
        return platforms;
    }
    public Array<LiftPlatform> getEnemies(){
        Array<LiftPlatform> enemies = new Array<LiftPlatform>();
        enemies.addAll(platforms);
        //enemies.addAll(turtles);
        return enemies;
    }
}
