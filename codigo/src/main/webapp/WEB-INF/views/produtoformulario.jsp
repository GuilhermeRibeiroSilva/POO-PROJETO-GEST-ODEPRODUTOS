<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Formulário de Produto</title>
</head>
<body>
    <h2>
        <c:choose>
            <c:when test="${not empty produto}">Editar Produto</c:when>
            <c:otherwise>Novo Produto</c:otherwise>
        </c:choose>
    </h2>

    <form action="<c:choose>
                    <c:when test='${not empty produto}'>editarProduto</c:when>
                    <c:otherwise>adicionarProduto</c:otherwise>
                  </c:choose>" 
          method="post">
        
        <c:if test="${not empty produto}">
            <input type="hidden" name="id" value="${produto.id}">
        </c:if>
        
        <div>
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome" value="${produto.nome}" required/>
        </div>
        
        <div>
            <label for="preco">Preço:</label>
            <input type="number" id="preco" name="preco" value="${produto.preco}" 
                   step="0.01" min="0" required/>
        </div>
        
        <div>
            <input type="submit" value="Salvar"/>
            <a href="produtos">Cancelar</a>
        </div>
    </form>
</body>
</html>
