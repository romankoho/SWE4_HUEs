package at.fhooe.swe4.database;

import at.fhooe.swe4.model.*;
import at.fhooe.swe4.model.enums.Category;
import at.fhooe.swe4.model.enums.Condition;
import at.fhooe.swe4.model.enums.FederalState;
import at.fhooe.swe4.model.enums.Status;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface Database extends Remote {
    List<DemandItem> getDemandItems() throws RemoteException;
    void addDemand(DemandItem d) throws RemoteException, SQLException;

    void deleteDemand(DemandItem d) throws RemoteException;

    void updateDemand(DemandItem d, Article article, ReceivingOffice relatedOffice, Integer amount) throws RemoteException;

    void reduceDemand(DemandItem d, Integer amount) throws RemoteException;

    List<Donation> getDonations() throws RemoteException;
    void addDonation(Donation d) throws RemoteException, SQLException;
    List<Article> getArticles() throws RemoteException;
    void addArticle(Article a) throws RemoteException, SQLException;
    void deleteArticle(Article a) throws RemoteException;
    void updateArticle(Article a, String name, String description, Condition condition, Category category) throws RemoteException;

    List<ReceivingOffice> getOffices() throws RemoteException;
    void addOffice(ReceivingOffice o) throws RemoteException, SQLException;
    void deleteOffice(ReceivingOffice o) throws RemoteException;
    void updateOffice(ReceivingOffice o, String name, FederalState federalState, String district,
                             String address, Status status) throws RemoteException;

    HashMap<String, User> getUsers() throws RemoteException;
    void addUser(String key, User user) throws RemoteException, SQLException;
    User getUserByEMail(String email) throws RemoteException, SQLException;
    void clearDB() throws RemoteException;

}
