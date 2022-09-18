package socket.network.model;

public abstract class Server {
    public static final int PORT = 6789;
    public static final int MAX_USERS = 2;
    public static final String CONNECT = "C";
    public static final String DISCONNECT = "D";
    public static final String WELCOME_MSG = "Bem-vindo ao chat!";
    public static final String WAITING_USERS = "Aguardando outro usuário...";
    public static final String START_CHAT = "Inicie a conversa:";
    public static final String END_CHAT = "O outro usuário saiu do chat!";

    public Server() {
        startServer();
    }
    public abstract void startServer();
}
