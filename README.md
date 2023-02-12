## Pour executer le programme : 

### Ajout des dependances : 

- `File > Project Structures > Modules > + (add) > JAR or directories` ajouter :
  - "json-20220924.jar" 
  - "json-lib-2.4-jdk15.jar" 
  - "json-simple-1.1.1.jar"
  - "gson-2.10.1.jar"

### Ensuite, pour creer l'archive executable :

- `File > Project Structures > Artifacts > + (add) > JAR > From modules and dependencies > Main class : "Main.java"`

- `Build > Build Artifacts >  Build`

### Executer le .jar
- Allez a l'emplacement du .jar dans le terminal : 
inf2050-hiv2023-projet-equipe17/out/artifacts/Refund_jar
- ajoutez le fichier Reclamation a ce repoertoire
- Ensuite, dans la ligne de commande, à l’emplacement du .jar, exécutez la commande suivante : 

- `java -jar Refund.jar Reclamation.json Remboursement.json`