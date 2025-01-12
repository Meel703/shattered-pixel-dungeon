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
import com.elementalpixel.elementalpixeldungeon.levels.painters.CavesPainter;
import com.elementalpixel.elementalpixeldungeon.levels.painters.Painter;
import com.elementalpixel.elementalpixeldungeon.levels.traps.BurningTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.ConfusionTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.CorrosionTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.FrostTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.GrippingTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.GuardianTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.PitfallTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.PoisonDartTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.RockfallTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.StormTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.SummoningTrap;
import com.elementalpixel.elementalpixeldungeon.levels.traps.WarpingTrap;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.particles.PixelParticle;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WaterLevel extends RegularLevel {

	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
	}
	
	@Override
	protected int standardRooms(boolean forceMax) {
		if (forceMax) return 9;
		//6 to 9, average 7.333
		return 6+ Random.chances(new float[]{2, 3, 3, 1});
	}
	
	@Override
	protected int specialRooms(boolean forceMax) {
		if (forceMax) return 3;
		//1 to 3, average 2.2
		return 1+Random.chances(new float[]{2, 4, 4});
	}
	
	@Override
	protected Painter painter() {
		return new CavesPainter()
				.setWater(feeling == Feeling.WATER ? 0.85f : 0.30f, 6)
				.setTraps(nTraps(), trapClasses(), trapChances());
	}
	
	@Override
	public String tilesTex() {
		return Assets.Environment.TILES_CAVES;
	}
	
	@Override
	public String waterTex() {
		return Assets.Environment.WATER_CAVES;
	}
	
	@Override
	protected Class<?>[] trapClasses() {
		return new Class[]{
				BurningTrap.class, PoisonDartTrap.class, FrostTrap.class, StormTrap.class, CorrosionTrap.class,
				GrippingTrap.class, RockfallTrap.class,  GuardianTrap.class,
				ConfusionTrap.class, SummoningTrap.class, WarpingTrap.class, PitfallTrap.class };
	}

	@Override
	protected float[] trapChances() {
		return new float[]{
				4, 4, 4, 4, 4,
				2, 2, 2,
				1, 1, 1, 1};
	}
	
	@Override
	public String tileName( int tile ) {
		switch (tile) {
			case Terrain.WATER:
				return Messages.get(WaterLevel.class, "water_name");
			default:
				return super.tileName( tile );
		}
	}
	
	@Override
	public String tileDesc( int tile ) {
		switch (tile) {
			case Terrain.ENTRANCE:
				return Messages.get(WaterLevel.class, "entrance_desc");
			case Terrain.EXIT:
				return Messages.get(WaterLevel.class, "exit_desc");
			case Terrain.WALL_DECO:
				return Messages.get(WaterLevel.class, "wall_deco_desc");
			case Terrain.BOOKSHELF:
				return Messages.get(WaterLevel.class, "bookshelf_desc");
			default:
				return super.tileDesc( tile );
		}
	}
	
	@Override
	public Group addVisuals() {
		super.addVisuals();
		addCavesVisuals( this, visuals );
		return visuals;
	}
	
	public static void addCavesVisuals( Level level, Group group ) {
		for (int i=0; i < level.length(); i++) {
			if (level.map[i] == Terrain.WALL_DECO) {
				group.add( new Vein( i ) );
			}
		}
	}
	
	private static class Vein extends Group {
		
		private int pos;
		
		private float delay;
		
		public Vein( int pos ) {
			super();
			
			this.pos = pos;
			
			delay = Random.Float( 2 );
		}
		
		@Override
		public void update() {
			
			if (visible = (pos < Dungeon.level.heroFOV.length && Dungeon.level.heroFOV[pos])) {
				
				super.update();

				if ((delay -= Game.elapsed) <= 0) {

					//pickaxe can remove the ore, should remove the sparkling too.
					if (Dungeon.level.map[pos] != Terrain.WALL_DECO){
						kill();
						return;
					}
					
					delay = Random.Float();
					
					PointF p = DungeonTilemap.tileToWorld( pos );
					((Sparkle)recycle( Sparkle.class )).reset(
						p.x + Random.Float( DungeonTilemap.SIZE ),
						p.y + Random.Float( DungeonTilemap.SIZE ) );
				}
			}
		}
	}
	
	public static final class Sparkle extends PixelParticle {
		
		public void reset( float x, float y ) {
			revive();
			
			this.x = x;
			this.y = y;
			
			left = lifespan = 0.5f;
		}
		
		@Override
		public void update() {
			super.update();
			
			float p = left / lifespan;
			size( (am = p < 0.5f ? p * 2 : (1 - p) * 2) * 2 );
		}
	}
}