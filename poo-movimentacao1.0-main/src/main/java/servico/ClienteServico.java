package servico;

import dao.ClienteDAO;
import entidade.Cliente;

public class ClienteServico {

    ClienteDAO dao = new ClienteDAO();

    public Cliente inserir(Cliente cliente) {
		Cliente cliente1 = dao.inserir(cliente);
		return cliente1;
	}
}
