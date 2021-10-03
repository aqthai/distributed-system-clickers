import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class StudentListServant() extends UnicastRemoteObject implements StudentList {
    private Vector<StudentServant> theList;

    public StudentListServant() throws RemoteException{
        theList = new Vector<StudentServant>();
    }

    public Student newStudent(User u) throws RemoteException {
        StudentServant s = new StudentServant(u);
        theList.addElement(s);
        return s;
    }

    public Vector allStudents() throws RemoteException{
        return theList;
    }
}