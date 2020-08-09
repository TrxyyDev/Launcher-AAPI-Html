package fr.trxyy.htmlfx;

import fr.trxyy.alternative.alternative_api.GameEngine;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class LauncherHTML {

	final WebView webView = new WebView();
	final WebEngine webEngine = webView.getEngine();

	public LauncherHTML(GameEngine engine, Pane root) {
		webView.setContextMenuEnabled(false);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				webView.getEngine().load(ClassLoader.getSystemResource("index.html").toExternalForm());
			}
		});

		webView.setPrefSize(engine.getLauncherPreferences().getWidth(), engine.getLauncherPreferences().getHeight());
		root.getChildren().add(webView);
	}

}
