package br.com.ucsal.persistencia;

import br.com.ucsal.annotations.Singleton;
import br.com.ucsal.model.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class MemoriaProdutoRepository implements ProdutoRepository<Produto, Integer> {
    private static volatile MemoriaProdutoRepository instancia;
    private final Map<Integer, Produto> produtos;
    private final AtomicInteger currentId;

    private MemoriaProdutoRepository() {
        this.produtos = new HashMap<>();
        this.currentId = new AtomicInteger(1);
    }

    public static MemoriaProdutoRepository getInstancia() {
        if (instancia == null) {
            synchronized (MemoriaProdutoRepository.class) {
                if (instancia == null) {
                    instancia = new MemoriaProdutoRepository();
                }
            }
        }
        return instancia;
    }

    @Override
    public void adicionar(Produto entidade) {
        int id = currentId.getAndIncrement();
        entidade.setId(id);
        produtos.put(entidade.getId(), entidade);
    }

    @Override
    public void atualizar(Produto entidade) {
        produtos.put(entidade.getId(), entidade);
    }

    @Override
    public void remover(Integer id) {
        produtos.remove(id);
    }

    @Override
    public List<Produto> listar() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Produto obterPorID(Integer id) {
        return produtos.get(id);
    }
}
