package socket.network.tcp;

import socket.app.enums.SairEnum;
import socket.network.model.Client;
import socket.network.model.Server;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import static socket.app.model.MessageUtils.DISCONNECT;
import static socket.app.model.MessageUtils.END_CHAT;

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
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o servidor",
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startChat() {
        new Thread(() -> {
            try {
                int sair = SairEnum.NAO_SAIR.getCodigo();

                JOptionPane.showMessageDialog(null, "Aguardando início", "Resposta",
                        JOptionPane.INFORMATION_MESSAGE);

                while (sair != SairEnum.SAIR.getCodigo()) {
                    String response = in.readUTF();

                    JOptionPane.showMessageDialog(null, response, "Resposta", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Reply: " + response);

                    if (response.trim().equals(END_CHAT)) {
                        sair = SairEnum.SAIR.getCodigo();
                    } else {
                        String strMessage = JOptionPane.showInputDialog(null,
                                "Envie sua mensagem: (Digite 'D' para sair)");

                        if (strMessage.trim().equals(DISCONNECT)) {
                            out.writeUTF(DISCONNECT);
                            sair = SairEnum.SAIR.getCodigo();
                        } else {
                            out.writeUTF(strMessage);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Houve uma falha na comunicação com o servidor",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}