package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entidade.Conta;
import entidade.Movimentacao;

public class ContaDAO {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("bancoPU");

    public Conta inserir(Conta cliente) {
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

    public Conta alterar(Conta conta) {
        Conta contaBanco = null;
        if (conta.getId() != null) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            contaBanco = buscarPorId(conta.getId());

            if (contaBanco != null) {
                em.merge(contaBanco);
            }

            em.getTransaction().commit();
            em.close();
        }
        return contaBanco;
    }

    public List<Conta> listarTodos() {
        EntityManager em = emf.createEntityManager();
        List<Conta> movimentacaoes = em.createQuery("from Conta", Conta.class).getResultList();
        em.close();
        return movimentacaoes;
    }

    public Conta buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Conta conta = em.find(Conta.class, id);
        em.close();
        return conta;
    }

    public int operacoesPorDia(String cpf) {
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("from Movimentacao m where m.cpfCorrentista = :cpf and DATE(m.dataTransacao) = CURRENT_DATE");
		query.setParameter("cpf", cpf);
		Long count = (Long) query.getSingleResult();
		em.close();
    	return count.intValue();
		//SELECT COUNT(m) FROM Movimentacao m WHERE cpfCorrentista = :cpf AND DATE(dataTransacao) = CURRENT_DATE
	}


    public int contarPorConta(Long id) {
        EntityManager em = emf.createEntityManager();
        Long count = em.createQuery(
            "SELECT COUNT(c) FROM Conta c WHERE c.cliente.id = :id_cliente", Long.class)
            .setParameter("id_cliente", id)
            .getSingleResult();
        em.close();
        return count.intValue();
    }
}
