package teste;

import entidade.Conta;
import entidade.ContaTipo;

public class TesteConta {


    public static void main(String[] args) {
        Conta conta = new Conta();

        conta.setContaTipo(ContaTipo.CONTA_CORRENTE);
    }
}
