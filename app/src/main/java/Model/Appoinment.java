package Model;

public class Appoinment {
    String Patient_Name;
    String Doctor_Name;

    public Appoinment() {
    }

    public Appoinment(String patient_Name, String doctor_Name) {
        Patient_Name = patient_Name;
        Doctor_Name = doctor_Name;
    }

    public String getPatient_Name() {
        return Patient_Name;
    }

    public void setPatient_Name(String patient_Name) {
        Patient_Name = patient_Name;
    }

    public String getDoctor_Name() {
        return Doctor_Name;
    }

    public void setDoctor_Name(String doctor_Name) {
        Doctor_Name = doctor_Name;
    }
}
