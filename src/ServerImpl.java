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
        String[] parts = command.split(","); // Διαχωρισμός της εντολής σε τμήματα

        int action = Integer.parseInt(parts[0]); // Ο πρώτος αριθμός καθορίζει τη λειτουργία
        int key = Integer.parseInt(parts[1]); // Ο δεύτερος αριθμός είναι το κλειδί

        // Διαχείριση εντολών: 0 (κλείσιμο), 1 (εισαγωγή), 2 (διαγραφή), 3 (αναζήτηση)
        if (action == 0) {
            System.out.println("Κλείσιμο σύνδεσης.");
            return "bye"; // Επιστροφή μηνύματος αποσύνδεσης
        } else if (action == 1) {
            int value = Integer.parseInt(parts[2]); // Ο τρίτος αριθμός είναι η τιμή για εισαγωγή
            hashMap.put(key, value); // Προσθήκη στη δομή δεδομένων
            return "1"; // Επιτυχής εισαγωγή
        } else if (action == 2) {
            // Διαγραφή κλειδιού και επιστροφή του αντίστοιχου μηνύματος
            if (hashMap.remove(key) != null) {
                return "1"; // Επιτυχής διαγραφή
            } else {
                return "0"; // Αποτυχία διαγραφής (το κλειδί δεν υπάρχει)
            }
        } else if (action == 3) {
            // Αναζήτηση κλειδιού και επιστροφή της τιμής του αν υπάρχει
            if (hashMap.containsKey(key)) {
                return hashMap.get(key).toString();
            } else {
                return "0"; // Το κλειδί δε βρέθηκε
            }
        } else {
            return "0"; // Λανθασμένη εντολή
        }
    }

}