package socket.app;

import socket.app.enums.ConnectionEnum;
import socket.network.model.Server;
import socket.network.tcp.TCPServer;
import socket.network.udp.UDPServer;

import javax.swing.*;
import java.util.Arrays;

import static socket.app.enums.ConnectionEnum.UDP;

public class MainServer {

    public static void main(String[] args) {
        MainServer mainServer = new MainServer();
        mainServer.showMenu();
    }

    private void showMenu() {
        String[] conTypes = Arrays.stream(ConnectionEnum.values())
                .map(Enum::name)
                .toArray(String[]::new);

        int selected = JOptionPane.showOptionDialog(null, "Tipo de conexão:", "Server", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, conTypes, 0);

        Server server = selected == UDP.getOption() ?
            new UDPServer() :
            new TCPServer();
    }
}
