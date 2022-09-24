package socket.network.udp;

import socket.app.enums.SairEnum;
import socket.network.model.Client;
import socket.network.model.Server;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import static socket.app.model.MessageUtils.*;

public class UDPClient extends Client {

    private DatagramSocket datagramSocket;

    public UDPClient(String ipAddress) {
        super(ipAddress);
    }

    @Override
    public void startClient() {
        try {
            datagramSocket = new DatagramSocket();
            connectToServer();
            startChat();
            JOptionPane.showMessageDialog(null, "Até mais!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve uma falha na comunicação com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void connectToServer() {
        try {
            InetAddress aHost = InetAddress.getByName(this.getIpAddress());
            int serverPort = Server.PORT;
            byte[] message = new byte[1];
            message[0] = CONNECT;
            DatagramPacket request = new DatagramPacket(message, message.length, aHost, serverPort);
            datagramSocket.send(request);
            getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startChat() throws IOException {
        int sair = SairEnum.NAO_SAIR.getCodigo();

        JOptionPane.showMessageDialog(null, WAITING_START, "Resposta",
                JOptionPane.INFORMATION_MESSAGE);

        while (sair != SairEnum.SAIR.getCodigo()) {
            String response = getResponse();

            System.out.println("Reply: " + response);
            JOptionPane.showMessageDialog(null, response, "Resposta", JOptionPane.INFORMATION_MESSAGE);

            if (response.trim().equals(END_CHAT)) {
                sair = SairEnum.SAIR.getCodigo();
            } else {
                String strMessage = JOptionPane.showInputDialog(null, SEND_MESSAGE);
                if (strMessage == null) {
                    sendMessage(DISCONNECT);
                    sair = SairEnum.SAIR.getCodigo();
                } else {
                    sendMessage(strMessage);
                    JOptionPane.showMessageDialog(null, WAITING_RESPONSE, "Aguardando", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void sendMessage(String strMessage) throws IOException {
        byte[] message = strMessage.getBytes(StandardCharsets.UTF_8);
        InetAddress aHost = InetAddress.getByName(this.getIpAddress());
        int serverPort = Server.PORT;
        DatagramPacket request = new DatagramPacket(message, message.length, aHost, serverPort);
        datagramSocket.send(request);
    }

    private String getResponse() throws IOException {
        byte[] answer = new byte[1000];
        DatagramPacket reply = new DatagramPacket(answer, answer.length);
        datagramSocket.receive(reply);

        return new String(reply.getData());
    }
}
