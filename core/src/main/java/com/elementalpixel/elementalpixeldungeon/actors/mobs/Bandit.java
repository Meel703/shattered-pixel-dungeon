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

package com.elementalpixel.elementalpixeldungeon.actors.mobs;


import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Blindness;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Buff;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Cripple;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Poison;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Hero;
import com.elementalpixel.elementalpixeldungeon.items.Item;
import com.elementalpixel.elementalpixeldungeon.sprites.BanditSprite;
import com.watabou.utils.Random;

public class Bandit extends Thief {
	
	public Item item;
	
	{
		spriteClass = BanditSprite.class;

		//guaranteed first drop, then 1/3, 1/9, etc.
		lootChance = 1f;
	}
	
	@Override
	protected boolean steal( Hero hero ) {
		if (super.steal( hero )) {
			
			Buff.prolong( hero, Blindness.class, Blindness.DURATION/2f );
			Buff.affect( hero, Poison.class ).set(Random.Int(5, 7) );
			Buff.prolong( hero, Cripple.class, Cripple.DURATION/2f );
			Dungeon.observe();
			
			return true;
		} else {
			return false;
		}
	}
	
}
