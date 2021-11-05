package com.example.project;

import java.util.ArrayList;

public class DoctorDashboard extends Dashboard {
    private VisitSummary visitSummary;
    private ArrayList<Appointment> appointments;
    private ArrayList<Message> messages;

    public DoctorDashboard() {
        visitSummary = null;
        appointments = new ArrayList<Appointment>();
        messages = new ArrayList<Message>();
    }
    public DoctorDashboard(VisitSummary summary, ArrayList<Appointment> appointments, ArrayList<Message> messages) {
        visitSummary = summary;
        this.appointments = appointments;
        this.messages = messages;
    }
    public VisitSummary getVisitSummary() {
        return visitSummary;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public void setVisitSummary(VisitSummary newSummary) {
        visitSummary = newSummary;
    }
    public void setAppointments(ArrayList<Appointment> newAppts) {
        appointments = newAppts;
    }
    public void setMessages(ArrayList<Message> newMessages) {
        messages = newMessages;
    }
}
