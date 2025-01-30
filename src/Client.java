import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private static final String[] COMMANDS = {"Insert", "Delete", "Search", "Exit"};

    public static void main(String[] args)
            throws MalformedURLException, RemoteException, NotBoundException {
        // Connects to the RMI registry
        Registry registry = LocateRegistry.getRegistry(1888);
        Operations operations = (Operations) registry.lookup("Operations");

        // The JFrame will be used for the pop up windows.
        JFrame frame = new JFrame();
        frame.setAlwaysOnTop(true);

        // guard for loop exit
        boolean finish = false;
        do {
            // Read the command
            Object selectionObject = JOptionPane.showInputDialog(frame,
                    "Select command",
                    "Storage Operations",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    COMMANDS,
                    COMMANDS[0]);

            // Make sure that the made a selection
            if (selectionObject == null) {
                break;
            }
            String command = selectionObject.toString();

            // This try/catch ensures proper number input from the user
            try {
                switch (command) {
                    case "Insert":
                        int insertKey = Integer.parseInt(JOptionPane.showInputDialog("Select key to insert"));
                        int insertValue = Integer.parseInt(JOptionPane.showInputDialog("Select value to insert"));
                        if (operations.insert(insertKey, insertValue) == 0) {
                            JOptionPane.showMessageDialog(frame, "Error while inserting key/value pair");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Insert successful");
                        }
                        break;
                    case "Delete":
                        int deleteKey = Integer.parseInt(JOptionPane.showInputDialog("Select key to delete"));
                        if (operations.delete(deleteKey) == 0) {
                            JOptionPane.showMessageDialog(frame, "Error while deleting key");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Delete successful");
                        }
                        break;
                    case "Search":
                        int searchKey = Integer.parseInt(JOptionPane.showInputDialog("Select key to search"));
                        int searchValue = operations.search(searchKey);
                        if (searchValue == 0) {
                            JOptionPane.showMessageDialog(frame, "Error while searching for key");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Value: " + searchValue);
                        }
                        break;
                    case "Exit":
                        finish = true;
                        break;
                }
            } catch (ArithmeticException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        } while (!finish);
        System.exit(0);
    }
}