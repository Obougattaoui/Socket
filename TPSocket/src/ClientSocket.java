import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket extends Thread{
	private PrintWriter printWriter;
	private BufferedReader bufferedReader;
	
	public ClientSocket() {
		//connexion avec le serveur:
		try {
			Socket s = new Socket("localhost", 1234);
			bufferedReader = new BufferedReader(new InputStreamReader(s.getInputStream()));
			printWriter = new PrintWriter(s.getOutputStream(), true);
			//démarrer le thread:
			this.start();
			Scanner clavier = new Scanner(System.in);
			while(true) {
				System.out.println("Donner votre requéte: ");
				String req = clavier.nextLine();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new ClientSocket();
	}
	@Override
	public void run() {
		String rep;
		try {
			while((rep = bufferedReader.readLine()) != null) {
					System.out.println(rep);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
