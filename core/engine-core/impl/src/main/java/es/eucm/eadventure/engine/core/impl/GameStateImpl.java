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

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import es.eucm.eadventure.common.model.elements.EAdChapter;
import es.eucm.eadventure.common.model.elements.EAdEffect;
import es.eucm.eadventure.common.model.elements.scene.EAdScene;
import es.eucm.eadventure.common.model.elements.scene.EAdSceneElement;
import es.eucm.eadventure.common.model.elements.scenes.SceneElementDefImpl;
import es.eucm.eadventure.common.model.elements.scenes.SceneImpl;
import es.eucm.eadventure.common.model.elements.variables.EAdVarDef;
import es.eucm.eadventure.common.model.elements.variables.SystemFields;
import es.eucm.eadventure.engine.core.evaluators.EvaluatorFactory;
import es.eucm.eadventure.engine.core.game.GameState;
import es.eucm.eadventure.engine.core.game.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.factories.EffectGOFactory;
import es.eucm.eadventure.engine.core.gameobjects.factories.EventGOFactory;
import es.eucm.eadventure.engine.core.gameobjects.factories.SceneElementGOFactory;
import es.eucm.eadventure.engine.core.gameobjects.go.EffectGO;
import es.eucm.eadventure.engine.core.gameobjects.go.SceneGO;
import es.eucm.eadventure.engine.core.guiactions.GUIAction;
import es.eucm.eadventure.engine.core.plugins.PluginHandler;

@Singleton
public class GameStateImpl implements GameState {

	private SceneGO<?> scene;

	private List<EffectGO<?>> effects;

	private ValueMap valueMap;

	private SceneElementGOFactory sceneElementFactory;

	private EffectGOFactory effectFactory;

	private Stack<EAdScene> previousSceneStack;

	private EAdChapter currentChapter;

	/**
	 * Queue for effects added
	 */
	private List<EffectGO<?>> effectsQueue;

	private static Logger logger = Logger.getLogger("GameState");

	private boolean paused;

	private EAdScene loadingScreen;

	private EvaluatorFactory evaluatorFactory;

	private EventGOFactory eventGOFactory;

	private PluginHandler pluginHandler;

	@Inject
	public GameStateImpl(@Named("LoadingScreen") EAdScene loadingScreen,
			SceneElementGOFactory gameObjectFactory,
			EffectGOFactory effectFactory, ValueMap valueMap,
			EvaluatorFactory evaluatorFactory, EventGOFactory eventGOFactory,
			PluginHandler pluginHandler) {
		logger.log(Level.INFO, "New instance");
		effects = new ArrayList<EffectGO<?>>();
		effectsQueue = new ArrayList<EffectGO<?>>();
		this.loadingScreen = loadingScreen;
		this.valueMap = valueMap;
		this.sceneElementFactory = gameObjectFactory;
		this.previousSceneStack = new Stack<EAdScene>();
		this.evaluatorFactory = evaluatorFactory;
		this.effectFactory = effectFactory;
		this.pluginHandler = pluginHandler;
		this.eventGOFactory = eventGOFactory;
		// TODO improve
		installPlugins();
	}

	public SceneGO<?> getScene() {
		if (scene == null) {
			logger.log(Level.FINE, "null scene, Loading screen: "
					+ (loadingScreen != null));
			this.scene = (SceneGO<?>) sceneElementFactory.get(loadingScreen);
			previousSceneStack.push(loadingScreen);
		}
		return scene;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.GameState#setScene(es.eucm.eadventure.
	 * engine.core.gameobjects.SceneGO)
	 */
	@Override
	public void setScene(SceneGO<? extends EAdScene> newScene) {
		// Clean cache
		sceneElementFactory.clean();
		if (this.scene != null && this.scene.getElement() != null) {
			valueMap.setValue(scene.getElement(),
					SceneImpl.VAR_SCENE_LOADED, Boolean.FALSE);
			if (scene.getElement().getReturnable())
				previousSceneStack.push(scene.getElement());
		}
		this.scene = newScene;
		if (this.scene != null && this.scene.getElement() != null) {
			valueMap.setValue(scene.getElement(),
					SceneImpl.VAR_SCENE_LOADED, Boolean.TRUE);
			for (Entry<EAdVarDef<?>, Object> e : scene.getElement().getVars()
					.entrySet()) {
				valueMap.setValue(scene.getElement(), e.getKey(), e.getValue());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#getEffects()
	 */
	@Override
	public List<EffectGO<?>> getEffects() {
		return effects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#getValueMap()
	 */
	@Override
	public ValueMap getValueMap() {
		return valueMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * es.eucm.eadventure.engine.core.GameState#addEffect(es.eucm.eadventure
	 * .common.model.effects.EAdEffect)
	 */
	@Override
	public void addEffect(EAdEffect e, GUIAction action, EAdSceneElement parent) {
		addEffect(-1, e, action, parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.core.GameState#addEffect(int,
	 * es.eucm.eadventure.common.model.effects.EAdEffect)
	 */
	@Override
	// TODO consider leaving effect initialization for later
	public void addEffect(int pos, EAdEffect e, GUIAction action,
			EAdSceneElement parent) {
		if (e != null && evaluatorFactory.evaluate(e.getCondition())) {
			EffectGO<?> effectGO = effectFactory.get(e);
			effectGO.setGUIAction(action);
			effectGO.setParent(parent);
			effectGO.initilize();
			if (e.isQueueable())
				synchronized (effectsQueue) {
					pos = pos == -1 ? effectsQueue.size() : pos;
					effectsQueue.add(pos, effectGO);
				}
			else {
				effectGO.update();
				effectGO.finish();
			}
		}

	}

	@Override
	public EAdScene getPreviousScene() {
		return previousSceneStack.pop();
	}

	@Override
	public EAdChapter getCurrentChapter() {
		return currentChapter;
	}

	@Override
	public void setCurrentChapter(EAdChapter currentChapter) {
		this.currentChapter = currentChapter;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	@Override
	public void updateEffectsQueue() {
		synchronized (effectsQueue) {
			for (EffectGO<?> e : effectsQueue) {
				effects.add(e);
				logger.info("Added " + e);
			}
			effectsQueue.clear();
		}

	}

	@Override
	public void addEffect(EAdEffect e) {
		this.addEffect(e, null, null);
	}

	@Override
	public EAdSceneElement getActiveElement() {
		return valueMap.getValue(
				valueMap.getValue(SystemFields.ACTIVE_ELEMENT),
				SceneElementDefImpl.VAR_SCENE_ELEMENT);
	}

	@Override
	public void setActiveElement(EAdSceneElement activeElement) {
		valueMap.setValue(SystemFields.ACTIVE_ELEMENT, activeElement);
	}

	@Override
	public void setInitialScene(EAdScene initialScene) {
		((LoadingScreen) loadingScreen).setInitialScreen(initialScene);
	}

	private void installPlugins() {
		pluginHandler.install(effectFactory);
		pluginHandler.install(eventGOFactory);
		pluginHandler.install(sceneElementFactory);
	}

}
