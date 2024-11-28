package visao;

import java.util.Date;

import controle.ClienteControle;
import controle.ContaControle;
import entidade.Cliente;
import entidade.Conta;
import entidade.ContaTipo;

public class ContaTela {
    public static void main(String[] args) {
        ClienteControle controleCliente = new ClienteControle();
        Cliente cliente = controleCliente.buscarPorId(1L);
        ContaControle controleConta = new ContaControle();
        Conta conta = new Conta();
        
        conta.setDataAbertura(new Date());
        conta.setContaTipo(ContaTipo.CONTA_POUPANCA);
        conta.setCliente(cliente);
        controleConta.adicionarConta(cliente.getId());
        controleConta.inserir(conta);
    }
}