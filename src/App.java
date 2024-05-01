import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Raylib.*;

import module_gui.Gui;
import module_gui.NavPanel;
import module_gui.GridView;
import module_gui.ListView;
import module_gui.SettingsWindow;
import module_gui.AboutWindow;
import module_gui.ExitWindow;
import module_gui.AccountWindow;
import module_gui.ContactWindow;
import module_gui.DefaultBackground;
import module_gui.PreviewWindow;
import module_gui.ItemWindow;

import module_data.Parser;
import module_data.Database;

import java.util.ArrayList;

// # Todo
//
// - git repo
//
// v state 
// v exit window
// v category arrows for navigating
// v category search 
// v list (quick search, cart, bookmarks) search panel
// v list ~hover~ PRESSED tooltip element
// - settings elements
// v contact panel
// v about panel
// v account panel
// v titlebar move
// - headerbar move
// 
// - pfp loading
// - account loader 
// - user dir
// - search class
// - settings / config class
// - database manager class
// - plug all above to GUI
//
// SETTINGS PANEL:
// - theme
// - font
// - titlebar height 
// - window shadows
// - custom cursor
// - no icons mode
// - disable account 

public class App {
  
  // CONSTANTS
  final static int    WIN_FPS   = 60;
  final static String WIN_LABEL = "Pills UA";

  final static int    MENU_OFFSET = 5;

  // final static int    SEARCH    = 0;
  // final static int    BUY       = 1;

  // TWEAKABLE
  static int PANEL_HEIGHT = 55;
  static int WIN_W        = 800;
  static int WIN_H        = 600;

  // CONFIG
  static String[][] config = { {} };

  // GLOBAL STATE MENU
  static int[] page_selected   = {-1};
  static int[] item_selected   = {-1};

  public static void main(String[] args) {
    //
    // State 
    //

      // panel calls
    final int NAVPANEL_CALL_NONE  = -1;
    
      // USER MENU
    final int NAVPANEL_CALL_ACCOUNT  =  0;
    final int NAVPANEL_CALL_SETTINGS =  1;
    final int NAVPANEL_CALL_ABOUT    =  2;
    final int NAVPANEL_CALL_EXIT     =  3;

      // GENERAL menu

    final int NAVPANEL_CALL_QSEARCH   = MENU_OFFSET + 0;
    final int NAVPANEL_CALL_BUY       = MENU_OFFSET + 1;
    final int NAVPANEL_CALL_BOOKMARKS = MENU_OFFSET + 2;
    final int NAVPANEL_CALL_CART      = MENU_OFFSET + 3;
    final int NAVPANEL_CALL_CONTACT   = MENU_OFFSET + 4;

    int panel_call = NAVPANEL_CALL_NONE; 

      // close signals states
    boolean shouldclose_about      = !false;
    boolean shouldclose_account    = !false;
    boolean shouldclose_settings   = !false;
    boolean shouldclose_exit       = !false;

    boolean shouldclose_search     = false;
    boolean shouldclose_categories = !false;
    boolean shouldclose_bookmarks  = !false;
    boolean shouldclose_cart       = !false;
    boolean shouldclose_contact    = !false;

    //
    // CREATE WINDOW
    //

    boolean[] exit_trigger = {false};
    // SetConfigFlags(FLAG_WINDOW_RESIZABLE);
    InitWindow(WIN_W,WIN_H,WIN_LABEL);
    SetTargetFPS(WIN_FPS);



    //
    // ELEMENTS GEOMETRY
    //

    int avrg_padding = 10;

    Rectangle navpan_g = new Rectangle().x(0).y(0).width(WIN_W).height(PANEL_HEIGHT);

    Rectangle content_view_g = new Rectangle()
      .y(PANEL_HEIGHT + avrg_padding)
      .x(avrg_padding)
      .width(WIN_W - avrg_padding*2)
      .height(WIN_H-PANEL_HEIGHT-avrg_padding*2);

    Rectangle content_view_window_g = new Rectangle()
      .y(PANEL_HEIGHT + avrg_padding * 2)
      .x(avrg_padding + (float)(WIN_W * 0.1 * 0.5))
      .width((float)(WIN_W * 0.9) - (float)(avrg_padding*2))
      .height((float) (WIN_H * 0.9) - (float)(PANEL_HEIGHT-avrg_padding*2));

    Rectangle content_halfview_g = new Rectangle()
      .y(PANEL_HEIGHT + (float)(WIN_H - PANEL_HEIGHT - WIN_H/1.5) / 2)
      .x((float)(WIN_W - WIN_W/1.5) / 2)
      .width((float) (WIN_W / 1.5))
      .height((float)(WIN_H / 1.5));

    Rectangle content_halfview_offseted_g = new Rectangle()
      .y(PANEL_HEIGHT + (float)(WIN_H - PANEL_HEIGHT - WIN_H/1.5) / 2 - (PANEL_HEIGHT/2))
      .x((float)(WIN_W - WIN_W/1.5) / 2)
      .width((float) (WIN_W / 1.5))
      .height((float)(WIN_H / 1.5));

    Rectangle content_smallview_g = new Rectangle()
      .y(PANEL_HEIGHT + (float)(WIN_H - PANEL_HEIGHT - WIN_H/3) / 2)
      .x((float)(WIN_W - WIN_W/3) / 2)
      .width((float) (WIN_W / 3))
      .height((float)(WIN_H / 3));

    Rectangle content_narrow_g = new Rectangle()
      .y(PANEL_HEIGHT + (float)(WIN_H - PANEL_HEIGHT - WIN_H/1.5) / 2)
      .x((float)(WIN_W - WIN_W/3) / 2)
      .width((float) (WIN_W / 3))
      .height((float)(WIN_H / 1.5));


    // Dynamic elements

    Rectangle tooltip_big_g = new Rectangle()
      .x(0)
      .y(0)
      .width(400)
      .height(200);


    // CREATE ELEMENTS
    
    // panel
    NavPanel navpan     = new NavPanel(navpan_g,page_selected);
    ItemWindow product = new ItemWindow(content_halfview_g);

    // tooltips
    // PreviewWindow item_tooltip = new PreviewWindow(tooltip_big_g);

    // List/Grid elements
    // (qsearch, categories, bookmarks, cart)
    
    GridView categories = new GridView(content_view_g,item_selected,"#180# Product search"); 
    
    ListView search     = new ListView(
        content_view_window_g,
        tooltip_big_g,
        "#42# Search all products", 
        "Search product in DATABASE",
        null
      );
    search.add_subwindow(product);

    ListView bookmarks = new ListView(
        content_view_window_g,
        tooltip_big_g,
        "#42# Search bookmarked", 
        "Search product in BOOKMARKS",
        "BOOKMARKS"
      );
    ListView cart  = new ListView(
        content_view_window_g, 
        tooltip_big_g,
        "#42# Search in cart", 
        "Search product in SHOPPING CART",
        "BOOKMARKS"
      );


    ContactWindow contact = new ContactWindow(content_smallview_g);

    // USER windows

    SettingsWindow settings = new SettingsWindow(content_view_window_g,config);
    AboutWindow about = new AboutWindow(content_halfview_offseted_g);
    AccountWindow account = new AccountWindow(content_narrow_g);
    ExitWindow exit = new ExitWindow(content_smallview_g,exit_trigger);



    // DEBUG
    Parser p = new Parser("./meta/app.cfg");
    String str = p.pull_value("APPEARANCE","draw_shadows",null);

    Parser ps = new Parser("./meta/db/pills");
    String strs = ps.pull_value("ENTRY","name","1");

    System.out.println("SEARCH : ["+ strs +"]");
    Database db = new Database();
    ArrayList<String[]> s =  db.search("MAIN","Head");
    
    for(int i = 0; i < s.size(); i++)
    {
      System.out.println("\nFOUND ENTRY ["+ i +"]: \n");
      for(int j = 0; j < 6; j++)
        System.out.println("\t >" + s.get(i)[j]);
    }

       // System.out.println("SEARCH : ["+s[0] +","+ s[1] +"]");
       // String val = db.getvalue_from_item("pills","1","name");



    // if (str != null)
      // System.out.println("\n value: " + val + "\n\n");

    while(!WindowShouldClose()) {
      BeginDrawing();
      ClearBackground(RAYWHITE);

        //UPDATE BLOCK 
        {

          // greater windows / categories (may resize, take content space)

          if (!shouldclose_search) {
            shouldclose_search = search.run();
            shouldclose_categories = true;
          }

          // top

          if (!shouldclose_bookmarks)
          {
            shouldclose_bookmarks = bookmarks.run();
            shouldclose_cart = true;
            shouldclose_categories = true;
          }

          if (!shouldclose_cart)
          {
            shouldclose_cart = cart.run();
            shouldclose_bookmarks = true;
            shouldclose_categories = true;
          }

          // bot

          if (!shouldclose_categories) {
            shouldclose_categories = categories.run();
            shouldclose_cart = true;
            shouldclose_bookmarks = true;
          }


          // lesser windows (unresiable, just a small window)

          // MENU

          if (!shouldclose_contact)
            shouldclose_contact = contact.run();

          // TEMP
          // product.run();

          // USER

          if (!shouldclose_account)
            shouldclose_account = account.run();

          if (!shouldclose_settings)
            shouldclose_settings = settings.run();
          
          if (!shouldclose_about)
            shouldclose_about = about.run();
  
          if (!shouldclose_exit)
            shouldclose_exit = exit.run();


          // item_tooltip.run();

          
          // should run above everything
          switch ( navpan.run()) 
          {
              // MENU

            case NAVPANEL_CALL_QSEARCH: 
              shouldclose_search=!shouldclose_search;
              shouldclose_categories=true;
              shouldclose_cart = true;
              shouldclose_bookmarks = true;
              break;

            case NAVPANEL_CALL_BUY: 
              shouldclose_categories=!shouldclose_categories;
              shouldclose_cart = true;
              shouldclose_bookmarks = true;
              shouldclose_search = true;
              break;
            
            case NAVPANEL_CALL_BOOKMARKS: 
              shouldclose_bookmarks=!shouldclose_bookmarks;
              shouldclose_categories =true;
              shouldclose_search = true;
              shouldclose_cart = true;
              break;

            case NAVPANEL_CALL_CART: 
              shouldclose_cart=!shouldclose_cart;
              shouldclose_bookmarks = true;
              shouldclose_search = true;
              shouldclose_categories = true;
              break;
             
            case NAVPANEL_CALL_CONTACT: 
              shouldclose_contact=!shouldclose_contact;
              break;
              
              // USER

            case NAVPANEL_CALL_ACCOUNT: 
              shouldclose_account=!shouldclose_account;
              break;

            case NAVPANEL_CALL_ABOUT:
              shouldclose_about=!shouldclose_about;
              break;

            case NAVPANEL_CALL_EXIT:
              shouldclose_exit=!shouldclose_exit;
              break;

            case NAVPANEL_CALL_SETTINGS:
              shouldclose_settings=!shouldclose_settings;
              break;

            default: // NAVPANEL_CALL_NONE
              break;
          }

          // if pressed yes in exit window
          if (exit_trigger[0])
            break;

        }

      EndDrawing();
    }

  }
}
