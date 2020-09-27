package Model;

public class ChooseDr {
    String Name;
    String Qualification;

    public ChooseDr() {
    }

    public ChooseDr(String name, String qualification) {
        Name = name;
        Qualification = qualification;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }
}
