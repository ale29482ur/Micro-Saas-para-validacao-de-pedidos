package Process.example.ValidationProject.model;

public enum StatusPedido {

    EM_ANALISE_TECNICA, APROVADO, CANCELADO, EM_REPARO, AGUARDANDO_PAGAMENTO, PAGO, FINALIZADO;

    public StatusPedido proximoStatus() {
        return switch (this) {
            case EM_ANALISE_TECNICA -> APROVADO;
            case APROVADO -> EM_REPARO;
            case EM_REPARO -> AGUARDANDO_PAGAMENTO;
            case AGUARDANDO_PAGAMENTO -> PAGO;
            case PAGO -> FINALIZADO;
            default -> throw new IllegalStateException("Não é possível avançar a partir de " + this);
        };
    }

    public StatusPedido cancelStatus() {
        if (this == FINALIZADO || this == CANCELADO) {
            throw new IllegalStateException("nao foi possivel cancelar um pedido");
        }
        return CANCELADO;
    }

}