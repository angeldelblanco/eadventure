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

package ead.engine.core.game;

import ead.common.model.elements.EAdAdventureModel;
import ead.common.model.elements.EAdChapter;

/**
 * Main game interface. Include the methods to update the game state, render the
 * current game state, evaluate conditions according to the current game state,
 * etc.
 */
public interface Game {

	/**
	 * Container width
	 */
	public static final String WIDTH = "width";

	/**
	 * Container height
	 */
	public static final String HEIGHT = "height";

	/**
	 * Game is fullscreen
	 */
	public static final String FULLSCREEN = "fullscreen";

	/**
	 * Sets if System.exit must be called when the game is closed
	 */
	public static final String EXIT_WHEN_CLOSE = "exit_when_close";

	/**
	 * Initialize the whole engine, loading default properties, string, creating
	 * the GUI...
	 */
	void initialize();

	/**
	 * This method is called once the underlying context is created, and runs in
	 * the game thread
	 */
	void setUp();

	/**
	 * Updates the game state
	 */
	void update();

	/**
	 * Renders the game to the screen.
	 * 
	 */
	void render();

	/**
	 * Returns the current adventure game model ({@link EAdAdventureModel})
	 * 
	 * @return The adventure game model
	 */
	EAdAdventureModel getAdventureModel();

	/**
	 * Returns the current chapter
	 * 
	 * @return
	 */
	EAdChapter getCurrentChapter();

	/**
	 * Loads the given eAdventure model
	 * 
	 * @param model
	 */
	void loadGame(EAdAdventureModel model);

	/**
	 * Disposes all the resources allocated by the engine and destroys the GUI
	 */
	void dispose();

}
