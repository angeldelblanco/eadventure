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

package es.eucm.eadventure.engine.core;

import java.util.Map;

import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.variables.EAdField;
import es.eucm.eadventure.common.model.variables.EAdOperation;
import es.eucm.eadventure.common.model.variables.EAdVarDef;
import es.eucm.eadventure.engine.core.operator.OperatorFactory;

/**
 * The value map interfaces allows for the definition of key-value pairs of
 * different classes.
 */
public interface ValueMap {

	/**
	 * Sets the operator factory used for this value amp
	 * 
	 * @param operatorFactory
	 *            the operator factory
	 */
	void setOperatorFactory(OperatorFactory operatorFactory);

	// Sets

	/**
	 * Sets the field to given value
	 * 
	 * @param field
	 *            the field
	 * @param value
	 *            the value to the field
	 */
	void setValue(EAdField<?> field, Object value);

	void setValue(EAdElement element, EAdVarDef<?> varDef, Object value);

	/**
	 * Sets the variable to the result value of the operation
	 * 
	 * @param var
	 * @param operation
	 */
	void setValue(EAdField<?> var, EAdOperation operation);

	/**
	 * Sets the variable value for the given element
	 * 
	 * @param element
	 *            the element holding the variable. If the element is
	 *            {@code null}, it's considered that the variable belongs to the
	 *            system
	 * @param var
	 *            the variable definition
	 * @param operation
	 *            the operation whose result will be assigned to the variable
	 */
	void setValue(EAdElement element, EAdVarDef<?> var, EAdOperation operation);
	
	// Gets

	/**
	 * Returns the value of the field
	 * 
	 * @param <S>
	 * @param field
	 *            the field to be consulted
	 * @return the value of the field
	 */
	<S> S getValue(EAdField<S> field);

	/**
	 * Returns the value of the variable in the given element
	 * 
	 * @param element
	 *            the element. If the element is {@code null}, is considered as
	 *            a system variable
	 * @param varDef
	 *            the variable definition to be consulted
	 * @return the variable's value
	 */
	<S> S getValue(EAdElement element, EAdVarDef<S> varDef);
	
	/**
	 * Returns the variables associated to an element, whose values are
	 * different from the defaults
	 * 
	 * @param element
	 *            the element
	 * @return a map with the variables
	 */
	Map<EAdVarDef<?>, Object> getElementVars(EAdElement element);

	/**
	 * Removes all fields associated to the given element
	 * 
	 * @param element
	 *            the element
	 */
	void remove(EAdElement element);

}
