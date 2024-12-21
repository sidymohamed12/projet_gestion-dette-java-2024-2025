package com.dette.services.servicespe;

import com.dette.core.Service;
import com.dette.entities.User;

public interface IUserService extends Service<User> {
    User connexion(String login, String password);
}
