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
        System.out.println("server started ..");
        DatagramSocket ss=new DatagramSocket(4000);
        DatagramPacket pktSent=null;
        DatagramPacket pktRcvd=null;
        String recvdData=null;

        while(true){
            //receiving from client
            byte[] byteArrayRecvd =new byte[1024];
            pktRcvd=new DatagramPacket(byteArrayRecvd,byteArrayRecvd.length);
            ss.receive(pktRcvd);
            byteArrayRecvd=pktRcvd.getData();
            recvdData=new String(byteArrayRecvd,0,pktRcvd.getLength(),"UTF-8");
            System.out.println("recvdData data is .. "+recvdData);

            //perform login operations and respond to client
            byte[] byteArraySent =new byte[1024];
//            String resp=login(recvdData);
            String resp=recvdData;
            byteArraySent=resp.getBytes();
            pktSent=new DatagramPacket(byteArraySent,byteArraySent.length,pktRcvd.getAddress(),pktRcvd.getPort());
            ss.send(pktSent);
        }	        
    }
}
	