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


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.Char;
import com.elementalpixel.elementalpixeldungeon.actors.mobs.npcs.Ghost;
import com.elementalpixel.elementalpixeldungeon.items.food.MysteryMeat;
import com.elementalpixel.elementalpixeldungeon.items.wands.Wand;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.sprites.CharSprite;
import com.elementalpixel.elementalpixeldungeon.sprites.GreatCrabSprite;
import com.elementalpixel.elementalpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class GreatCrab extends Crab {

	{
		spriteClass = GreatCrabSprite.class;

		HP = HT = 25;
		defenseSkill = 0; //see damage()
		baseSpeed = 1f;

		EXP = 6;

		state = WANDERING;

		loot = new MysteryMeat().quantity(2);
		lootChance = 1f;

		properties.add(Property.MINIBOSS);
	}

	private int moving = 0;

	@Override
	protected boolean getCloser( int target ) {
		//this is used so that the crab remains slower, but still detects the player at the expected rate.
		moving++;
		if (moving < 3) {
			return super.getCloser( target );
		} else {
			moving = 0;
			return true;
		}

	}

	@Override
	public void damage( int dmg, Object src ){
		//crab blocks all attacks originating from its current enemy if it sees them.
		//All direct damage is negated, no exceptions. environmental effects go through as normal.
		if ((enemySeen && state != SLEEPING && paralysed == 0)
				&& ((src instanceof Wand && enemy == Dungeon.hero)
				|| (src instanceof Char && enemy == src))){
			GLog.n( Messages.get(this, "noticed") );
			sprite.showStatus( CharSprite.NEUTRAL, Messages.get(this, "blocked") );
			Sample.INSTANCE.play( Assets.Sounds.HIT_PARRY, 1, Random.Float(0.96f, 1.05f));
		} else {
			super.damage( dmg, src );
		}
	}

	@Override
	public void die( Object cause ) {
		super.die( cause );

		Ghost.Quest.process();
	}
}
