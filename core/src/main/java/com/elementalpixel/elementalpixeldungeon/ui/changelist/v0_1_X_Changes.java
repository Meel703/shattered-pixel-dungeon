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

package com.elementalpixel.elementalpixeldungeon.ui.changelist;


import com.elementalpixel.elementalpixeldungeon.Assets;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.scenes.ChangesScene;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSprite;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSpriteSheet;
import com.elementalpixel.elementalpixeldungeon.ui.Icons;
import com.elementalpixel.elementalpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class v0_1_X_Changes {
	
	public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo( "v0.1.X", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		changeInfos.add(changes);
		
		add_v0_1_0_Changes(changeInfos);
		add_v0_1_2_Changes(changeInfos);
		add_v0_1_2_1_Changes(changeInfos);
		add_v0_1_2_2_Changes(changeInfos);
		add_v0_1_2_3_Changes(changeInfos);
		add_v0_1_2_4_Changes(changeInfos);
	}
	

	public static void add_v0_1_0_Changes( ArrayList<ChangeInfo> changeInfos ){
		
		ChangeInfo changes = new ChangeInfo("v0.1a", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.RATION, null), "Food Changes",
				"_-_ Eating gives you minor buffs\n")
		);

		changes.addButton( new ChangeButton(new Image(Assets.Sprites.ROGUE, 0, 90, 12, 15),  "New Class",
				"_-_ Added new class - Alchemist:\n" +
						"Starts with alchemist dagger and alchemist flask\n\n" +
						"Subclasses:\n\n" +
						"- _Elementalist_ heals himself while affected by fire or toxic gas. He's also immune to Paralysis and Vertigo\n\n" +
						"- _Scientist_ crafts 2 potions instead of 1. Effects of most potions are stronger."
				) );

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.AMULET, null), "Amulet changes",
				"_-_ Added Broken Amulet of Yendor\n" +
						"_-_ Added 4 new chapters of each element after Yog\n\n" +
						"_-_ In the end of each chapter, there will be new boss which will drop fragment of its element. You have to acquire all fragments to fix the Broken Amulet of Yendor"
				)
		);
	}

	public static void add_v0_1_2_Changes( ArrayList<ChangeInfo> changeInfos) {
		ChangeInfo changes = new ChangeInfo("v0.1.2a", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_LEATHER, null), "Elemental Infusion",
				"_-_ v0.1.2a adds Elemental Infusions!\n\n" +
				        "_-_ Goo, DM300 and YogDzewa now drop Elemental Stone which you can use to infuse your armor or weapon, you can infuse both with the same elements (fire, air, water, earth), but they have different effects. Infusion on armor has always defensive effect and infusion on weapon has always offensive effect."
				)
		);
	}

	public static void add_v0_1_2_1_Changes( ArrayList<ChangeInfo> changeInfos) {
		ChangeInfo changes = new ChangeInfo("v0.1.2.1a", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
				"_-_ Fixed many missing descriptions\n\n" +
						"_-_ Burning on the higher floors doesn't  cause crash anymore\n\n" +
						"_-_ Elemental stones don't get used if you use them just for info\n\n" +
						"_-_ Shop on 20th floor is finally working\n\n" +
						"_-_ Enemies can no longer heal in toxic gas if Elementalist\n"
				)
		);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ Tier 2 Alchemist talent Attuned meal has been finished\n\n" +
						"_-_ Cost of Alchemist's armor special ability has been changed form 0 to 35\n\n" +
						"_-_ Icon has been changed\n\n" +
						"_-_ Dwarf King on floor 50 doesn't drop armor kit anymore\n\n" +
						"_-_ Water chapter and Air chapter have been swapped\n\n" +
						"_-_ Grass has been completely removed from Water chapter\n\n" +
						"_-_ Changed Elemental Stone and Alchemist Dagger sprites\n\n" +
						"_-_ All heroes start with Dew Vial\n"

				));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ELIXIR_TOXIC, null), "Alchemist Flask",
				"Alchemist Flask has been nerfed:\n\n" +
						"_-_ Base damage has been lowered from 1-5 to 1-3\n" +
						"_-_ Alchemist Flask can miss\n" +
						"_-_ Special effects are more consistent:\n\n" +
						"_-_ 5% chance to Paralysis\n" +
						"_-_ 6% chance to Corrosion\n" +
						"_-_ 6% chance to Root\n" +
						"_-_ 8% chance to Poison\n" +
						"_-_ 10% chance to Cripple\n" +
						"_-_ 15% chance to Burning\n" +
						"_-_ 15% chance to Frost\n" +
						"_-_ 15% chance to Toxic Gas\n" +
						"_-_ 20% chance to no special effect"
		));

	}

	public static void add_v0_1_2_2_Changes( ArrayList<ChangeInfo> changeInfos ) {
		ChangeInfo changes = new ChangeInfo("v0.1.2.2a", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
						"_-_ Alchemist Flask no longer scales with your equipped weapon\n\n" +
								"_-_ Icon should look better on different devices"
				)
		);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
				"_-_ Magical Holster can store Elemental Stones\n\n" +
						"_-_ Testing item can be acquired only via debug mode\n\n" +
						"_-_ Minor changes to the Alchemist Flask\n\n" +
						"_-_ Completed T3 talents"

				)
		);

		changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ELEMENTAL_STONE, null), "Elemental Stones",
				"Elemental Stones can transfer upgrades! Movable are only upgrades gained via upgrading with Scroll of Upgrade\n"

		));
	}

	public static void add_v0_1_2_3_Changes( ArrayList<ChangeInfo> changeInfos ) {
		ChangeInfo changes = new ChangeInfo("v0.1.2.3a", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(new Image(Assets.Sprites.SPINNER, 144, 0, 16, 16), Messages.get(ChangesScene.class, "bugfixes"),
						"_-_ Fixed that Alchemist flask couldn't be augmented or enchanted\n\n" +
								"_-_ Fixed that fragments couldn't be saved.\n\n" +
								"_-_ Fixed that bosses after Yog occasionally didn't drop fragments\n"
				)
		);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
						"_-_ Added empty potion flasks which are necessary for crafting regular potions. Empty potion flasks can be acquired by drinking any potion, elixir or brew\n\n" +
								"_-_ Changed fragments' sprites\n\n" +
								"_-_ Added Infernal fire which inflicts infernal flame\n\n"

				)
		);

		changes.addButton( new ChangeButton(new Image(Assets.Sprites.WARRIOR, 0, 90, 12, 15),  "Reworked Berserker",
				"_-_ Berserker has been heavily reworked. He can no longer survive lethal damage and passive damage boost has been removed, although rage meter remains. Berserker can now manually activate berserking to gain shielding and damage boost, scaling with his amount of rage. After berserking ends, berserker has to wait until he can start building rage again.\n\n" +
						"_-_ Berserker's talents have been tweaked as a response to his rework\n\n"
		) );
	}

	public static void add_v0_1_2_4_Changes( ArrayList<ChangeInfo> changeInfos) {
		ChangeInfo changes = new ChangeInfo("v0.1.2.4a", false, "");
		changes.hardlight(Window.TITLE_COLOR);
		changeInfos.add(changes);

		changes.addButton(new ChangeButton(Icons.get(Icons.PREFS), Messages.get(ChangesScene.class, "misc"),
						"_-_ Changed names of chapters after final boss:\n\n_-_ Fire chapter is now Infernal Bastion\n_-_ Water chapter is now Endless lakes\n_-_ Air chapter is now Warden's Hideout\n_-_ Earth chapter is now The Last Stand\n\n"

				)
		);

		changes.addButton( new ChangeButton(new Image(Assets.Sprites.STATUE, 0, 0, 12, 15),  "Added new boss",
				"_-_ Boss in Infernal Bastion - Great Fire Demon has been added."
		) );


	}


}
