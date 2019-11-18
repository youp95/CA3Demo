package DTO;


import java.io.Serializable;

public class PersonDTO implements Serializable /* tjek */{

   private String name;
   private String height;
   private String mass;
   private String birthyear;

   public PersonDTO(String name, String height, String mass, String birthyear) {
        this.name = name;
        this.height = height;
        this.mass = mass;
        this.birthyear = birthyear;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "name=" + name + ", height=" + height + ", mass=" + mass + ", birthyear=" + birthyear + '}';
    }

}