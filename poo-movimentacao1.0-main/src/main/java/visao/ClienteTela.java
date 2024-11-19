package visao;


import controle.ClienteControle;
import entidade.Cliente;

public class ClienteTela {
    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        ClienteControle controle = new ClienteControle();
        
        cliente.setNome("Daniele");
        cliente.setCpf("13136951905");
        controle.inserir(cliente);
    }
    
}