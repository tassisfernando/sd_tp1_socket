package socket.network.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

class Connection extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;

    public Connection (Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            this.start();
        } catch (IOException e) {
            System.out.println("Connection: " + e.getMessage());
        }
    }

    @Override
    public void run(){
        try {			                 // an echo server
            String data = in.readUTF();	 // read a line of data from the stream
            System.out.println("Received from client: " + data);
            out.writeUTF(data);
        } catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
        } catch(IOException e) {
            System.out.println("readline: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                /*close failed*/}
        }
    }
}
