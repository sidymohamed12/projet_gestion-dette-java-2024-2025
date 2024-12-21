package com.dette.views;

import java.util.List;
import java.util.Scanner;

import com.dette.core.ViewImplement;
import com.dette.entities.Client;
import com.dette.entities.User;
import com.dette.enums.Role;
import com.dette.services.servicespe.IClientService;
import com.dette.services.servicespe.IUserService;
import com.dette.views.viewspe.IClientView;

public class ClientView extends ViewImplement<Client> implements IClientView {

    private IClientService clientService;
    private IUserService userService;

    public ClientView(Scanner scanner, IClientService clientService, IUserService userService) {
        super(scanner);
        this.clientService = clientService;
        this.userService = userService;
    }

    public Client saisie() {
        Client client = new Client();
        System.out.println("entrer le surnom : ");
        client.setSurnom(scanner.nextLine());
        System.out.println("entrer son adresse : ");
        client.setAdresse(scanner.nextLine());
        System.out.println("entrer son telephone : ");
        client.setTelephone(scanner.nextLine());
        return client;
    }

    @Override
    public Client getBy() {
        clientService.findAll().forEach(System.out::println);
        System.out.println("Entrez le telephone du client : ");
        String tel = scanner.nextLine();
        Client client = clientService.getBy(tel);
        return client;
    }

    @Override
    public void linkClientUser(Client client) {
        System.out.println("Voulez-vous ajouter un compte pour ce client : ");
        if (askToContinue()) {
            scanner.nextLine();
            User user = new User();
            System.out.println("entrer le login : ");
            user.setLogin(scanner.nextLine());
            System.out.println("entrer le mdp : ");
            user.setPassword(scanner.nextLine());
            user.setRole(Role.client);
            user.setEtat(true);
            user.setClient(client);
            userService.create(user);
            // ---
            client.setUser(user);
            clientService.update(client);
        }

    }

    @Override
    public void listerClientUser(boolean statut, List<Client> clients) {
        clients
                .stream()
                .filter(cl -> (statut && cl.getUser() != null) || (!statut && cl.getUser() == null))
                .forEach(System.out::println);
    }

    @Override
    public void searchByTelephone() {
        Client client = getBy();
        if (client != null) {
            System.out.println("client : " + client);
        } else {
            System.out.println("🚨 client introuvable 🚨");
        }
    }

    @Override
    public void createUserForClient() {
        Client client = getBy();
        if (client != null) {
            System.out.println("client : " + client);
            if (client.getUser() == null) {
                linkClientUser(client);
            } else {
                System.out.println("🚨 Le client a déjà un utilisateur associé. 🚨");
            }
        } else {
            System.out.println("🚨 Client introuvable. 🚨");
        }
    }

}
