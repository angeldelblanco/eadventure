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

package es.eucm.ead.reader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.eucm.ead.model.elements.AdventureGame;
import es.eucm.ead.model.elements.Chapter;
import es.eucm.ead.model.elements.extra.EAdList;
import es.eucm.ead.model.elements.extra.EAdMap;
import es.eucm.ead.model.elements.scenes.Scene;
import es.eucm.ead.reader.model.Manifest;
import es.eucm.ead.reader.model.ReaderVisitor;
import es.eucm.ead.reader.model.XMLFileNames;
import es.eucm.ead.reader.model.translators.MapClassTranslator;
import es.eucm.ead.tools.TextFileReader;
import es.eucm.ead.tools.reflection.ReflectionProvider;
import es.eucm.ead.tools.xml.XMLNode;
import es.eucm.ead.tools.xml.XMLParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AdventureReader {

	static private Logger logger = LoggerFactory
			.getLogger(AdventureReader.class);

	private TextFileReader reader;

	private XMLParser parser;

	private ReaderVisitor readerVisitor;

	private Object result;

	private String path;

	@Inject
	public AdventureReader(ReflectionProvider reflectionProvider,
			XMLParser parser, TextFileReader reader) {
		this.reader = reader;
		this.parser = parser;
		this.readerVisitor = new ReaderVisitor(reflectionProvider);
		this.path = "@";
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Manifest getManifest() {
		Manifest m = (Manifest) read(XMLFileNames.MANIFEST);
		readerVisitor
				.addClazzTranslator(new MapClassTranslator(m.getClasses()));
		readerVisitor
				.addFieldsTranslator(new MapClassTranslator(m.getFields()));
		readerVisitor
				.addParamsTranslator(new MapClassTranslator(m.getParams()));
		return m;
	}

	public Object read(String file) {
		String xml = reader.read(path + file);
		XMLNode node;

		try {
			node = parser.parse(xml);
			readerVisitor.loadElement(node,
					new ReaderVisitor.VisitorListener() {
						@Override
						public boolean loaded(XMLNode node, Object object,
								boolean isNullInOrigin) {
							result = object;
							return true;
						}
					});
			readerVisitor.finish();
		} catch (Exception e) {
			logger.error("{}", e);
		}
		return result;

	}

	/**
	 * Reads the full model
	 *
	 * @return the complete model of the game
	 */
	public AdventureGame readFullModel() {
		// Manifest
		Manifest manifest = getManifest();

		// Model
		AdventureGame model = manifest.getModel();
		int chapterIndex = 0;

		EAdMap<EAdList<String>> chaptersScenes = manifest.getChaptersScenes();

		for (String chapterId : manifest.getChapterIds()) {
			// Chapters
			Chapter c = (Chapter) readChapter(chapterId);
			model.addChapter(c);
			if (chapterId.equals(manifest.getInitialChapter())) {
				model.setInitialChapter(c);
			}

			// Scenes
			String initialSceneId = manifest.getInitialScenes().get(
					chapterIndex++);
			EAdList<String> chapterScenes = chaptersScenes.get(chapterId);
			for (String sceneId : chapterScenes) {
				Scene s = readScene(sceneId);
				c.addScene(s);
				if (sceneId.equals(initialSceneId)) {
					c.setInitialScene(s);
				}
			}
		}
		return model;
	}

	public Chapter readChapter(String chapterId) {
		return (Chapter) read(chapterId + "." + XMLFileNames.CHAPTER);
	}

	public Scene readScene(String sceneId) {
		return (Scene) read(sceneId + "." + XMLFileNames.SCENE);
	}

	public void clear() {
		readerVisitor.clear();
	}
}
