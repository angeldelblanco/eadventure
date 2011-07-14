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

package es.eucm.eadventure.common.model.effects.impl;

import es.eucm.eadventure.common.interfaces.Element;
import es.eucm.eadventure.common.model.EAdMap;
import es.eucm.eadventure.common.model.effects.EAdEffect;
import es.eucm.eadventure.common.model.impl.EAdMapImpl;

/**
 * An effect that launches a random effect from a list of effects
 * 
 * 
 */
@Element(runtime = EAdRandomEffect.class, detailed = EAdRandomEffect.class)
public class EAdRandomEffect extends AbstractEAdEffect {

	/**
	 * Effect's list
	 */
	private EAdMap<EAdEffect, Float> effects;

	public EAdRandomEffect(String id) {
		super(id);
		effects = new EAdMapImpl<EAdEffect, Float>(id + "_randomEffectMap",
				EAdEffect.class, Float.class);
	}

	/**
	 * Adds an effect with a probability
	 * 
	 * @param effect
	 *            the effect
	 * @param probability
	 *            the probability
	 */
	public void addEffect(EAdEffect effect, float probability) {
		effects.put(effect, probability);
	}

	/**
	 * Returns the effects
	 * 
	 * @return the effects
	 */
	public EAdMap<EAdEffect, Float> getEffects() {
		return effects;
	}

}
