import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements Operations {
    private static final int SIZE = 1048576;

    // We assume that the hash table only stores positive (> 0) values.
    // Therefore a key with 0 (zero) value indicates that nothing is stored there.
    // Since the key is an integer we decided to use an array of integers as a hashtable,
    // instead of using Hashtable implementation of the core library.
    private final int[] HASH_TABLE = new int[SIZE];

    // This lock is used in the synchronized blocks to ensure thread-safe access to the HASH_TABLE.
    private final Object lock = new Object();

    public static void main(String[] args) {
        try {
            // Starts the RMI Registry
            Registry registry = LocateRegistry.createRegistry(1888);
            registry.rebind("Operations", new Server());
            System.out.println("Server ready...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }

    protected Server() throws RemoteException {
        super();
    }

    @Override
    public int insert(int key, int value) throws RemoteException {
        synchronized (lock) {
            if (key < 0 || key > SIZE - 1) {
                System.out.println("Server: Invalid key: " + key);
                return 0;
            }
            if (value < 1) {
                System.out.println("Server: Invalid value: " + value);
                return 0;
            }

            System.out.println("Server: Wrote key " + key + " value " + value);
            HASH_TABLE[key] = value;
            return 1;
        }
    }

    @Override
    public int delete(int key) throws RemoteException {
        synchronized (lock) {
            if (key < 0 || key > SIZE - 1) {
                System.out.println("Server: Invalid key: " + key);
                return 0;
            }
            System.out.println("Server: Deleted key " + key);
            HASH_TABLE[key] = 0;
            return 1;
        }
    }

    @Override
    public int search(int key) throws RemoteException {
        // no need for thread-safety check for the read operations
        if (key < 0 || key > SIZE - 1) {
            System.out.println("Server: Invalid key: " + key);
            return 0;
        }
        System.out.println("Server: Search key " + key + " value " + HASH_TABLE[key]);
        return HASH_TABLE[key];
    }
}