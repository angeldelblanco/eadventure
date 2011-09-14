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

package es.eucm.eadventure.engine.core.platform.impl.specialassetrenderers;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.inject.Inject;

import es.eucm.eadventure.common.resources.assets.multimedia.Video;
import es.eucm.eadventure.engine.core.platform.AssetHandler;
import es.eucm.eadventure.engine.core.platform.SpecialAssetRenderer;
import es.eucm.eadventure.engine.core.platform.impl.specialassetrenderers.extra.VLCMediaPlayerEventListener;
import es.eucm.eadventure.engine.core.platform.impl.specialassetrenderers.extra.WinRegistry;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 * <p>Video renderer for desktop (and applets) using vlcj library {@link http://code.google.com/p/vlcj/}</p>
 * 
 *
 */
public class VLCDesktopVideoRenderer implements SpecialAssetRenderer<Video, Component> {
	
	/**
	 * Logger
	 */
	private static Logger logger = Logger.getLogger("VLCDesktopVideoRenderer");

	/**
	 * The vlcj media player (controls, etc.)
	 */
	private EmbeddedMediaPlayer mediaPlayer;

	/**
	 * The vlcj surface for the video
	 */
	private CanvasVideoSurface videoSurface;
	
	/**
	 * True if finished
	 */
	protected boolean finished;
	
	/**
	 * True if started
	 */
	protected boolean started;

	/**
	 * The vlcj media player factory
	 */
	private static MediaPlayerFactory mediaPlayerFactory;
	
	/**
	 * Used to configure vlc when necessary
	 */
	private static String vlcOptions = "";
	
	/**
	 * The path of the video file
	 */
	private String path;
	
	/**
	 * The eAd asset handler
	 */
	private AssetHandler assetHandler;

	@Inject
	public VLCDesktopVideoRenderer(AssetHandler assetHandler) {
		this.assetHandler = assetHandler;
		initilizeVariables();
	}
	
	@Override
	public Component getComponent(Video asset) {
		if (mediaPlayerFactory == null) {
			String[] options = {vlcOptions};
			mediaPlayerFactory = new MediaPlayerFactory (options);
		}
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.black);
		started = false;
		
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);
		
		mediaPlayer.addMediaPlayerEventListener(new VLCMediaPlayerEventListener(this));
		
		path = asset.getURI().getPath();
		if (assetHandler != null)
			path = assetHandler.getAbsolutePath(asset.getURI().getPath());

		finished = false;

		return canvas;
	}
	
	/**
	 * Initialize system and system dependent variables for vlcj.
	 */
	private void initilizeVariables() {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("win")) {
			String temp = null;
			try {
				temp = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE,
							"Software\\VideoLAN\\VLC", "InstallDir");
				if ( temp == null )
					temp = WinRegistry.readString(WinRegistry.HKEY_LOCAL_MACHINE,
							"Software\\Wow6432Node\\VideoLAN\\VLC", "InstallDir");
				System.out.println(temp);
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
			
			if (temp == null) {
				// not exists, extract
					
			}
			String pathLibvlc = temp;
			String pathPlugins = temp + "\\plugins";
			System.setProperty("jna.library.path", pathLibvlc);
			System.setProperty("VLC_PLUGIN_PATH", pathPlugins);
			vlcOptions = "--no-video-title-show";
		} else if (os.contains("mac")) {
			String temp = "/Applications/VLC.app";
			if (!new File("/Applications/VLC.app/").exists()) {
				// not exists, extract
				//temp = ....;
			} else
				logger.log(Level.INFO, "VLC installed");
			String pathLibvlc = temp + "/Contents/MacOS/lib/";
			String pathPlugins = temp + "/Contents/MacOS/plugins/";
			System.setProperty("jna.library.path", pathLibvlc);
			System.setProperty("VLC_PLUGIN_PATH", pathPlugins);
			vlcOptions = "--vout=macosx";
		} else {
			logger.log(Level.SEVERE, "OS not supported by VLC video plugin");
		}
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public boolean start() {
		if (!started && mediaPlayer != null) {
			String [] mediaOptions ={};
			mediaPlayer.playMedia(path, mediaOptions);
			started = true;
			return true;
		}
		return false;
	}

	/**
	 * Set the finished flag
	 * 
	 * @param b The new value for finished
	 */
	public void setFinished(boolean b) {
		this.finished = b;
	}

	/**
	 * Set the started flag
	 * 
	 * @param b The new value for started
	 */
	public void setStarted(boolean b) {
		this.started = b;
	}
	
}
