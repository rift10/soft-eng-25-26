DAY = 1

all: clean build run

build:
	javac -Xlint:all -Xlint:-serial -Xlint:-this-escape -d classes day-$(DAY)/*.java

run:
	java -cp classes Day$(DAY) $(DAY)

clean:
	rm -f *.class
	rm -f *~