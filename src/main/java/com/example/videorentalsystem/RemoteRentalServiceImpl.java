package com.example.videorentalsystem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * <h1>RemoteRentalServiceImpl Class</h1>
 * This class provides the concrete server-side implementation of the
 * RemoteRentalService network contract, interacting directly with the DAO layer.
 * * @author Daisy Itago
 * @version 1.0
 */
public class RemoteRentalServiceImpl extends UnicastRemoteObject implements RemoteRentalService {

    /**
     * Public constructor required to export the object onto an anonymous ephemeral port.
     * @throws RemoteException If an encryption or network export failure occurs.
     */
    public RemoteRentalServiceImpl() throws RemoteException {
        super(); // Exports the object to the RMI runtime environment
    }

    // --- GENRE IMPLEMENTATIONS ---
    @Override
    public void saveGenre(Genre genre) throws RemoteException {
        System.out.println("RMI Server: Processing saveGenre request for '" + genre.getName() + "'");
        GenreDAO.saveGenre(genre);
    }

    @Override
    public List<Genre> getAllGenres() throws RemoteException {
        System.out.println("RMI Server: Fetching all active genres from database...");
        return GenreDAO.getAllGenres();
    }

    // --- MOVIE IMPLEMENTATIONS ---
    @Override
    public void saveMovie(Movie movie) throws RemoteException {
        System.out.println("RMI Server: Processing saveMovie request for '" + movie.getTitle() + "'");
        MovieDAO.saveMovie(movie);
    }

    @Override
    public List<Movie> getAllMovies() throws RemoteException {
        System.out.println("RMI Server: Fetching all active movies from database...");
        return MovieDAO.getAllMovies();
    }

    // --- CUSTOMER IMPLEMENTATIONS ---
    @Override
    public void saveCustomer(Customer customer) throws RemoteException {
        System.out.println("RMI Server: Processing saveCustomer request for '" + customer.getFullName() + "'");
        CustomerDAO.saveCustomer(customer);
    }

    @Override
    public List<Customer> getAllCustomers() throws RemoteException {
        System.out.println("RMI Server: Fetching all active customers from database...");
        return CustomerDAO.getAllCustomers();
    }

    // --- RENTAL IMPLEMENTATIONS ---
    @Override
    public void saveRental(Rental rental) throws RemoteException {
        System.out.println("RMI Server: Logging new rental checkout record...");
        RentalDAO.saveRental(rental);
    }

    @Override
    public List<Rental> getAllRentals() throws RemoteException {
        System.out.println("RMI Server: Fetching historical rental logs...");
        return RentalDAO.getAllRentals();
    }
}