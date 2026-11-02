package ca.cegep.biblio.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ca.cegep.biblio.exception.LivreIntrouvableException;
import ca.cegep.biblio.model.Livre;
import ca.cegep.biblio.repository.LivreRepository;

/** Logique métier de la bibliothèque. */
@Service
public class LivreService {

    private final LivreRepository repository;
    private static final int ANNEE_PUBLICATION_PAST = 1450;
    private static final int ANNEE_PUBLICATION_FUTUR = 2100;
    private final double FRAIS_JEUNESSE = 0.10;
    private final double FRAIS_SCIENCE = 0.50;
    private final double FRAIS_BASE = 0.25;
    public LivreService(LivreRepository repository) {
        this.repository = repository;
    }

    public List<Livre> lister() {
        return repository.findAll();
    }

    public Livre trouver(Long id) {
        return repository.findById(id).orElseThrow(() -> new LivreIntrouvableException(id));
    }

    public Livre creer(String titre, String auteur, String isbn, int anneePublication, String categorie) {
        if (titre == null || titre.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        if (auteur == null || auteur.isBlank()) {
            throw new IllegalArgumentException("L'auteur est obligatoire");
        }
        if (anneePublication < ANNEE_PUBLICATION_PAST || anneePublication > ANNEE_PUBLICATION_FUTUR) {
            throw new IllegalArgumentException("L'année de publication est invalide");
        }
        Livre livre = new Livre(null, titre, auteur, isbn, anneePublication, categorie);
        return repository.save(livre);
    }

    public void validerLivre(String titre, String auteur, int anneePublication) {
        if (titre == null || titre.isBlank()) {
            throw new IllegalArgumentException("Le titre est obligatoire");
        }
        if (auteur == null || auteur.isBlank()) {
            throw new IllegalArgumentException("L'auteur est obligatoire");
        }
        if (anneePublication < ANNEE_PUBLICATION_PAST || anneePublication > ANNEE_PUBLICATION_FUTUR) {
            throw new IllegalArgumentException("L'année de publication est invalide");
        }
    }

    public Livre modifier(Long id, String titre, String auteur, String isbn, int anneePublication, String categorie) {
        Livre livre = trouver(id);
        validerLivre(titre, auteur, anneePublication);
        
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setIsbn(isbn);
        livre.setAnneePublication(anneePublication);
        livre.setCategorie(categorie);
        return repository.save(livre);
    }

    public void supprimer(Long id) {
        trouver(id);
        repository.deleteById(id);
    }

    public Livre emprunter(Long id) {
        Livre livre = trouver(id);
        if (!livre.isDisponible()) {
            throw new IllegalStateException("Ce livre est déjà emprunté");
        }
        livre.setDisponible(false);
        return repository.save(livre);
    }

    public Livre retourner(Long id) {
        Livre livre = trouver(id);
        if (livre.isDisponible()) {
            throw new IllegalStateException("Ce livre n'est pas emprunté");
        }
        livre.setDisponible(true);
        return repository.save(livre);
    }

    public double calculerFraisRetard(String categorie, int joursRetard) {
        double frais = 0;
        if (joursRetard > 0) {
            if (categorie.equals("JEUNESSE")) {
                frais = joursRetard * FRAIS_JEUNESSE;
                if (frais > 5) {
                    frais = 5;
                }
            } else if (categorie.equals("SCIENCE")) {
                frais = joursRetard * FRAIS_SCIENCE;
                if (frais > 20) {
                    frais = 20;
                }
            } else {
                frais = joursRetard * FRAIS_BASE;
                if (frais > 10) {
                    frais = 10;
                }
            }
        }
        return frais;
    }
}
