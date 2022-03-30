toString : *.java
	javac GenToString.java Variable.java IO.java

run :
	make toString
	java GenToString -d ../src/main/java/model
	make clean

clean :
	rm *.class