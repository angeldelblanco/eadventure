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

package ead.elementfactories.demos.normalguy;

import ead.common.model.elements.effects.ChangeSceneEf;
import ead.common.model.elements.effects.InterpolationEf;
import ead.common.model.elements.effects.enums.InterpolationLoopType;
import ead.common.model.elements.effects.enums.InterpolationType;
import ead.common.model.elements.events.SceneElementEv;
import ead.common.model.elements.events.enums.SceneElementEventType;
import ead.common.model.elements.guievents.EAdMouseEvent;
import ead.common.model.elements.scenes.SceneElementImpl;
import ead.common.resources.assets.drawable.basics.ImageImpl;
import ead.common.util.EAdPosition.Corner;
import ead.elementfactories.demos.scenes.EmptyScene;

public class NgMainScreen extends EmptyScene {

	public NgMainScreen() {
		setBackground(new SceneElementImpl( new ImageImpl(
				"@drawable/ng_mainscreen_bg.png")));
		getBackground().setId("background");
		SceneElementImpl spiral = new SceneElementImpl(
				new ImageImpl("@drawable/ng_spiral.png"));
		spiral.setId("spiral");
		spiral.setPosition(Corner.CENTER, 400, 300);
		getComponents().add(spiral);
		SceneElementImpl logo = new SceneElementImpl(
				new ImageImpl("@drawable/ng_logo.png"));
		logo.setId("spiral");
		logo.setPosition(Corner.CENTER, 400, 300);
		getComponents().add(logo);
		logo.setVarInitialValue(SceneElementImpl.VAR_SCALE, 0.0f);

		// Animations
		SceneElementEv e = new SceneElementEv();
		InterpolationEf rotate = new InterpolationEf(spiral,
				SceneElementImpl.VAR_ROTATION, 0, 2 * Math.PI, 50000, 0,
				InterpolationLoopType.RESTART, -1, InterpolationType.DESACCELERATE);
		e.addEffect(SceneElementEventType.ADDED_TO_SCENE, rotate);
		spiral.getEvents().add(e);
		
		e = new SceneElementEv();
		InterpolationEf bounce = new InterpolationEf(logo,
				SceneElementImpl.VAR_SCALE, 0.0f, 1.0f, 1000, 1000,
				InterpolationLoopType.NO_LOOP, 1, InterpolationType.LINEAR);
		e.addEffect(SceneElementEventType.ADDED_TO_SCENE, bounce);
		
		ChangeSceneEf changeScene = new ChangeSceneEf( );
		changeScene.setId("changeScene");
		changeScene.setNextScene(new NgRoom1());
		getBackground().addBehavior(EAdMouseEvent.MOUSE_LEFT_PRESSED, changeScene);
		
		logo.getEvents().add(e);
		

	}

	@Override
	public String getSceneDescription() {
		return "A game showing the eAdventure 2.0 features";
	}

	public String getDemoName() {
		return "Normal Guy";
	}

}