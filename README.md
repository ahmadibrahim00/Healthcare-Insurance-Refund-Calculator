Pour executer le programme : 

- `File > Project Structures > Modules > + (add) > JAR or directories` ajouter :
  - "json-20220924.jar" 
  - "json-lib-2.4-jdk15.jar" 
  - "json-simple-1.1.1.jar"

Ensuite,

- `File > Project Structures > Artifacts > + (add) > JAR > From modules and dependencies > Main class : "Main.java"`

Build > Build Artifacts >  Build

Ensuite, dans la ligne de commande, à l’emplacement du .jar, exécuter la commande suivante : 
- `java -jar Refund.jar Assurance.json résultat.json`