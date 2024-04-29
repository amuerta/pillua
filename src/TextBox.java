package module_gui;

import static com.raylib.Jaylib.RAYWHITE;
import static com.raylib.Jaylib.VIOLET;
import static com.raylib.Jaylib.GREEN;
import static com.raylib.Raylib.*;
import java.util.Arrays;

public class TextBox {
  
  static final int MAX_STR_LEN = 256;

  byte[] str;
  byte[] placeholder;
  boolean box_active = false;
  Rectangle geometry;

  public TextBox(Rectangle geometry, String placeholder_str) {
    this.str = new byte[MAX_STR_LEN];
    this.geometry = geometry;

    for(int i = 0; i < MAX_STR_LEN; i++) {
      str[i]     = 0;
    }

    placeholder = placeholder_str.getBytes();
    placeholder = Arrays.copyOf(placeholder, placeholder.length + 1);
    placeholder[placeholder.length - 1] = '\0';
  }

  public void run() {
    int byte_str_len = 0;

    for(int i = 0; i < MAX_STR_LEN && str[i] !='\0'; i++)
      byte_str_len++;

    int s_state = GuiTextBox(
        geometry,
        (!box_active && byte_str_len < 1) ? placeholder : str,
        MAX_STR_LEN,
        box_active //(byte_str_len >= MAX_STR_LEN - 1)? false : search_active
    );
      
    if (s_state == 1 ) 
      box_active = !box_active;
  }
}
