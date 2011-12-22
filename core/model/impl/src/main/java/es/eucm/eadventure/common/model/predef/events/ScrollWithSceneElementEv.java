package es.eucm.eadventure.common.model.predef.events;

import es.eucm.eadventure.common.model.EAdElement;
import es.eucm.eadventure.common.model.elements.effects.variables.ChangeFieldEf;
import es.eucm.eadventure.common.model.elements.events.SceneElementEv;
import es.eucm.eadventure.common.model.elements.events.enums.SceneElementEventType;
import es.eucm.eadventure.common.model.elements.scenes.SceneElementImpl;
import es.eucm.eadventure.common.model.elements.variables.EAdField;
import es.eucm.eadventure.common.model.elements.variables.EAdOperation;
import es.eucm.eadventure.common.model.elements.variables.FieldImpl;
import es.eucm.eadventure.common.model.elements.variables.SystemFields;
import es.eucm.eadventure.common.model.elements.variables.operations.MathOp;

public class ScrollWithSceneElementEv extends SceneElementEv {

	public ScrollWithSceneElementEv(EAdElement scene, EAdElement character) {
		this.setId("scrollWidthElement");
		EAdField<Integer> xElement = new FieldImpl<Integer>(character,
				SceneElementImpl.VAR_X);
		EAdField<Integer> yElement = new FieldImpl<Integer>(character,
				SceneElementImpl.VAR_Y);
		EAdField<Integer> xScene = new FieldImpl<Integer>(scene,
				SceneElementImpl.VAR_X);
		EAdField<Integer> yScene = new FieldImpl<Integer>(scene,
				SceneElementImpl.VAR_Y);
		EAdField<Integer> widthScene = new FieldImpl<Integer>(scene,
				SceneElementImpl.VAR_WIDTH);
		EAdField<Integer> heightScene = new FieldImpl<Integer>(scene,
				SceneElementImpl.VAR_HEIGHT);
		
		
		// [0] = x-element
		// [1] = width
		// [2] = scene-width
		String expression = " -( ([1] - [2]) min ( 0 max ([0] - ([2] / 2 )) ))";
		EAdOperation opX = new MathOp( expression,  xElement, widthScene, SystemFields.GAME_WIDTH  );
		EAdOperation opY = new MathOp( expression,  yElement, heightScene, SystemFields.GAME_HEIGHT );
		
		ChangeFieldEf effectX = new ChangeFieldEf( );
		effectX.addField(xScene);
		effectX.setOperation(opX);
		ChangeFieldEf effectY = new ChangeFieldEf( );
		effectY.addField(yScene);
		effectY.setOperation(opY);
		
		addEffect(SceneElementEventType.ALWAYS, effectX);
		addEffect(SceneElementEventType.ALWAYS, effectY);

	}

}
