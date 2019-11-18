package DTO;


import java.io.Serializable;

public class PersonDTO implements Serializable /* tjek */{

   private String name;
   private String gender;
   private String culture;
   private String titles;

   public PersonDTO(String name, String height, String mass, String birthyear) {
        this.name = name;
        this.gender = gender;
        this.culture = culture;
        this.titles = titles;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "name=" + name + ", gender=" + gender + ", culture=" + culture + ", titles=" + titles + '}';
    }

   

}