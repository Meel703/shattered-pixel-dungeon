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

package com.elementalpixel.elementalpixeldungeon.items.potions.exotic;


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.Actor;
import com.elementalpixel.elementalpixeldungeon.actors.Char;
import com.elementalpixel.elementalpixeldungeon.actors.blobs.Blob;
import com.elementalpixel.elementalpixeldungeon.actors.blobs.Fire;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Buff;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Burning;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Cripple;
import com.elementalpixel.elementalpixeldungeon.actors.hero.Hero;
import com.elementalpixel.elementalpixeldungeon.effects.MagicMissile;
import com.elementalpixel.elementalpixeldungeon.levels.Level;
import com.elementalpixel.elementalpixeldungeon.levels.Terrain;
import com.elementalpixel.elementalpixeldungeon.mechanics.Ballistica;
import com.elementalpixel.elementalpixeldungeon.mechanics.ConeAOE;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.scenes.CellSelector;
import com.elementalpixel.elementalpixeldungeon.scenes.GameScene;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;

import java.util.ArrayList;

public class PotionOfDragonsBreath extends ExoticPotion {
	
	{
		icon = ItemSpriteSheet.Icons.POTION_DRGBREATH;
	}

	@Override
	//need to override drink so that time isn't spent right away
	protected void drink(final Hero hero) {
		curUser = hero;
		curItem = this;
		
		GameScene.selectCell(targeter);
	}
	
	private CellSelector.Listener targeter = new CellSelector.Listener() {
		@Override
		public void onSelect(final Integer cell) {

			if (cell == null && !isKnown()){
				identify();
				detach(curUser.belongings.backpack);
			} else if (cell != null) {
				identify();
				Sample.INSTANCE.play( Assets.Sounds.DRINK );
				curUser.sprite.operate(curUser.pos, new Callback() {
					@Override
					public void call() {

						curItem.detach(curUser.belongings.backpack);

						curUser.spend(1f);
						curUser.sprite.idle();
						curUser.sprite.zap(cell);
						Sample.INSTANCE.play( Assets.Sounds.BURNING );

						final Ballistica bolt = new Ballistica(curUser.pos, cell, Ballistica.STOP_SOLID | Ballistica.IGNORE_SOFT_SOLID);

						int maxDist = 6;
						int dist = Math.min(bolt.dist, maxDist);

						final ConeAOE cone = new ConeAOE(bolt, 6, 60, Ballistica.STOP_SOLID | Ballistica.STOP_TARGET | Ballistica.IGNORE_SOFT_SOLID);

						//cast to cells at the tip, rather than all cells, better performance.
						for (Ballistica ray : cone.rays){
							((MagicMissile)curUser.sprite.parent.recycle( MagicMissile.class )).reset(
									MagicMissile.FIRE_CONE,
									curUser.sprite,
									ray.path.get(ray.dist),
									null
							);
						}
						
						MagicMissile.boltFromChar(curUser.sprite.parent,
								MagicMissile.FIRE_CONE,
								curUser.sprite,
								bolt.path.get(dist / 2),
								new Callback() {
									@Override
									public void call() {
										ArrayList<Integer> adjacentCells = new ArrayList<>();
										for (int cell : cone.cells){
											//ignore caster cell
											if (cell == bolt.sourcePos){
												continue;
											}

											//knock doors open
											if (Dungeon.level.map[cell] == Terrain.DOOR){
												Level.set(cell, Terrain.OPEN_DOOR);
												GameScene.updateMap(cell);
											}

											//only ignite cells directly near caster if they are flammable
											if (Dungeon.level.adjacent(bolt.sourcePos, cell) && !Dungeon.level.flamable[cell]){
												adjacentCells.add(cell);
											} else {
												GameScene.add( Blob.seed( cell, 5, Fire.class ) );
											}
											
											Char ch = Actor.findChar( cell );
											if (ch != null) {
												
												Buff.affect( ch, Burning.class ).reignite( ch );
												Buff.affect(ch, Cripple.class, 5f);
											}
										}

										//ignite cells that share a side with an adjacent cell, are flammable, and are further from the source pos
										//This prevents short-range casts not igniting barricades or bookshelves
										for (int cell : adjacentCells){
											for (int i : PathFinder.NEIGHBOURS4){
												if (Dungeon.level.trueDistance(cell+i, bolt.sourcePos) > Dungeon.level.trueDistance(cell, bolt.sourcePos)
														&& Dungeon.level.flamable[cell+i]
														&& Fire.volumeAt(cell+i, Fire.class) == 0){
													GameScene.add( Blob.seed( cell+i, 5, Fire.class ) );
												}
											}
										}

										curUser.next();
									}
								});
						
					}
				});
			}
		}
		
		@Override
		public String prompt() {
			return Messages.get(PotionOfDragonsBreath.class, "prompt");
		}
	};
}