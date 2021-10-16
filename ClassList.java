import java.rmi.*;
import java.util.Vector;

public interface ClassList extends Remote {
    Student newStudent(User u) throws RemoteException;
    Student getStudent(String name) throws RemoteException;
    Vector allStudents() throws RemoteException;
}