package com.dette.repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dette.core.database.implement.RepositoryBDImpl;
import com.dette.entities.Client;
import com.dette.entities.User;
import com.dette.repository.implement.ClientRepository;

public class ClientRepositoryBD extends RepositoryBDImpl<Client> implements ClientRepository {

    public ClientRepositoryBD(UserRepositoryBD userRepository) {
        super(userRepository, null, null, null);
        clazz = Client.class;
        tableName = "client";
        colomnSelectBy = "telephone";
        colones = new String[] { "surnom", "telephone", "adresse", "userId", "createdAt", "updatedAt", "createdBy",
                "updatedBy" };
    }

    public ClientRepositoryBD() {
        super(new UserRepositoryBD(), null, null, null);
        clazz = Client.class;
        tableName = "client";
        colomnSelectBy = "telephone";
        colones = new String[] { "surnom", "telephone", "adresse", "userId", "createdAt", "updatedAt", "createdBy",
                "updatedBy" };
    }

    @Override
    public Client getClientByUser(User user) {
        Client client = null;
        try {
            ResultSet res = executeQuery(generateSql("SELECT", tableName, null, "userId", user.getId()));
            if (res.next()) {
                client = converToObjet(res, clazz);
            }
        } catch (SQLException e) {
            System.out.println("Erreur requête : " + e.getMessage());
        }
        return client;
    }

}
