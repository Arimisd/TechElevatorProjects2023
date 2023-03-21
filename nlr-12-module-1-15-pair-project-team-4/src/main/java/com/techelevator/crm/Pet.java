package com.techelevator.crm;

import java.util.ArrayList;
import java.util.List;

public class Pet {
    private String name;
    private String species;
    private List<String> vaccination;

    public Pet(String name, String species) {
        this.name = name;
        this.species = species;
        this.vaccination = new ArrayList<>();
    }

    public String listVaccinations() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vaccination.size(); i++) {
            sb.append(vaccination.get(i));
            if (i < vaccination.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
        public String getName () {
            return name;
        }

        public void setName (String name){
            this.name = name;
        }

        public String getSpecies () {
            return species;
        }

        public void setSpecies (String species){
            this.species = species;
        }

        public List<String> getVaccination () {
            return vaccination;
        }

        public void setVaccination (List < String > vaccination) {
            this.vaccination = vaccination;
        }
    }


