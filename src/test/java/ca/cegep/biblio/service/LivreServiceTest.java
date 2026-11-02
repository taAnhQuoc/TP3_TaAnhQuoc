package ca.cegep.biblio.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.cegep.biblio.model.Livre;
import ca.cegep.biblio.repository.LivreRepository;
/**
 * TP partie C : à vous d'implémenter les 3 tests ci-dessous.
 * Retirez @Disabled de chaque test une fois son implémentation écrite.
 * Respectez le modèle Arrange / Act / Assert.
 */
@ExtendWith(MockitoExtension.class)
class LivreServiceTest {

    @Mock
    private LivreRepository repository;

    @InjectMocks
    private LivreService service;

    // ---------- 1 test simple (sans mock) ----------

    @Test
    void calculerFraisRetard_longRetard_plafonneLeMontant() {
        // À tester : pour une catégorie donnée, un très grand nombre de jours de retard
        // ne dépasse jamais le plafond de cette catégorie.
        // Indice : la méthode ne dépend pas du repository.
        double resultat = service.calculerFraisRetard("JEUNESSE", 100);
        double validation = 5.0;

        assertEquals(validation,resultat);
    }

    // ---------- 1 test sur une exception (assertThrows) ----------

    @Test
    void emprunter_livreDejaEmprunte_lanceIllegalStateException() {
        // À tester : emprunter un livre dont disponible == false lance une IllegalStateException.
        // Indice : le repository (mock) doit retourner ce livre pour son id.

            Long id = 1L;
            Livre livre = new Livre();
            livre.setDisponible(false);
            
            when(repository.findById(id)).thenReturn(Optional.of(livre));
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> service.emprunter(id));

            assertEquals("Ce livre est déjà emprunté",exception.getMessage());
            verify(repository, never()).save(any());
        
    }

    // ---------- 1 test avec mock (when / thenReturn / verify) ----------

    @Test
    void emprunter_livreDisponible_marqueIndisponibleEtSauvegarde() {
        // À tester : emprunter un livre disponible le rend indisponible
        // et appelle repository.save(...) exactement une fois.
        // Définissez le comportement du mock avec when(...).thenReturn(...).
        Long id = 1L;
        Livre livre = new Livre();
        livre.setDisponible(true);
        when(repository.findById(id)).thenReturn(Optional.of(livre));
        when(repository.save(livre)).thenReturn(livre);

        Livre resultat = service.emprunter(id);

        assertFalse(resultat.isDisponible());

        verify(repository).save(livre);
    }
}


