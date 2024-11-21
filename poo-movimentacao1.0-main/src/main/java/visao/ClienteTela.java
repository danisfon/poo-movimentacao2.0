package visao;


import controle.ClienteControle;
import entidade.Cliente;

public class ClienteTela {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        ClienteControle controle = new ClienteControle();
        
        cliente.setNome("José");
        cliente.setCpf("05497395058");
        controle.inserir(cliente);
    }
    
}