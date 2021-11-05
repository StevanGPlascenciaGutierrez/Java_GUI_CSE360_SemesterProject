package com.example.project;

import java.util.ArrayList;

public class PatientDashboard extends Dashboard {
    private Insurance insurance;
    private Pharmacy pharmacy;
    private ArrayList<Immunization> immunizations;

    public PatientDashboard() {
        insurace = null;
        pharmacy = null;
        immunizations = new ArrayList<Immunization>();
    }
    public Insurance getInsurance() {
        return insurance;
    }
    public Pharmacy getPharmacy() {
        return pharmacy;
    }
    public ArrayList<Immunization> getImmunizations() {
        return immunizations;
    }
    public void setInsurance(Insurance newInsurance) {
        insurance = newInsurance;
    }
    public void setPharmacy(Pharmacy newPharm) {
        pharmacy = newPharm;
    }
    public void setImmunizations(ArrayList<Immunization> newImmunizations) {
        immunizations = newImmunizations;
    }
}
