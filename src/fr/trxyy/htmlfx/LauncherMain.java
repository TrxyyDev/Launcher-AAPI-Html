package fr.trxyy.htmlfx;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.GameFolder;
import fr.trxyy.alternative.alternative_api.GameLinks;
import fr.trxyy.alternative.alternative_api.GameStyle;
import fr.trxyy.alternative.alternative_api.GameVersion;
import fr.trxyy.alternative.alternative_api.LauncherPreferences;
import fr.trxyy.alternative.alternative_api_ui.LauncherBackground;
import fr.trxyy.alternative.alternative_api_ui.LauncherPane;
import fr.trxyy.alternative.alternative_api_ui.base.AlternativeBase;
import fr.trxyy.alternative.alternative_api_ui.base.LauncherBase;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LauncherMain extends AlternativeBase {
	private GameFolder gameFolder = new GameFolder("customlauncher");
	private LauncherPreferences launcherPreferences = new LauncherPreferences("Launcher Template AlternativeAPI", 950, 600, true);
	private GameEngine gameEngine = new GameEngine(gameFolder, launcherPreferences, GameVersion.V_1_16_1, GameStyle.VANILLA);
	private GameLinks gameLinks = new GameLinks("http://127.0.0.1/alternative/1_16_1/", "1.16.1.json");

	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		this.gameEngine.reg(primaryStage);
		this.gameEngine.reg(gameLinks);
		LauncherBase launcher = new LauncherBase(primaryStage, scene, StageStyle.TRANSPARENT, gameEngine);
		launcher.setIconImage(primaryStage, getResourceLocation().loadImage(gameEngine, "favicon.png"));
	}

	private Parent createContent() {
		LauncherPane contentPane = new LauncherPane(gameEngine);
		Rectangle rect = new Rectangle(gameEngine.getLauncherPreferences().getWidth(), gameEngine.getLauncherPreferences().getHeight());
		rect.setArcHeight(10.0);
		rect.setArcWidth(10.0);
		contentPane.setClip(rect);
		contentPane.setStyle("-fx-background-color: transparent;");
		new LauncherHTML(gameEngine, contentPane);
		new LauncherPanel(contentPane, gameEngine);
		return contentPane;
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
