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

package com.elementalpixel.elementalpixeldungeon.levels.rooms.standard;


import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.items.Generator;
import com.elementalpixel.elementalpixeldungeon.items.Gold;
import com.elementalpixel.elementalpixeldungeon.items.Heap;
import com.elementalpixel.elementalpixeldungeon.levels.Level;
import com.elementalpixel.elementalpixeldungeon.levels.Terrain;
import com.elementalpixel.elementalpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Random;

public class GrassyGraveRoom extends StandardRoom {
	
	@Override
	public void paint(Level level) {
		
		Painter.fill( level, this, Terrain.WALL );
		for (Door door : connected.values()) {
			door.set( Door.Type.REGULAR );
		}

		if (Dungeon.depth == 36 || Dungeon.depth == 37 || Dungeon.depth == 38 || Dungeon.depth == 39) {
			Painter.fill( level, this, 1, Terrain.EMPTY);
		} else {

			Painter.fill(level, this, 1, Terrain.GRASS);
		}
		
		int w = width() - 2;
		int h = height() - 2;
		int nGraves = Math.max( w, h ) / 2;
		
		int index = Random.Int( nGraves );
		
		int shift = Random.Int( 2 );
		for (int i=0; i < nGraves; i++) {
			int pos = w > h ?
					left + 1 + shift + i * 2 + (top + 2 + Random.Int( h-2 )) * level.width() :
					(left + 2 + Random.Int( w-2 )) + (top + 1 + shift + i * 2) * level.width();
			level.drop( i == index ? Generator.random() : new Gold().random(), pos ).type = Heap.Type.TOMB;
		}
	}
}
