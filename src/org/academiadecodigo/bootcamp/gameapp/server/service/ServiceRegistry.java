package org.academiadecodigo.bootcamp.gameapp.server.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cyrille on 02/07/17.
 */
public final class ServiceRegistry {

    private static ServiceRegistry instance;

    private Map<String, Service> serviceMap;

    private ServiceRegistry() {
        serviceMap = new HashMap<>();
    }

    public static ServiceRegistry getInstance() {

        if (instance == null) {

            synchronized (ServiceRegistry.class) {

                if (instance == null) {
                    instance = new ServiceRegistry();
                }
            }
        }

        return instance;
    }

    public void addService(Service service){
        System.out.println(service.getName());
        serviceMap.put(service.getName(), service);
    }

    public <T extends Service> T getService(String name){
        return (T) serviceMap.get(name);
    }

}
