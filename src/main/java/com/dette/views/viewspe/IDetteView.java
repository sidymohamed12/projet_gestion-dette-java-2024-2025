package com.dette.views.viewspe;

import java.util.List;

import com.dette.core.View;
import com.dette.entities.Client;
import com.dette.entities.Dette;
import com.dette.enums.Etat;

public interface IDetteView extends View<Dette> {
    Etat saisiEtat();

    void listerDetteNonSolde(List<Dette> dettes);

    void listerDetteSolde(List<Dette> dettes);

    void filtrerDetteByEtat(Etat etat, List<Dette> dettes);

    void createDetteClient(Dette dette);

    Dette getById();

    Dette traiterDette(Dette dette);

    void ListedetteOfClient(Client client, List<Dette> dettes);

    void ListeDemandeDetteClient(Client client, List<Dette> dettes);

}
