package es.eucm.eadventure.engine.core.platform.impl.extra;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import es.eucm.eadventure.common.resources.StringHandler;
import es.eucm.eadventure.common.resources.assets.AssetDescriptor;
import es.eucm.eadventure.common.resources.assets.drawable.BundledDrawable;
import es.eucm.eadventure.common.resources.assets.drawable.Caption;
import es.eucm.eadventure.common.resources.assets.drawable.ComposedDrawable;
import es.eucm.eadventure.common.resources.assets.drawable.DisplacedDrawable;
import es.eucm.eadventure.common.resources.assets.drawable.Image;
import es.eucm.eadventure.common.resources.assets.drawable.SpriteImage;
import es.eucm.eadventure.common.resources.assets.drawable.animation.impl.BundledDrawableImpl;
import es.eucm.eadventure.common.resources.assets.drawable.animation.impl.Frame;
import es.eucm.eadventure.common.resources.assets.drawable.animation.impl.FramesAnimation;
import es.eucm.eadventure.common.resources.assets.drawable.impl.CaptionImpl;
import es.eucm.eadventure.common.resources.assets.drawable.impl.ComposedDrawableImpl;
import es.eucm.eadventure.common.resources.assets.drawable.impl.DisplacedDrawableImpl;
import es.eucm.eadventure.common.resources.assets.drawable.impl.ImageImpl;
import es.eucm.eadventure.common.resources.assets.drawable.impl.IrregularShape;
import es.eucm.eadventure.common.resources.assets.drawable.impl.RectangleShape;
import es.eucm.eadventure.common.resources.assets.drawable.impl.SpriteImageImpl;
import es.eucm.eadventure.common.resources.impl.DefaultStringHandler;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.RuntimeAsset;
import es.eucm.eadventure.engine.core.platform.assets.impl.DesktopEngineCaption;
import es.eucm.eadventure.engine.core.platform.assets.impl.DesktopEngineImage;
import es.eucm.eadventure.engine.core.platform.assets.impl.DesktopEngineIrregularShape;
import es.eucm.eadventure.engine.core.platform.assets.impl.DesktopEngineSpriteImage;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeBundledAnimation;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeComposedDrawable;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeDisplacedDrawable;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeFramesAnimation;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeRectangleShape;
import es.eucm.eadventure.engine.core.platform.assets.impl.RuntimeSpriteImage;
import es.eucm.eadventure.engine.core.platform.impl.DesktopAssetHandler;

public class DesktopAssetHandlerModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StringHandler.class).to(DefaultStringHandler.class);
		bind(AssetHandler.class).to(DesktopAssetHandler.class);
		
		Map<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<?>>> map = new HashMap<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<?>>>( );

		bind( new TypeLiteral<Map<Class<? extends AssetDescriptor>, Class<? extends RuntimeAsset<?>>>>( ) {} ).toInstance( map );

		map.put(FramesAnimation.class, RuntimeFramesAnimation.class);
		map.put(ImageImpl.class, DesktopEngineImage.class);
		map.put(Image.class, DesktopEngineImage.class);
		map.put(Frame.class, DesktopEngineImage.class);
		map.put(Caption.class, DesktopEngineCaption.class);
		map.put(CaptionImpl.class, DesktopEngineCaption.class);
		map.put(ComposedDrawable.class, RuntimeComposedDrawable.class);
		map.put(ComposedDrawableImpl.class, RuntimeComposedDrawable.class);
		map.put(RectangleShape.class, RuntimeRectangleShape.class);
		map.put(IrregularShape.class, DesktopEngineIrregularShape.class);
		map.put(BundledDrawableImpl.class, RuntimeBundledAnimation.class);
		map.put(BundledDrawable.class, RuntimeBundledAnimation.class);
		map.put(DisplacedDrawable.class, RuntimeDisplacedDrawable.class);
		map.put(DisplacedDrawableImpl.class, RuntimeDisplacedDrawable.class);
		map.put(SpriteImage.class, RuntimeSpriteImage.class);
		map.put(SpriteImageImpl.class, DesktopEngineSpriteImage.class);
	}

	
	
}
