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

package com.elementalpixel.elementalpixeldungeon.windows;


import com.elementalpixel.elementalpixeldungeon.Dungeon;
import com.elementalpixel.elementalpixeldungeon.actors.buffs.Combo;
import com.elementalpixel.elementalpixeldungeon.items.Item;
import com.elementalpixel.elementalpixeldungeon.messages.Messages;
import com.elementalpixel.elementalpixeldungeon.scenes.PixelScene;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSprite;
import com.elementalpixel.elementalpixeldungeon.sprites.ItemSpriteSheet;
import com.elementalpixel.elementalpixeldungeon.ui.RedButton;
import com.elementalpixel.elementalpixeldungeon.ui.RenderedTextBlock;
import com.elementalpixel.elementalpixeldungeon.ui.Window;
import com.watabou.noosa.Image;

public class WndCombo extends Window {

	private static final int WIDTH_P = 120;
	private static final int WIDTH_L = 160;

	private static final int MARGIN  = 2;

	public WndCombo( Combo combo ){
		super();

		int width = PixelScene.landscape() ? WIDTH_L : WIDTH_P;

		float pos = MARGIN;
		RenderedTextBlock title = PixelScene.renderTextBlock(Messages.titleCase(Messages.get(this, "title")), 9);
		title.hardlight(TITLE_COLOR);
		title.setPos((width-title.width())/2, pos);
		title.maxWidth(width - MARGIN * 2);
		add(title);

		pos = title.bottom() + 3*MARGIN;

		Image icon;
		if (Dungeon.hero.belongings.weapon != null){
			icon = new ItemSprite(Dungeon.hero.belongings.weapon.image, null);
		} else {
			icon = new ItemSprite(new Item(){ {image = ItemSpriteSheet.WEAPON_HOLDER; }});
		}

		for (Combo.ComboMove move : Combo.ComboMove.values()) {
			Image ic = new Image(icon);

			RedButton moveBtn = new RedButton(move.desc(), 6){
				@Override
				protected void onClick() {
					super.onClick();
					hide();
					combo.useMove(move);
				}
			};
			ic.tint(move.tintColor);
			moveBtn.icon(ic);
			moveBtn.multiline = true;
			moveBtn.setSize(width, moveBtn.reqHeight());
			moveBtn.setRect(0, pos, width, moveBtn.reqHeight());
			moveBtn.enable(combo.canUseMove(move));
			add(moveBtn);
			pos = moveBtn.bottom() + MARGIN;
		}

		resize(width, (int)pos);

	}


}
