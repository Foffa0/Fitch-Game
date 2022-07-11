package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;

public class PressurePlate extends InteractiveTileObject {
	private static TiledMapTileSet tileSet;
	private final int BLANK_COIN = 28;
	private int playerId;
	
	public PressurePlate(FitchGame game, MapObject object, int playerId){
        super(game, object, 0);
        this.playerId = playerId;
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(FitchGame.PressurePlate_BIT);
    }

    @Override
    public void onHeadHit(Player player) {
    }
    @Override
    public void onFeetHit(Player player) {
        setCategoryFilter(FitchGame.PressurePlate_BIT);
        getCell().setTile(tileSet.getTile(BLANK_COIN));
    }
    
    @Override
    public void onFeetHitResolve(Player player) {
        setCategoryFilter(FitchGame.COIN_BIT);
        getCell().setTile(tileSet.getTile(26));
    }

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
    
    
}
