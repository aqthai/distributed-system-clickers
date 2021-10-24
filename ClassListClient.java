//package examples.RMIShape;
import java.rmi.*;
import java.util.Scanner;
import java.util.ArrayList;


public class ClassListClient{
   public static void main(String args[]){
		Scanner scanner = new Scanner(System.in);
		String menu1 = "";
		String answer = "";
		
 		// if(System.getSecurityManager() == null){
        // 	System.setSecurityManager(new SecurityManager());
        // } else System.out.println("Already has a security manager, so cant set RMI SM");
        ClassList aClassList = null;
        try{
            aClassList  = (ClassList) Naming.lookup("//127.0.0.1/ClassList");
 			System.out.println("Found server");
 			ArrayList<StudentServant> sList = aClassList.allStudents();
 			System.out.println("Got ArrayList");
			while (!menu1.equals("exit")){
				System.out.println("Login or Register?: (or exit)");
				menu1 = scanner.nextLine();
				if(menu1.equals("Read")){
					for (StudentServant s : sList){
						if (s.getStatus()){
							System.out.println(s.getName() + " is " );
						}
					}
				} else if (menu1.equals("Register")) {
					System.out.println("Enter username: ");
					String username = scanner.nextLine();
					System.out.println("Enter password: ");
					String password = scanner.nextLine();
					User u = new User("Student", username, password);
					Student you = aClassList.newStudent(u);
					//System.out.println(you.getName() + " has registered");
				} else if (menu1.equals("Login")) {
					System.out.println("Enter username: ");
					String username = scanner.nextLine();
					System.out.println("Enter password: ");
					String password = scanner.nextLine();
					Student you = aClassList.getStudent(username);
					if (you.getState().password.equals(password)){
						you.setStatusOn();
						System.out.println("Wait for questions and type answer [<your answer>, \"refresh\", \"logout\"]");
						answer = scanner.nextLine();
						while (!answer.equals("logout")){
							if (answer.equals("refresh")){
								System.out.println(you.getQuestion());
								System.out.println("Type answer: ");
								answer = scanner.nextLine();
								you.sendAnswer(answer);
							} else {
								you.sendAnswer(answer);
								System.out.println("Answer submitted");
								System.out.println("Wait for questions and type answer [<your answer>, \"refresh\", \"logout\"]");
								answer = scanner.nextLine();
							}
						}
						you.setStatusOff();
					} else {
						System.out.println("Wrong username or password.  Please try again.");
					}
				}
			}
			scanner.close();
		}catch(RemoteException e) {System.out.println("allStudents: " + e.getMessage());
	    }catch(Exception e) {System.out.println("Lookup: " + e);}
		
    }
}


	             
