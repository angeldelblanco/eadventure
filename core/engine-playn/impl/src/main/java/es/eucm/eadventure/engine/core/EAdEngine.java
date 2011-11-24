package es.eucm.eadventure.engine.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;
import static playn.core.PlayN.pointer;

import java.util.logging.Logger;

import playn.core.Canvas;
import playn.core.CanvasLayer;
import playn.core.Graphics;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;
import playn.core.PlayN;
import playn.core.Pointer;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.inject.Inject;

import es.eucm.eadventure.common.model.guievents.EAdMouseEvent;
import es.eucm.eadventure.common.model.guievents.enums.MouseActionType;
import es.eucm.eadventure.common.model.guievents.enums.MouseButton;
import es.eucm.eadventure.common.model.guievents.impl.EAdMouseEventImpl;
import es.eucm.eadventure.engine.core.guiactions.impl.MouseActionImpl;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.GUI;
import es.eucm.eadventure.engine.core.platform.PlatformConfiguration;
import es.eucm.eadventure.engine.core.platform.impl.PlayNAssetHandler;
import es.eucm.eadventure.engine.core.platform.impl.PlayNGUI;

public class EAdEngine implements playn.core.Game, Keyboard.Listener {

	private Canvas gameLayer;

	private float touchVectorX, touchVectorY;

	private Game game;

	private GUI gui;

	private AssetHandler assetHandler;

	private MouseState mouseState;
	
	private PlatformConfiguration platformConfiguration;

	private static final Logger logger = Logger.getLogger("EAdEngine");

	@Inject
	public EAdEngine(Game game, GUI gui, AssetHandler assetHandler,
			MouseState mouseState, PlatformConfiguration platformConfiguration) {
		this.game = game;
		this.gui = gui;
		this.assetHandler = assetHandler;
		this.mouseState = mouseState;
		this.platformConfiguration = platformConfiguration;
		((PlayNAssetHandler) assetHandler).setEngine(this);
	}

	@Override
	public void init() {
		graphics().setSize(platformConfiguration.getWidth(), platformConfiguration.getHeight());
		PlayN.log().debug("EAdEngine: init");

		/*
		 * gameLayer = graphics().createSurfaceLayer(graphics().width(),
		 * graphics().height()); graphics().rootLayer().add(gameLayer);
		 */

		CanvasLayer layer = graphics().createCanvasLayer(graphics().width(),
				graphics().height());
		graphics().rootLayer().add(layer);

		gameLayer = layer.canvas();
		gameLayer.setStrokeWidth(2);
		gameLayer.setStrokeColor(0xffff0000);
		gameLayer.strokeRect(1, 1, 46, 46);

		((PlayNGUI) gui).initializeCanvas(gameLayer, layer);

		keyboard().setListener(this);
		pointer().setListener(new Pointer.Listener() {
			@Override
			public void onPointerEnd(Pointer.Event event) {
				touchVectorX = touchVectorY = 0;
			}

			@Override
			public void onPointerDrag(Pointer.Event event) {
				touchMove(event.x(), event.y());
			}

			@Override
			public void onPointerStart(Pointer.Event event) {
				touchMove(event.x(), event.y());
			}
		});

		com.google.gwt.user.client.Event
				.addNativePreviewHandler(new NativePreviewHandler() {
					public void onPreviewNativeEvent(
							final NativePreviewEvent event) {
						int eventType = event.getTypeInt();
						int eventX = event.getNativeEvent().getClientX();
						int eventY = event.getNativeEvent().getClientY();
						MouseButton b = getMouseButton(event.getNativeEvent()
								.getButton());
						// TODO double click
						mouseState.setMousePosition(eventX, eventY);
						EAdMouseEvent e = null;
						switch (eventType) {
						case com.google.gwt.user.client.Event.ONCLICK:
							e = EAdMouseEventImpl.getMouseEvent(
									MouseActionType.CLICK, b);
							break;
						case com.google.gwt.user.client.Event.ONMOUSEDOWN:
							mouseState.setMousePressed(true);
							e = EAdMouseEventImpl.getMouseEvent(
									MouseActionType.PRESSED, b);
							break;
						case com.google.gwt.user.client.Event.ONMOUSEUP:
							mouseState.setMousePressed(false);
							e = EAdMouseEventImpl.getMouseEvent(
									MouseActionType.RELEASED, b);
							break;
						default:
							// not interested in other events
						}
						if (e != null)
							mouseState.getMouseEvents().add(
									new MouseActionImpl(e, eventX, eventY));
					}
				});
	}

	private MouseButton getMouseButton(int b) {
		switch (b) {
		case NativeEvent.BUTTON_LEFT:
			return MouseButton.BUTTON_1;
		case NativeEvent.BUTTON_MIDDLE:
			return MouseButton.BUTTON_2;
		case NativeEvent.BUTTON_RIGHT:
			return MouseButton.BUTTON_3;
		default:
			return MouseButton.NO_BUTTON;
		}
	}

	@Override
	public void onKeyDown(Event event) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onKeyTyped(Keyboard.TypedEvent event) {
		//TODO
	}

	@Override
	public void onKeyUp(Event event) {
		// TODO Auto-generated method stub

	}

	int updateCont = 0;

	int completeUpdate = 0;

	@Override
	public void update(float delta) {
		if (updateCont % 60 == 0) {
			PlayN.log().debug(
					"EAdEngine: update " + (updateCont - completeUpdate));
		}
		updateCont++;
		game.update();
		completeUpdate++;

	}

	private void touchMove(float x, float y) {
		float cx = graphics().screenWidth() / 2;
		float cy = graphics().screenHeight() / 2;

		touchVectorX = (x - cx) * 1.0f / cx;
		touchVectorY = (y - cy) * 1.0f / cy;
	}

	@Override
	public void paint(float alpha) {
		game.render(alpha);
	}

	@Override
	public int updateRate() {
		return 67;
	}

	public Graphics getGraphics() {
		return graphics();
	}
	

}
