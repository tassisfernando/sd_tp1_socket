package socket.network.tcp;

import socket.network.model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Server {

    public TCPServer() {
        super();
    }

    @Override
    public void startServer() {
        try {
            ServerSocket listenSocket = new ServerSocket(Server.PORT);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                Connection c = new Connection(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket: " + e.getMessage());
        }
    }


//    public static void main (String args[]) {
//        try {
//            int serverPort = Server.PORT; // the server port
//            ServerSocket listenSocket = new ServerSocket(serverPort);
//            while(true) {
//                Socket clientSocket = listenSocket.accept();
//                Connection c = new Connection(clientSocket);
//            }
//        } catch(IOException e) {
//            System.out.println("Listen socket:"+e.getMessage());
//        }
//    }
}


