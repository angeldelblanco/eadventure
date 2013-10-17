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

package es.eucm.ead.model.elements;

import es.eucm.ead.model.elements.extra.EAdList;
import es.eucm.ead.model.elements.extra.EAdMap;
import es.eucm.ead.model.interfaces.Element;
import es.eucm.ead.model.interfaces.Param;
import es.eucm.ead.model.interfaces.features.Identified;
import es.eucm.ead.model.params.variables.EAdVarDef;

/**
 * The eAdventure game model.
 */
@Element
public class BasicAdventureModel extends BasicElement implements
		EAdAdventureModel {

	public static final String EFFECTS_BINDS = "#effects_binds";
	public static final String EVENTS_BINDS = "#events_binds";
	public static final String SCENES_ELEMENT_BINDS = "#scene_element_binds";
	public static final String GAME_WIDTH = "#game_width";
	public static final String GAME_HEIGHT = "#game_height";
	public static final String GAME_TITLE = "#game_title";
	/**
	 * Not serialize (special treatment in writer)
	 */
	private EAdList<EAdChapter> chapters;

	/**
	 * Not serialize (special treatment in writer)
	 */
	private EAdChapter initialChapter;

	@Param
	private EAdMap<EAdVarDef<?>, Object> vars;

	@Param
	/**
	 * This events are launched after the game loads
	 */
	private EAdList<EAdEvent> events;

	@Param
	/**
	 * List with all the identified used in the game across chapters
	 */
	private EAdList<Identified> identified;

	/**
	 * Constructs a {@link BasicAdventureModel}.
	 */
	public BasicAdventureModel() {
		chapters = new EAdList<EAdChapter>();
		vars = new EAdMap<EAdVarDef<?>, Object>();
		events = new EAdList<EAdEvent>();
		identified = new EAdList<Identified>();
	}

	public EAdList<EAdChapter> getChapters() {
		return chapters;
	}

	@Override
	public EAdMap<EAdVarDef<?>, Object> getVars() {
		return vars;
	}

	@Override
	public <T> void setVarInitialValue(EAdVarDef<T> var, T value) {
		vars.put(var, value);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getVarInitialValue(EAdVarDef<T> var) {
		if (vars.containsKey(var)) {
			return (T) vars.get(var);
		}
		return var.getInitialValue();
	}

	public void setChapters(EAdList<EAdChapter> chapters) {
		this.chapters = chapters;
	}

	public void setVars(EAdMap<EAdVarDef<?>, Object> vars) {
		this.vars = vars;
	}

	@Override
	public void addChapter(EAdChapter chapter) {
		if (initialChapter == null) {
			this.initialChapter = chapter;
		}
		this.chapters.add(chapter);

	}

	@Override
	public EAdList<EAdEvent> getEvents() {
		return events;
	}

	public EAdChapter getInitialChapter() {
		return initialChapter;
	}

	public void setInitialChapter(EAdChapter initialChapter) {
		this.initialChapter = initialChapter;
	}

	public void addIdentified(Identified i) {
		this.identified.add(i);
	}

	public EAdList<Identified> getIdentified() {
		return identified;
	}

	public void setIdentified(EAdList<Identified> identified) {
		this.identified = identified;
	}

	public void setEvents(EAdList<EAdEvent> events) {
		this.events = events;
	}

}
