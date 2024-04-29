package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;


// TopPanel

public class SettingsWindow extends Dragable {
  static final int  titlebar_size = 24;
  static final int  padding       = 10;
  static final int  button_size   = 40;

  public Rectangle   geometry         ;
  public Rectangle   sidebar          ;
  public Rectangle   button_save      ;
  public Rectangle   button_reset     ;
  public Rectangle   group_border     ;
  public String   [] empty_labels     ;
  public Rectangle[] labels_geometry  ;

  public String      categories  ;
  public String[][]  parametrs   ;

  int[] index = {0}    ;
  int[] active = {-1}  ;

  public SettingsWindow(Rectangle geometry, String[][] config) {

    // for Dragable class extention
    super(geometry);

    this.categories = 
      "Apperance\n" + "Accesability\n" + 
      "Behaviour\n" + "Performance"    ;

    this.geometry = geometry;
    this.parametrs = config;


    // Lables

    this.empty_labels = new String[] {
      "Select any of categories to display thier settings",
        "Press Save to write changes for future use",
        "Press Reset to overwrite current settings with defaults" 
    };
    
    // initial calculate of geometry
    calculate_geometry();
  }

  @Override public void calculate_geometry() {
    float side_panel_height  = geometry.height()-titlebar_size-padding*2; 
    float side_panel_width =  geometry.width()/5 - padding * 2;

    this.sidebar = new Rectangle()
      .x(geometry.x() + padding )
      .y(geometry.y() + titlebar_size + padding )
      .width(side_panel_width)
      .height(side_panel_height - button_size * 2 - padding * 2);

    this.button_reset = new Rectangle()
      .x(geometry.x() + padding)
      .y(geometry.y() + geometry.height() - padding - button_size)
      .width(side_panel_width)
      .height(button_size);

    this.button_save = new Rectangle()
      .x(geometry.x() + padding)
      .y(button_reset.y() - button_size - padding)
      .width(side_panel_width)
      .height(button_size);

    this.group_border = new Rectangle()
      .x(geometry.x() + side_panel_width + padding * 2)
      .y(geometry.y() + padding + titlebar_size)
      .width(geometry.width() - side_panel_width - padding*3)
      .height(side_panel_height);

    this.labels_geometry = new Rectangle[empty_labels.length];

    // calculate labels
    for(int i = 0; i < empty_labels.length; i++) 
      this.labels_geometry[i] = new Rectangle()
        .x(group_border.x() + padding + group_border.width() / 4 )
        .y(group_border.y() + padding + (int)(group_border.height() / 3) )
        .width(group_border.width())
        .height(button_size * i + padding * i);
  }

  public boolean run() {
    
    boolean window_close_call = 

    // window
    GuiWindowBox(geometry,"#142# Settings") == 1;

    // categories
    GuiListView(sidebar,categories,index,active);
    
    // save and reset buttons
    int save  = GuiButton(button_save, "#2# Save"  );
    int reset = GuiButton(button_reset,"#74# Reset");

    switch (active[0]) {
      case 0:
        {
          GuiGroupBox(group_border, "Apperance Settings");
        }
        break;

      case 1: 
        {
          GuiGroupBox(group_border, "Accesability Settings");
        }
        break;

      case 2: 
        {
          GuiGroupBox(group_border, "Behaviour Settings");
        }
        break;

      case 3:
        {
          GuiGroupBox(group_border, "Performance Settings");
        }
        break;

      default:
        {
          for(int i = 0; i < empty_labels.length; i++)
            GuiLabel(labels_geometry[i], empty_labels[i]);
        }
        break;
    }

    drag_titlebar();

    return window_close_call;
  }

}

