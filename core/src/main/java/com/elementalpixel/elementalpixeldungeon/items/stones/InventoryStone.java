/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.elementalpixel.elementalpixeldungeon.items.stones;


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Invisibility;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Hero;
import com.elementalpixel.elementalpixeldungeon.items.Item;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.scenes.GameScene;
import com.elementalpixel.elementalpixeldungeon.windows.WndBag;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public abstract class InventoryStone extends Runestone {
	
	protected String inventoryTitle = Messages.get(this, "inv_title");
	protected WndBag.Mode mode = WndBag.Mode.ALL;
	
	{
		defaultAction = AC_USE;
	}
	
	public static final String AC_USE	= "USE";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_USE );
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);
		if (action.equals(AC_USE)){
			curItem = detach( hero.belongings.backpack );
			activate(curUser.pos);
		}
	}
	
	@Override
	protected void activate(int cell) {
		GameScene.selectItem( itemSelector, mode, inventoryTitle );
	}
	
	protected void useAnimation() {
		curUser.spend( 1f );
		curUser.busy();
		curUser.sprite.operate(curUser.pos);
		
		Sample.INSTANCE.play( Assets.Sounds.READ );
		Invisibility.dispel();
	}
	
	protected abstract void onItemSelected( Item item );
	
	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			
			//FIXME this safety check shouldn't be necessary
			//it would be better to eliminate the curItem static variable.
			if (!(curItem instanceof InventoryStone)){
				return;
			}
			
			if (item != null) {
				
				((InventoryStone)curItem).onItemSelected( item );
				
			} else{
				curItem.collect( curUser.belongings.backpack );
			}
		}
	};
	
}
