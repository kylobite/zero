run: ; javac -sourcepath . -d bin *.java; cd bin; java -cp . $(x); cd .. 
clean: ; cd bin; rm *.class; cd ..
