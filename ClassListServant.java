import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ClassListServant extends UnicastRemoteObject implements ClassList {
    public Instructor teacher;
    public ArrayList<StudentServant> theList;

    public ClassListServant(User u) throws RemoteException{
        teacher = new InstructorServant(u);
        theList = new ArrayList<StudentServant>();
    }

    public Student newStudent(User u) throws RemoteException {
        StudentServant s = new StudentServant(u);
        theList.add(s);
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

    public Instructor getInstructor() throws RemoteException {
        return teacher;
    }

    public ArrayList<StudentServant> allStudents() throws RemoteException{
        return theList;
    }
}