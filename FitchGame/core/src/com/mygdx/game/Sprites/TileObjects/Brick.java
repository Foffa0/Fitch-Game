package com.mygdx.game.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.FitchGame;
import com.mygdx.game.Sprites.Player;

public class Brick extends InteractiveTileObject {
    public Brick(FitchGame screen, MapObject object){
        super(screen, object, 0);
        fixture.setUserData(this);
        setCategoryFilter(FitchGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Player mario) {
       /* if(mario.isBig()) {
            setCategoryFilter(FitchGame.DESTROYED_BIT);
            getCell().setTile(null);
        }*/
    }
    @Override
    public void onFeetHit(Player mario) {
            setCategoryFilter(FitchGame.DESTROYED_BIT);
            getCell().setTile(null);
        }

	@Override
	public void onFeetHitResolve(Player mario) {
	}
   

}