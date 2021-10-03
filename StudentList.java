import java.rmi.*;
import java.util.Vector;

public interface StudentList extends Remote {
    Student newStudent(User u) throws RemoteException;
    Vector allStudents() throws RemoteException;
}