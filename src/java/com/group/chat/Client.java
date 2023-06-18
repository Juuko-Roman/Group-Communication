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
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

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
        String recvd =null;
        DatagramSocket ss=new DatagramSocket();
        DatagramPacket pktSent=null;
        DatagramPacket pktRcvd=null;
        System.out.println("client ready");

        
        byte[] byteArray =new byte[1024];
        final PrintWriter out = response.getWriter();

        //sending to server asking for allow login
        String msg=request.getParameter("search");
        byteArray=msg.getBytes();
        pktSent=new DatagramPacket(byteArray,msg.length(), InetAddress.getByName("localhost"),4000);
        ss.send(pktSent);

        //receiving response from server about allow login
        byte[] byteArrayRecvd =new byte[1024];
        pktRcvd=new DatagramPacket(byteArrayRecvd,byteArrayRecvd.length);
        ss.receive(pktRcvd);
        byteArrayRecvd=pktRcvd.getData();
        recvd=new String(byteArrayRecvd,0,pktRcvd.getLength(),"UTF-8");
        out.print("<h1>"+recvd+"</h1>");


        // socket.close();
        // dos.close();
                
    }

}


