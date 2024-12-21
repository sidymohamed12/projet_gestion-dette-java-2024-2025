package com.dette.services.servicespe;

import java.util.List;

import com.dette.core.Service;
import com.dette.entities.Client;
import com.dette.entities.Dette;

public interface IDetteService extends Service<Dette> {

    Dette getById(int id);

    List<Dette> detteOfClient(Client client);
}