toString : *.java
	javac GenToString.java Variable.java IO.java

run :
	make clean
	make toString
	java GenToString -d ../src/main/java/model

clean :
	rm *.class Test.java