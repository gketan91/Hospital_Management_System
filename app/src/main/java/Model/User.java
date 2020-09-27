package Model;

public class User {
    String Email;
    String Name;
    String UID;

    public User() {
    }

    public User(String email, String name, String UID) {
        Email = email;
        Name = name;
        this.UID = UID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
