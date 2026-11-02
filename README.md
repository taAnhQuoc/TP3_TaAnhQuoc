# biblio-api

## Questions d'examen A.1
1. Quelle classe joue le rôle du **Modèle**, du **Contrôleur** ? Où se trouve la **logique métier** ?
Le modèle c'est un entité (une classe qui représente les objets de livre ou une modèle de donnée). Le contrôleur c'est un point d'entrée pour qu'on accèss au service et la logique du métier c'est LivreService qui contient la logique des fonctions.

2. Que fait `GestionnaireErreurs` et quel code HTTP obtient-on pour un livre introuvable ? Et pour un double emprunt ?
GestionnaireErreurs remplace les erreurs d'exceptions gérer par l'application et on met à la place notre propre erreur pour notre application de bibliothéque. Le code 404 veut dire que le livre est introuvable ou elle n'existe pas. Et l'erreur 409 retourne une erreur que le livre est déja emprunté.
3. Où les données sont-elles stockées ? Que se passe-t-il quand l'application redémarre ?
Elles sont stockés temporairement dans la mémoire, et quand l'application redémarre. Elle recréerait les données.
4. Suivez le trajet complet d'un appel `POST /api/livres/1/emprunter` : quelles classes/méthodes sont appelées, dans quel ordre ?
LivreController -> LivreService -> emprunter() -> LivreRepository -> save()

## Questions d'examen A.3
1. GET /api/livres :
Cela retourne Le Petit Prince, une brève histoire du temps et Bonheur d'occasion.
2. POST /api/livres
{
  "id": 4,
  "titre": "Les Misérables",
  "auteur": "Victor Hugo",
  "isbn": "9782253096344",
  "anneePublication": 1862,
  "categorie": "ROMAN",
  "disponible": true
}
3. POST /api/livres
Code 400
{
  "erreur": "Le titre est obligatoire"
}
4. POST /api/livres/1/emprunter
200 {
  "id": 1,
  "titre": "Le Petit Prince",
  "auteur": "Antoine de Saint-Exupéry",
  "isbn": "9782070612758",
  "anneePublication": 1943,
  "categorie": "JEUNESSE",
  "disponible": false
}
409 {
  "erreur": "Ce livre est déjà emprunté"
}
5. GET /api/livres/2/frais-retard?joursRetard=100
{
  "livreId": 1,
  "joursRetard": 100,
  "frais": 5
}

---

## Partie D Code Smell
| Code Smell | Fichier | Ligne | Raison |
| -------- | ------- | -------- | ------- |
| Duplicated Code | LivreService  | Ligne 35 et 50 | Le code est en double dans cette partie.|
| Magic Numnber |  LivreService   | Ligne 86 a 102 | Il y a des nombres qui ne sont pas signicatifs et il faut le remplacer |
| Long Method   |  LivreService  |  49 a 57 | On separe la validation et la modification. Une fonctione gere un probleme a la fois |


## Partie E Workflow
un lien vers le workflow : https://github.com/taAnhQuoc/TP3_TaAnhQuoc/actions/runs/41547868950


