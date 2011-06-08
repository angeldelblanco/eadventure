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

package es.eucm.eadventure.common.impl.importer.subimporters;

import com.google.inject.Inject;

import es.eucm.eadventure.common.Importer;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.impl.importer.interfaces.EAdElementFactory;
import es.eucm.eadventure.common.model.EAdAdventureModel;
import es.eucm.eadventure.common.model.EAdChapter;
import es.eucm.eadventure.common.model.impl.EAdAdventureModelImpl;
import es.eucm.eadventure.common.resources.EAdString;
import es.eucm.eadventure.common.resources.StringHandler;

/**
 * An importer for old adventures
 * 
 * 
 */
public class AdventureImporter implements Importer<AdventureData, EAdAdventureModel> {

	private Importer<Chapter, EAdChapter> chapterImporter;

	private StringHandler stringHandler;
	
	private EAdElementFactory factory;
	
	@Inject
	public AdventureImporter(Importer<Chapter, EAdChapter> chapterImporter,
			StringHandler stringHandler, EAdElementFactory factory) {
		this.chapterImporter = chapterImporter;
		this.stringHandler = stringHandler;
		this.factory = factory;
	}
	
	@Override
	public EAdAdventureModel convert( AdventureData oldData ) {
		factory.setOldDataModel(oldData);
		EAdAdventureModelImpl model = new EAdAdventureModelImpl( );

		model.setPlayerMode( getPlayerMode( oldData ) );
		
		model.setTitle(new EAdString(stringHandler.getUniqueId()));
		stringHandler.addString(model.getTitle(), oldData.getTitle( ));

		model.setDescription(new EAdString(stringHandler.getUniqueId()));
		stringHandler.addString(model.getDescription(), oldData.getDescription());

		for ( Chapter oldChapter : oldData.getChapters( ) ) {
			EAdChapter newChapter = chapterImporter.convert( oldChapter );

			if ( newChapter != null ) {
				model.getChapters( ).add( newChapter );
			}
		}
		return model;
	}

	@Override
	public boolean equals( AdventureData oldModel, EAdAdventureModel newModel ) {
		if ( !( newModel.getPlayerMode( ).equals( getPlayerMode( oldModel ) ) ) )
			return false;

		if ( !( stringHandler.getString( newModel.getTitle() ).equals( oldModel.getTitle( ) ) ) ) {
			return false;
		}

		if ( !( stringHandler.getString( newModel.getDescription() ).equals( oldModel.getDescription( ) ) ) ) {
			return false;
		}

		int i = 0;
		for ( EAdChapter newChapter : newModel.getChapters( ) ) {
			if ( !( newChapter.equals( oldModel.getChapters( ).get( i ) ) ) ) {
				return false;
			}
			i++;
		}

		return true;
	}

	private EAdAdventureModel.PlayerMode getPlayerMode( AdventureData oldData ) {
		switch ( oldData.getPlayerMode( ) ) {
		case AdventureData.MODE_PLAYER_1STPERSON:
			return EAdAdventureModel.PlayerMode.FIRST_PERSON;
		case AdventureData.MODE_PLAYER_3RDPERSON:
			return EAdAdventureModel.PlayerMode.THIRD_PERSON;
		}
		return null;
	}

}
