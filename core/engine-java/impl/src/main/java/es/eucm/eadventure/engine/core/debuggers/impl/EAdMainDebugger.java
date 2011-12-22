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

package es.eucm.eadventure.engine.core.debuggers.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import es.eucm.eadventure.engine.core.debuggers.Debugger;
import es.eucm.eadventure.engine.core.gameobjects.go.DrawableGO;

@Singleton
public class EAdMainDebugger implements Debugger {

	private static List<Class<? extends Debugger>> debuggersClass = new ArrayList<Class<? extends Debugger>>();

	public static void addDebugger(Class<? extends Debugger> debugger) {
		debuggersClass.add(debugger);
	}

	private List<Debugger> debuggers;

	private List<DrawableGO<?>> gameObjects;

	@Inject
	public EAdMainDebugger(Injector injector) {
		debuggers = new ArrayList<Debugger>();
		for (Class<? extends Debugger> c : debuggersClass) {
			debuggers.add(injector.getInstance(c));
		}

		gameObjects = new ArrayList<DrawableGO<?>>();
	}

	@Override
	public List<DrawableGO<?>> getGameObjects() {
		gameObjects.clear();
		for (Debugger d : debuggers) {
			gameObjects.addAll(d.getGameObjects());
		}
		return gameObjects;
	}

}
