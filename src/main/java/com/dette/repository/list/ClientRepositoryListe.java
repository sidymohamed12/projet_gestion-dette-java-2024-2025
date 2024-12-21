package com.dette.repository.list;

import com.dette.core.database.implement.RepositoryListeImplement;
import com.dette.entities.Client;
import com.dette.entities.User;
import com.dette.repository.implement.ClientRepository;

public class ClientRepositoryListe extends RepositoryListeImplement<Client> implements ClientRepository {

    public Client selectBy(String name) {
        return listes.stream()
                .filter(client -> client.getTelephone().compareTo(name) == 0)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(Client client) {
        Client cl = listes.stream()
                .filter(value -> value.getTelephone().compareTo(client.getTelephone()) == 0)
                .findFirst()
                .orElse(null);
        if (cl != null) {
            cl = client;
        }
    }

    @Override
    public Client selectById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public Client getClientByUser(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getClientByUser'");
    }

}
