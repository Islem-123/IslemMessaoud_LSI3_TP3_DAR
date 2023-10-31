package Act3_2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Connexion
            System.out.println("je suis un client"); // Affiche un message indiquant que le client est en cours d'exécution
            Socket socket = new Socket("localhost", 1234); // Crée une socket et demande une connexion au serveur
            System.out.println("je suis un client connecté"); // Affiche un message indiquant une connexion réussie

            // Partie traitement
            InputStream is = socket.getInputStream(); // Obtient le flux d'entrée de la socket pour lire des données du serveur
            OutputStream os = socket.getOutputStream(); // Obtient le flux de sortie de la socket pour envoyer des données au serveur

            InputStreamReader isr = new InputStreamReader(is); // Crée un lecteur de flux de caractères pour l'entrée
            BufferedReader br = new BufferedReader(isr); // Crée un lecteur tampon pour lire des chaînes de caractères depuis le flux d'entrée
            System.out.println(br.readLine()); // Lit et affiche une ligne en provenance du serveur

            ObjectOutputStream oos = new ObjectOutputStream(os); // Crée un flux de sortie d'objets pour envoyer des objets au serveur

            int op1;
            int op2;
            String op;

            System.out.print("Donner le premier opérateur : "); // Demande à l'utilisateur le premier opérande
            Scanner scanner = new Scanner(System.in);
            op1 = scanner.nextInt(); // Lit le premier opérande depuis l'entrée de l'utilisateur

            System.out.print("Donner le deuxième opérateur : "); // Demande à l'utilisateur le deuxième opérande
            Scanner scanner1 = new Scanner(System.in);
            op2 = scanner1.nextInt(); // Lit le deuxième opérande depuis l'entrée de l'utilisateur (en tant qu'entier)

            do {
                System.out.print("\nDonner l'opération (+, -, *, /) : "); // Demande à l'utilisateur une opération
                Scanner scanner2 = new Scanner(System.in);
                op = scanner2.nextLine(); // Lit l'opération entrée par l'utilisateur
            } while (!op.equals("+") && !op.equals("-") && !op.equals("/") && !op.equals("*")); // Continue jusqu'à ce qu'une opération valide soit entrée

            // Crée un objet Operation avec les opérandes et l'opération fournis
            Operation operation = new Operation(op1, op2, op.charAt(0));
            oos.writeObject(operation); // Envoie l'objet Operation au serveur

            ObjectInputStream ois = new ObjectInputStream(is); // Crée un flux d'entrée d'objets pour recevoir des objets du serveur
            Operation operation1 = (Operation) ois.readObject(); // Reçoit et effectue une conversion de type d'un objet de type Operation depuis le serveur

            System.out.println(op1 + " " + op + " " + op2 + " = " + operation1.getResult1()); // Affiche le résultat reçu du serveur

            // Déconnexion
            System.out.println("Déconnexion client"); // Affiche un message indiquant la déconnexion du client
            socket.close(); // Ferme la socket
        } catch (Exception e) {
            e.printStackTrace(); // Affiche toutes les exceptions qui surviennent
        }
    }
}
