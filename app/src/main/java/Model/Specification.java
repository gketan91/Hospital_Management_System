package Model;

public class Specification {
    String Image;
    String Specification;

    public Specification() {
    }

    public Specification(String image, String specification) {
        Image = image;
        Specification = specification;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getSpecification() {
        return Specification;
    }

    public void setSpecification(String specification) {
        Specification = specification;
    }
}
