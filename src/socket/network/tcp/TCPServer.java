package socket.network.tcp;

import socket.network.model.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static socket.app.model.MessageUtils.*;

public class TCPServer extends Server {
    private List<TCPUser> users;
    private ServerSocket listenSocket;

    public TCPServer() {
        super();
    }

    @Override
    public void startServer() {
        users = new ArrayList<>();

        try {
            listenSocket = new ServerSocket(Server.PORT);

            while (true) {
                connectUsers();
                startChat();
                logUsers();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro na comunicação com os usuários...");
        }
    }

    private void connectUsers() throws IOException {
        int usersNum = 0;

        while (usersNum < MAX_USERS) {
            Socket socket = listenSocket.accept();
            TCPUser user = new TCPUser(new DataInputStream(socket.getInputStream()), new DataOutputStream(socket.getOutputStream()));
            users.add(user);
            user.getOut().writeUTF(WELCOME_MSG);
            logUsers();
            usersNum++;
        }
    }

    private void startChat() throws IOException {
        boolean quit = false;

        users.get(0).getOut().writeUTF(START_CHAT);
        logMessageEnviada(0, START_CHAT);

        while (!quit) {
            for (int i = 0; i < MAX_USERS; i++) {
                TCPUser user = users.get(i);
                TCPUser anotherUser = getAnotherUser(i);

                String message = user.getIn().readUTF();
                logMessage(i, message);

                if (message.equals(DISCONNECT)) {
                    anotherUser.getOut().writeUTF(END_CHAT);
                    logMessageEnviada(getAnotherId(i), END_CHAT);
                    quit = true;
                    break;
                }

                anotherUser.getOut().writeUTF(message);
                logMessageEnviada(getAnotherId(i), message);
            }
        }
        System.out.println("---Chat finalizado");
        users.clear();
        logUsers();
    }

    private TCPUser getAnotherUser(int i) {
        return i == 0 ? users.get(1) : users.get(0);
    }

    private int getAnotherId(int i) {
        return i == 0 ? 1 : 0;
    }

    private void logUsers() {
        System.out.println("Usuários conectados: " + users.toString());
    }

    private void logMessage(int user, String message) {
        System.out.printf("Usuário %s diz: %s\n", user, message);
    }

    private void logMessageEnviada(int user, String message) {
        System.out.printf("Mensagem enviada ao user %s: %s\n", user, message);
    }
}


