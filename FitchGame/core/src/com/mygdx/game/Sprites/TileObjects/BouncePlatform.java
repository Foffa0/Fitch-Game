package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;

public class BouncePlatform extends InteractiveTileObject {
	private static TiledMapTileSet tileSet;
	private final int BLANK_COIN = 28;
	
	public BouncePlatform(FitchGame game, MapObject object){
        super(game, object, 0);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(FitchGame.PressurePlate_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
    }
    @Override
    public void onFeetHit(Player player) {
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
    }
    
    @Override
    public void onFeetHitResolve(Player player) {
        getCell().setTile(tileSet.getTile(26));
    }   
}
