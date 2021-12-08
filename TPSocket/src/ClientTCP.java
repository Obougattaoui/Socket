import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {

	public static void main(String[] args) {
		try {
			//Création d'une socket + envoie d'une demande de connexion:
			System.out.println("etablir une connexion");
			Socket s = new Socket("localhost",1234);
			InputStream is = s.getInputStream();
			OutputStream os = s.getOutputStream();
			
			Scanner scanner= new Scanner(System.in);
			System.out.println("donner un nombre: ");
			int nb = scanner.nextInt();
			System.out.println("l'envoie au serveur : ");
			os.write(nb);
			System.out.println("l'attend de réponse");
			int rep = is.read();
			System.out.println("Réponse:  " + rep);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
