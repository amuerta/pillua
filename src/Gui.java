package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Raylib.*;


public class Gui {

  // test raylib is linked 
  public static void test_link() {
    SetConfigFlags(FLAG_WINDOW_UNDECORATED);
    InitWindow(800,600,"Hello from ja-");
    SetTargetFPS(60);

    Rectangle box = new Rectangle().x(0).y(0).width(800).height(600);

    while(!WindowShouldClose()) {
      BeginDrawing();
      ClearBackground(RAYWHITE);

      GuiWindowBox(box,"#198# WINDOW");
      EndDrawing();
    }
    CloseWindow();
  }

  

  // TopPanel

  // public class NavPanel {
  //   public Rectangle geometry;
  //
  //   public NavPanel(Rectangle geometry) {
  //     this.geometry = geometry;
  //   }
  //
  //   public void run() {
  //     GuiPanel(geometry,"Hello");
  //   }
  // }






}
