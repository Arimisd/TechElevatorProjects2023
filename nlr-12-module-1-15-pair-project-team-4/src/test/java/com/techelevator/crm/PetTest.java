package com.techelevator.crm;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {

    @Test
    void listVaccinations() {
// Arange
        Pet pet = new Pet("Bobo", "Dog");
        List<String> vaccinations = new ArrayList<>();
        vaccinations.add("Rabies");
        vaccinations.add("Distemper");
        vaccinations.add("Parvo");
        pet.setVaccination(vaccinations);
        //Act
        String expectedVaccinationString = "Rabies, Distemper, Parvo";
        String actualVaccinationString = pet.listVaccinations();
        //Assert
assertEquals(expectedVaccinationString, actualVaccinationString);
    }
}