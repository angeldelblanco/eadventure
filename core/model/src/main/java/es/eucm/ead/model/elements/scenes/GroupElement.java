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
import es.eucm.ead.model.elements.extra.EAdList;
import es.eucm.ead.model.interfaces.Element;
import es.eucm.ead.model.interfaces.Param;

/**
 * Represents an scene element that is compound with other scene elements. This
 * element acts as container. Its state (position, scale, rotation...) affects
 * its contained elements
 * 
 * 
 */
@Element
public class GroupElement extends SceneElement {

	/**
	 * A variable defining if this container must update its width to the
	 * minimum width to contain all its components
	 */
	public static final String VAR_AUTO_SIZE_HORIZONTAL = "autoSizeH";

	/**
	 * A variable defining if this container must update its height to the
	 * minimum height to contain all its components
	 */
	public static final String VAR_AUTO_SIZE_VERTICAL = "autoSizeV";

	@Param
	protected EAdList<SceneElement> sceneElements;

	public GroupElement() {
		super();
		sceneElements = new EAdList<SceneElement>();
	}

	public GroupElement(SceneElementDef definition) {
		this();
		this.setDefinition(definition);
	}

	public GroupElement(EAdDrawable asset) {
		this(new SceneElementDef(asset));
	}

	/**
	 * Sets the bounds for this container. Values equal or less than zero are
	 * interpreted as infinitum
	 * 
	 * @param width
	 *            the width
	 */
	public void setBounds(int width, int height) {
		// XXX This is not working
		putProperty(VAR_AUTO_SIZE_HORIZONTAL, width <= 0);
		//putProperty(VAR_WIDTH, width);
		putProperty(VAR_AUTO_SIZE_VERTICAL, height <= 0);
		//putProperty(VAR_HEIGHT, height);

	}

	public EAdList<SceneElement> getSceneElements() {
		return sceneElements;
	}

	/**
	 * Adds an element to the group
	 * @param e
	 */
	public void addSceneElement(SceneElement e) {
		this.sceneElements.add(e);
	}

	public void setSceneElements(EAdList<SceneElement> sceneElements) {
		this.sceneElements = sceneElements;
	}

}
