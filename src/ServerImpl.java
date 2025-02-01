import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements RemoteInterface {
    private static final int HASHMAP_SIZE = (int) Math.pow(2, 20);
    private final ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<>(HASHMAP_SIZE);

    protected ServerImpl() throws RemoteException {
        super();
    }

    @Override
    public String processCommand(String command) throws RemoteException {
        String[] parts = command.split(",");

        try {
            int action = Integer.parseInt(parts[0]);
            int key = Integer.parseInt(parts[1]);

            switch (action) {
                case 0:
                    return "bye"; // Τερματισμός επικοινωνίας
                case 1:
                    int value = Integer.parseInt(parts[2]);
                    hashMap.put(key, value);
                    return "1"; // Επιτυχής εισαγωγή
                case 2:
                    return hashMap.remove(key) != null ? "1" : "0"; // Διαγραφή
                case 3:
                    return hashMap.containsKey(key) ? hashMap.get(key).toString() : "0"; // Αναζήτηση
                default:
                    return "0"; // Λανθασμένη εντολή
            }
        } catch (Exception e) {
            return "Σφάλμα στην επεξεργασία της εντολής.";
        }
    }
}
