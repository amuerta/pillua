package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
import java.util.Arrays;
import module_gui.Dragable;

public class AboutWindow extends Dragable {
  
  final static int TITLEBAR_SIZE = 24;
  final static int padding = 10;

  public Rectangle geometry;
  public Rectangle picture;
  public Rectangle credits;
  public Rectangle about;
  public Rectangle credits_label;
  public Rectangle about_label;
  int gif_frames[] = {0}; // ptr
  
  Image framepic_image;
  Texture framepic_texture;

  public AboutWindow
    (Rectangle geometry) 
  {
    // plugs in logic for dragability 
    // of the element
    super(geometry);

    this.geometry = geometry;
    // calculate inital window geometry
    calculate_geometry();

    // picture loading
    this.framepic_image= LoadImageAnim("src/res/about.gif",gif_frames);
    ImageResize(this.framepic_image, (int)picture.width(), (int)picture.height());
    this.framepic_texture = LoadTextureFromImage(framepic_image);
  }

  public void draw_image() {
    DrawTexture(
        framepic_texture, 
        (int)this.picture.x(),
        (int)this.picture.y(),
        RAYWHITE
      );
  }


  @Override public void calculate_geometry()
  {
    this.picture = new Rectangle()
      .x     ((int)this.geometry.x() + padding)
      .y     ((int)this.geometry.y() + padding + TITLEBAR_SIZE)
      .width (200)
      .height(200);
      // .width (this.geometry.height() / 2 )
      // .height(this.geometry.height() / 2 );

    this.credits = new Rectangle()
      .x(picture.x() + picture.width() + padding)
      .y(picture.y())
      .width(geometry.width() - 3*padding - picture.width())
      .height(picture.height());

    this.about = new Rectangle()
      .x(picture.x())
      .y(picture.y()+picture.width()+padding)
      .width(geometry.width() - 2*padding)
      .height(geometry.height() - picture.height() - TITLEBAR_SIZE - 3*padding);

    this.credits_label = new Rectangle()
      .x(credits.x() + padding)
      .y(credits.y() + padding)
      .width(credits.width() - padding*2)
      .height(credits.height() - padding*2);

    this.about_label = new Rectangle()
      .x(about.x() + padding)
      .y(about.y() + padding)
      .width(about.width() - padding*2)
      .height(about.height() - padding*2);
  }

  // void drag_titlebar() 
  // {
  //   Rectangle titlebar_bounding_box = new Rectangle()
  //     .x(geometry.x())
  //     .y(geometry.y())
  //     .width(geometry.width())
  //     .height(TITLEBAR_SIZE);
  //
  //   Vector2 mouse = GetMousePosition();
  //
  //   if ( IsMouseButtonDown(MOUSE_BUTTON_LEFT) 
  //        && CheckCollisionPointRec(mouse,titlebar_bounding_box)   )
  //   {
  //
  //       Vector2 mouse_delta = GetMouseDelta();
  //       this.geometry
  //         .x(geometry.x() + mouse_delta.x())
  //         .y(geometry.y() + mouse_delta.y());
  //
  //       // recalculate window geometry
  //       calculate_geometry();
  //   }
  // }



  public boolean run() 
  {
    // labels 
    String credits_s = 
      "GUI DESIGN  : \n Myroslav Shpak (@amuerta), Malyga Max (@Cr1m7)\n" +
      "SOURCE CODE : \n Myroslav Shpak (@amuerta)\n" +
      "LIBRARY USED: \n Raylib and Raygui (@raysan5)\n > used under zlib liscence (non commercial use)\n" +
      "\n" + 
      "Special thanks to @raysan5 for creating \n" +  
      "one of the best graphical libraries of all time." + 
      "\n \n" + 
      "The picture is lemurian from our favourite game:\n     Risk of rain 2"
      ;

    String about_s = 
      "Program was made as a Java course project for KNUCA (formely known as KNUBA) university\n" +
      "The goal was to make a dummy program that is capable of `selling` pharma aids\n" +
      "Program features API socket written in java for external communication, GUI modules and \n" +
      "full raylib theme support.\n" +
      "\n" +
      "This software was never meant for actual use, if you found it somehow, it can only be used as\n" +
      "sort of example of how to use java with Jaylib (Java Raylib binding).\n" + 
      "This program sourcecode will be avilable on my github account (github/amuerta/pillua.git)\n" +
      "and is free to: download, modify, public, sell, etc."
      ;

    int should_close;

    should_close = GuiWindowBox(geometry,"#191# About program");
    draw_image();

    GuiGroupBox(credits, "Credits");
      GuiLabel(credits_label,credits_s);
    //
    GuiGroupBox(about, "About Program");
      GuiLabel(about_label,about_s);
    // 

    // Update window after 
    // being moved and 
    // apply move logic
    
    // NOTE:
    // > depends on Dragable class
      drag_titlebar();
    
    // byte[] bs;
    // bs = s.getBytes();
    // bs = Arrays.copyOf(bs, bs.length + 1);
    // bs[bs.length - 1] = '\0';

    return should_close == 1;
  }

  

}

