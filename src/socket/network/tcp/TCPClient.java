package socket.network.tcp;

import socket.network.model.Client;
import socket.network.model.Server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends Client {
    private DataInputStream in;
    private DataOutputStream out;

    public TCPClient(String ipAddress) {
        super(ipAddress);
    }

    @Override
    public void startClient() {
        Socket socket = null;

        try {
            socket = new Socket(this.getIpAddress(), Server.PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("Hello World");      	// UTF is a string encoding see Sn. 4.4
            String data = in.readUTF();	    // read a line of data from the stream
            System.out.println("Received: "+ data) ;
            JOptionPane.showMessageDialog(null, data);
        } catch (UnknownHostException e){
            System.out.println("Socket:"+e.getMessage());
        } catch (EOFException e){
            System.out.println("EOF:"+e.getMessage());
        } catch (IOException e){
            System.out.println("readline:"+e.getMessage());
        } finally {
            if (socket!=null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
            }
        }
    }

//    public static void main (String args[]) {
//        // arguments supply message and hostname
//        Socket s = null;
//        try{
//            int serverPort = 7896;
//            s = new Socket(args[1], serverPort);

//            DataInputStream in = new DataInputStream( s.getInputStream());
//            DataOutputStream out =new DataOutputStream( s.getOutputStream());

//            out.writeUTF(args[0]);      	// UTF is a string encoding see Sn. 4.4
//            String data = in.readUTF();	    // read a line of data from the stream
//            System.out.println("Received: "+ data) ;
//        } catch (UnknownHostException e){
//            System.out.println("Socket:"+e.getMessage());
//        } catch (EOFException e){
//            System.out.println("EOF:"+e.getMessage());
//        } catch (IOException e){
//            System.out.println("readline:"+e.getMessage());
//        } finally {
//            if(s!=null) {
//                try {
//                    s.close();
//                } catch (IOException e) {
//                    System.out.println("close:" + e.getMessage());
//                }
//            }
//        }
//    }
}