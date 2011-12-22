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

package es.eucm.eadventure.common.impl.importer.subimporters.effects;

import com.google.inject.Inject;

import es.eucm.eadventure.common.EAdElementImporter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadventure.common.impl.importer.interfaces.EAdElementFactory;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.model.elements.conditions.EmptyCond;
import es.eucm.eadventure.common.model.elements.effects.EffectsMacro;
import es.eucm.eadventure.common.model.elements.effects.TriggerMacroEf;

public class TriggerMacroImporter extends
		EffectImporter<MacroReferenceEffect, TriggerMacroEf> {

	private EAdElementFactory factory;

	@Inject
	public TriggerMacroImporter(
			EAdElementImporter<Conditions, EAdCondition> conditionImporter,
			EAdElementFactory factory) {
		super(conditionImporter);
		this.factory = factory;
	}

	@Override
	public TriggerMacroEf init(MacroReferenceEffect oldObject) {
		TriggerMacroEf effect = new TriggerMacroEf();
		effect.setId("triggerMacro" + oldObject.getTargetId());
		return effect;
	}

	@Override
	public TriggerMacroEf convert(MacroReferenceEffect oldObject, Object object) {
		TriggerMacroEf effect = super.convert(oldObject, object);
		effect.putMacro(
				(EffectsMacro) factory.getElementById(oldObject.getTargetId()),
				EmptyCond.TRUE_EMPTY_CONDITION);
		return effect;
	}

}
