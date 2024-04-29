package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;

public class ContactWindow {
  
  final static int TITLEBAR_SIZE = 24;
  final static int PADDING = 10;

  public Rectangle geometry;
  public Rectangle content; 

  public String contact_info = 
    "PROJECT REPOSITORY: \n" + 
    "  > https://github.com/amuerta/pillua.git\n" +
    "\n" +
    
    "Project leader:\n" +
    " > @amuerta | email: notarealemail@gmail.com\n" + 
    
    "Project designer:\n" +
    " > @crim7   | email: verynotarealemail@gmail.com\n" + 
    "\n"
  ;

  public ContactWindow 
    (Rectangle geometry) 
  {
    this.geometry = geometry;
    this.content = new Rectangle()
      .x(geometry.x()+PADDING)
      .y(geometry.y()+PADDING)
      .width(geometry.width() - 2*PADDING)
      .height(geometry.height() - 2*PADDING);

  }

  public boolean run() 
  {
    boolean should_close = 
      GuiWindowBox(geometry,"#191# Contact Devs") == 1;
        GuiLabel(content, contact_info);

    return should_close;
  }

  

}

