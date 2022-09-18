package socket.app.enums;

public enum SairEnum {
    NAO_SAIR("Não sair", 0),
    SAIR("Sair", 1);

    String opcao;
    int codigo;

    SairEnum(String opcao, int codigo) {
        this.opcao = opcao;
        this.codigo = codigo;
    }

    public String getOpcao() {
        return opcao;
    }

    public int getCodigo() {
        return codigo;
    }
}
