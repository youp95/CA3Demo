
package DTO;


public class PersonDTO {

   private String name;
   private String height;
   private String mass;
   private String birth_year;

   public PersonDTO(String name, String height, String mass, String birth_year) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.birth_year = birth_year;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "name=" + name + ", height=" + height + ", mass=" + mass + ", birth_year=" + birth_year + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMass() {
        return mass;
    }

    public void setMass(String mass) {
        this.mass = mass;
    }

    public String getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(String birth_year) {
        this.birth_year = birth_year;
    }
    
    

    

   

}
