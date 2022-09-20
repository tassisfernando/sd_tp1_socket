package socket.network.model;

public abstract class Server {
    public static final int PORT = 6789;
    public static final int MAX_USERS = 2;

    public Server() {
        startServer();
    }
    public abstract void startServer();
}
