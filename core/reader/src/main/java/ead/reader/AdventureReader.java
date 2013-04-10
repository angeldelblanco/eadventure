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

package ead.reader;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import ead.common.model.assets.AssetDescriptor;
import ead.common.model.elements.EAdAdventureModel;
import ead.reader.model.XMLVisitor;
import ead.reader.model.XMLVisitor.VisitorListener;
import ead.reader.model.translators.MapClassTranslator;
import ead.tools.reflection.ReflectionProvider;
import ead.tools.xml.XMLDocument;
import ead.tools.xml.XMLNode;
import ead.tools.xml.XMLNodeList;
import ead.tools.xml.XMLParser;

@Singleton
public class AdventureReader implements VisitorListener {

	private XMLParser xmlParser;
	private XMLVisitor visitor;
	private EAdAdventureModel model;
	private static Logger logger = LoggerFactory.getLogger("AdventureReader");

	@Inject
	public AdventureReader(XMLParser parser,
			ReflectionProvider reflectionProvider) {
		this.xmlParser = parser;
		this.visitor = new XMLVisitor(reflectionProvider);
	}

	public EAdAdventureModel readXML(String xml) {
		readXML(xml, this);
		boolean done = false;
		while (!done) {
			done = visitor.step();
		}
		return model;
	}

	public void readXML(String xml, VisitorListener listener) {
		XMLDocument document = xmlParser.parse(xml);
		visitor.init();

		// Load classes keys
		Map<String, String> classes = new HashMap<String, String>();
		XMLNode node = document.getFirstChild();
		XMLNode adventure = null;
		XMLNode keyMap = null;

		// This loop is to avoid some weird node GWT adds to the root element
		// when there are errors
		XMLNodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			XMLNode n = list.item(i);
			if (n.getNodeName().equals("e")) {
				adventure = n;
			} else if (n.getNodeName().equals("classes")) {
				keyMap = n;
			}
		}

		if (keyMap == null) {
			logger.warn("No classes node found");
		} else {
			XMLNodeList entries = keyMap.getChildNodes();
			for (int i = 0; i < entries.getLength(); i++) {
				XMLNode n = entries.item(i);
				String className = n.getAttributeValue(DOMTags.VALUE_AT);
				classes.put(n.getAttributeValue(DOMTags.KEY_AT), className);
			}
			visitor.addTranslator(new MapClassTranslator(classes));
		}

		if (adventure == null) {
			logger.warn("No model node found");
		} else {
			visitor.loadElement(adventure, listener);
		}
	}

	public boolean step() {
		return visitor.step();
	}

	@Override
	public boolean loaded(XMLNode node, Object object, boolean isNullInOrigin) {
		this.model = (EAdAdventureModel) object;
		return true;
	}

	public Collection<AssetDescriptor> getAssets() {
		return visitor.getAssets();
	}

}
