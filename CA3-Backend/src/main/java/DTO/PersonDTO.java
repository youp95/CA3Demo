package DTO;


import java.io.Serializable;

public class PersonDTO implements Serializable /* tjek */{

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

    

   

}