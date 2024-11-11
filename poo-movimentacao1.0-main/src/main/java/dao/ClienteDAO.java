package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entidade.Cliente;
import entidade.Movimentacao;
import javax.persistence.Query;

public class ClienteDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Cliente inserir(Cliente cliente) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
        return cliente;
    }

    public void excluir(Long id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Movimentacao movimentacao = em.find(Movimentacao.class, id);
		if (movimentacao != null) {
			em.remove(movimentacao);
		}
		em.getTransaction().commit();
		em.close();
	}

    public Cliente alterar(Cliente cliente) {
        Cliente clienteBanco = null;
        if (cliente.getId() != null) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            clienteBanco = buscarPorId(cliente.getId());

            if (clienteBanco != null) {
                em.merge(clienteBanco);
            }

            em.getTransaction().commit();
            em.close();
        }
        return clienteBanco;
    }

    public List<Cliente> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Cliente> movimentacaoes = em.createQuery("from Cliente", Cliente.class).getResultList();
        em.close();
        return movimentacaoes;
    }

    public Cliente buscarPorCpf(String cpf){
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.cpfCorrentista = :cpf");
        query.setParameter("cpf", cpf);
		Cliente cliente = (Cliente) query.getSingleResult();
		em.close();
		return cliente;
	}

    public Cliente buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Cliente cliente = em.find(Cliente.class, id);
        em.close();
        return cliente;
    }


}
