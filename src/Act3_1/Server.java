package Act3_1;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private int nombreclient;

    public static void main(String[] args) {
        // Programme principal, démarrage du serveur en lançant un thread Server.
        (new Server()).start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Démarrage du Serveur ");
            
            while (true) {
                // Le serveur attend de nouvelles connexions de clients.
                Socket s = ss.accept();
                // Création d'un thread distinct pour chaque client.
                new ClientProcess(s, ++nombreclient).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ClientProcess extends Thread {
        private int numclient;
        private Socket s;

        public ClientProcess(Socket s, int numclient) {
            super();
            this.numclient = numclient;
            this.s = s;
        }

        @Override
        public void run() {
            try {
                System.out.println("Le client numéro " + numclient + " de l'adresse IP : " + s.getRemoteSocketAddress());
                (new PrintWriter(s.getOutputStream(), true)).println("Bienvenue, vous êtes le client numéro " + numclient);
                // La ligne ci-dessus envoie un message de bienvenue au client.

                // true signifie que l'envoi est "auto-flush", ce qui signifie que le message est
                // envoyé immédiatement sans avoir besoin d'appeler flush().

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
