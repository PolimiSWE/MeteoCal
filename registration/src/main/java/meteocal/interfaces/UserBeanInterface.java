/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteocal.interfaces;

import meteocal.entity.User;

/**
 *
 * @author Milos
 */
public interface UserBeanInterface {
    public User getUser();
    public User getUserByUsername(String username);
    public void addUser(User usr);
    public void saveUser(User usr);
    public void saveUser();
    public void selectUser(String usrnm);

    public void setUsername(String username);

    public void setPassword(String password);

    public String getUsername();

    public String getPassword();

    public void createNew();

    public void setName(String name);

    public void setSurname(String surname);

    public void setEmail(String email);
}
