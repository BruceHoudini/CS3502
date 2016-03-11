package os_resources;
import java.io.*;
import java.util.Arrays;
public class Loader 
     {
         public static void main (String[] args) throws IOException
         {
             String line;
             BufferedReader in;
             boolean compLoader = false;

             in = new BufferedReader(new FileReader("Program-File.txt"));
             line = in.readLine();
             

             while(line != null)
             {
               /** finds instance of "//" and determines if it is a job or data
                * finds the buffer size available 
                * loops for the number of words within job*/
               if(line[0] == "/"){
                    if(line[1] == "/"){
                         if(line[3] == "J"){
                              int loopval;
                              loopval = parseInt(Arrays.copyOfRange(line,5,6),16);
                              //find buffer size using data
                              for(int i = 0; i < loopval; i++){
                                   //
                              }
                         }
                    }
               }  
             }
             compLoader = true;
         }

    }
