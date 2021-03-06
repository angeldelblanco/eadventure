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

package es.eucm.ead.model.elements.scenes;

import es.eucm.ead.model.assets.drawable.EAdDrawable;
import es.eucm.ead.model.elements.trajectories.Trajectory;
import es.eucm.ead.model.interfaces.Element;
import es.eucm.ead.model.interfaces.Param;
import es.eucm.ead.model.params.util.Position.Corner;

/**
 * <p>
 * Default implementation of the {@link Scene} interface
 * </p>
 */
@Element
public class Scene extends GroupElement {

	public static final String VAR_TRAJECTORY_DEFINITION = "trajectory_generator";

	@Param
	protected SceneElement background;

	/**
	 * This property indicates if the game can return to this scene after a
	 * cutscene or similiar
	 */
	@Param
	protected boolean returnable;

	/**
	 * Default constructor. Creates an completely empty scene
	 */
	public Scene() {

	}

	/**
	 * Creates a scene with its backgrounds. Sets the position for the scene and the background to 0, 0 (corner top left)
	 *
	 * @param bg the background
	 */
	public Scene(SceneElement bg) {
		background = bg;
		background.setPosition(Corner.TOP_LEFT, 0, 0);
		returnable = true;
		setCenter(Corner.TOP_LEFT);
	}

	public Scene(EAdDrawable backgroundDrawable) {
		this(new SceneElement(backgroundDrawable));
	}

	/**
	 * @return the background element of the scene
	 */
	public SceneElement getBackground() {
		return background;
	}

	/**
	 * Sets the scene element representing the background
	 *
	 * @param background the scene element for the background
	 */
	public void setBackground(SceneElement background) {
		this.background = background;
	}

	/**
	 * @return true if the game can return to this scene after a cutscene or
	 *         similiar
	 */
	public boolean getReturnable() {
		return returnable;
	}

	/**
	 * Sets if the game can return to this scene after a cutscene or similar
	 *
	 * @param returnable if the game can return to this scene after a cutscene or
	 *                   similar
	 */
	public void setReturnable(boolean returnable) {
		this.returnable = returnable;
	}

	/**
	 * Sets trajectory for the scene. It's a shortcut for setting {@link Scene#VAR_TRAJECTORY_DEFINITION} property with the trajectory definition
	 *
	 * @param trajectoryDefinition the trajectory definition
	 */
	public void setTrajectoryDefinition(Trajectory trajectoryDefinition) {
		putProperty(VAR_TRAJECTORY_DEFINITION, trajectoryDefinition);
	}

	/**
	 * Adds an scene element to the scene
	 *
	 * @param element the element to be added
	 */
	public void add(SceneElement element) {
		this.getSceneElements().add(element);
	}

}
