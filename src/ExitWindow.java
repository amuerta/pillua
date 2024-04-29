package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
// import java.util.Arrays;

public class ExitWindow {
 
  public static final int padding    = 20;
  public static final int label_size = 24;

  public Rectangle geometry;
  public Rectangle label;
  public Rectangle button_yes;
  public Rectangle button_no;
 
  public boolean[] exit_handle;

  public ExitWindow 
    (Rectangle geometry, boolean[] exit_handle) 
  {
    this.exit_handle = exit_handle;
    this.geometry = geometry;

    this.label = new Rectangle()
      .x(geometry.x() + padding )
      .y(geometry.y() + padding + label_size)
      .width(geometry.x() - padding*2)
      .height(geometry.y()/2 - padding*2 - label_size);


    this.button_yes = new Rectangle()
      .x(label.x() )
      .y(label.y() + label.height() + padding )
      .width(geometry.x()/2 - padding*2)
      .height(geometry.y()/2 - padding*2  );

    this.button_no = new Rectangle()
      .x(label.x() + button_yes.width() + 2*padding )
      .y(label.y() + label.height() + padding )
      .width(geometry.x()/2 - padding*2)
      .height(geometry.y()/2 - padding*2  );
  }


  public boolean run() 
  {
    boolean window_close_call =

    GuiWindowBox(geometry, "#159# Exit") == 1;
      GuiLabel(label,"You sure wanna quit program?");
      if (GuiButton(button_yes,"Yes")==1)
        this.exit_handle[0] = true;

      if (GuiButton(button_no,"No")==1)
        return true;

      return window_close_call;
  }

  

}

