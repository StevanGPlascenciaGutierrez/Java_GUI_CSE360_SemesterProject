package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Message {
    private String content;
    private String sender;
    private String receiver;
    private String timeStamp;
    private int ChatNum;



    public Message() {
        content = "";
        sender = "";
        receiver = "";
        timeStamp = "";
    }
    public Message(String content, String sender, String receiver, String timeStamp) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.timeStamp = timeStamp;
    }
    public int getChatNum() {
        return ChatNum;
    }

    public void setChatNum(int chatNum) {
        ChatNum = chatNum;
    }
    public String getContent() {
        return content;
    }
    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setContent(String newContent) {
        content = newContent;
    }
    public void setReceiver(String newReceiver) {
        receiver = newReceiver;
    }
    public void setSender(String newSender) {
        sender = newSender;
    }
    public void setTimeStamp(String newTime) {
        timeStamp = newTime;
    }

    public ArrayList<Message> selectHealth(int patientID, int doctorID)
    {
        String sql = "SELECT Content "
                + "FROM Messages WHERE ChatNum = ?";

        ArrayList<Message> messageList = new ArrayList<>();

        // Connects
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // Creates a prepared statement
            int chatNUMBER = getChat(patientID, doctorID);
            pstmt.setInt(1,chatNUMBER);
            ResultSet result  = pstmt.executeQuery();


            while (result.next()) {
                Message newMessage = new Message();
                newMessage.setContent(result.getString("Content"));
                newMessage.setChatNum(chatNUMBER);
                messageList.add(newMessage);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messageList;
    }

    public static void insertMessage(String content, int patientID, int doctorID){
        String sql = "INSERT INTO Messages(Content,ChatNum) VALUES(?,?)";
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2,getChat(patientID,doctorID));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static int getChat(int patientID, int doctorID)
    {
        String sql = "SELECT ChatNum "
                + "FROM Chat WHERE patientID = ? AND doctorID = ?";

        // Connects
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt  = conn.prepareStatement(sql)){

            // Creates a prepared statement
            pstmt.setInt(1,patientID);
            pstmt.setInt(2,doctorID);
            ResultSet result  = pstmt.executeQuery();

            if (result.next())
            {
                return result.getInt("ChatNum");
            }
            else
            {
                insertChat(patientID, doctorID);
                return getChat(patientID, doctorID);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public static void insertChat(int patientID, int doctorID){
        String sql = "INSERT INTO Chat(patientID,doctorID) VALUES(?,?)";
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,patientID);
            pstmt.setInt(2,doctorID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
