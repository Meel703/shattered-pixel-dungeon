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

package com.elementalpixel.elementalpixeldungeon.items.potions;


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.blobs.Blob;
import com.elementalpixel.elementalpixeldungeon.actors.blobs.ToxicGas;
import com.elementalpixel.elementalpixeldungeon.actors.hero.HeroClass;
import com.elementalpixel.elementalpixeldungeon.actors.hero.HeroSubClass;
import com.elementalpixel.elementalpixeldungeon.scenes.GameScene;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

public class PotionOfToxicGas extends Potion {

	{
		icon = ItemSpriteSheet.Icons.POTION_TOXICGAS;
	}

	@Override
	public void shatter( int cell ) {

		if (Dungeon.level.heroFOV[cell]) {
			identify();

			splash( cell );
			Sample.INSTANCE.play( Assets.Sounds.SHATTER );
			Sample.INSTANCE.play( Assets.Sounds.GAS );
		}
		if (curUser.subClass == HeroSubClass.SCIENTIST) {
			GameScene.add( Blob.seed( cell, 1400, ToxicGas.class ) );
		} else {
			GameScene.add( Blob.seed( cell, 1000, ToxicGas.class ) );
		}
	}
	
	@Override
	public int value() {
		if (curUser.heroClass == HeroClass.ALCHEMIST) {
			return isKnown() ? 25 * quantity : super.value();
		} else {
			return isKnown() ? 30 * quantity : super.value();
		}
	}
}