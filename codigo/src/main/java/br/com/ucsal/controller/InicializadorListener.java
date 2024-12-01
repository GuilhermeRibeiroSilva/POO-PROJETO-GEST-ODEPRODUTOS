package br.com.ucsal.controller;

import br.com.ucsal.config.DependencyManager;
import br.com.ucsal.config.ServiceLocator;
import br.com.ucsal.model.Produto;
import br.com.ucsal.persistencia.PersistenciaFactory;
import br.com.ucsal.persistencia.PersistenciaFactory.TipoPersistencia;
import br.com.ucsal.persistencia.ProdutoRepository;
import br.com.ucsal.service.ProdutoService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class InicializadorListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Configura o tipo de persistência
            PersistenciaFactory.setTipoPersistencia(TipoPersistencia.HSQLDB);
            
            // Configura o repositório
            DependencyManager dm = DependencyManager.getInstance();
            ProdutoRepository<Produto, Integer> repository = 
                PersistenciaFactory.createProdutoRepository();
            dm.register(ProdutoRepository.class, repository);
            
            // Registra e configura o ProdutoService
            ProdutoService produtoService = new ProdutoService();
            dm.processInject(produtoService);
            dm.register(ProdutoService.class, produtoService);
            
            System.out.println("Sistema inicializado com sucesso");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar o sistema: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}