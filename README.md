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

## Questions d'examens
1. Quelle classe joue le rôle du **Modèle**, du **Contrôleur** ? Où se trouve la **logique métier** ?
Le modèle c'est un entité (une classe qui représente les objets de livre ou une modèle de donnée). Le contrôleur c'est un point d'entrée pour qu'on accèss au service et la logique du métier c'est LivreService qui contient la logique des fonctions.

2. Que fait `GestionnaireErreurs` et quel code HTTP obtient-on pour un livre introuvable ? Et pour un double emprunt ?
GestionnaireErreurs remplace les erreurs d'exceptions gérer par l'application et on met à la place notre propre erreur pour notre application de bibliothéque. Le code 404 veut dire que le livre est introuvable ou elle n'existe pas. Et l'erreur 409 retourne une erreur que le livre est déja emprunté.
3. Où les données sont-elles stockées ? Que se passe-t-il quand l'application redémarre ?
Elles sont stockés temporairement dans la mémoire, et quand l'application redémarre. Elle recréerait les données.
4. Suivez le trajet complet d'un appel `POST /api/livres/1/emprunter` : quelles classes/méthodes sont appelées, dans quel ordre ?
LivreController -> LivreService -> emprunter() -> LivreRepository -> save()

---

> Remplacez le contenu ci-dessous par vos réponses du TP (voir `ENONCE_TP.md`).

