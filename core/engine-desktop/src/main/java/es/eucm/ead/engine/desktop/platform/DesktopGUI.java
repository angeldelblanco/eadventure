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

package es.eucm.ead.engine.desktop.platform;

import com.badlogic.gdx.Gdx;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import es.eucm.ead.engine.factories.SceneElementFactory;
import es.eucm.ead.engine.game.GUIImpl;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.nio.IntBuffer;

@Singleton
public class DesktopGUI extends GUIImpl {

	static private Logger logger = LoggerFactory.getLogger(DesktopGUI.class);

	private JFrame frame;

	private Canvas canvas;

	private Component component;

	@Inject
	public DesktopGUI(SceneElementFactory sceneElementFactory) {
		super(sceneElementFactory);
	}

	@Override
	public void reset() {
		super.reset();
		// Set transparent mouse
		Gdx.app.postRunnable(new Runnable() {

			@Override
			public void run() {
				try {
					Mouse.setNativeCursor(new Cursor(16, 16, 0, 0, 1,
							getCursor(), null));
				} catch (LWJGLException e) {
					logger.error("Error hiding cursor", e);
				}
			}
		});
	}

	static public void doInEDTNow(Runnable doRun) {
		if (javax.swing.SwingUtilities.isEventDispatchThread()) {
			doRun.run();
		} else {
			try {
				javax.swing.SwingUtilities.invokeAndWait(doRun);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (InvocationTargetException e) {
				// FIXME: showExceptionDialog(e);
				e.printStackTrace(System.err);
			}
		}
	}

	@Override
	public void showSpecialResource(Object object, int x, int y,
			boolean fullscreen) {
		if (object == component) {

		} else if (object == null) {
			doInEDTNow(new Runnable() {
				@Override
				public void run() {
					canvas.setCursor(java.awt.Cursor.getDefaultCursor());
					canvas.setVisible(true);
					if (component != null) {
						component.setVisible(false);
						frame.remove((Component) component);
					}
					frame.validate();
					component = null;
				}
			});
		} else if (object != component) {
			component = (Component) object;
			doInEDTNow(new Runnable() {
				@Override
				public void run() {
					canvas.setVisible(false);
					canvas.setCursor(null);

					frame.getContentPane().setFocusable(true);
					component.setVisible(true);
					component.invalidate();
					frame.add((Component) component);
					frame.validate();

				}
			});
		}
	}

	private IntBuffer getCursor() {
		BufferedImage biCursor = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		int[] data = biCursor.getRaster().getPixels(0, 0, 16, 16, (int[]) null);

		IntBuffer ib = BufferUtils.createIntBuffer(16 * 16);
		for (int i = 0; i < data.length; i += 4) {
			ib.put(data[i] | data[i + 1] << 8 | data[i + 2] << 16
					| data[i + 3] << 24);
		}
		ib.flip();
		return ib;
	}

	/**
	 * Returns the JFrame holding the engine
	 *
	 * @return
	 */
	public JFrame getFrame() {
		return frame;
	}

	public Canvas createCanvas(int width, int height, boolean exitOnClose) {
		create(width, height, exitOnClose);
		return canvas;
	}

	private void create(int width, int height, boolean exitOnClose) {
		frame = new JFrame();

		// Sets a null cursor (so the in-game one is used)
		frame.setCursor(frame.getToolkit().createCustomCursor(
				new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB),
				new Point(0, 0), "null"));
		canvas = new Canvas();
		canvas.setSize(width, height);
		frame.add(canvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				Gdx.app.exit();

			}
		});
		if (!exitOnClose) {
			frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		}

		// Frame needs to be visible so Gdx can create the right context
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
