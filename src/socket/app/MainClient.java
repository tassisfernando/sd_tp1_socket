package socket.app;

import socket.app.model.ConnectionEnum;
import socket.network.model.Client;
import socket.network.tcp.TCPClient;
import socket.network.udp.UDPClient;

import javax.swing.*;
import java.util.Arrays;

import static socket.app.model.ConnectionEnum.UDP;

public class MainClient {

    public static void main(String[] args) {
        MainClient mainClient = new MainClient();
        mainClient.showMenu();
    }

    private void showMenu() {
        String[] conTypes = Arrays.stream(ConnectionEnum.values())
                .map(Enum::name)
                .toArray(String[]::new);

        int selected = JOptionPane.showOptionDialog(null, "Tipo de conexão:", "Cliente", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, conTypes, 0);

        String ipAddress = JOptionPane.showInputDialog(null, "IP do servidor:");

        Client client = selected == UDP.getOption() ?
                new UDPClient(ipAddress) :
                new TCPClient(ipAddress);
    }
}
