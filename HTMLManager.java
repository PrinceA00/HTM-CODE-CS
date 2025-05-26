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
  
  public Queue<HTMLTag> getTags(){
      return tags ;  
  }
  
  public String toString(){
   String newTag=""
   for(HTMLTag tag: tags){
      newTag+=tag;
   }
   return newTag.trim();
  }
    
}
