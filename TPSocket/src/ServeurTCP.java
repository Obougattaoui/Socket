import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP {

	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(1234);
			System.out.println("J'attend une connexion ....");
			//ouvrir un service d'�coute (attend des clients):
			Socket s = ss.accept();
			//si connexion est �tablit : ==> accept msg
			InputStream is = s.getInputStream();
			//envoie des messages :
			OutputStream os = s.getOutputStream();
			//l'attend d'un nombre envoy� par le client :
			System.out.println("j'atend un nombre");
			int nb = is.read();
			System.out.println("j'ai recoit un msg");
			//faire un traitement: 
			int res = nb * 12;
			//envoyer r�sultat au client :
			System.out.println("j'envoie la r�ponse");
			os.write(res);
			//close la connexion : ==> socket
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
