import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;

public class ClassListServant extends UnicastRemoteObject implements ClassList {
    private Instructor teacher;
    private Vector<StudentServant> theList;

    public ClassListServant(User u) throws RemoteException{
        teacher = new InstructorServant(u);
        theList = new Vector<StudentServant>();
    }

    public Student newStudent(User u) throws RemoteException {
        StudentServant s = new StudentServant(u);
        theList.addElement(s);
        return s;
    }

    public Student getStudent(String name) throws RemoteException {
        for (Student s : theList){
            if (s.getState().username.equals(name)){
                return s;
            }
        }
        return null;
    }

    public Vector allStudents() throws RemoteException{
        return theList;
    }
}