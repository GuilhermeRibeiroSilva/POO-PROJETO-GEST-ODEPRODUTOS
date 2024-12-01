package br.com.ucsal.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.ucsal.annotations.Rota;
import br.com.ucsal.config.ServiceLocator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

@WebServlet(urlPatterns = {"/view/*"})
public class ProdutoController extends HttpServlet {
    private Map<String, RouteInfo> rotas = new HashMap<>();
    
    private static class RouteInfo {
        Method method;
        String httpMethod;
        
        RouteInfo(Method method, String httpMethod) {
            this.method = method;
            this.httpMethod = httpMethod;
        }
    }
    
    @Override
    public void init() throws ServletException {
        carregarRotas();
    }
    
    private void carregarRotas() throws ServletException {
        try {
            Reflections reflections = new Reflections("br.com.ucsal.controller", 
                new MethodAnnotationsScanner());
            
            Set<Method> metodos = reflections.getMethodsAnnotatedWith(Rota.class);
            
            for (Method method : metodos) {
                Rota rota = method.getAnnotation(Rota.class);
                rotas.put(rota.value(), new RouteInfo(method, rota.method()));
            }
            System.out.println("Rotas carregadas: " + rotas.keySet());
        } catch (Exception e) {
            throw new ServletException("Erro ao carregar rotas", e);
        }
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String path = request.getPathInfo();
        if (path == null) path = "/";
        
        RouteInfo routeInfo = rotas.get(path);
        
        if (routeInfo != null) {
            if (!request.getMethod().equalsIgnoreCase(routeInfo.httpMethod)) {
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, 
                    "Método HTTP não permitido para esta rota");
                return;
            }
            
            try {
                Class<?> classe = routeInfo.method.getDeclaringClass();
                Object instance = ServiceLocator.getInstance().getService(classe);
                routeInfo.method.invoke(instance, request, response);
            } catch (Exception e) {
                throw new ServletException("Erro ao processar operação no ProdutoController: " + e.getMessage(), e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, 
                "Rota não encontrada: " + path);
        }
    }
}
