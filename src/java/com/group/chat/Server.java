/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.group.chat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 *
 * @author rjuuko
 */
public class Server extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    
    public static String login(String auth){
        String response ="not authenticated";
        switch (auth){
        case "login":
                response= "Login";
                break;
        }
        return response;
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, UnknownHostException, IOException {
        try {
            int port = 8888;
            InetAddress group = InetAddress.getByName("230.0.0.1");
            try (MulticastSocket socket = new MulticastSocket()) {
                String message = "Hello Client!";
                byte[] sendData = message.getBytes();
                
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, group, port);
                socket.send(sendPacket);
                
                System.out.println("Message sent to multicast group.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }	        
    }
}
	