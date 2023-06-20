/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.group.chat;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import org.apache.tomcat.util.json.JSONParser;

/**
 *
 * @author rjuuko
 */
public class Client extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

     private void processRequest(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, UnknownHostException, IOException {
        final PrintWriter out = response.getWriter();
         try {
            int port = 8888;
            InetAddress group = InetAddress.getByName("230.0.0.1");
            MulticastSocket socket = new MulticastSocket(port);
            socket.joinGroup(group);
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

//            while(true){
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(receivePacket);

                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
    //            System.out.println("Received message from server: " + receivedMessage);
//                out.print("<h1>Received message from server: " + receivedMessage+"</h1>");
                   JSONParser parser=new JSONParser("{'name':'roman'}");
                   out.print(parser.parseObject());
//            }

//            socket.leaveGroup(group);
//            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // socket.close();
        // dos.close();
                
    }

}


