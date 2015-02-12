package puzzleGame;
import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class rfidTP extends GameCanvas implements Runnable{
	
	private static final int SLEEP_INTERVAL = 100;
	private Thread puzzleThread; // Le Thread dans lequel tourne le jeu
	private boolean puzzleThreadQuit; // booléen qui vaut false tant que le jeu n’est pas terminé
	private Image[] puzzleImages; // L’ensemble des images
	private int[][] puzzleStatus; // L’état actuel de la grille
	private int nbCoups; // Le nombre de déplacements efectué
	private long startTime; // La date/heure à laquelle a commencé le jeu (en millisecondes)
	
	//constructeur
	protected rfidTP(boolean suppressKeyEvents) {
		super(suppressKeyEvents);
		this.puzzleImages = new Image[8];
		this.puzzleStatus = new int[3][3];
		
		//on ajoute au tableau de taille 8 les images
		for (int i = 0; i < puzzleImages.length; i++) {
			String image = "TUX/TUX-" + (i+1) + ".png";
			try {
				//on essaie de mettre l'image dans le tableau puzzleImages
				puzzleImages[i].createImage(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.newGame();
	}
	
	
	//partie run de l'application
	public void run() {
		//appel la fonction qui mélange les images du puzzles
		generateRandomPuzzleStatus();
		
		nbCoups = 0;
		startTime = System.currentTimeMillis();
		
		//temps que le puzzle n'est pas terminer
		while ( ! puzzleThreadQuit) { 
			updateGameScreen();
			updateGameScreen();
			
			try {
				puzzleThread.sleep(SLEEP_INTERVAL);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void generateRandomPuzzleStatus() {
		
		//TODO générer nombre dans le tableau 
		
		puzzleStatus[0][0] = 1;
		puzzleStatus[0][1] = 5;
		puzzleStatus[0][2] = 4;
		
		puzzleStatus[1][0] = 7;
		puzzleStatus[1][1] = 2;
		puzzleStatus[1][2] = 3;
		
		puzzleStatus[2][0] = -1;
		puzzleStatus[2][1] = 6;
		puzzleStatus[2][2] = 0;
		
	}

	public void updateGameScreen() {
		for (int i = 0; i < puzzleStatus.length; i++) {//on boucle sur le taille du tableau
			for (int j = 0; j < puzzleStatus[i].length; j++) { //recupère les valeur du tableau 
				int a = puzzleStatus[i][j];
				Graphics g = this.getGraphics();
				if(a == -1){
					g.drawRect(72, 72, 82, 82);// dessiner le rectangle
				}else{
					switch (i) {
					case 0:
						switch (j) {//partie du haut
						case 0: g.drawImage(puzzleImages[a], 72, 72, Graphics.TOP|Graphics.LEFT); 
								verifyGameState();
							break;
						case 1: g.drawImage(puzzleImages[a], 72, 72, Graphics.TOP|Graphics.HCENTER);
								verifyGameState();
							break;
						case 2: g.drawImage(puzzleImages[a], 72, 72, Graphics.TOP|Graphics.RIGHT);
								verifyGameState();
							break;
						default:
							break;
						}
						break;
					case 1:
						switch (j) {//milieu 
						case 0:g.drawImage(puzzleImages[a], 72, 72, Graphics.BASELINE|Graphics.LEFT);
								verifyGameState();
							break;
						case 1:g.drawImage(puzzleImages[a], 72, 72, Graphics.BASELINE|Graphics.HCENTER);
								verifyGameState();
							break;
						case 2:g.drawImage(puzzleImages[a], 72, 72, Graphics.BASELINE|Graphics.RIGHT);
								verifyGameState();
							break;
						default:
							break;
						}
						break;
					case 2:
						switch (j) { //partie inférieur 
						case 0:g.drawImage(puzzleImages[a], 72, 72, Graphics.BOTTOM|Graphics.LEFT); 
								verifyGameState();
							break;
						case 1:g.drawImage(puzzleImages[a], 72, 72, Graphics.BOTTOM|Graphics.HCENTER);
								verifyGameState();
							break;
						case 2:g.drawImage(puzzleImages[a], 72, 72, Graphics.BOTTOM|Graphics.RIGHT);
								verifyGameState();
							break;
						default:
							break;
						}
						break;
					default: verifyGameState();
						break;
					}
				}
			}
		}
	}
	//vérification de l'état du tableau 
	public void verifyGameState(){
		
		int k = 0;
		boolean end = false; //le puzzle est défini comme non terminer
		
		//boucle sur le puzzleImage
		for (int i = 0; i < puzzleImages.length; i++) {
			for (int j = 0; j < puzzleStatus[i].length; j++) { //récupère l'image par rapport a une place du tableau
				if(puzzleStatus[i][j] == k){ 
					end = true;
				}else {
					end = false;
				}
				k++;
			}
		}
		if(end == true){
			new Alert("Bravo vous avez réussi !");
			puzzleThreadQuit = true;
		}
	}


	//nouvelle partie
	public void newGame() {
		 if(puzzleThread.isAlive())
			 puzzleThreadQuit = true;
		 puzzleThread = new Thread();
		 puzzleThread.run();
	}
	
	//arret jeux
	public void stopGame(){
		puzzleThreadQuit = true;
		try {
			puzzleThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		puzzleThread = null;
	}

	
	

}
