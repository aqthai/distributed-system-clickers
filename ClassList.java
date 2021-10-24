import java.rmi.*;
import java.util.ArrayList;

public interface ClassList extends Remote {
    Student newStudent(User u) throws RemoteException;
    Student getStudent(String name) throws RemoteException;
    Instructor getInstructor() throws RemoteException;
    void setInstructor(User u) throws RemoteException;
    ArrayList<StudentServant> allStudents() throws RemoteException;
}