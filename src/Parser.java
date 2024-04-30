package module_data;

import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Parser {

  public static final String BEG_TOKEN    = "{" ;
  public static final String END_TOKEN    = "}" ;
  public static final String SEP_TOKENS   = ";,";
  public static final String ASIGN_TOKENS = "=";

  public static final int ITEM_ID         = 0;
  public static final int ITEM_NAME       = 1;
  public static final int ITEM_DESC       = 2;
  public static final int ITEM_DESC_FULL  = 3;
  public static final int ITEM_COUNT      = 4;
  public static final int ITEM_PRICE      = 5;
  public static final int ITEM_DELIV      = 6;
  public static final int ITEM_ASSET      = 7;
  
  public static final int ITEM_BUFFER_SIZE= 8;


  File target;

  public Parser(String file) {
    this.target = new File(file);

    if (!target.exists()) { 
      System.err.println("FAILED TO OPEN LOCAL DATABASE");
      System.exit(-1);
    }

  }

  public String pull_value(String object_type, String object_field, String id) {
    String item = 
      (id == null)?
        pull_object_api(object_type,new String[] {object_field} ,false)
        :
        pull_object_api(object_type,new String[] {id, object_field} ,true);

    StringTokenizer tk = new StringTokenizer(item, BEG_TOKEN + SEP_TOKENS + END_TOKEN);

    while(tk.hasMoreTokens()) {
      String pair = tk.nextToken();
      String final_token = null;
      //                              DEBUG : 
                                    System.out.println("\t TKN: "+pair);
      if (pair.contains(object_field) && pair.contains(ASIGN_TOKENS)) {
        StringTokenizer ptk = new StringTokenizer(pair,ASIGN_TOKENS);
        
      //                              DEBUG : 
                                    System.out.println("\t\t SUBTKN: "+pair);
        while(ptk.hasMoreTokens())
          final_token = ptk.nextToken();
        return final_token;
      }
    }
    return null;
  }

  private boolean has_any_patterns(String src, String type,String[] args) {
    boolean has_any_pattern = false;

    for(String arg : args)
      has_any_pattern = src.contains(arg);

    has_any_pattern = src.contains(type) && has_any_pattern;
    return has_any_pattern;
  }


  private boolean has_all_patterns(String src, String type,String[] args) {
    boolean has_all = false;

    for(String arg : args) {
      has_all = src.contains(arg);
      if (!has_all)
        break;
    }

    has_all = src.contains(type) && has_all;
    return has_all;
  }

  public String pull_object(String object_type,String[] object_args) { 
    return pull_object_api(object_type,object_args,true); 
  }

  private String pull_object_api(String object_type,String[] object_args,boolean restrict) {
    int line_skip             = 0;
    int iter                  = 0;
    final int MAX_ENTRY_LINES = 10;
    final int MAX_ITERATIONS  = 64;
    String compiled_pattern   = object_type + "(";
    boolean has_any_patterns  = false;


    if (object_args.length > 1) 
      for (int i = 0; i < object_args.length; i++)
      {
        compiled_pattern += object_args[i];
        if (i < object_args.length - 1)
          compiled_pattern += ",";
      }
    else
      compiled_pattern += object_args[0] + ")";
    
    try {
      Scanner reader = new Scanner(target);

      // construct buffer

      String src_string = "";

      while(reader.hasNextLine()) { 
        String line = reader.nextLine();

        if (line.contains(compiled_pattern) || 
            (
             (!restrict) ? 
              has_any_patterns(line,object_type,object_args)
              : has_all_patterns(line,object_type,object_args)
             ) ) 
        {
          while(!src_string.contains(END_TOKEN)) {
            
            if (iter >= MAX_ITERATIONS)
              break;

            src_string += line;
            line = reader.nextLine();
            iter++;
          }
          break;
        }

      }
      System.out.println("LINE: \n" + src_string + "\n :: EOL");
      return src_string;

    } catch (FileNotFoundException e) {
      System.out.println("Java is indeed horibble language.");
    }
    return null;
  }

  public void push(String[] metadata)
  {

  }


}
