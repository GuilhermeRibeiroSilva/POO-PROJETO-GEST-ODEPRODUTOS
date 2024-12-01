package br.com.ucsal.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import br.com.ucsal.annotations.Inject;
import br.com.ucsal.persistencia.PersistenciaFactory;
import br.com.ucsal.persistencia.ProdutoRepository;

public class DependencyManager {
    private static DependencyManager instance;
    private final Map<Class<?>, Object> dependencies;

    private DependencyManager() {
        this.dependencies = new HashMap<>();
    }

    public static DependencyManager getInstance() {
        if (instance == null) {
            synchronized (DependencyManager.class) {
                if (instance == null) {
                    instance = new DependencyManager();
                }
            }
        }
        return instance;
    }

    public void processInject(Object object) {
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                try {
                    Object dependency = null;
                    if (ProdutoRepository.class.isAssignableFrom(field.getType())) {
                        dependency = PersistenciaFactory.createProdutoRepository();
                    } else {
                        dependency = dependencies.get(field.getType());
                    }
                    
                    if (dependency == null) {
                        throw new RuntimeException("Dependência não encontrada para: " + field.getType());
                    }
                    
                    field.set(object, dependency);
                } catch (Exception e) {
                    throw new RuntimeException("Erro ao injetar dependência em " + 
                        clazz.getSimpleName() + "." + field.getName(), e);
                }
            }
        }
    }

    public <T> void register(Class<T> type, T implementation) {
        dependencies.put(type, implementation);
    }

    public <T> T get(Class<T> type) {
        @SuppressWarnings("unchecked")
        T dependency = (T) dependencies.get(type);
        if (dependency == null) {
            throw new RuntimeException("Dependência não registrada: " + type);
        }
        return dependency;
    }

    }
