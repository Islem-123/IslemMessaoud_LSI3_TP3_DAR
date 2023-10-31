package Act3_2;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
	// Permet de suivre l'ordre de communication des clients (numéro du client)
    private int nombreClient; 
    // Appel du serveur
    public static void main(String[] args) {
        (new Server()).start();
    }
    public void run() {
        try {
        	// Crée un ServerSocket et le lie au port 1234
            ServerSocket ss = new ServerSocket(1234); 
            System.out.println("Serveur démarré");

            while (true) {
            	// Attendez qu'un client se connecte et acceptez la connexion
                Socket s = ss.accept(); 
                // Crée un nouveau thread pour gérer le client
                new ClientProcess(s, ++nombreClient).start(); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    	// Crée un thread pour gérer un client
        public class ClientProcess extends Thread {
    	// Numéro unique du client
        private int numClient;
        // Le socket pour communiquer avec le client
        private Socket s; 
        public ClientProcess(Socket s, int numClient) 
        {
            this.s = s;
            this.numClient = numClient;
        }
        
        public void run() {
        		// Affiche un message sur le serveur indiquant le numéro du client et son adresse IP
            System.out.println("Client numéro : " + numClient + " Adresse IP : " + s.getRemoteSocketAddress());
            try {
                // Envoyez un message de bienvenue au client
                new PrintWriter(s.getOutputStream(), true).println("Bienvenue, vous êtes le client numéro " + numClient);
                // Obtenez les flux d'entrée et de sortie pour communiquer avec le client
                InputStream is = s.getInputStream();
                OutputStream os = s.getOutputStream();
                // Créez un ObjectInputStream pour recevoir un objet de type Operation du client
                ObjectInputStream ois = new ObjectInputStream(is);
                Operation operation = (Operation) ois.readObject();
                // Extrayez les détails de l'opération à partir de l'objet reçu
                int op1 = operation.getOp1();
                int op2 = operation.getOp2();
                char op = operation.getOperation();
                int result = 0;
                // Effectuez l'opération arithmétique demandée
                switch (op) {
                    case '+':
                        result = op1 + op2;
                        break;
                    case '-':
                        result = op1 - op2;
                        break;
                    case '*':
                        result = op1 * op2;
                        break;
                    case '/':
                        result = op1 / op2;
                        break;
                }
                // Créez un ObjectOutputStream pour renvoyer un objet de type Operation au client
                ObjectOutputStream oos = new ObjectOutputStream(os);
                Operation operation1 = new Operation(op1, op2, op);
                operation1.setResult1(result);
                // Envoyez un objet Operation au client
                oos.writeObject(operation1); 
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
