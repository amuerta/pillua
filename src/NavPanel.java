package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;


// TopPanel

public class NavPanel {
  public Rectangle geometry;
  public Rectangle menu_button;
  public Rectangle user_button;
  public Rectangle user_window_geometry;
  public Rectangle menu_window_geometry;
  public Rectangle title;
  public int[] active ;

  boolean[] button_user = {false};
  boolean[] button_menu = {false};

  final static int item_size = 30;
  final static int MENU_INDEXES_OFFSET = 5;
  String menu_items = "Quick Search\nBuy\nBookmarks\nCart\nContact";
  String user_items = "Account\nSettings\nAbout\nExit";

  public NavPanel(Rectangle geometry, int[] selected_item) {
    
    Vector2 windowsize_menu = new Vector2().x(100).y(item_size * 5);
    Vector2 windowsize_user = new Vector2().x(100).y(item_size * 4);

    int padding = (int)geometry.height() / 8;
    int button_size = (int)geometry.height();

    this.geometry = geometry;
    this.menu_button = new Rectangle()
      .x(padding)
      .y(padding)
      .width((int)(1.5*button_size) - 2*padding)
      .height(button_size - 2*padding);
 
    this.user_button = new Rectangle()
      .x(geometry.width()-((int)(1.5 * button_size)))
      .y(padding)
      .width((int)(1.5*button_size) - 2*padding)
      .height(button_size - 2*padding);

    this.title = new Rectangle()
      .x(0)
      .y(0)
      .width(geometry.width())
      .height(geometry.height());

    this.menu_window_geometry = new Rectangle()
      .x(0 + padding)
      .y(geometry.height() + padding)
      .height(windowsize_menu.y())
      .width(windowsize_menu.x());

    this.user_window_geometry = new Rectangle()
      .x(geometry.width() - windowsize_user.x() - padding)
      .y(geometry.height() + padding)
      .height(windowsize_user.y())
      .width(windowsize_user.x());
  }

  public int run() {
    // Bar background
    GuiStatusBar(title,"");

    // LABELS
    //  - UA
    DrawText("UA",
        (int)(geometry.width() - MeasureText("UA",(int)geometry.height())) / 2,
        4,// (int)geometry.height(),
        (int)geometry.height(),
        RAYWHITE);
    //  - Pills
    DrawText("P I L L S",
        (int)(geometry.width() - MeasureText("P I L L S",(int)geometry.height()/2)) / 2,
        4 + (int)geometry.height() / 4,
        (int)geometry.height() / 2,
        VIOLET);
  
    // Handle input of buttons
    GuiToggle(user_button,"#181# User",button_user);
    GuiToggle(menu_button,"#102# Menu",button_menu);

    if (button_user[0]) 
      button_menu[0] = false;
    if (button_menu[0]) 
      button_user[0] = false;
 
    // draw list
    if (button_menu[0]) 
    {
      int[] index = {0};
      int[] active = {-1};
      int[] focus;
      GuiListView(menu_window_geometry,menu_items,index,active);

      // if user pressed any item -> close 
      if (active[0] >= 0)
        button_menu[0] = false;

      return MENU_INDEXES_OFFSET + active[0];
    }

    if (button_user[0])
    {
      int[] index = {0};
      int[] active = {-1};
      int[] focus;
      GuiListView(user_window_geometry,user_items,index,active);

      // if user pressed any item -> close 
      if (active[0] >= 0)
        button_user[0] = false;

      return active[0];
    }

    return -1;
  }

}

