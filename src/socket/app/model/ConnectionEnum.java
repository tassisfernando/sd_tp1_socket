package socket.app.model;

public enum ConnectionEnum {
    UDP(0),
    TCP(1);

    int option;

    ConnectionEnum(int option) {
        this.option = option;
    }

    public int getOption() {
        return option;
    }
}
