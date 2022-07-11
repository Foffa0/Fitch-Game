package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;

public class Water extends InteractiveTileObject {
	
	private int killPlayerId;
	
    public Water(FitchGame screen, MapObject object, int playerId){
        super(screen, object, 0);
        this.killPlayerId = playerId;
        fixture.setUserData(this);
        setCategoryFilter(FitchGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player player) {

    }
    @Override
    public void onFeetHit(Player player) {
    		if (player.getPlayerId() == killPlayerId || killPlayerId == -1)
    			player.die();
       }

	@Override
	public void onFeetHitResolve(Player mario) {
	}
   

}
