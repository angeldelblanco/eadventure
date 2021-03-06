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

package es.eucm.ead.engine.factories.mapproviders;

import es.eucm.ead.engine.assets.RuntimeAsset;
import es.eucm.ead.engine.assets.drawables.*;
import es.eucm.ead.engine.assets.drawables.shapes.GdxRectangleShape;
import es.eucm.ead.engine.assets.multimedia.RuntimeMusic;
import es.eucm.ead.engine.assets.multimedia.RuntimeSound;
import es.eucm.ead.model.assets.AssetDescriptor;
import es.eucm.ead.model.assets.drawable.basics.Caption;
import es.eucm.ead.model.assets.drawable.basics.Image;
import es.eucm.ead.model.assets.drawable.basics.NinePatchImage;
import es.eucm.ead.model.assets.drawable.basics.animation.FramesAnimation;
import es.eucm.ead.model.assets.drawable.basics.shapes.BezierShape;
import es.eucm.ead.model.assets.drawable.basics.shapes.CircleShape;
import es.eucm.ead.model.assets.drawable.basics.shapes.RectangleShape;
import es.eucm.ead.model.assets.drawable.compounds.ComposedDrawable;
import es.eucm.ead.model.assets.drawable.compounds.EAdStateDrawable;
import es.eucm.ead.model.assets.drawable.compounds.StateDrawable;
import es.eucm.ead.model.assets.drawable.filters.EAdFilteredDrawable;
import es.eucm.ead.model.assets.drawable.filters.FilteredDrawable;
import es.eucm.ead.model.assets.multimedia.Music;
import es.eucm.ead.model.assets.multimedia.Sound;
import es.eucm.ead.model.assets.text.BasicFont;
import es.eucm.ead.model.assets.text.EAdFont;
import es.eucm.ead.engine.assets.drawables.shapes.GdxBezierShape;
import es.eucm.ead.engine.assets.drawables.shapes.GdxCircleShape;
import es.eucm.ead.engine.assets.fonts.RuntimeFont;

public class AssetHandlerMap
		extends
		AbstractMapProvider<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<? extends AssetDescriptor>>> {

	public AssetHandlerMap() {
		factoryMap.put(Image.class, RuntimeImage.class);
		factoryMap.put(BezierShape.class, GdxBezierShape.class);
		factoryMap.put(RectangleShape.class, GdxRectangleShape.class);
		factoryMap.put(CircleShape.class, GdxCircleShape.class);
		factoryMap.put(Caption.class, RuntimeCaption.class);
		factoryMap.put(ComposedDrawable.class, RuntimeComposedDrawable.class);
		factoryMap.put(BasicFont.class, RuntimeFont.class);
		factoryMap.put(EAdFont.class, RuntimeFont.class);
		factoryMap.put(StateDrawable.class, RuntimeStateDrawable.class);
		factoryMap.put(EAdStateDrawable.class, RuntimeStateDrawable.class);
		factoryMap.put(FramesAnimation.class, RuntimeFramesAnimation.class);
		factoryMap
				.put(EAdFilteredDrawable.class, RuntimeFilteredDrawable.class);
		factoryMap.put(FilteredDrawable.class, RuntimeFilteredDrawable.class);
		factoryMap.put(Sound.class, RuntimeSound.class);
		factoryMap.put(Music.class, RuntimeMusic.class);
		factoryMap.put(NinePatchImage.class, RuntimeNinePatchImage.class);
	}

}
