package com.dette.repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dette.core.database.implement.RepositoryBDImpl;
import com.dette.entities.Dette;
import com.dette.entities.Client;
import com.dette.repository.implement.DetteRepository;

public class DetteRepositoryBD extends RepositoryBDImpl<Dette> implements DetteRepository {

    public DetteRepositoryBD(ClientRepositoryBD clientRepositoryBD) {
        super(null, clientRepositoryBD, null, null);
        clazz = Dette.class;
        tableName = "dette";
        colomnSelectBy = "libelle";
        colones = new String[] { "montant", "montantVerser", "archiver", "date", "clientId", "etatId", "createdAt",
                "updatedAt" };
    }

    public DetteRepositoryBD() {
        super(null, new ClientRepositoryBD(), null, null);
        clazz = Dette.class;
        tableName = "dette";
        colomnSelectBy = "libelle";
        colones = new String[] { "montant", "montantVerser", "archiver", "date", "clientId", "etatId", "createdAt",
                "updatedAt", "createdBy", "updatedBy" };
    }

    // @Override
    // public void insert(Dette dette) {
    // Dette dt = dette;
    // super.insert(dette);
    // System.out.println(dette);
    // List<Detail> details = dette.getDetails();
    // for (Detail detail : details) {
    // detail.onPrePersist();

    // if (detailRepository.selectById(detail.getId()) != null) {
    // detail.setDette(dt);
    // detailRepository.update(detail);
    // } else {
    // detail.setDette(dt);
    // detailRepository.insert(detail);
    // }

    // }

    // }

    @Override
    public List<Dette> detteOfClient(Client client) {
        List<Dette> entities = new ArrayList<>();
        try {
            ResultSet res = executeQuery(generateSql("SELECT", tableName, null, "clientId", client.getId()));
            while (res.next()) {
                entities.add(converToObjet(res, clazz));
            }
        } catch (SQLException e) {
            System.out.println("Erreur requête : " + e.getMessage());
        }
        return entities;
    }
}
