package module_data;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;



import module_data.Parser;

public class Database {

  public static final int ITEM_ID         = 0;
  public static final int ITEM_NAME       = 1;
  public static final int ITEM_DESC       = 2;
  public static final int ITEM_DESC_FULL  = 3;
  public static final int ITEM_COUNT      = 4;
  public static final int ITEM_PRICE      = 5;
  public static final int ITEM_DELIV      = 6;
  public static final int ITEM_ASSET      = 7;
  
  public static final int ITEM_BUFFER_SIZE= 8;

  public static final String DB_PATH = "./meta/db";
  public static final String DB_LOCK = "./meta/db/Lockfile";
  public static final String OBJECT_ITEM = "ENTRY";
  
  public File DB;
  public File DB_LOCKFILE;
  public File[] CATEGORIES;

  public Database() {
    this.DB = new File(DB_PATH);
    this.DB_LOCKFILE = new File(DB_LOCK);

    if (!DB.exists() || !DB_LOCKFILE.exists()) { 
      System.err.println("FAILED TO OPEN LOCAL DATABASE");
      System.exit(-1);
    }
    this.CATEGORIES = DB.listFiles();
  }


  public ArrayList<String[]> search(String c, String name) {

    ArrayList<String[]> queary_result = new ArrayList<String[]>();
    String content = null;
    File f = new File(DB_LOCK);
    if (!f.exists())
      return null;

    try {
      Scanner reader = new Scanner(DB_LOCKFILE);
      while(reader.hasNextLine()) { 
        content += reader.nextLine();
      }

    } catch (FileNotFoundException e) {
      System.out.println("Java is indeed horibble language.");
    }

    if (content != null)
    {
      StringTokenizer items = new StringTokenizer(content,";>");

      while(items.hasMoreTokens())
      {
        String item = items.nextToken();
        StringTokenizer fields = new StringTokenizer(item,",");

        // ID, NAME, DESC, AVIL,CATG,PIC_PATH ;
        final int TOKENS_EXPECTED = 6;
        final int TOKENS_TAIL     = 1;


        if (fields.countTokens() != TOKENS_EXPECTED )
        {
          if (fields.countTokens() != TOKENS_TAIL)
            System.out.println("UNMATCHED ITEM COUNT: "+fields.countTokens());
          continue;
        }

        String[] item_metadata = new String[TOKENS_EXPECTED];
        
        int i = 0; while(fields.hasMoreTokens())
        {
          String item_field = fields.nextToken()
            .replace("\\","\n"); // : breakline token
          item_metadata[i] = item_field;
            i++;
                           // DEBUG : System.out.println("\t\t FOUND FIELD: "+item_field.replace("\\","\n"));
        }

        final int CATEGORY = 4;
        final int NAME     = 1;

        if (c != null) {
          if (item_metadata[CATEGORY].contains(c) && item_metadata[NAME].contains(name))
            queary_result.add(item_metadata);
            //return item_metadata;
        }
        else if (item_metadata[NAME].contains(name))
          queary_result.add(item_metadata);
          // return item_metadata;

      }
    }
    return queary_result;
  }


  public String getvalue_from_item(String category, String id, String field_name) {
    String value = null; 
    System.out.println("CATEGORY LITERALL : '" + category + "'");
    for(File item : CATEGORIES) {
      System.out.println("\t\t FILE: '"+ item.getName()+"' ");
      if (item.getName().trim().equals(category)) {
        System.out.println("REACHED");
        Parser target = new Parser(DB_PATH+"/"+category);
        value = target.pull_value(OBJECT_ITEM,field_name,id);
        return value;
      }
    }
    return value;
  }


}
