package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;

public class MoveableBox extends InteractiveTileObject {
	private static TiledMapTileSet tileSet;
	private final int BLANK_COIN = 28;
	
	public MoveableBox(FitchGame game, MapObject object){
        super(game, object, 1);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(FitchGame.PressurePlate_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
    }
    @Override
    public void onFeetHit(Player player) {
    }
    
    @Override
    public void onFeetHitResolve(Player player) {
    }
}
