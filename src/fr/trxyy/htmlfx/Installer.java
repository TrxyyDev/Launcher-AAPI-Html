package fr.trxyy.htmlfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Installer extends Application {

	private final WebView webViewer = new WebView();

	public static void main(String[] args) {
		Application.launch(Installer.class);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		final VBox layout = new VBox();
		layout.getChildren().add(webViewer);
		primaryStage.setScene(new Scene(layout));
		primaryStage.setTitle("Lypsia Launcher - (HTML Edition)");
//		primaryStage.getIcons().add(new Image(""));
		primaryStage.setResizable(false);
		primaryStage.setWidth(1000);
		primaryStage.setHeight(620);
		webViewer.setContextMenuEnabled(false);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				webViewer.getEngine().load(ClassLoader.getSystemResource("index.html").toExternalForm());
			}
		});

		primaryStage.show();
	}

}
