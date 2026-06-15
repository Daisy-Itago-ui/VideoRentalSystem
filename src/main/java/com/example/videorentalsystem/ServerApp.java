package com.example.videorentalsystem;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * <h1>ServerApp Program</h1>
 * This main utility class instantiates the RMI Registry environment,
 * creates the remote implementation service, and binds it to the network port.
 * * @author Daisy Itago
 * @version 1.0
 */
public class ServerApp {

    /**
     * Main entry point to launch the VLS database host server application.
     * @param args Command line arguments passed during execution.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Initializing VLS Distributed Server Core...");

            // 1. Fire up the RMI Registry on the industry-standard port 1099
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry successfully established on port 1099.");

            // 2. Instantiate the remote service worker implementation
            RemoteRentalService service = new RemoteRentalServiceImpl();

            // 3. Bind the service instance to a unique nickname string reference
            registry.rebind("VlsRentalService", service);

            System.out.println("VLS Remote Service bound to registry identifier: 'VlsRentalService'");
            System.out.println("Server is officially standing by. Ready to process remote client transactions...");

        } catch (Exception e) {
            System.err.println("CRITICAL FAILURE: Server aborted initialization sequence!");
            e.printStackTrace();
        }
    }
}

