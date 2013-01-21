package ead.demos.elementfactories.scenes.scenes;

import ead.common.widgets.Label;
import ead.common.widgets.TextArea;
import ead.common.widgets.containers.ColumnContainer;

public class WidgetsScene extends EmptyScene {

	public WidgetsScene() {
		this.setId("WidgetsScene");
		ColumnContainer container = new ColumnContainer();
		for (int i = 0; i < 10; i++) {
			container.add(new Label("Label " + i));
		}
		container.add(new TextArea(200, 200));
		this.getSceneElements().add(container);
	}
}
