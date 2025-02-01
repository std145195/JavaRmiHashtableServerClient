import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Δημιουργία registry στην πόρτα 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Δημιουργία και καταχώρηση του αντικειμένου ServerImpl
            ServerImpl server = new ServerImpl();
            registry.rebind("RemoteServer", server);

            System.out.println("✅ Ο RMI Server ξεκίνησε επιτυχώς!");
        } catch (Exception e) {
            System.err.println("❌ Σφάλμα κατά την εκκίνηση του RMI Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
