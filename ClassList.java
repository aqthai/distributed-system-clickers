import java.rmi.*;
import java.util.Vector;
import java.util.ArrayList;

public interface ClassList extends Remote {
    Student newStudent(User u) throws RemoteException;
    Student getStudent(String name) throws RemoteException;
    ArrayList<StudentServant> allStudents() throws RemoteException;
}