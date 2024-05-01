package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;

import module_gui.PreviewWindow;

import module_data.Database;

import java.util.Arrays;
import java.util.ArrayList;

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

  public ArrayList<String[]> items_buffer; 
  public String             category_name; 

  public int[] hovered      = {-1};
  public int[] active       = {-1};


  public ItemWindow     subwindow ;
  public boolean subwindow_active ;

  public ListView(Rectangle geometry,Rectangle tooltip_geometry, String search_bar_preview, String titlebar_string, String category_name) {
    this.geometry = geometry;
    this.tooltip_geometry = tooltip_geometry;
    this.titlebar_string = titlebar_string;
    this.items_concat = "one\ntwo\nthree\nfour";

    this.tooltip = new PreviewWindow(this.tooltip_geometry, false);

    this.items_buffer = new ArrayList<String[]> ();
    this.category_name = category_name;

    this.subwindow = null;
    this.subwindow_active = false;

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

  public void add_subwindow(ItemWindow win) {
    this.subwindow = win;
  } 

  public String cstr_to_string() {
    String buffer = "";
    for (int i = 0;  MAX_STR_LEN < i || search_str[i] != '\0' ; i++ )
        buffer += (char)search_str[i];
    return buffer;
  }

  public boolean run() {
    
    // rebuild string and item buffer
    String sbar = cstr_to_string();
    items_buffer.clear();
    items_buffer = new Database().search(category_name,sbar);

    // rebuild list appearance

    final int DBID  = 0;
    final int NAME  = 1;
    final int DESC  = 2;
    final int AVIL  = 3;
    final int CATG  = 4;
    final int PICT  = 5;

    items_concat = "";
    for (int i = 0; i < items_buffer.size(); i++) {
      items_concat += items_buffer.get(i)[NAME];
        if (i < items_buffer.size() - 1)
          items_concat += "\n";
    }



    // WINDOW 
    //
    boolean window_close_call = 
      GuiWindowBox(geometry,titlebar_string) == 1;
 
    int byte_str_len = 0;

    for(int i = 0; i < MAX_STR_LEN && search_str[i] !='\0'; i++)
      byte_str_len++;

    // IF WINDOW INACTIVE DRAW DUMMY BOX
    if (subwindow_active) {
      GuiDummyRec(search_bar,"");
    } 
    else {
      int s_state = GuiTextBox(
          search_bar,
          (!search_active && byte_str_len < 1) ? search_placeholder : search_str,
          MAX_STR_LEN,
          search_active //(byte_str_len >= MAX_STR_LEN - 1)? false : search_active
          );

      if (s_state == 1 ) 
        search_active = !search_active;
    }

    if (subwindow_active)
      GuiDummyRec(list,"");
    else
      GuiListView(list,items_concat,hovered,active);
    

    // part that handles tooltip and right click
    if (active[0] >= 0 && !subwindow_active)
    {
      int button_g = active[0];

      tooltip.geometry
        .x(list_buttons[button_g].x() + 4*PADDING)
        .y(list_buttons[button_g].y() + 4*PADDING);

      String[] item = items_buffer.get(active[0]);

      tooltip.run(
          item[AVIL].equals("true"), 
          item[DESC],
          item[NAME],
          item[PICT]
        );

      if (
          IsMouseButtonPressed(MOUSE_BUTTON_RIGHT) 
          && 
            CheckCollisionPointRec(
              GetMousePosition(),
              list_buttons[button_g]
            ) 
        ) // TODO: 
          // [DONE] add actual call to page; 
          //        make window elements mask when subwin shown
      {
        subwindow_active = !subwindow_active;
        System.out.println("CALL SUBWINDOW");
      }
    }



    // SUBWINDOW 
    //
    if (subwindow != null && subwindow_active)
      subwindow_active = !subwindow.run();

    return window_close_call;
  }

}
