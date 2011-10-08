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

package es.eucm.eadventure.engine.core.evaluators.impl;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import es.eucm.eadventure.common.model.conditions.impl.OperationCondition;
import es.eucm.eadventure.engine.core.evaluators.Evaluator;
import es.eucm.eadventure.engine.core.operator.OperatorFactory;

@Singleton
public class OperationConditionEvaluator implements
		Evaluator<OperationCondition> {

	private static final Logger logger = Logger
			.getLogger("VarConditionEvaluator");

	private OperatorFactory operatorFactory;

	@Inject
	public OperationConditionEvaluator(OperatorFactory operatorFactory) {
		this.operatorFactory = operatorFactory;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean evaluate(OperationCondition condition) {
		Object value1 = operatorFactory.operate(Object.class,
				condition.getOp1());
		Object value2 = operatorFactory.operate(Object.class,
				condition.getOp2());

		if (value1 instanceof Comparable) {
			int result = ((Comparable) value1).compareTo(value2);
			switch (condition.getOperator()) {
			case EQUAL:
				return result == 0;
			case GREATER:
				return result > 0;
			case GREATER_EQUAL:
				return result >= 0;
			case LESS_EQUAL:
				return result <= 0;
			case LESS:
				return result < 0;
			case DIFFERENT:
				return result != 0;
			default:
				return false;
			}
		} else {
			boolean equals = value1.equals(value2);
			switch (condition.getOperator()) {
			case EQUAL:
			case GREATER_EQUAL:
			case LESS_EQUAL:
				return equals;
			case DIFFERENT:
				return !equals;
			default:
				logger.info("Attempeted to compare " + value1 + " & " + value2
						+ " with an invalid operator. false was returned.");
			}
		}

		return false;

	}

}