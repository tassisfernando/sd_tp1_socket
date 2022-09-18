package socket.network.model;

public abstract class Client {

    private String ipAddress;

    public Client(String ipAddress) {
        this.ipAddress = ipAddress;
        startClient();
    }

    public abstract void startClient();

    public String getIpAddress() {
        return ipAddress;
    }
}
