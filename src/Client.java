import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Αναζήτηση του Remote Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Ανάκτηση του Remote Server
            RemoteInterface server = (RemoteInterface) registry.lookup("RemoteServer");

            System.out.println("🔗 Συνδεθήκαμε στον RMI Server!");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nΕισαγωγή εντολής της μορφής Α,Β,Γ: ");
                String command = scanner.nextLine();

                String response = server.processCommand(command);
                System.out.println("📨 Απάντηση server: " + response);

                if ("bye".equals(response)) {
                    System.out.println("🔴 Αποσύνδεση...");
                    break;
                }
            }
            scanner.close();
        } catch (Exception e) {
            System.err.println("❌ Σφάλμα στο Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}