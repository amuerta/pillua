package module_gui;

import static com.raylib.Raylib.*;


public class Dragable {
  
  Rectangle geometry;
  static final int TITLEBAR_SIZE = 24;

  public Dragable(Rectangle geometry) {
    this.geometry = geometry;
  }

  public void calculate_geometry() {}

  void drag_titlebar() 
  {
    Rectangle bounding_box = new Rectangle()
      .x(geometry.x())
      .y(geometry.y())
      .width(geometry.width())
      .height(TITLEBAR_SIZE);

    Vector2 mouse = GetMousePosition();

    if ( IsMouseButtonDown(MOUSE_BUTTON_LEFT) 
         && CheckCollisionPointRec(mouse,bounding_box)   )
    {

        Vector2 mouse_delta = GetMouseDelta();
        this.geometry
          .x(geometry.x() + mouse_delta.x())
          .y(geometry.y() + mouse_delta.y());

        // recalculate window geometry
        calculate_geometry();
    }
  }
}
