package com.example.videorentalsystem;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * <h1>RemoteRentalService Interface</h1>
 * This acts as the distributed network contract for the Video Library
 * Rental System (VLS), mapping methods that are remotely executable via Java RMI.
 * * @author Daisy Itago
 * @version 1.0
 */
public interface RemoteRentalService extends Remote {

    // --- GENRE METHODS ---
    void saveGenre(Genre genre) throws RemoteException;
    List<Genre> getAllGenres() throws RemoteException;

    // --- MOVIE METHODS ---
    void saveMovie(Movie movie) throws RemoteException;
    List<Movie> getAllMovies() throws RemoteException;

    // --- CUSTOMER METHODS ---
    void saveCustomer(Customer customer) throws RemoteException;
    List<Customer> getAllCustomers() throws RemoteException;

    // --- RENTAL METHODS ---
    void saveRental(Rental rental) throws RemoteException;
    List<Rental> getAllRentals() throws RemoteException;
}
