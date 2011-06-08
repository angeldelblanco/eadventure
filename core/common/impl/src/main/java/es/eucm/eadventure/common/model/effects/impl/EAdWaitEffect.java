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

import es.eucm.eadventure.common.Element;
import es.eucm.eadventure.common.Param;

/**
 * 
 * Effect that produces a delay in the game. It waits the specified time until
 * disappear
 * 
 */
@Element( runtime = EAdWaitEffect.class, detailed = EAdWaitEffect.class)
public class EAdWaitEffect extends AbstractEAdEffect {

	/**
	 * Time to wait for this effect, in milliseconds
	 */
	@Param("time")
	private int time;

	/**
	 * Constructs a blocking and opaque wait effect with time = 0
	 * 
	 * @param parent
	 *            Element's parent
	 * @param id
	 *            Element's id
	 */
	public EAdWaitEffect(String id) {
		this(id, 0);
	}

	/**
	 * Constructs a blocking and opaque wait effect with the given time
	 * 
	 * @param parent
	 *            Element's parent
	 * @param id
	 *            Element's id
	 * @param time
	 *            the time for this effect
	 */
	public EAdWaitEffect(String id, int time) {
		super(id);
		this.time = time;
		this.setBlocking(true);
		this.setOpaque(true);
	}

	/**
	 * Sets the time to wait for this effect
	 * 
	 * @param time
	 *            the time (in milliseconds)
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * 
	 * @return the time to wait for this effect
	 */
	public int getTime() {
		return time;
	}

}
