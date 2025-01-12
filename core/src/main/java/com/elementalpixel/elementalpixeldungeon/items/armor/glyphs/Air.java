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

package com.elementalpixel.elementalpixeldungeon.items.armor.glyphs;


import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.Char;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Blindness;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Buff;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Cripple;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Talent;
import com.elementalpixel.elementalpixeldungeon.items.armor.Armor;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSprite;

public class Air extends Armor.Glyph {
    public static int counter = 2;

    private static ItemSprite.Glowing WHITE = new ItemSprite.Glowing( 0xFFFFFF );

    @Override
    public int proc(Armor armor, Char attacker, Char defender, int damage) {
        Buff.affect(attacker, Cripple.class);
        if (Talent.ElementalSurge) {
            if (Dungeon.hero.pointsInTalent(Talent.ATTUNED_MEAL) == 1) {
                Buff.affect(attacker, Blindness.class);
                Talent.ElementalSurge = false;
            } else {
                counter--;
                Buff.affect(attacker, Blindness.class);

                if (counter == 0) {
                    Talent.ElementalSurge = false;
                }
            }
        }

        if (Dungeon.hero.hasTalent(Talent.OPPRESSIVE_OFFENCE)) {
            return damage - Math.round(damage * (0.05f * Dungeon.hero.pointsInTalent(Talent.OPPRESSIVE_OFFENCE)) );
        } else {
            return damage;
        }


    }



    @Override
    public ItemSprite.Glowing glowing() {
        return WHITE;
    }
}
