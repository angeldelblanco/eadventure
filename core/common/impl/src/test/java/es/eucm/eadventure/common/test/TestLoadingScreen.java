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

package es.eucm.eadventure.common.test;

import com.google.inject.Inject;

import es.eucm.eadventure.common.model.EAdAdventureModel;
import es.eucm.eadventure.common.resources.EAdString;
import es.eucm.eadventure.common.resources.StringHandler;

public class TestLoadingScreen extends BasicGameElementTestScreen {

	@Inject
	public TestLoadingScreen(StringHandler stringHandler, EAdAdventureModel adventure) {
		super("LoadingScreen", adventure.getChapters().get(0));
		stringHandler.addString(new EAdString("question"), "�Qu� pregunta te podr�a hacer?");
		stringHandler.addString(new EAdString("answer1"), "Que como estoy, a lo mejor");
		stringHandler.addString(new EAdString("answer2"), "No s�, lo que veas.");
		stringHandler.addString(new EAdString("answer3"), "A lo mejor algo relacionado con el mundo e las preguntas.");
		stringHandler.addString(new EAdString("stringName"), "Start game");
		stringHandler.addString(new EAdString("panielName"), "Paniel");
		stringHandler.addString(new EAdString("panielDescription"), "Es Paniel. Parece ser que le gusta hacer el moonwalker todo el rato. #f");
		stringHandler.addString(new EAdString("handAction"), "�Ch�cala!");
		stringHandler.addString(new EAdString("orientedName"), "Oriented");
		stringHandler.addString(new EAdString("stringId"), "Esto es un string no ya largo, sino largu�iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiisimo s�lo para probar si todo ese c�digo que hay por ah� para e incluso le pongo variables como mi nombre que es #nombre e incluso un n�mero que es #n cortar las l�neas funciona m�s o menos bien, que estar�a bien, vamos, que tampoco...");
	}

}
