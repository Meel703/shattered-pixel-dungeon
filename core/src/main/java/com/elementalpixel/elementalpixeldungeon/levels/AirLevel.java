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

package com.elementalpixel.elementalpixeldungeon.levels;


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.mobs.npcs.Wandmaker;
import com.elementalpixel.elementalpixeldungeon.effects.particles.FlameParticle;
import com.elementalpixel.elementalpixeldungeon.levels.painters.Painter;
import com.elementalpixel.elementalpixeldungeon.levels.painters.PrisonPainter;
import com.elementalpixel.elementalpixeldungeon.levels.rooms.Room;
import com.elementalpixel.elementalpixeldungeon.levels.traps.AlarmTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.BurningTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.ChillingTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.ConfusionTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.CorrosionTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.DistortionTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.FlockTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.GrippingTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.OozeTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.PoisonDartTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.ShockingTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.StormTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.SummoningTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.TeleportationTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.ToxicTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.WarpingTrap;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Group;
import com.watabou.noosa.Halo;
import com.watabou.noosa.particles.Emitter;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class AirLevel extends RegularLevel {

	{
		color1 = 0x6a723d;
		color2 = 0x88924c;
	}
	
	@Override
	protected int standardRooms(boolean forceMax) {
		if (forceMax) return 8;
		//6 to 8, average 6.75
		return 6+ Random.chances(new float[]{4, 2, 2});
	}
	
	@Override
	protected int specialRooms(boolean forceMax) {
		if (forceMax) return 3;
		//1 to 3, average 2.0
		return 1+Random.chances(new float[]{3, 4, 3});
	}
	
	@Override
	protected Painter painter() {
		return new PrisonPainter()
				.setWater(feeling == Feeling.WATER ? 0.90f : 0.30f, 4)
				.setGrass(feeling == Feeling.GRASS ? 0.80f : 0.20f, 3)
				.setTraps(nTraps(), trapClasses(), trapChances());
	}
	
	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_PRISON;
	}
	
	@Override
	public String waterTex() {
		return Assets.Environment.WATER_PRISON;
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{
				DistortionTrap.class, StormTrap.class, WarpingTrap.class,
				ConfusionTrap.class, CorrosionTrap.class,
				ToxicTrap.class, FlockTrap.class,
};
	}

	@Override
	protected float[] trapChances() {
		return new float[]{
				4, 4, 4,
				2, 2,
				1, 1 };
	}

	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(AirLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}

	@Override
	public String tileDesc(int tile) {
		switch (tile) {
			case Terrain.EMPTY_DECO:
				return Messages.get(AirLevel.class, "empty_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(AirLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		addPrisonVisuals(this, visuals);
		return visuals;
	}

	public static void addPrisonVisuals(Level level, Group group){
		for (int i=0; i < level.length(); i++) {
			if (level.map[i] == Terrain.WALL_DECO) {
				group.add( new Torch( i ) );
			}
		}
	}

	public static class Torch extends Emitter {
		
		private int pos;
		
		public Torch( int pos ) {
			super();
			
			this.pos = pos;
			
			PointF p = DungeonTilemap.tileCenterToWorld( pos );
			pos( p.x - 1, p.y + 2, 2, 0 );
			
			pour( FlameParticle.FACTORY, 0.15f );
			
			add( new Halo( 12, 0xFFFFCC, 0.4f ).point( p.x, p.y + 1 ) );
		}
		
		@Override
		public void update() {
			if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
				super.update();
			}
		}
	}
}