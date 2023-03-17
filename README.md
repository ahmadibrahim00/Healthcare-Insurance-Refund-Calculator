# Projet de session : Demande de changement #1

## Technologies présentes :

### Langage de programmation

Tout le code a été écrit avec le langage de programmation Java (JDK19).

### IDE

Ce programme a été fait entièrement avec l'IDE IntelliJ IDEA 2022.3, et les instructions pour exécuter le programme sont écrites en s'appuyant sur les menus et fonctionnalités de cet IDE.

### Gestion du projet

Ce programme utilise maven pour gérer les dépendances et le projet en tant que tel. On garantit que le projet fonctionne 
avec maven 3.8.1 et les versions plus récentes. Donc, nous encourageons d'utiliser maven 3.8.1 ou une version plus récente.

## Pour executer le programme :

### Fichier d'entrée valide

Le fichier d'entrée, contenant les informations du client et des réclamations doivent respecter la structure du fichier 
"Assurance.json" dans la racine du projet. S'il y a des champs manquants, que les données ne respectent pas les formats, qu'il
y a des données erronées ou que le fichier n'a pas l'extension ".json", le fichier de sortie contiendra un message d'erreur
significatif. De plus, il est important d'avoir le fichier "Assurance.json" dans le root repository du projet, et de ne
pas toucher à son nom ou à son contenu, ce qui empêcherait de tester le programme avant de l'exécuter.

### Executer le projet avec la ligne de commande

Les noms de répertoires, du fichier d'entrée et du fichier de sortie peuvent être différents, ils servent seulement d'exemple pour montrer comment
le projet doit être exécuté avec la ligne de commande.
- Une fois le projet télécharger sur votre ordinateur, il faut se déplacer dans le root directory du projet.
- `cd "C:\Users\monNom\projet"`
- Ensuite, on entre les commandes suivantes successivement :
- `mvn clean`
- Pour supprimer les fichiers créés par maven lors d'exécutions passées, pour pouvoir mettre les dépendances et le projet
à jour.
- `mvn package`
- Pour build le projet, donc compiler les fichiers .java, exécuter les tests, créer Remboursement.jar, etc. 
- `java -jar "target\Remboursement.jar" "Reclamation.json" "Remboursement.json"`
- Puis finalement on exécute le programme et un fichier "Remboursement.json" est créé et placé dans le root directory du projet, 
contenant soit un fichier contenant les montant à rembourser ou un message d'erreur. Il y aura aussi un fichier nommé
"Test.json" de créé lors de l'exécution des tests. Ce fichier peut être effacé, au besoin, toutefois, il sera créé après
chaque commande d'exécution desa tests.