package puzzleGame;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class SWTMIDlet extends MIDlet implements CommandListener {
	//variable local
	private Display display;
	private Command exitCommand;
	private Command newGameCommand;
	private rfidTP gameCanvas;
	
	//constructeur
	public SWTMIDlet(){
		super(); //appel le constructeur de la classe mère
		this.exitCommand = new Command("Exit", Command.EXIT, 1); //sortie du jeux commande
		this.newGameCommand = new Command("New Game", Command.OK, 1); //nouvelle partie commande
		this.gameCanvas = new rfidTP(false); //puzzle
		
	}
	//démarrage jeux
	protected void startApp()throws MIDletStateChangeException {
		//ajout des commande
		this.gameCanvas.addCommand(newGameCommand);
		this.gameCanvas.addCommand(exitCommand);
		
		//mise en place de l'action des commandes
		this.commandAction(newGameCommand, gameCanvas);
		this.commandAction(exitCommand, gameCanvas);
		
		//affichage sur l'écran
		this.display = Display.getDisplay(this);
		display.setCurrent(gameCanvas);
	}

	//action commande utilisateur
	public void commandAction(Command c, Displayable d) {
		//si il clique sur exit
		if(c.getLabel() == "Exit"){
			gameCanvas.stopGame();
			try {
				//fermeture application
				this.destroyApp(true);
			} catch (MIDletStateChangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//si il clique sur nouvelle partie 
		if(c.getLabel() == "New Game"){
			gameCanvas.newGame();
			try {
				//lancement application
				this.startApp();
			} catch (MIDletStateChangeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	//fermeture de l'application
	protected void destroyApp(boolean unconditional)
			throws MIDletStateChangeException {
		notifyDestroyed();
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}


}
