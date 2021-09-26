import java.io.Serializable;

public class Question implements Serializable {
    public String question;
    public String answer;

    //	constructors
    public Question() { }
    
    public Question(String aQuestion, String anAnswer) {
      question = aQuestion;
      answer = anAnswer;
    }

    public void print(){
      System.out.print(question + " : " + answer);
	  }
}
