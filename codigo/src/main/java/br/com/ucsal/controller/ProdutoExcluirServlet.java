package br.com.ucsal.controller;

import java.io.IOException;
import br.com.ucsal.annotations.Rota;
import br.com.ucsal.service.ProdutoService;
import br.com.ucsal.config.ServiceLocator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ProdutoExcluirServlet {
	private ProdutoService produtoService;

	public ProdutoExcluirServlet() {
		this.produtoService = ServiceLocator.getInstance().getService(ProdutoService.class);
	}

	@Rota(value = "/excluirProduto", method = "GET")
	public void excluir(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			produtoService.removerProduto(id);
			response.sendRedirect("listarProdutos");
		} catch (Exception e) {
			throw new ServletException("Erro ao excluir produto", e);
		}
	}
}
