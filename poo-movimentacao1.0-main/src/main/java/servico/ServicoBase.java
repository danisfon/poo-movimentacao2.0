package servico;

import dao.GenericoDAO;

public interface ServicoBase<T> {

    GenericoDAO<T> getDAO();

    default T inserir(T entidade) {
        return getDAO().inserir(entidade);
    }

    T alterar(T entidade);
    void excluir(Long id);
}
