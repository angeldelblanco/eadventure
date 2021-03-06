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

package es.eucm.ead.editor.model.nodes.asset;

import es.eucm.ead.editor.model.EditorModel;
import es.eucm.ead.editor.model.EditorModelImpl;
import es.eucm.ead.editor.model.nodes.DependencyNode;
import es.eucm.ead.editor.model.nodes.EditorNode;

import java.util.ArrayList;

/**
 * Synthetic node for i18n-susceptible game strings.
 *
 * @author mfreire
 */
public class AssetsNode extends EditorNode {

	private ArrayList<AssetNode> assetNodes = new ArrayList<AssetNode>();

	public AssetsNode(int id) {
		super(id);
	}

	public ArrayList<AssetNode> getNodes(EditorModel m) {
		assetNodes.clear();
		for (DependencyNode n : ((EditorModelImpl) m).getNodesById().values()) {
			if (n instanceof AssetNode) {
				((AssetNode) n).setBase(m.getLoader().getSaveDir());
				assetNodes.add((AssetNode) n);
			}
		}
		return assetNodes;
	}

	@Override
	public String getTextualDescription(EditorModel m) {
		StringBuilder sb = new StringBuilder();
		for (DependencyNode n : getNodes(m)) {
			sb.append(n.getTextualDescription(m));
		}
		return sb.toString();
	}
}
