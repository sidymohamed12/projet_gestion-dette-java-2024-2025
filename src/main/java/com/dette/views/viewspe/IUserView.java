package com.dette.views.viewspe;

import java.util.List;

import com.dette.core.View;
import com.dette.entities.User;
import com.dette.enums.Role;

public interface IUserView extends View<User> {
    Role saisieRoleUser();

    User setEtatUser(boolean status, User user);

    User authentification();

    void listerUserbyRole(List<User> users);

    void listerUserActif(List<User> users);
}
