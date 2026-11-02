# biblio-api

API REST de gestion d'une petite bibliothèque (Spring Boot 3.5, Java 25, architecture MVC).

## Démarrer

```bash
./mvnw spring-boot:run
```

- Swagger UI : http://localhost:8080/swagger-ui.html
- Les données sont gardées **en mémoire** (perdues à l'arrêt).

## Structure

| Package | Rôle |
|---|---|
| `model` | Le modèle : `Livre` |
| `repository` | Accès aux données (en mémoire) |
| `service` | Logique métier |
| `controller` | Contrôleur REST (+ `LivreRequest`, corps JSON des requêtes) |
| `exception` | Exceptions et traduction en codes HTTP |

## Endpoints

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/livres` | Lister les livres |
| GET | `/api/livres/{id}` | Obtenir un livre |
| POST | `/api/livres` | Ajouter un livre |
| PUT | `/api/livres/{id}` | Modifier un livre |
| DELETE | `/api/livres/{id}` | Supprimer un livre |
| POST | `/api/livres/{id}/emprunter` | Emprunter un livre |
| POST | `/api/livres/{id}/retourner` | Retourner un livre |
| GET | `/api/livres/{id}/frais-retard?joursRetard=N` | Frais de retard en $ |

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
|  |     |          |         |
|     |    |          |         |


> Remplacez le contenu ci-dessous par vos réponses du TP (voir `ENONCE_TP.md`).

