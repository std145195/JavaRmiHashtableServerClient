import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Operations extends Remote {
    int insert(int key, int value) throws RemoteException;

    int delete(int key) throws RemoteException;

    int search(int key) throws RemoteException;
}