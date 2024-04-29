package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
import java.util.Arrays;

public class DefaultBackground {
  
  final static int PADDING = 10;
  final static int BUTTON_SIZE = 40;

  public Rectangle geometry;
  public Rectangle content;
  public Rectangle button;

  public DefaultBackground
    (Rectangle geometry) 
  {
    this.geometry = geometry;
    this.content = new Rectangle() 
      .x(geometry.x() + PADDING)
      .y(geometry.y() + PADDING)
      .width(geometry.width() - PADDING*2 )
      .height(geometry.height() - PADDING*3 - BUTTON_SIZE);
    this.button = new Rectangle()
      .x(content.x())
      .y(content.y()+content.height() + PADDING)
      .width(content.width())
      .height(BUTTON_SIZE);
  }

  
  public boolean run() 
  {

    String info = 
      "\n"+
      "Current offers avilable in storage: $NOE\n"+
      "\n"+
      "Total amount of items offered     : $TNOE\n"+
      "\n" + 
      "\n" +
      "             -- BEGIN SHOPPING --"
      ;

    int button_result;
    int should_close = 
      GuiGroupBox(geometry,"WELCOME TO PILLS UA!");
        GuiLabel(content,info);
        button_result = GuiButton(button,"Quick Search");
    return should_close == 1;
  }

  

}

