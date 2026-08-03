MAIN_CLASS = Main.java

all: build run

build:
	javac -Xlint:all -Xlint:-serial -Xlint:-this-escape -d classes *.java

run:
	java -cp classes $(MAIN_CLASS)

clean:
	rm -f *.class
	rm -f *~
