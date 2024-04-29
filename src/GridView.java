package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
import java.util.Arrays;

// TopPanel

public class GridView {
  static final int MAX_STR_LEN = 256;
  static final int padding = 10;

  public Rectangle geometry;
  public Rectangle grid;
  public Rectangle search_bar;
  public Rectangle button_up;
  public Rectangle button_down;

  public String items; 
  public byte[] search_str = new byte[MAX_STR_LEN];
  public byte[] search_placeholder;// = 

  boolean search_active = false;

  Vector2 mouse = new Vector2();
  int cell_size = 175;
  int bar_offset = 50;
  Vector2 grid_size = new Vector2().x(0).y(0);


  public GridView(Rectangle geometry, int[] selected_item, String search_bar_preview) {
    this.geometry = geometry;
    this.items = "";//"one\ntwo\nthree\nfour\n";

    while(grid_size.x() < geometry.width()) {
      grid_size.x(grid_size.x() + cell_size); 
    }

    while(grid_size.y() < geometry.height()- bar_offset) {
      grid_size.y(grid_size.y() + cell_size); 
    }

    grid_size.y(grid_size.y()-cell_size);
    grid_size.x(grid_size.x()-cell_size);

    this.grid = new Rectangle()
      .x( geometry.x() + (geometry.width() - grid_size.x()) / 2 )
      .y( bar_offset + geometry.y() + (geometry.height() - grid_size.y()) / 2 )
      .width(grid_size.x())
      .height(grid_size.y());

    this.search_bar = new Rectangle()
      .x( geometry.x() + (geometry.width() - grid_size.x()) / 2 )
      .y( geometry.y() + (geometry.height() - grid_size.y()) / 2 - bar_offset/2)
      .width(grid_size.x() - bar_offset*2 - padding*2)
      .height(bar_offset);

    this.button_up = new Rectangle()
      .x( search_bar.x() + search_bar.width() + padding )
      .y( search_bar.y())
      .width(bar_offset)
      .height(bar_offset);

    this.button_down = new Rectangle()
      .x( button_up.x() + button_up.width() + padding )
      .y( search_bar.y())
      .width(bar_offset)
      .height(bar_offset);

    for(int i = 0; i < MAX_STR_LEN; i++)
      search_str[i] = 0;

    search_placeholder = search_bar_preview.getBytes();
    search_placeholder = Arrays.copyOf(search_placeholder, search_placeholder.length + 1);
    search_placeholder[search_placeholder.length - 1] = '\0';
  }

  public boolean run() {
    // Grid window  (box was badly readable)
    int should_close =
      GuiGroupBox(geometry,"Search in categories");
 
    GuiButton(button_up  , "#121#");
    GuiButton(button_down, "#120#");

    int byte_str_len = 0;

    for(int i = 0; i < MAX_STR_LEN && search_str[i] !='\0'; i++)
      byte_str_len++;

    int s_state = GuiTextBox(
        search_bar,
        (!search_active && byte_str_len < 1) ? search_placeholder : search_str,
        MAX_STR_LEN,
        search_active //(byte_str_len >= MAX_STR_LEN - 1)? false : search_active
    );
      
    if (s_state == 1 ) 
      search_active = !search_active;
    
    GuiGrid(grid,items,cell_size,1,mouse);
  
    return should_close == 1;

  }

}



