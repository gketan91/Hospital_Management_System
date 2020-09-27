package Model;

public class AddDoctor {
    String Email;
    Integer Phone;
    String Name;
    public AddDoctor() {
    }

    public AddDoctor(String email, Integer phone, String name) {
        Email = email;
        Phone = phone;
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getPhone() {
        return Phone;
    }

    public void setPhone(Integer phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
