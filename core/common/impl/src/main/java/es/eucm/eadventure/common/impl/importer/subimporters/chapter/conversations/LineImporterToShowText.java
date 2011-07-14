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

package es.eucm.eadventure.common.impl.importer.subimporters.chapter.conversations;

import com.google.inject.Inject;

import es.eucm.eadventure.common.EAdElementImporter;
import es.eucm.eadventure.common.GenericImporter;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.model.effects.impl.text.EAdShowText;
import es.eucm.eadventure.common.model.elements.EAdCondition;
import es.eucm.eadventure.common.resources.assets.drawable.Caption;

public class LineImporterToShowText implements EAdElementImporter<ConversationLine, EAdShowText>{
	
	@Inject
	private EAdElementImporter<Conditions, EAdCondition> conditionsImporter;
	
	@Inject
	private GenericImporter<ConversationLine, Caption> captionImporter;

	public EAdShowText init(ConversationLine line) {
		return new EAdShowText();
	}
	
	@Override
	public EAdShowText convert(ConversationLine line, Object object) {
		EAdShowText effect = (EAdShowText) object;

		// Set conditions

		if (line.getConditions() != null) {
			EAdCondition condition = conditionsImporter.init(line
					.getConditions());
			condition = conditionsImporter.convert(line
					.getConditions(), condition);
			if (condition != null) {
				effect.setCondition(condition);
			}
		}
		
		Caption caption = captionImporter.init(line);		
		caption = captionImporter.convert(line, caption);		
		effect.setCaption(caption, 300, 300);
		return effect;
	}

}
