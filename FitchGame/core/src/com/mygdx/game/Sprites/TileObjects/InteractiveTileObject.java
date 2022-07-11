package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;

public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(FitchGame game, MapObject object, int type){
        this.object = object;
        this.world = game.getWorld();
        this.map = game.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        if (type == 0) {
        	bdef.type = BodyDef.BodyType.StaticBody;
        } else if (type == 1) {
        	bdef.type = BodyDef.BodyType.DynamicBody;
        }
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / FitchGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / FitchGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / FitchGame.PPM, bounds.getHeight() / 2 / FitchGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);

    }

    public abstract void onHeadHit(Player mario);
    public abstract void onFeetHit(Player mario);
    public abstract void onFeetHitResolve(Player mario);
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * FitchGame.PPM / 16),
                (int)(body.getPosition().y * FitchGame.PPM / 16));
    }

}
