package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;

import module_gui.Dragable;

public class ItemWindow extends Dragable {
 
  public static final int PADDING    = 10;
  public static final int LABEL_SIZE = 24;

  public Rectangle geometry;
  public Rectangle icon;
  public Rectangle content;
  public Rectangle content_text;
  public Rectangle buttons;
  public Rectangle button_buy;
  public Rectangle button_cart;
  public Rectangle button_bookmark;


  public boolean follow_cursor;

  public ItemWindow
    (Rectangle geometry) 
  {
    super(geometry);
    this.geometry = geometry;
    this.follow_cursor = follow_cursor;

    // inital geometry calulations
    calculate_geometry();
  }


  @Override 
    public void calculate_geometry() 
  {
    this.icon = new Rectangle()
      .x(geometry.x() + PADDING)
      .y(geometry.y() + PADDING + LABEL_SIZE)
      .width( (geometry.width() - 2*PADDING) / 3)
      .height((geometry.width() - 2*PADDING) / 3);

    this.buttons = new Rectangle()
      .x(icon.x())
      .y(icon.y() + icon.height() + PADDING)
      .width(icon.width())
      .height(geometry.height() - icon.height() - PADDING*3 - LABEL_SIZE);

    this.content = new Rectangle()
      .x(icon.x() + icon.width() + PADDING )
      .y(icon.y())
      .width(geometry.width() - icon.width() - PADDING*3)
      .height(geometry.height() - PADDING*2 - LABEL_SIZE);

    this.content_text = new Rectangle()
      .x(content.x() + PADDING)
      .y(content.y() + PADDING)
      .width(content.width() - 2*PADDING)
      .height(content.height() - 2*PADDING);

    float button_height = ( buttons.height() - PADDING*4 ) / 3 ;
    float button_width = buttons.width() - PADDING*2;

    this.button_buy = new Rectangle()
      .x(buttons.x()+PADDING)
      .y(buttons.y()+PADDING)
      .width(button_width)
      .height(button_height);
    this.button_cart = new Rectangle()
      .x(button_buy.x())
      .y(button_buy.y() + button_height + PADDING)
      .width(button_width)
      .height(button_height);
    this.button_bookmark = new Rectangle()
      .x(button_cart.x())
      .y(button_cart.y() + button_height + PADDING)
      .width(button_width)
      .height(button_height);
  }


  public boolean run() 
  {
    // DrawRectangleRec(geometry,RAYWHITE); 
    int win_close_call = 
      GuiWindowBox(geometry, "Item info"); 
    {
      GuiGroupBox(icon, "*icon*");
      GuiGroupBox(content, "*content*");
      GuiGroupBox(buttons, "*buttons*");

      if (GuiButton(button_buy, "Buy") == 1)
        ;

      if (GuiButton(button_cart, "Add to cart") == 1)
        ;

      if (GuiButton(button_bookmark, "Add to bookmarks") == 1)
        ;
    }
    drag_titlebar();

    return win_close_call == 1;
  }

  

}

