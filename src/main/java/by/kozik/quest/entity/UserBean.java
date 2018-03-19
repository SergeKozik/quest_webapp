package by.kozik.quest.entity;

import by.kozik.quest.service.RoleEnum;

import java.io.Serializable;

/**
 * Created by Roldo on 24.11.16.
 */
public class UserBean implements Serializable {
    private String nick;
    private int role;
    private String email;
    private String password;
    private int id;

    public UserBean() {
        nick="";
        role= RoleEnum.ANONYMOUS.getPriority();
        password="";
        email="";
        id=-1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
