import java.util.*;

public class HTMLManager {
  private Queue<HTMLTag> tags;
  public HTMLManager(Queue<HTMLTag> html){
      
       
      if (html== null){
         throws new illegalArgumentException;
    
      }else{
         tags= new LinkedList<HTMLTag>();
         for(HTMLTag tag: html){
           tags.add(tag);
         }
      }
      
  
  }
  
}
