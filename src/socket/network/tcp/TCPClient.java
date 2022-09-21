package socket.network.tcp;

import socket.app.enums.SairEnum;
import socket.network.model.Client;
import socket.network.model.Server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static socket.app.model.MessageUtils.*;

public class TCPClient extends Client {
    private DataInputStream in;
    private DataOutputStream out;

    public TCPClient(String ipAddress) {
        super(ipAddress);
    }

    @Override
    public void startClient() {
        try {
            connectToServer();
            startChat();
            JOptionPane.showMessageDialog(null, "Até mais!");
        } catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve uma falha na comunicação com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void connectToServer() {
        Socket socket;
        try {
            socket = new Socket(this.getIpAddress(), Server.PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            in.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startChat() {
        try {
            int sair = SairEnum.NAO_SAIR.getCodigo();

            JOptionPane.showMessageDialog(null, WAITING_START, "Resposta",
                    JOptionPane.INFORMATION_MESSAGE);

            while (sair != SairEnum.SAIR.getCodigo()) {
                String response = in.readUTF();

                JOptionPane.showMessageDialog(null, response, "Resposta", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Reply: " + response);

                if (response.trim().equals(END_CHAT)) {
                    sair = SairEnum.SAIR.getCodigo();
                } else {
                    String strMessage = JOptionPane.showInputDialog(null, SEND_MESSAGE);
                    if (strMessage == null) {
                        out.writeUTF(DISCONNECT);
                        sair = SairEnum.SAIR.getCodigo();
                    } else {
                        out.writeUTF(strMessage);
                        JOptionPane.showMessageDialog(null, WAITING_RESPONSE, "Aguardando", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Houve uma falha na comunicação com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}