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

package es.eucm.eadventure.common.model.elements;

import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.EAdElementList;
import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.params.guievents.EAdGUIEvent;
import es.eucm.eadventure.common.resources.EAdString;

/**
 * Actor in the eAdventure model. Actors are reusable elements, placed in
 * eAdventure scene.
 */
public interface EAdActor extends EAdElement {

	/**
	 * Returns actor's name
	 * 
	 * @return actor's name
	 */
	EAdString getName();

	/**
	 * Returns the effects list associated with the given GUI event,
	 * {@code null} if there is no effects associated. This method shouldn't be
	 * used to add new effects to the actor. Returned list could be {@code null}
	 * 
	 * @param event
	 *            the GUI event
	 * @return the effects list associated with the given event
	 */
	EAdElementList<EAdEffect> getEffects(EAdGUIEvent event);

}
