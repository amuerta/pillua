# Java course project -> Pillua

## About
This project was made under the OOP course of KNUCA (Kiev National University of Construction and Acrhitecure).
Entire project was made possible by Raysan and his awesome C library Raylib. For this specific language [Jaylib](https://github.com/electronstudio/jaylib.git) binding of the library was used.
Project theme was about creating a pahrma shop program for optimal selling of aids. Project was made in extreme hurry due to unpleasnt developing expirience under time pressure and war happening close by.

### Project features:
- simple immidiate ui gui
- simple (naive even) parser of regual text files
- raygui theme support
- basic mouse support
- demonstartion of java concepts abuse *(which futher proofs how bad OOP is)*
- a demonstartion of how to use raygui with java.

### Personal thoughts
If you try raylib, do so! But not in java. Java was extremly horrible language for graphics api as low level as Raylib, i would suggest C++ if you enjoy OOP style language. Jaylib is 
obviously better than [Raylib-J](https://github.com/CreedVI/Raylib-J), that one was pure pain to use, with textures API being reimplemnted poorly in java, causing all sorts of bugs and craches.
## Quick start
0. Install git and make (if not present)
   ```console
   # Debian / Ubuntu
   sudo apt install git make
   # Arch Linux
   sudo pacman -Syu git make
   # MACOS
   brew install git make
   # Windows: https://chocolatey.org/
   choco install git make
   ```
1. Install JVM, JDK  and javac -  all Java related packages
   ```console
   sudo apt intall open-jdk* java
   ```
2. Install raylib, offical using instructions at: https://github.com/raysan5/raylib?tab=readme-ov-file#build-and-installation
3. Clone the repo and enter project directory
   ```console
   git clone --depth=1 https://github.com/amuerta/pillua.git
   cd ./pillua
   ```
4. Build and run project using make
   ```console
   make run
   ```
   
