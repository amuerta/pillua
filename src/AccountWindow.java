package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
import java.util.Arrays;
import module_gui.TextBox;

// TODO:
//
//  - implement login system
//  - upload user image

public class AccountWindow extends Dragable {
  static final int MAX_STR_LEN = 256;
  static final int SEARCH_BAR_SIZE = 40;
  static final int TITLEBAR_HEIGHT = 24;
  static final int PADDING = 10;

  public String titlebar_string;

  public Rectangle geometry;
  public Rectangle name_text;
  public Rectangle password_text;
  public Rectangle picture_frame;
  public Rectangle picture;
  public Rectangle login_button;

  // DEPARCATED:
  // + refactored TextBox routine 
  // into separate class for convinience

  TextBox name_textbox;
  // public byte[] name_str = new byte[MAX_STR_LEN];
  // public byte[] password_str = new byte[MAX_STR_LEN];
  
  TextBox password_textbox;
  // public byte[] name_placeholder;
  // public byte[] password_placeholder;
 
  public String items_concat;
  public boolean search_active ;

  public int[] hovered      = {-1};
  public int[] active       = {-1};

  public AccountWindow(Rectangle geometry) {

    // Dragable extention
    super(geometry);


    this.geometry = geometry;
    this.titlebar_string = "Manage user Account";
    
    // inital geometry calculation

    calculate_geometry();

  }

  @Override public void calculate_geometry() 
  {
    // baseline element here
    this.picture_frame = new Rectangle()
      .x(
          geometry.x() + PADDING + 
          ((geometry.width() - 2*PADDING) / 2) / (float)3
          )
      .y(geometry.y() + PADDING + TITLEBAR_HEIGHT)
      .width((float)(geometry.width() - 2*PADDING) / (float)1.5)
      .height((float)(geometry.width() - 2*PADDING) / (float)1.5);
    
    this.picture = new Rectangle()
      .x(picture_frame.x() + PADDING )
      .y(picture_frame.y() + PADDING )
      .width(picture_frame.width() - PADDING*2)
      .height(picture_frame.height() - PADDING*2);

    this.name_text = new Rectangle()
      .x(picture_frame.x())
      .y(picture_frame.y() + picture_frame.height() + PADDING)
      .width(picture_frame.width())
      .height(SEARCH_BAR_SIZE);

    this.password_text = new Rectangle()
      .x(name_text.x())
      .y(name_text.y() + SEARCH_BAR_SIZE + PADDING)
      .width(name_text.width())
      .height(name_text.height());

    this.login_button = new Rectangle() 
      .x(password_text.x())
      .y(password_text.y() + password_text.height() + PADDING)
      .width(name_text.width())
      .height(name_text.height()*(float)1.5);
   

    if (name_textbox == null) {
      this.name_textbox     = new TextBox(name_text,"Enter username");
      this.password_textbox = new TextBox(password_text,"Enter password for user");
    }
    else {
      this.name_textbox.geometry     = name_text;
      this.password_textbox.geometry = password_text;
    }
  }

  public boolean run() {
    boolean window_close_call = 
      GuiWindowBox(geometry,titlebar_string) == 1;
        GuiGroupBox(picture_frame,"Current user: $USER");
        GuiGroupBox(picture,"User pfp placeholder");

        name_textbox.run();
        password_textbox.run();

        boolean login = 
          GuiButton(login_button,"LOGIN") == 1;

        if(login) 
          // TODO
        ;
        

        // handle titlebar drag event :: Dragable extention
        drag_titlebar();

    return window_close_call;
  }

}
