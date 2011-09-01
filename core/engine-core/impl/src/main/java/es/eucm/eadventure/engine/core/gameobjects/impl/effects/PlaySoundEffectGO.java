package es.eucm.eadventure.engine.core.gameobjects.impl.effects;

import com.google.inject.Inject;

import es.eucm.eadventure.common.StringsReader;
import es.eucm.eadventure.common.model.effects.impl.EAdPlaySoundEffect;
import es.eucm.eadventure.engine.core.GameState;
import es.eucm.eadventure.engine.core.ValueMap;
import es.eucm.eadventure.engine.core.gameobjects.GameObjectFactory;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeSound;

public class PlaySoundEffectGO extends AbstractEffectGO<EAdPlaySoundEffect> {

	@Inject
	public PlaySoundEffectGO(AssetHandler assetHandler,
			StringsReader stringHandler, GameObjectFactory gameObjectFactory,
			GUI gui, GameState gameState, ValueMap valueMap,
			PlatformConfiguration platformConfiguration) {
		super(assetHandler, stringHandler, gameObjectFactory, gui, gameState,
				valueMap, platformConfiguration);
	}

	@Override
	public void initilize() {
		super.initilize();
		RuntimeSound sound = (RuntimeSound) assetHandler
				.getRuntimeAsset(element.getSound());
		sound.loadAsset();
		sound.play();
	}

	@Override
	public boolean isVisualEffect() {
		return false;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

}
