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

package es.eucm.eadventure.engine.core.platform.assets.impl;

import java.awt.geom.GeneralPath;

import es.eucm.eadventure.common.model.params.EAdPosition;

public class DesktopBezierShape extends RuntimeBezierShape {
	
	private GeneralPath path;

	@Override
	public boolean loadAsset() {
		super.loadAsset();
		path = new GeneralPath();
		
		EAdPosition p = descriptor.getPoints().get(0);
		path.moveTo(p.getX(), p.getY());
		
		int pointIndex = 1;
		EAdPosition p1, p2, p3;
		for ( Integer i: descriptor.getSegmentsLength() ){
				switch( i ){
				case 1:
					p1 = descriptor.getPoints().get(pointIndex++);
					path.lineTo(p1.getX(), p1.getY());
					break;
				case 2:
					p1 = descriptor.getPoints().get(pointIndex++);
					p2 = descriptor.getPoints().get(pointIndex++);
					path.quadTo(p1.getX(), p1.getY(), p2.getX(), p2.getY());
					break;
				case 3:
					p1 = descriptor.getPoints().get(pointIndex++);
					p2 = descriptor.getPoints().get(pointIndex++);
					p3 = descriptor.getPoints().get(pointIndex++);
					path.curveTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(), p3.getX(), p3.getY());
					break;			
			}
		}
		
		if ( descriptor.isClosed() )
			path.closePath();
		
		return true;
	}
	
	public GeneralPath getShape( ){
		return path;
	}

}
