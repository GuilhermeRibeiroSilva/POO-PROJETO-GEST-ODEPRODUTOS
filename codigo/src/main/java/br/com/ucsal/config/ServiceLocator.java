package br.com.ucsal.config;

public class ServiceLocator {
    private static ServiceLocator instance;
    private final DependencyManager dependencyManager;

    private ServiceLocator() {
        this.dependencyManager = DependencyManager.getInstance();
    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                if (instance == null) {
                    instance = new ServiceLocator();
                }
            }
        }
        return instance;
    }

    public <T> T getService(Class<T> serviceClass) {
        return dependencyManager.get(serviceClass);
    }
} 