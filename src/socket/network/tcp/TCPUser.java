package socket.network.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class TCPUser {

    private DataInputStream in;
    private DataOutputStream out;

    public TCPUser(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
