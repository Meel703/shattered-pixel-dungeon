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

package com.elementalpixel.elementalpixeldungeon.items;


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Barrier;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Buff;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Hero;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Talent;
import com.elementalpixel.elementalpixeldungeon.effects.Speck;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.sprites.CharSprite;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSpriteSheet;
import com.elementalpixel.elementalpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class Dewdrop extends Item {
	
	{
		image = ItemSpriteSheet.DEWDROP;
		
		stackable = true;
		dropsDownHeap = true;
	}
	
	@Override
	public boolean doPickUp( Hero hero ) {
		
		DewVial vial = hero.belongings.getItem( DewVial.class );
		
		if (vial != null && !vial.isFull()){
			
			vial.collectDew( this );
			
		} else {

			if (!consumeDew(1, hero)){
				return false;
			}
			
		}
		
		Sample.INSTANCE.play( Assets.Sounds.DEWDROP );
		hero.spendAndNext( TIME_TO_PICK_UP );
		
		return true;
	}

	public static boolean consumeDew(int quantity, Hero hero){
		//20 drops for a full heal
		int heal = Math.round( hero.HT * 0.05f * quantity );

		int effect = Math.min( hero.HT - hero.HP, heal );
		int shield = 0;
		if (hero.hasTalent(Talent.SHIELDING_DEW)){
			shield = heal - effect;
			int maxShield = Math.round(hero.HT *0.2f*hero.pointsInTalent(Talent.SHIELDING_DEW));
			int curShield = 0;
			if (hero.buff(Barrier.class) != null) curShield = hero.buff(Barrier.class).shielding();
			shield = Math.min(shield, maxShield-curShield);
		}
		if (effect > 0 || shield > 0) {
			hero.HP += effect;
			if (shield > 0) Buff.affect(hero, Barrier.class).incShield(shield);
			hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			if (effect > 0 && shield > 0){
				hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Dewdrop.class, "both", effect, shield) );
			} else if (effect > 0){
				hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Dewdrop.class, "heal", effect) );
			} else {
				hero.sprite.showStatus( CharSprite.POSITIVE, Messages.get(Dewdrop.class, "shield", shield) );
			}

		} else {
			GLog.i( Messages.get(Dewdrop.class, "already_full") );
			return false;
		}

		return true;
	}

	@Override
	//max of one dew in a stack
	public Item quantity(int value) {
		quantity = Math.min( value, 1);
		return this;
	}

}
