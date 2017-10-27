java gui.ButtonBar
java gui.MenuBar
java gui.DraggableFrame
java gui.CategoryComboBox

cd $HOME/gitRepos
# Get log4j
wget http://mirrors.ibiblio.org/apache/logging/log4j/1.2.17/log4j-1.2.17.tar.gz
tar tf log4j-1.2.17.tar.gz
# Find where com.apple/eawt.AboutHandler etc. are
#for i in `find /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/ -name '*.jar'`
#do
#    echo $i; jar tf $i | grep apple | grep -v applet
#done
# Why this is necessary, I don't know
ln -s /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar rt.jar

cd ~/gitRepos
# Get guava which has com.google... String
wget http://search.maven.org/remotecontent?filepath=com/google/guava/guava/19.0/guava-19.0.jar
mv remote* guava-19.0.jar

# Get older version of org.apache.commons.lang for Cinch
wget http://apache.mesi.com.ar//commons/lang/binaries/commons-lang-2.6-bin.tar.gz
tar xf commons-lang-2.6-bin.tar.gz

cd $HOME/gitRepos/githubSnippets/Java/GUI
java -cp $HOME/gitRepos/commons-lang-2.6/commons-lang-2.6.jar:./rt.jar:$HOME/gitRepos/apache-log4j-1.2.17/log4j-1.2.17.jar:$HOME/gitRepos/Cinch/target/classes:$HOME/gitRepos/guava-19.0.jar:. gui.MemoFrame
