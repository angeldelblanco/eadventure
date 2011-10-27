/**
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the
 *    <e-UCM> research group.
 *
 *    Copyright 2005-2010 <e-UCM> research group.
 *
 *    You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *
 *    <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *
 * ****************************************************************************
 *
 *  This file is part of eAdventure, version 2.0
 *
 *      eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with eAdventure.  If not, see <http://www.gnu.org/licenses/>.
 */

package es.eucm.eadventure.common.resources.assets.drawable.basics.impl;

import es.eucm.eadventure.common.interfaces.Param;
import es.eucm.eadventure.common.model.extra.EAdList;
import es.eucm.eadventure.common.model.extra.impl.EAdListImpl;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.params.EAdFont;
import es.eucm.eadventure.common.params.EAdFontImpl;
import es.eucm.eadventure.common.params.EAdString;
import es.eucm.eadventure.common.params.fills.impl.EAdColor;
import es.eucm.eadventure.common.params.fills.impl.EAdPaintImpl;
import es.eucm.eadventure.common.params.paint.EAdPaint;
import es.eucm.eadventure.common.resources.assets.drawable.basics.Caption;

public class CaptionImpl implements Caption {

	/**
	 * Default padding for bubbles
	 */
	private static final int DEFAULT_PADDING = 30;

	@Param("label")
	private final EAdString label;

	@Param("font")
	private EAdFont font;

	@Param("textColor")
	private EAdPaint textPaint;

	@Param("hasBubble")
	private boolean hasBubble;

	@Param("bubbleColor")
	private EAdPaint bubblePaint;

	@Param("preferredWidth")
	private int preferredWidth;

	@Param("preferredHeight")
	private int preferredHeight;

	@Param("padding")
	private int padding;

	@Param("fields")
	private EAdList<EAdField<?>> fields;

	public CaptionImpl() {
		this(EAdString.newEAdString("label"));
	}

	public CaptionImpl(EAdString label) {
		this.label = label;
		textPaint = new EAdPaintImpl(EAdColor.BLACK, EAdColor.WHITE);
		bubblePaint = new EAdPaintImpl(EAdColor.WHITE, EAdColor.BLACK);
		this.font = EAdFontImpl.BIG;
		this.hasBubble = false;
		this.bubblePaint = null;
		preferredHeight = AUTO_SIZE;
		preferredWidth = AUTO_SIZE;
		padding = DEFAULT_PADDING;
		fields = new EAdListImpl<EAdField<?>>(EAdField.class);
	}

	@Override
	public EAdString getText() {
		return label;
	}

	@Override
	public EAdFont getFont() {
		return font;
	}

	public void setFont(EAdFont font) {
		this.font = font;
	}

	@Override
	public EAdPaint getTextPaint() {
		return textPaint;
	}

	public void setTextPaint(EAdPaint textColor) {
		this.textPaint = textColor;
	}

	@Override
	public boolean hasBubble() {
		return hasBubble;
	}

	@Override
	public EAdPaint getBubblePaint() {
		return bubblePaint;
	}

	public void setBubblePaint(EAdPaint bubbleColor) {
		this.bubblePaint = bubbleColor;
		setHasBubble(bubbleColor != null);
	}

	@Override
	public int getPreferredWidth() {
		return preferredWidth;
	}

	@Override
	public int getPreferredHeight() {
		return preferredHeight;
	}

	/**
	 * Sets preferred width for this text. Could be a positive number or
	 * {@link Caption#AUTO_SIZE} or {@link Caption#SCREEN_SIZE}
	 * 
	 * @param maxWidth
	 *            the width
	 * 
	 */
	public void setPreferredWidth(int maxWidth) {
		this.preferredWidth = maxWidth;
	}

	/**
	 * Sets preferred height for this text. Could be a positive number or
	 * {@link Caption#AUTO_SIZE} or {@link Caption#SCREEN_SIZE}
	 * 
	 * @param maxHeight
	 *            the height
	 */
	public void setPreferredHeight(int maxHeight) {
		this.preferredHeight = maxHeight;
	}

	public void setHasBubble(boolean b) {
		this.hasBubble = b;
	}

	@Override
	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	@Override
	public EAdList<EAdField<?>> getFields() {
		return fields;
	}

}
