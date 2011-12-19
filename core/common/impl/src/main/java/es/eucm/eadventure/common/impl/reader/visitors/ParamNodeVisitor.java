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

package es.eucm.eadventure.common.impl.reader.visitors;

import java.lang.reflect.Field;
import java.util.logging.Level;

import org.w3c.dom.Node;

import es.eucm.eadventure.common.DOMTags;
import es.eucm.eadventure.common.impl.reader.extra.ObjectFactory;

public class ParamNodeVisitor extends NodeVisitor<Object> {

	@Override
	public Object visit(Node node, Field field, Object parent, Class<?> listClass) {
		if (node.getTextContent() != null) {
			try {
				Class<?> c = listClass;
				if (c == null || node.getAttributes().getNamedItem(DOMTags.CLASS_AT) != null) {
					String clazz = node.getAttributes().getNamedItem(DOMTags.CLASS_AT).getNodeValue();
					clazz = translateClass(clazz);
					c = ClassLoader.getSystemClassLoader().loadClass(clazz);
				} 
				
				Object object = null;

				String value = node.getTextContent();
				if ( ObjectFactory.getParamsMap().containsKey(value)){
					object = ObjectFactory.getParamsMap().get(value);
					logger.info(value + " of value " + object.toString() + " and type "  + object.getClass() + " was compressed." );
				}
				else {
					object = ObjectFactory.getObject(value, c);
					if (node.getAttributes().getNamedItem(DOMTags.UNIQUE_ID_AT) != null) 
						ObjectFactory.getParamsMap().put(node.getAttributes().getNamedItem(DOMTags.UNIQUE_ID_AT).getNodeValue(), object);
				}
				 

				setValue(field, parent, object);
				
				return object;
			} catch (ClassNotFoundException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			} catch (IllegalArgumentException e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			} 
		}
		return null;
	}


	
	@Override
	public String getNodeType() {
		return DOMTags.PARAM_AT;
	}

}
