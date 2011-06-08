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

package es.eucm.eadventure.common.model.test;

import junit.framework.TestCase;

import org.junit.Test;

import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.common.model.params.guievents.EAdMouseEvent;

public class EAdMouseEventTest extends TestCase {
	
	private EAdMouseEvent event1, event2, event3;
	
	@Override
	public void setUp() {
		event1 = EAdMouseEventImpl.getMouseEvent( EAdMouseEvent.MouseActionType.DOUBLE_CLICK );
		event2 = EAdMouseEventImpl.getMouseEvent( EAdMouseEvent.MouseActionType.DOUBLE_CLICK );
		event3 = EAdMouseEventImpl.getMouseEvent( EAdMouseEvent.MouseActionType.DRAG );
	}

	@Test
	public void testEqualsObject() {
		assertEquals( event1, event2 );
		assertTrue( ! event1.equals( event3 ) );
	}

}
