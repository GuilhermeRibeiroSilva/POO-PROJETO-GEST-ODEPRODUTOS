package br.com.ucsal.controller;

import java.io.IOException;
import java.util.List;

import br.com.ucsal.annotations.Rota;
import br.com.ucsal.model.Produto;
import br.com.ucsal.persistencia.HSQLProdutoRepository;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.config.ServiceLocator;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class ProdutoListarServlet {
    private static final long serialVersionUID = 1L;
	private ProdutoService produtoService;

	public ProdutoListarServlet() {
        this.produtoService = ServiceLocator.getInstance().getService(ProdutoService.class);
	}
	

    @Rota(value = "/listarProdutos", method = "GET")
    public void listar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            request.setAttribute("produtos", produtos);
            request.getRequestDispatcher("/WEB-INF/views/produtolista.jsp")
                  .forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao listar produtos", e);
        }
    }

}
