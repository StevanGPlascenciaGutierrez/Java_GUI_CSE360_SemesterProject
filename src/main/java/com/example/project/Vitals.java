package com.example.project;

public class Vitals {
    private double bloodPressure;
    private double heartRate;
    private double respiratoryRate;
    private double bodyTemp;

    public Vitals(){
    }

    public Vitals(Double bloodPressure, Double heartRate, Double respiratoryRate, Double bodyTemp){
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.bodyTemp = bodyTemp;
    }

    public Double getBloodPressure() {
        return bloodPressure;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public Double getRespiratoryRate() {
        return respiratoryRate;
    }

    public Double getBodyTemp(){
        return bodyTemp;
    }

    public void setBloodPressure(Double newBloodPressure) {
        this.bloodPressure = newBloodPressure;
    }

    public void setHeartRate(Double newHeartRate) {
        this.heartRate = newHeartRate;
    }

    public void setRespiratoryRate(Double newRespiratoryRate) {
        this.respiratoryRate = newRespiratoryRate;
    }

    public void setDescription(Double newBodyTemp) {
        this.bodyTemp = newBodyTemp;
    }

}