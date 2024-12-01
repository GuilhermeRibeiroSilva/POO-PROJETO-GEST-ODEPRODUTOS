package br.com.ucsal.controller;

import java.io.IOException;
import br.com.ucsal.annotations.Rota;
import br.com.ucsal.model.Produto;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.config.ServiceLocator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProdutoEditarServlet {
    private ProdutoService produtoService;

    public ProdutoEditarServlet() {
        this.produtoService = ServiceLocator.getInstance().getService(ProdutoService.class);
    }

    @Rota(value = "/editarProduto", method = "GET")
    public void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Produto produto = produtoService.obterProdutoPorId(id);
            request.setAttribute("produto", produto);
            request.getRequestDispatcher("/WEB-INF/views/produtoformulario.jsp")
                  .forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar produto para edição", e);
        }
    }

    @Rota(value = "/editarProduto", method = "POST")
    public void atualizar(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            double preco = Double.parseDouble(request.getParameter("preco"));
            
            Produto produto = new Produto(id, nome, preco);
            produtoService.atualizarProduto(produto);
            response.sendRedirect("listarProdutos");
        } catch (Exception e) {
            throw new ServletException("Erro ao atualizar produto", e);
        }
    }
}

