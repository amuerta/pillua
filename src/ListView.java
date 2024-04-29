package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
import java.util.Arrays;
import module_gui.PreviewWindow;

// TODO:
//
//  - implement list search from passed List<T>
//  - return item on press or/and hover

public class ListView {
  static final int MAX_STR_LEN = 256;
  static final int SEARCH_BAR_SIZE = 40;
  static final int TITLEBAR_HEIGHT = 24;
  static final int PADDING  = 10;
  static final int NBUTTONS = 10;

  public String titlebar_string;

  public Rectangle geometry;
  public Rectangle list;
  public Rectangle search_bar;
  public Rectangle tooltip_geometry;

  public byte[] search_str = new byte[MAX_STR_LEN];
  public byte[] search_placeholder;
  public String items_concat;
  public boolean search_active ;

  public PreviewWindow tooltip;
  public Rectangle[] list_buttons = new Rectangle[NBUTTONS];

  public int[] hovered      = {-1};
  public int[] active       = {-1};

  public ListView(Rectangle geometry,Rectangle tooltip_geometry, String search_bar_preview, String titlebar_string) {
    this.geometry = geometry;
    this.tooltip_geometry = tooltip_geometry;
    this.titlebar_string = titlebar_string;
    this.items_concat = "one\ntwo\nthree\nfour\n";

    this.tooltip = new PreviewWindow(this.tooltip_geometry, false);

    // build string for search and ghost text as search_placeholder
    for(int i = 0; i < MAX_STR_LEN; i++)
      search_str[i] = 0;

    search_placeholder = search_bar_preview.getBytes();
    search_placeholder = Arrays.copyOf(search_placeholder, search_placeholder.length + 1);
    search_placeholder[search_placeholder.length - 1] = '\0';

    // geometry of elements

    this.search_bar = new Rectangle()
      .x(geometry.x() + PADDING)
      .y(geometry.y() + PADDING + TITLEBAR_HEIGHT)
      .width(geometry.width() - PADDING * 2)
      .height(SEARCH_BAR_SIZE);

    this.list = new Rectangle()
      .x(search_bar.x())
      .y(search_bar.y() + search_bar.height() + PADDING)
      .width(search_bar.width())
      .height(
          (geometry.height() - search_bar.height()) - PADDING*3 - TITLEBAR_HEIGHT
        );

    // building collsion geometries for buttons
    for(int i = 0; i < NBUTTONS; i++)
      list_buttons[i] = new Rectangle()
        .x( list.x() )
        .y(list.y() + i*(list.height()/10))
        .width(list.width())
        .height(list.height()/10);

  }

  public boolean run() {
    boolean window_close_call = 
      GuiWindowBox(geometry,titlebar_string) == 1;
 
 
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
    GuiListView(list,items_concat,hovered,active);
    

    // part that handles tooltip and right click
    if (active[0] >= 0)
    {
      int button_g = active[0];

      tooltip.geometry
        .x(list_buttons[button_g].x() + 4*PADDING)
        .y(list_buttons[button_g].y() + 4*PADDING);

      tooltip.run();

      if (
          IsMouseButtonPressed(MOUSE_BUTTON_RIGHT) 
          && 
            CheckCollisionPointRec(
              GetMousePosition(),
              list_buttons[button_g]
            ) 
        ) // TODO: 
          // add actual call to page;
        System.out.println("PRESSED RIGHT");
    }

    return window_close_call;
  }

}
