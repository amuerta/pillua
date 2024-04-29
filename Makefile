LIB="./lib"
RAYLIB="./lib/jaylib-5.0.0-0.jar"

build: 
	javac -d bin -cp $(LIB)/*.jar src/App.java src/*.java

run: build
	java -cp ./bin:$(RAYLIB) ./src/App.java
