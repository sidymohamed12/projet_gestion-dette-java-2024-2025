package com.dette.views.viewspe;

import java.util.List;

import com.dette.core.View;
import com.dette.entities.Client;

public interface IClientView extends View<Client> {
    void linkClientUser(Client client);

    void listerClientUser(boolean statut, List<Client> clients);

    void searchByTelephone();

    void createUserForClient();
}
