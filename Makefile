all:
	javac *.java
	java checker
clean:
	rm -f *.class
	find . -type f -name '*.class' -delete
