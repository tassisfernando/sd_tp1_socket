package socket.network.udp;

import socket.network.model.Client;
import socket.network.model.Server;

import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPClient extends Client {

    private DatagramSocket datagramSocket;

    public UDPClient(String ipAddress) {
        super(ipAddress);
    }

    @Override
    public void startClient() {
        try {
            datagramSocket = new DatagramSocket();
            String strMessage = "Hello World";
            byte[] message = strMessage.getBytes(StandardCharsets.UTF_8);

            InetAddress aHost = InetAddress.getByName(this.getIpAddress());
            int serverPort = Server.PORT;
            DatagramPacket request =
                    new DatagramPacket(message,  message.length, aHost, serverPort);
            datagramSocket.send(request);

            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(reply);

            JOptionPane.showMessageDialog(null, reply.getData());
            System.out.println("Reply: " + new String(reply.getData()));
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Houve uma falha na comunicação com o servidor.");
        }
    }

    /*
    public static void main(String args[]){
        // args give message contents and destination hostname
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket();
            byte [] m = args[0].getBytes();
            InetAddress aHost = InetAddress.getByName(args[1]);
            int serverPort = Server.PORT;
            DatagramPacket request =
                    new DatagramPacket(m,  args[0].length(), aHost, serverPort);
            aSocket.send(request);
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            System.out.println("Reply: " + new String(reply.getData()));

        } catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e){
            System.out.println("IO: " + e.getMessage());
        } finally {
            if(aSocket != null)
                aSocket.close();
        }
    }*/
}
