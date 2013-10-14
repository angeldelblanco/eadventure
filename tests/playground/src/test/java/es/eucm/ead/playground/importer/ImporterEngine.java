package es.eucm.ead.playground.importer;

import es.eucm.ead.engine.desktop.DesktopGame;
import es.eucm.ead.importer.AdventureConverter;

public class ImporterEngine {

	public static String TEST = "/home/eva/eadventure/juegos/PrimerosAuxiliosGame";

	public static void main(String args[]) {
		AdventureConverter converter = new AdventureConverter();
		String convertedFolder = converter.convert(TEST, null);

		DesktopGame game = new DesktopGame();
		game.setPath(convertedFolder);
		game.start();
		game.load();
	}
}
