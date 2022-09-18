package socket.network.udp;

import java.net.InetAddress;

public class UDPUser {

    private InetAddress ipAddress;
    private int port;

    public UDPUser(InetAddress ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public int getPort() {
        return port;
    }
}
