package servico;

import dao.ClienteDAO;
import dao.GenericoDAO;
import entidade.Cliente;
import validar.ValidarCPF;

public class ClienteServico implements ServicoBase<Cliente>{

    ClienteDAO dao = new ClienteDAO();

    @Override
	public Cliente inserir(Cliente cliente) {
        ValidarCPF.validarCpf(cliente.getCpf());       
        return dao.inserir(cliente);
    }

    @Override
	public void excluir(Long id) {
    }

    @Override
	public Cliente alterar(Cliente cliente) {
        return null;
    }

    @Override
    public GenericoDAO<Cliente> getDAO() {
        throw new UnsupportedOperationException("");
    }

    public boolean validarCliente(Cliente cliente){
        Cliente clienteValido= dao.buscarPorCpf(cliente.getCpf());
        if (clienteValido == null || !clienteValido.getCpf().equals(cliente.getCpf())) {
            return false;
        }
        return true;
    }

    public Cliente buscarPorId(Long id) {
        return dao.buscarPorId(id);
    }
}
