import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Αναζήτηση του RMI Registry στον τοπικό υπολογιστή (localhost) στην πόρτα 1099
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Ανάκτηση του Remote Server μέσω του lookup (το όνομα "RemoteServer" πρέπει να ταιριάζει με αυτό του server)
            RemoteInterface server = (RemoteInterface) registry.lookup("RemoteServer");

            System.out.println("🔗 Συνδεθήκαμε στον RMI Server!");

            // Δημιουργία Scanner για ανάγνωση εισόδου από τον χρήστη
            Scanner scanner = new Scanner(System.in);

            //  Επαναλαμβανόμενη λήψη εντολών από τον χρήστη
            while (true) {
                System.out.println("\nΕισαγωγή εντολής της μορφής Α,Β,Γ: ");
                String command = scanner.nextLine(); // Ο χρήστης εισάγει μια εντολή

                //  Αποστολή της εντολής στον server μέσω της μεθόδου `processCommand()`
                String response = server.processCommand(command);

                // Εμφάνιση της απάντησης του server
                System.out.println("📨 Απάντηση server: " + response);

                // Αν η απάντηση είναι "bye", τερματίζουμε τη σύνδεση
                if ("bye".equals(response)) {
                    System.out.println("🔴 Αποσύνδεση...");
                    break;
                }
            }

            // Κλείσιμο του Scanner πριν τον τερματισμό
            scanner.close();

        } catch (Exception e) {
            // Διαχείριση πιθανών σφαλμάτων κατά την εκτέλεση
            System.err.println("❌ Σφάλμα στο Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}