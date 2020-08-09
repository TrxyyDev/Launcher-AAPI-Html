package fr.trxyy.htmlfx;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import fr.trxyy.alternative.alternative_api.GameEngine;
import fr.trxyy.alternative.alternative_api.account.AccountType;
import fr.trxyy.alternative.alternative_api.auth.GameAuth;
import fr.trxyy.alternative.alternative_api.updater.GameUpdater;
import fr.trxyy.alternative.alternative_api.utils.FontLoader;
import fr.trxyy.alternative.alternative_api.utils.Mover;
import fr.trxyy.alternative.alternative_api.utils.config.UserConfig;
import fr.trxyy.alternative.alternative_api.utils.config.UsernameSaver;
import fr.trxyy.alternative.alternative_api_ui.LauncherAlert;
import fr.trxyy.alternative.alternative_api_ui.base.IScreen;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherButton;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherImage;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherLabel;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherPasswordField;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherProgressBar;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherRectangle;
import fr.trxyy.alternative.alternative_api_ui.components.LauncherTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

public class LauncherPanel extends IScreen {
	/** TOP */
	private LauncherRectangle topRectangle;
	private LauncherImage titleImage;
	private LauncherLabel titleLabel;
	private LauncherButton closeButton;
	private LauncherButton reduceButton;
	/** BUTTONS IMAGE URL */
	private LauncherButton facebookButton;
	private LauncherButton twitterButton;
	private LauncherButton instagramButton;
	private LauncherButton youtubeButton;
	/** LOGIN */
	private LauncherTextField usernameField;
	private LauncherPasswordField passwordField;
	private LauncherButton loginButton;
	private LauncherButton settingsButton;
	/** UPDATE */
	public Timeline timeline;
	private DecimalFormat decimalFormat = new DecimalFormat(".#");
	private Thread updateThread;
	private GameUpdater updater = new GameUpdater();
	private LauncherRectangle updateRectangle;
	private LauncherLabel updateLabel;
	private LauncherLabel currentFileLabel;
	private LauncherLabel percentageLabel;
	private LauncherLabel currentStep;
	/** USERNAME SAVER, CONFIG SAVER */
	private UsernameSaver usernameSaver;
	public UserConfig userConfig;
	/** PROGRESS BAR */
	public LauncherProgressBar bar;
	/** LINKS FOR BUTTONS IMAGES */
	private String FACEBOOK_URL = "http://facebook.com/";
	private String INSTAGRAM_URL = "http://instagram.com/";
	private String TWITTER_URL = "http://twitter.com/";
	private String YOUTUBE_URL = "http://youtube.com/c/Trxyy";
	/** GAMEENGINE REQUIRED */
	private GameEngine theGameEngine;
	/** AUTO LOGIN */
	private Timer autoLoginTimer;
	private LauncherLabel autoLoginLabel;
	private LauncherRectangle autoLoginRectangle;
	private LauncherButton autoLoginButton;

	public LauncherPanel(Pane root, GameEngine engine) {
		this.theGameEngine = engine;
		/** ===================== CONFIGURATION UTILISATEUR ===================== */
		this.userConfig = new UserConfig(theGameEngine);
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.usernameSaver = new UsernameSaver(theGameEngine);
		/** ===================== RECTANGLE NOIR EN HAUT ===================== */
		this.topRectangle = new LauncherRectangle(root, 0, 0, theGameEngine.getWidth(), 31);
		this.topRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.topRectangle.setOpacity(1.0);
		/** ===================== AFFICHER UN LOGO ===================== */
		this.drawLogo(theGameEngine, getResourceLocation().loadImage(theGameEngine, "alternativeapi_logo.png"), 0, engine.getHeight() - 120, 350, 80, root, Mover.DONT_MOVE);
		/** ===================== ICONE BLOCK EN HAUT MILIEU ===================== */
		this.titleImage = new LauncherImage(root);
		this.titleImage.setImage(getResourceLocation().loadImage(theGameEngine, "favicon.png"));
		this.titleImage.setSize(25, 25);
		this.titleImage.setPosition(theGameEngine.getWidth() / 3 + 40, 3);
		/** ===================== TEXTE EN HAUT MILIEU ===================== */
		this.titleLabel = new LauncherLabel(root);
		this.titleLabel.setText("Mon Super Launcher");
		this.titleLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.titleLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.titleLabel.setPosition(theGameEngine.getWidth() / 2 - 80, -4);
		this.titleLabel.setOpacity(0.7);
		this.titleLabel.setSize(500, 40);
		/** ===================== BOUTON FERMER ===================== */
		this.closeButton = new LauncherButton(root);
		this.closeButton.setInvisible();
		this.closeButton.setPosition(theGameEngine.getWidth() - 35, 2);
		this.closeButton.setSize(15, 15);
		this.closeButton.setBackground(null);
		LauncherImage closeImage = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "close.png"));
		closeImage.setSize(15, 15);
		this.closeButton.setGraphic(closeImage);
		this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		/** ===================== BOUTON REDUIRE ===================== */
		this.reduceButton = new LauncherButton(root);
		this.reduceButton.setInvisible();
		this.reduceButton.setPosition(theGameEngine.getWidth() - 55, 2);
		this.reduceButton.setSize(15, 15);
		this.reduceButton.setBackground(null);
		LauncherImage reduceImage = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "reduce.png"));
		reduceImage.setSize(15, 15);
		this.reduceButton.setGraphic(reduceImage);
		this.reduceButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) ((LauncherButton) event.getSource()).getScene().getWindow();
				stage.setIconified(true);
			}
			
		});
		/** ===================== CASE PSEUDONYME ===================== */
		this.usernameField = new LauncherTextField(root);
		this.usernameField.setText(this.usernameSaver.getUsername());
		this.usernameField.setPosition(5, theGameEngine.getHeight() - 57);
		this.usernameField.setSize(270, 50);
		this.usernameField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.usernameField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.usernameField.setVoidText("Nom de compte");
		/** ===================== CASE MOT DE PASSE ===================== */
		this.passwordField = new LauncherPasswordField(root);
		this.passwordField.setPosition(280, theGameEngine.getHeight() - 57);
		this.passwordField.setSize(270, 50);
		this.passwordField.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.passwordField.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.passwordField.setVoidText("Mot de passe (vide = crack)");
		/** ===================== BOUTON DE CONNEXION ===================== */
		this.loginButton = new LauncherButton(root);
		this.loginButton.setText("Connexion");
		this.loginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.loginButton.setPosition(theGameEngine.getWidth() / 2 + 80, theGameEngine.getHeight() - 57);
		this.loginButton.setSize(180, 49);
		this.loginButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.loginButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				usernameSaver.writeUsername(usernameField.getText());
				/**
				 * ===================== AUTHENTIFICATION OFFLINE (CRACK) =====================
				 */
				if (usernameField.getText().length() < 3) {
					new LauncherAlert("Authentification echouee",
							"Il y a un probleme lors de la tentative de connexion: Le pseudonyme doit comprendre au moins 3 caracteres.");
				} else if (usernameField.getText().length() > 3 && passwordField.getText().isEmpty()) {
					GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(), AccountType.OFFLINE);
					if (auth.isLogged()) {
						update(auth);
					}
				}
				/** ===================== AUTHENTIFICATION OFFICIELLE ===================== */
				else if (usernameField.getText().length() > 3 && !passwordField.getText().isEmpty()) {
					GameAuth auth = new GameAuth(usernameField.getText(), passwordField.getText(),
							AccountType.MOJANG);
					if (auth.isLogged()) {
						update(auth);
					} else {
						new LauncherAlert("Authentification echouee!",
								"Impossible de se connecter, l'authentification semble etre une authentification 'en-ligne'"
										+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres. (compte non migrer)"
										+ "\n-Faites bien attention aux majuscules et minuscules. \nAssurez-vous d'utiliser un compte Mojang.");
					}
				} else {
					new LauncherAlert("Authentification echouee!",
							"Impossible de se connecter, l'authentification semble etre une authentification 'hors-ligne'"
									+ " \nIl y a un probleme lors de la tentative de connexion. \n\n-Verifiez que le pseudonyme comprenne au minimum 3 caracteres.");
				}
			}
		});
		/** ===================== BOUTON PARAMETRES ===================== */
		this.settingsButton = new LauncherButton(root);
		this.settingsButton.setText("Param�tres");
		this.settingsButton.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4); -fx-text-fill: white;");
		this.settingsButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.settingsButton.setPosition(theGameEngine.getWidth() / 2 + 80, theGameEngine.getHeight() - 90);
		this.settingsButton.setSize(180, 25);
		/** ===================== BOUTON URL FACEBOOK ===================== */
		this.facebookButton = new LauncherButton(root);
		this.facebookButton.setInvisible();
		this.facebookButton.setPosition(theGameEngine.getWidth() - 58, theGameEngine.getHeight() / 2 - 100);
		LauncherImage facebookImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "fb_icon.png"));
		facebookImg.setSize(50, 50);
		this.facebookButton.setGraphic(facebookImg);
		this.facebookButton.setSize((int) facebookImg.getFitWidth(), (int) facebookImg.getFitHeight());
		this.facebookButton.setBackground(null);
		this.facebookButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(FACEBOOK_URL);
			}
		});
		/** ===================== BOUTON URL TWITTER ===================== */
		this.twitterButton = new LauncherButton(root);
		this.twitterButton.setInvisible();
		this.twitterButton.setPosition(theGameEngine.getWidth() - 58, theGameEngine.getHeight() / 2 - 48);
		LauncherImage twitterImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "twitter_icon.png"));
		twitterImg.setSize(50, 50);
		this.twitterButton.setGraphic(twitterImg);
		this.twitterButton.setSize((int) twitterImg.getFitWidth(), (int) twitterImg.getFitHeight());
		this.twitterButton.setBackground(null);
		this.twitterButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(TWITTER_URL);
			}
		});
		/** ===================== BOUTON URL INSTAGRAM ===================== */
		this.instagramButton = new LauncherButton(root);
		this.instagramButton.setInvisible();
		this.instagramButton.setPosition(theGameEngine.getWidth() - 58, theGameEngine.getHeight() / 2 + 4);
		LauncherImage instagramImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "insta_icon.png"));
		instagramImg.setSize(50, 50);
		this.instagramButton.setGraphic(instagramImg);
		this.instagramButton.setSize((int) instagramImg.getFitWidth(), (int) instagramImg.getFitHeight());
		this.instagramButton.setBackground(null);
		this.instagramButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(INSTAGRAM_URL);
			}
		});
		/** ===================== BOUTON URL YOUTUBE ===================== */
		this.youtubeButton = new LauncherButton(root);
		this.youtubeButton.setInvisible();
		this.youtubeButton.setPosition(theGameEngine.getWidth() - 58, theGameEngine.getHeight() / 2 + 56);
		LauncherImage youtubeImg = new LauncherImage(root, getResourceLocation().loadImage(theGameEngine, "yt_icon.png"));
		youtubeImg.setSize(50, 50);
		this.youtubeButton.setGraphic(youtubeImg);
		this.youtubeButton.setSize((int) youtubeImg.getFitWidth(), (int) youtubeImg.getFitHeight());
		this.youtubeButton.setBackground(null);
		this.youtubeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				openLink(YOUTUBE_URL);
			}
		});

		/**
		 * ============================== MISE A JOUR ==============================
		 **/
		this.updateRectangle = new LauncherRectangle(root, theGameEngine.getWidth() / 2 - 175, theGameEngine.getHeight() / 2 - 60, 350, 180);
		this.updateRectangle.setArcWidth(10.0);
		this.updateRectangle.setArcHeight(10.0);
		this.updateRectangle.setFill(Color.rgb(0, 0, 0, 0.60));
		this.updateRectangle.setVisible(false);
		/** =============== LABEL TITRE MISE A JOUR =============== **/
		this.updateLabel = new LauncherLabel(root);
		this.updateLabel.setText("- MISE A JOUR -");
		this.updateLabel.setAlignment(Pos.CENTER);
		this.updateLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 22F));
		this.updateLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.updateLabel.setPosition(theGameEngine.getWidth() / 2 - 95, theGameEngine.getHeight() / 2 - 55);
		this.updateLabel.setOpacity(1);
		this.updateLabel.setSize(190, 40);
		this.updateLabel.setVisible(false);
		/** =============== ETAPE DE MISE A JOUR =============== **/
		this.currentStep = new LauncherLabel(root);
		this.currentStep.setText("Preparation de la mise a jour.");
		this.currentStep.setFont(Font.font("Verdana", FontPosture.ITALIC, 18F)); // FontPosture.ITALIC
		this.currentStep.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentStep.setAlignment(Pos.CENTER);
		this.currentStep.setPosition(theGameEngine.getWidth() / 2 - 160, theGameEngine.getHeight() / 2 + 83);
		this.currentStep.setOpacity(0.4);
		this.currentStep.setSize(320, 40);
		this.currentStep.setVisible(false);
		/** =============== FICHIER ACTUEL EN TELECHARGEMENT =============== **/
		this.currentFileLabel = new LauncherLabel(root);
		this.currentFileLabel.setText("launchwrapper-12.0.jar");
		this.currentFileLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.currentFileLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.currentFileLabel.setAlignment(Pos.CENTER);
		this.currentFileLabel.setPosition(theGameEngine.getWidth() / 2 - 160, theGameEngine.getHeight() / 2 + 25);
		this.currentFileLabel.setOpacity(0.8);
		this.currentFileLabel.setSize(320, 40);
		this.currentFileLabel.setVisible(false);
		/** =============== POURCENTAGE =============== **/
		this.percentageLabel = new LauncherLabel(root);
		this.percentageLabel.setText("0%");
		this.percentageLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 30F));
		this.percentageLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
		this.percentageLabel.setAlignment(Pos.CENTER);
		this.percentageLabel.setPosition(theGameEngine.getWidth() / 2 - 50, theGameEngine.getHeight() / 2 - 5);
		this.percentageLabel.setOpacity(0.8);
		this.percentageLabel.setSize(100, 40);
		this.percentageLabel.setVisible(false);
		
		this.bar = new LauncherProgressBar(root);
		this.bar.setPosition(theGameEngine.getWidth() / 2 - 125, theGameEngine.getHeight() / 2 + 60);
		this.bar.setSize(250, 20);
		this.bar.setVisible(false);
		
		/** =============== LOGIN AUTOMATIQUE (CRACK SEULEMENT) =============== **/
		this.autoLoginRectangle = new LauncherRectangle(root, 0, theGameEngine.getHeight() - 32, 1000, theGameEngine.getHeight());
		this.autoLoginRectangle.setFill(Color.rgb(0, 0, 0, 0.70));
		this.autoLoginRectangle.setOpacity(1.0);
		this.autoLoginRectangle.setVisible(false);
		
		this.autoLoginLabel = new LauncherLabel(root);
		this.autoLoginLabel.setText("Connexion auto dans 3 secondes. Appuyez sur ECHAP pour annuler.");
		this.autoLoginLabel.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 18F));
		this.autoLoginLabel.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
		this.autoLoginLabel.setPosition(theGameEngine.getWidth() / 2 - 280, theGameEngine.getHeight() - 34);
		this.autoLoginLabel.setOpacity(0.7);
		this.autoLoginLabel.setSize(700, 40);
		this.autoLoginLabel.setVisible(false);
		
		this.autoLoginButton = new LauncherButton(root);
		this.autoLoginButton.setText("Annuler");
		this.autoLoginButton.setFont(FontLoader.loadFont("Comfortaa-Regular.ttf", "Comfortaa", 14F));
		this.autoLoginButton.setPosition(theGameEngine.getWidth() / 2 + 60, theGameEngine.getHeight() - 30);
		this.autoLoginButton.setSize(200, 20);
		this.autoLoginButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.4); -fx-text-fill: black;");
		this.autoLoginButton.setVisible(false);
		this.autoLoginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoLoginTimer.cancel();
				autoLoginLabel.setVisible(false);
				autoLoginButton.setVisible(false);
				autoLoginRectangle.setVisible(false);
			}
		});
		
		if (this.usernameSaver.getUsername().length() > 2 && !this.usernameSaver.getUsername().contains("@") && userConfig.getAutoLogin()) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					autoLoginTimer = new Timer();
					TimerTask timerTask = new TimerTask() {
						int waitTime = 5;
						int elapsed = 0;
						@Override
						public void run() {
							elapsed++;

							if (elapsed % waitTime == 0) {
								loginButton.fire();
								autoLoginTimer.cancel();
								autoLoginLabel.setVisible(false);
								autoLoginButton.setVisible(false);
								autoLoginRectangle.setVisible(false);
							} else {
								int time = (waitTime - (elapsed % waitTime));
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										autoLoginLabel.setText("Connexion auto dans " + time + " secondes.");
									}
								});
							}
						}
					};
					autoLoginTimer.schedule(timerTask, 0, 1000);
					autoLoginLabel.setVisible(true);
					autoLoginRectangle.setVisible(true);
					autoLoginButton.setVisible(true);
				}
			});
		}
	}

	private void update(GameAuth auth) {
		this.usernameField.setDisable(true);
		this.passwordField.setDisable(true);
		this.loginButton.setDisable(true);
		this.settingsButton.setDisable(true);
		this.usernameField.setVisible(false);
		this.passwordField.setVisible(false);
		this.loginButton.setVisible(false);
		this.settingsButton.setVisible(false);
		this.updateRectangle.setVisible(true);
		this.updateLabel.setVisible(true);
		this.currentStep.setVisible(true);
		this.currentFileLabel.setVisible(true);
		this.percentageLabel.setVisible(true);
		this.bar.setVisible(true);
		updater.reg(theGameEngine);
		updater.reg(auth.getSession());
		theGameEngine.reg(this.updater);
		this.updateThread = new Thread() {
			public void run() {
				theGameEngine.getGameUpdater().run();
			}
		};
		this.updateThread.start();
		/** ===================== REFAICHIR LE NOM DU FICHIER, PROGRESSBAR, POURCENTAGE  ===================== **/
		this.timeline = new Timeline(
				new KeyFrame[] { new KeyFrame(javafx.util.Duration.seconds(0.0D), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						timelineUpdate(theGameEngine);
					}
				}, new javafx.animation.KeyValue[0]),
						new KeyFrame(javafx.util.Duration.seconds(0.1D), new javafx.animation.KeyValue[0]) });
		this.timeline.setCycleCount(Animation.INDEFINITE);
		this.timeline.play();
	}

	public void timelineUpdate(GameEngine engine) {
		if (engine.getGameUpdater().downloadedFiles > 0) {
			this.percentageLabel.setText(decimalFormat.format(
				engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload) + "%");
		}
		this.currentFileLabel.setText(engine.getGameUpdater().getCurrentFile());
		this.currentStep.setText(engine.getGameUpdater().getCurrentInfo());
		double percent = (engine.getGameUpdater().downloadedFiles * 100.0D / engine.getGameUpdater().filesToDownload / 100.0D);
		this.bar.setProgress(percent);
	}

}
