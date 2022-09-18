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
import java.util.Arrays;

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
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Houve uma falha na comunicação com o servidor.");
        }
    }

    private void connectToServer() throws IOException {
        try {
            sendMessage(Server.CONNECT);
            getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startChat() throws IOException {
        int sair = SairEnum.NAO_SAIR.getCodigo();
        String[] options = Arrays.stream(SairEnum.values()).map(SairEnum::getOpcao).toArray(String[]::new);

        while (sair != SairEnum.SAIR.getCodigo()) {
            String strMessage = JOptionPane.showInputDialog(null, "Envie sua mensagem:");
            sendMessage(strMessage);

            String response = getResponse();

            JOptionPane.showMessageDialog(null, response, "Resposta", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Reply: " + response);

            if (response.equals(Server.END_CHAT)) {
                sair = SairEnum.SAIR.getCodigo();
            } else {
                sair = JOptionPane.showOptionDialog(null, "Deseja sair?", "Chat", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, options, 0);
                sendMessage(Server.DISCONNECT);
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
