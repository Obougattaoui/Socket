import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SerrveurMT extends Thread{
	private int nbClients;
	public static void main(String[] args) {
		//lancer un thread:
		new SerrveurMT().start();
	}
	@Override
	public void run() {
		try {
			//pour chaque ServerSocket ==> on va créer un Thread:
			ServerSocket ss = new ServerSocket(1234);
			System.out.println("Démarrage du serveur multi thread :");
			//connecter un client à n'importe quel moment :
			while(true) {
				//attend une connexion pour gérer un client:
				Socket s = ss.accept();
				++nbClients;
				new Conversation(s, nbClients).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//création d'une classe interne:
	class Conversation extends Thread{
		private Socket socket;
		private int numero;
		
		public Conversation(Socket socket, int numero) {
			this.socket = socket;
			this.numero = numero;
		}
		@Override
		public void run() {
			//mettre output, input :
			try {
				//ouvrir le flux d'entrée:
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				//ouvrir le flux de sortie :
				OutputStream os = socket.getOutputStream();
				//envoyer une chaine de caractére:
				PrintWriter pw = new PrintWriter(os, true);
				//connaitre l'adresse IP du client qui se connecte: 
				String IP = socket.getRemoteSocketAddress().toString();
				System.out.println("Connexion du client numéro: " + numero + " IP " + IP);
				pw.println("Bienvenue vous étes le client numéro : " + this.numero);
				while(true) {
					String req = br.readLine();
					pw.println(req.length());//println ajout d'un caractére de fin de ligne
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
