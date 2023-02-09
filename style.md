# Style et conventions à suivre pour l'écriture du code.

## Conventions

Le langage doit être écrit en suivant les conventions écrite dans le *"Java Code Convention"* de Oracle dont le lien est le suivant :
https://www.oracle.com/technetwork/java/codeconventions-150003.pdf

Toutefois, il faut aussi ajouter :
- qu'il faut écrire au maximum un return par methode.
- qu'on accepte qu'une déclaration *' if '* ait aucune accolade qui la délimite, si la condition est appliqué sur seulement une instruction.
- qu'une variable locale n'est pas obligé d'être initié au tout début d'une méthode si la méthode doit être validé par une déclaration *if* avant l'exécution des instructions.
- qu'un niveau d'indentation vaut 4 espaces.

## Langue

Pour nommer les variables, les methodes et tout ce qui est nommé par le programmeur, doit être écrit en français, à l'exception des méthodes tel que :
`getVariable()` et `setVariable(var variable)` , par exemple.