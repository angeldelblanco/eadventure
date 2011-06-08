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

package es.eucm.eadventure.engine.core.platform.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.params.EAdFont;
import es.eucm.eadventure.common.model.params.EAdRectangle;
import es.eucm.eadventure.engine.core.platform.FontCache;
import es.eucm.eadventure.engine.core.platform.RuntimeFont;

public abstract class FontCacheImpl implements FontCache {

	protected Logger logger = LoggerFactory.getLogger(FontCacheImpl.class);

	protected Map<EAdFont, RuntimeFont> fontCache;

	@Inject
	public FontCacheImpl() {
		logger.debug("New instance");
		fontCache = new HashMap<EAdFont, RuntimeFont>();
	}

	/**
	 * Puts a runtime font in the cache
	 * 
	 * @param font
	 *            {@link EAdFont}
	 * @param rFont
	 *            {@link RuntimeFont} associated to the given {@link EAdFont}
	 */
	public void put(EAdFont font, RuntimeFont rFont) {
		fontCache.put(font, rFont);
	}

	/**
	 * Returns {@link RuntimeFont} associated to the given {@link EAdFont}
	 * 
	 * @param font
	 *            the {@link EAdFont}
	 * @return {@link RuntimeFont} associated to the given {@link EAdFont}
	 */
	public RuntimeFont get(EAdFont font) {
		if ( !fontCache.containsKey(font) ){
			this.addEAdFont(font);
		}
		return fontCache.get(font);
	}

	/**
	 * Returns the string width with the given font in the current context, -1
	 * if font is not present in the cache
	 * 
	 * @param string
	 *            String to be measured
	 * @param font
	 *            Font used in string measurement
	 * @return the string width with the given font in the current context, -1
	 *         if font is not present in the cache
	 */
	public int stringWidth(String string, EAdFont font) {
		if (fontCache.containsKey(font))
			return fontCache.get(font).stringWidth(string);
		else
			return -1;
	}

	/**
	 * Returns one line's height with the given font, -1 if font is not present
	 * in the cache
	 * 
	 * @param font
	 *            Font used in string measurement
	 * @return one line's height with the given font, -1 if font is not present
	 *         in the cache
	 */
	public int lineHeight(EAdFont font) {
		if (fontCache.containsKey(font))
			return fontCache.get(font).lineHeight();
		else
			return -1;
	}

	/**
	 * Returns the string bounds with the given {@link EAdFont}, <b>null</b> if
	 * font is not present in the cache
	 * 
	 * @param string
	 *            string to be measured
	 * @return the string bounds, <b>null</b> if font is not present in the
	 *         cache
	 */
	public EAdRectangle stringBounds(String string, EAdFont font) {
		if (fontCache.containsKey(font))
			return fontCache.get(font).stringBounds(string);
		else
			return null;
	}

	/**
	 * Adds a new {@link RuntimeFont} to cache based on the given
	 * {@link EAdFont}
	 * 
	 * @param font
	 *            given {@link EAdFont}
	 */
	public abstract void addEAdFont(EAdFont font);

}
