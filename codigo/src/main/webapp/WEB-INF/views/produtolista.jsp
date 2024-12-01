<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Produtos</title>
</head>
<body>
    <h2>Produtos</h2>
    <a href="adicionarProduto" class="botao-adicionar">Adicionar Novo Produto</a><br/><br/>

    <c:if test="${not empty produtos}">
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Preço</th>
                <th>Ações</th>
            </tr>
            <c:forEach var="produto" items="${produtos}">
                <tr>
                    <td>${produto.id}</td>
                    <td>${produto.nome}</td>
                    <td>R$ ${produto.preco}</td>
                    <td>
                        <a href="editarProduto?id=${produto.id}">Editar</a>
                        <a href="excluirProduto?id=${produto.id}" 
                           onclick="return confirm('Tem certeza que deseja excluir este produto?');">
                            Excluir
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    
    <c:if test="${empty produtos}">
        <p>Nenhum produto cadastrado.</p>
    </c:if>
</body>
</html>
