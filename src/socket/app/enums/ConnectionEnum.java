package socket.app.enums;

import java.util.Arrays;

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

    public static ConnectionEnum findConnectionById(int id) {
        return Arrays.stream(values())
                .filter(connectionEnum -> connectionEnum.option == id)
                .findFirst()
                .orElse(UDP);
    }
}
