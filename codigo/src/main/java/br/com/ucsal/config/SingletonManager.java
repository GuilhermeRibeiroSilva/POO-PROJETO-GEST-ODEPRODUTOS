package br.com.ucsal.config;

import br.com.ucsal.annotations.Singleton;
import java.util.HashMap;
import java.util.Map;

public class SingletonManager {
    private static SingletonManager instance;
    private final Map<Class<?>, Object> singletons;

    private SingletonManager() {
        this.singletons = new HashMap<>();
    }

    public static SingletonManager getInstance() {
        if (instance == null) {
            synchronized (SingletonManager.class) {
                if (instance == null) {
                    instance = new SingletonManager();
                }
            }
        }
        return instance;
    }

    public <T> T getSingleton(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Singleton.class)) {
            throw new IllegalArgumentException("Classe não é um Singleton");
        }

        @SuppressWarnings("unchecked")
        T singleton = (T) singletons.get(clazz);
        
        if (singleton == null) {
            synchronized (singletons) {
                singleton = (T) singletons.get(clazz);
                if (singleton == null) {
                    try {
                        singleton = clazz.getDeclaredConstructor().newInstance();
                        singletons.put(clazz, singleton);
                    } catch (Exception e) {
                        throw new RuntimeException("Erro ao criar singleton", e);
                    }
                }
            }
        }
        return singleton;
    }
} 