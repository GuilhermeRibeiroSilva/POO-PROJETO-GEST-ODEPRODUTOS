package br.com.ucsal.controller;

import java.io.IOException;
import br.com.ucsal.annotations.Rota;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.config.ServiceLocator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProdutoAdicionarServlet {
    private ProdutoService produtoService;

    public ProdutoAdicionarServlet() {
        this.produtoService = ServiceLocator.getInstance().getService(ProdutoService.class);
    }

    @Rota(value = "/adicionarProduto", method = "GET")
    public void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp")
              .forward(request, response);
    }

    @Rota(value = "/adicionarProduto", method = "POST")
    public void adicionar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String nome = request.getParameter("nome");
            double preco = Double.parseDouble(request.getParameter("preco"));
            produtoService.adicionarProduto(nome, preco);
            response.sendRedirect("listarProdutos");
        } catch (Exception e) {
            throw new ServletException("Erro ao adicionar produto", e);
        }
    }
}

