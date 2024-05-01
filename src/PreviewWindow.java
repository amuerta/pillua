package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;

public class PreviewWindow {
 
  public static final int PADDING    = 10;
  // public static final int label_size = 24;

  public Rectangle geometry;
  public Rectangle icon;
  public Rectangle content;
  public Rectangle info;
  public Rectangle status;
 
  public boolean follow_cursor;

  public PreviewWindow
    (Rectangle geometry, boolean follow_cursor) 
  {
    this.geometry = geometry;
    this.follow_cursor = follow_cursor;

    // inital geometry calulations
    calculate_geometry();
  }


  void calculate_geometry() 
  {
    // update cursor pos

    if (follow_cursor) {
      Vector2 mouse = GetMousePosition();
      this.geometry.x(mouse.x());
      this.geometry.y(mouse.y());
    }
    else
    {
      this.geometry.x(geometry.x());
      this.geometry.y(geometry.y());
    }

    this.icon = new Rectangle()
      .x(geometry.x() + PADDING)
      .y(geometry.y() + PADDING)
      .width(geometry.height() - 2*PADDING)
      .height(geometry.height() - 2*PADDING);

    this.content = new Rectangle()
      .x(icon.x() + icon.width() + PADDING)
      .y(icon.y())
      .width(geometry.width() - icon.width() - 3*PADDING)
      .height(icon.height());

    this.info = new Rectangle()
      .x(content.x() + PADDING)
      .y(content.y() + PADDING)
      .width(content.width() - 2*PADDING)
      .height(content.height() - 2*PADDING);

    this.status = new Rectangle()
      .x(icon.x() + icon.width() - icon.width()/2 - PADDING)
      .y(icon.y() + icon.height() - icon.height()/4 - PADDING)
      .width(icon.width() / 2)
      .height(icon.height() / 4);

  }


  public void run(boolean avil, String desc, String name, String icon_path) 
  {
    DrawRectangleRec(geometry,RAYWHITE); 
    GuiGroupBox(geometry, "Item info"); 
    {
      GuiGroupBox(icon, "*icon*");
      GuiGroupBox(content, name);
      GuiStatusBar(status,(avil)? "Avilabale" : "Sold Out");
      GuiLabel(info,"PRODUCT DESCRIPTION:"+"\n  "+desc);

    }
    calculate_geometry();
  }

  

}

