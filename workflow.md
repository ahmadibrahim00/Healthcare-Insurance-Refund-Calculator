## Workflow centralisé

Notre démarche par rapport au "workflow" en est une basée sur le jugement de chacun des 
membres de l'équipe. Tous les changements peuvent être "push" directement sur le dépôt distant de notre projet.

### Initialisation du dépôt distant et des dépôt locaux

Le scrum master de l'équipe crée le dépôt distant avec les commandes :

```
git init --initial-branch=main
git remote add [remote] [Remote Server Link]
```
ensuite, donne les droits aux membres d'avoir accès au projet et de le modifier avec la commande suivante :
```
git clone <URL>
```
Rendu à cette étape, tout le monde a accès au projet et chaque changement effectué et poussé est vu par les autres 
membres. Maintenant, il faut expliquer les différentes étapes que chaque programmeur doit accomplir avant d'envoyer ses 
changements sur le dépôt distant.

### Division des tâches

Il est important avant que l'on commence à programmer que chaque membre de l'équipe sache quoi faire et que le travail
soit bien divisé entre les membres. De plus, le travail est divisé pour faire en sorte qu'il y ait le moins de 
dépendances entre les différentes parties des membres. Toutefois, si le projet à accomplir empêche l'indépendance de la
partie de deux ou plusieurs membres, il est important que chaque membre impliqué soit averti de chaque changement 
effectué sur une partie dépendante. C'est la responsabilité de chaque membre de s'assurer que tout soit bien communiqué.

### Code et tests

Une fois les tâches séparer, on peut maintenant commencer à développer chacune des parties du projet. Tout au long de 
la programmation, il est important de suivre le style pré-établi dans le fichier "style.md". Une fois que le code est 
terminé, il faut le tester et s'assurer que le code ne contienne aucune défaillance, qu'il compile et qu'il exécute
la fonction pour laquelle il a été écrit. 
```
git add "nomFichier"
```
Si tout est beau là on peut commit les changements apportés.


### Commit et push

Chaque commit doit représenter un seul changement d'un aspect du projet et doit être accompagné d'un message qui lui est
significatif. On doit au mieux de nos capacités éviter les commits inutiles pour pouvoir mieux se retrouver dans des 
versions précédentes du projet.
```
git commit -m "message"
```
Ensuite, que les changements ont été commit sur le dépôt distant du projet, le tout peut
être push directement, si le programmeur juge que ce qui l'envoie est de qualité et respecte les directives de ce
document et du projet.
```
git push
```

### Conflits

Avec la séparation des tâches que l'on fait, il serait étonnant de se retrouver avec des conflits dans le code source 
écrit. Toutefois, s'il y a des conflits, la personne qui encontre ce conflit relevé doit s'entretenir avec l'autre 
développeur qui a écrit le code avant elle et discuté pour savoir quel bout de code est le meilleur.

### Autres membres

Une fois les changements poussés sur le dépôt distant, les autres membres de l'équipe doivent vérifier que tout semble 
fait en respectant les directives pré-établies, que ça compile et que ça ne cause pas d'erreurs au projet global, tout 
en emmenant une fonctionnalité de plus ou vient améliorer le code écrit précédemment.

### Branching, forking, merge, etc.

Chaque membre peut décider de sa façon de commit/push ses changements. Donc, il n'est pas interdit de créer d'autres
branches ou des forks et de les merge avec la branche main.

### Version

On n'utilise pas de tags pour représenter les versions de notre projet. Les versions du projet sont représentées par les
dates de remise. Donc, pour voir une version il suffit d'aller voir le dernier commit avant la date limite de changement
du sprint.


