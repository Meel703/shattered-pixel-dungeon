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


import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Hero;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Talent;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfExperience extends Potion {


	{
		icon = ItemSpriteSheet.Icons.POTION_EXP;

		bones = true;

	}
	
	@Override
	public void apply( Hero hero ) {
		identify();
		hero.earnExp( hero.maxExp(), getClass() );
	}
	
	@Override
	public int value() {
		if (Dungeon.hero.hasTalent(Talent.FAMILIAR_FACE)) {
			if (Dungeon.hero.pointsInTalent(Talent.FAMILIAR_FACE) == 1) {
				return isKnown() ? (50 * quantity) / 4 * 3 : super.value();
			} else {
				return isKnown() ? (50 * quantity) / 2 : super.value();
			}
		} else {
			return isKnown() ? 50 * quantity : super.value();
		}
	}
}
