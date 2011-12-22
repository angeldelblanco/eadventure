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

package es.eucm.eadventure.engine.core.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.eucm.eadventure.common.model.elements.guievents.enums.MouseButton;
import es.eucm.eadventure.common.model.elements.scene.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.scene.EAdSceneElementDef;
import es.eucm.eadventure.common.model.elements.scenes.SceneElementImpl;
import es.eucm.eadventure.common.model.elements.variables.SystemFields;
import es.eucm.eadventure.engine.core.game.GameState;
import es.eucm.eadventure.engine.core.gameobjects.go.DrawableGO;
import es.eucm.eadventure.engine.core.gameobjects.go.SceneElementGO;
import es.eucm.eadventure.engine.core.guiactions.MouseAction;
import es.eucm.eadventure.engine.core.input.MouseState;

@Singleton
public class MouseStateImpl implements MouseState {

	/**
	 * Constant representing the mouse out of the window
	 */
	public static final int OUT_VAL = -1;

	/**
	 * Z used in drag
	 */
	public static final int DRAG_Z = Integer.MAX_VALUE;

	private GameState gameState;

	// Mouse position

	/**
	 * X coordinate of the windows' pixel the mouse is in
	 */
	private int mousePixelX = OUT_VAL;

	/**
	 * Y coordinate of the windows' pixel the mouse is in
	 */
	private int mousePixelY = OUT_VAL;

	// Mouse pressed

	/**
	 * Whether the mouse is pressed
	 */
	private boolean mousePressed = false;

	/**
	 * Which button is pressed on the mouse
	 */
	private MouseButton buttonPressed = null;

	/**
	 * Queue with mouse events
	 */
	private Queue<MouseAction> mouseEvents;

	/**
	 * Game object under the mouse
	 */
	private DrawableGO<?> gameObjectUnderMouse;

	// Drag & Drop attributes

	/**
	 * Game object being dragged
	 */
	private SceneElementGO<?> draggingGameObject;

	/**
	 * X virtual coordinate where the drag was started
	 */
	private int initDragX;

	/**
	 * Y virtual coordinate where the drag was started
	 */
	private int initDragY;

	private int diffX;

	private int diffY;

	/**
	 * Z of the scene element being dragged
	 */
	private int initZ;

	@Inject
	public MouseStateImpl(GameState gameState) {
		mouseEvents = new ConcurrentLinkedQueue<MouseAction>();
		this.gameState = gameState;
	}

	public int getMouseX() {
		return mousePixelX;
	}

	public int getMouseY() {
		return mousePixelY;
	}

	public void setMousePosition(int mouseX, int mouseY) {
		this.mousePixelX = mouseX;
		this.mousePixelY = mouseY;
		updateDrag();
	}

	private void updateDrag() {
		if (this.draggingGameObject != null) {
			EAdSceneElement e = draggingGameObject.getElement();
			int mouseVirtualX = gameState.getValueMap().getValue(SystemFields.MOUSE_SCENE_X);
			int mouseVirtualY = gameState.getValueMap().getValue(SystemFields.MOUSE_SCENE_Y);
			gameState.getValueMap().setValue(e, SceneElementImpl.VAR_X,
					mouseVirtualX - diffX);
			gameState.getValueMap().setValue(e, SceneElementImpl.VAR_Y,
					mouseVirtualY - diffY);
		}
	}

	public boolean isMousePressed(MouseButton button) {
		return mousePressed && button == this.buttonPressed;
	}

	public void setMousePressed(boolean mousePressed, MouseButton button) {
		this.mousePressed = mousePressed;
		this.buttonPressed = button;
	}

	public Queue<MouseAction> getMouseEvents() {
		return mouseEvents;
	}

	public DrawableGO<?> getGameObjectUnderMouse() {
		return gameObjectUnderMouse;
	}

	public void setGameObjectUnderMouse(DrawableGO<?> gameObjectUnderMouse) {
		this.gameObjectUnderMouse = gameObjectUnderMouse;
	}

	public boolean isInside() {
		if (mousePixelX == OUT_VAL && mousePixelY == OUT_VAL)
			return false;
		return true;
	}

	@Override
	public EAdSceneElementDef getDraggingElement() {
		if (draggingGameObject != null)
			return draggingGameObject.getElement().getDefinition();
		else
			return null;
	}

	@Override
	public int getMouseScaledX() {
		return gameState.getValueMap().getValue(SystemFields.MOUSE_X);
	}

	@Override
	public int getMouseScaledY() {
		return gameState.getValueMap().getValue(SystemFields.MOUSE_Y);
	}

	public SceneElementGO<?> getDraggingGameObject() {
		return draggingGameObject;
	}

	@Override
	public void setDraggingGameObject(SceneElementGO<?> dragElementGO) {

		if (dragElementGO != null) {
			draggingGameObject = dragElementGO;
			EAdSceneElement sceneElement = dragElementGO.getElement();

			initDragX = gameState.getValueMap().getValue(sceneElement,
					SceneElementImpl.VAR_X);
			initDragY = gameState.getValueMap().getValue(sceneElement,
					SceneElementImpl.VAR_Y);

			int mouseVirtualX = gameState.getValueMap().getValue(SystemFields.MOUSE_SCENE_X);
			int mouseVirtualY = gameState.getValueMap().getValue(SystemFields.MOUSE_SCENE_Y);
			diffX = mouseVirtualX - initDragX;
			diffY = mouseVirtualY - initDragY;

			initZ = gameState.getValueMap().getValue(sceneElement,
					SceneElementImpl.VAR_Z);

			gameState.getValueMap().setValue(sceneElement,
					SceneElementImpl.VAR_Z, DRAG_Z);

		} else {
			if (draggingGameObject != null) {
				EAdSceneElement sceneElement = draggingGameObject.getElement();
				gameState.getValueMap().setValue(sceneElement,
						SceneElementImpl.VAR_Z, initZ);
				if (gameState.getValueMap().getValue(sceneElement,
						SceneElementImpl.VAR_RETURN_WHEN_DRAGGED)) {
					gameState.getValueMap().setValue(sceneElement,
							SceneElementImpl.VAR_X, initDragX);
					gameState.getValueMap().setValue(sceneElement,
							SceneElementImpl.VAR_Y, initDragY);
				}
			}
			draggingGameObject = null;
		}
	}

}
