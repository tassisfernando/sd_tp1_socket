package socket.network.udp;

import socket.network.model.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static socket.app.model.MessageUtils.*;

public class UDPServer extends Server {
    private List<UDPUser> users;
    private DatagramSocket aSocket;

    public UDPServer() {
        super();
    }

    @Override
    public void startServer() {
        users = new ArrayList<>();
        aSocket = null;
        while (true) {
            try {
                connectUsers();
                startChat();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro na comunicação com os usuários...");
            } finally {
                if (aSocket != null) {
                    aSocket.close();
                }
            }
        }
    }

    private void connectUsers() throws IOException {
        // create socket at agreed port
        aSocket = new DatagramSocket(Server.PORT);
        byte[] buffer = new byte[8];
        int usersNum = 0;

        while (usersNum < MAX_USERS) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(request);
            log(request);

            if (buffer[0] == CONNECT) {
                UDPUser user = new UDPUser(request.getAddress(), request.getPort());
                users.add(user);

                byte[] output = WELCOME_MSG.getBytes(StandardCharsets.UTF_8);
                DatagramPacket reply = new DatagramPacket(output, output.length, request.getAddress(), request.getPort());
                aSocket.send(reply);
                usersNum++;
                logUsers();
            } else {
                byte[] output = WAITING_USERS.getBytes(StandardCharsets.UTF_8);
                DatagramPacket reply = new DatagramPacket(output, output.length, request.getAddress(), request.getPort());
                aSocket.send(reply);
            }
        }
    }

    private void startChat() throws IOException {
        byte[] request;
        byte[] response = new byte[1000];
        DatagramPacket messenger;
        DatagramPacket receiver;
        boolean quit = false;

        request = START_CHAT.getBytes(StandardCharsets.UTF_8);

        messenger = new DatagramPacket(request, request.length, users.get(0).getIpAddress(), users.get(0).getPort());
        aSocket.send(messenger);

        while (!quit) {
            for (int i = 0; i < MAX_USERS; i++) {
                UDPUser user = users.get(i);
                UDPUser anotherUser = getAnotherUser(i);

                receiver = new DatagramPacket(response, response.length);
                aSocket.receive(receiver);

                while (!receiver.getAddress().equals(user.getIpAddress()) && receiver.getPort() != user.getPort()) {
                    aSocket.receive(receiver);
                }

                String message = new String(receiver.getData()).trim();

                if (message.equals(DISCONNECT)) {
                    request = END_CHAT.getBytes(StandardCharsets.UTF_8);
                    messenger = new DatagramPacket(request, request.length, anotherUser.getIpAddress(), anotherUser.getPort());
                    aSocket.send(messenger);
                    log(messenger);
                    quit = true;
                    break;
                }

                request = receiver.getData();
                messenger = new DatagramPacket(request, request.length, anotherUser.getIpAddress(), anotherUser.getPort());
                aSocket.send(messenger);
                log(messenger);
                response = new byte[1000];
            }
        }
        System.out.println("\n---Chat finalizado");
        users.clear();
        logUsers();
    }

    private UDPUser getAnotherUser(int i) {
        return i == 0 ? users.get(1) : users.get(0);
    }

    private void log(DatagramPacket request) {
        System.out.printf("Request:\n IP: %s \nMessage: %s",
                request.getAddress().getHostAddress(),
                new String(request.getData()));
    }

    private void logUsers() {
        System.out.println("Usuários conectados: " + users.toString());
    }
}