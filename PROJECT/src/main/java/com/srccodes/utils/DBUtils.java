package com.srccodes.utils;


import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.srccodes.beans.User;
 
public class DBUtils {
 
    public static User findUser(Connection conn, //
            String userName, String password) throws SQLException {
 
        String sql = "Select a.user, a.Password, a.email, a.client_id, a.owner_id, a.admin_id from user a " //
                + " where a.user = ? and a.password= ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String email = rs.getString("email");
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(email);

            int client_id, owner_id, admin_id;
            client_id = rs.getInt("client_id");
            owner_id = rs.getInt("owner_id");
            admin_id = rs.getInt("admin_id");
            
            if(client_id != 0) {
            	user.setType("client");
            }
            else if(owner_id != 0) {
            	user.setType("owner");
            }
            else user.setType("admin");
            
            return user;
        }
        return null;
    }
    
    public static User findUser(Connection conn, //
            String userName, String password, String email) throws SQLException {
 
        String sql = "Select a.user, a.Password, a.email, a.client_id, a.owner_id, a.admin_id from user a " //
                + " where a.user = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
        pstm.setString(2, email);
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String newEmail = rs.getString("email");
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(newEmail);

            int client_id, owner_id, admin_id;
            client_id = rs.getInt("client_id");
            owner_id = rs.getInt("owner_id");
            admin_id = rs.getInt("admin_id");
            
            if(client_id != 0) {
            	user.setType("client");
            }
            else if(owner_id != 0) {
            	user.setType("owner");
            }
            else user.setType("admin");
            
            return user;
        }
        return null;
    }
   
    public static User findUser(Connection conn, String userName) throws SQLException {
 
    	String sql = "Select a.user, a.Password, a.email, a.client_id, a.owner_id, a.admin_id from user a " //
                + " where a.user = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, userName);
 
        ResultSet rs = pstm.executeQuery();
 
        if (rs.next()) {
            String password = rs.getString("Password");
            String email = rs.getString("Email");
            User user = new User();
            user.setUserName(userName);
            user.setPassword(password);
            user.setEmail(email);
            int client_id, owner_id, admin_id;
            client_id = rs.getInt("client_id");
            owner_id = rs.getInt("owner_id");
            admin_id = rs.getInt("admin_id");

            if(client_id != 0) {
            	user.setType("client");
            }
            else if(owner_id != 0) {
            	user.setType("owner");
            }
            else user.setType("admin");
            
            return user;
        }
        return null;
    }
    
    public static void addUser(Connection conn, String userName, String password, String email, String type, String firstName, String lastName) throws SQLException{
    	
    	if(type.equals("client")) {
    	
	    	String sql1 = "INSERT INTO `mydb`.`client` (`user`, `password`, `email`, `first_name`, `last_name`) VALUES (?, ?, ?, ?, ?)";
	    	
	    	PreparedStatement pstm = conn.prepareStatement(sql1);
	    	pstm.setString(1, userName);
	    	pstm.setString(2, password);
	    	pstm.setString(3, email);
	    	pstm.setString(4, firstName);
	    	pstm.setString(5, lastName);
	    	pstm.execute();
	    	
	    	String sql2 = "Select a.client_id from client a "//
	                + " where a.user = ? and a.password = ?";
	    	pstm = conn.prepareStatement(sql2);
        	pstm.setString(1, userName);
        	pstm.setString(2, password);
        	
        	ResultSet rs = pstm.executeQuery();
        	int id = 0;
        	
        	if(rs.next()) {
        		id = rs.getInt("client_id");
        	}
	    		
	    	String sql3 = "INSERT INTO `mydb`.`user` (`user`, `password`, `email`, `client_id`) VALUES (?, ?, ?, ?)";
	    	
	    	pstm = conn.prepareStatement(sql3);
	    	pstm.setString(1, userName);
	    	pstm.setString(2, password);
	    	pstm.setString(3, email);
	    	pstm.setInt(4,  id);
	    	pstm.execute();
    	}
    	
    	if(type.equals("owner")) {
    		
    		String sql1 = "INSERT INTO `mydb`.`owner` (`user`, `password`, `email`, `approval`, `first_name`, `last_name`) VALUES (?, ?, ?, 0, ?, ?)";
	    	
	    	PreparedStatement pstm = conn.prepareStatement(sql1);
	    	pstm.setString(1, userName);
	    	pstm.setString(2, password);
	    	pstm.setString(3, email);
	    	pstm.setString(4, firstName);
	    	pstm.setString(5, lastName);
	    	pstm.execute();
	    	
	    	String sql2 = "Select a.owner_id from owner a "//
	                + " where a.user = ? and a.password = ?";
	    	pstm = conn.prepareStatement(sql2);
        	pstm.setString(1, userName);
        	pstm.setString(2, password);
        	
        	ResultSet rs = pstm.executeQuery();
        	int id = 0;
        	
        	if(rs.next()) {
        		id = rs.getInt("owner_id");
        	}
    		
        	String sql3 = "INSERT INTO `mydb`.`user` (`user`, `password`, `email`, `owner_id`) VALUES (?, ?, ?, ?)";
        	
        	pstm = conn.prepareStatement(sql3);
        	pstm.setString(1, userName);
        	pstm.setString(2, password);
        	pstm.setString(3, email);
        	pstm.setInt(4, id);
        	pstm.execute();
        	}
    	
    }
    
    public static void changePassword(Connection conn, String userName, String password) throws SQLException{
   
    	String sql1 = "Select a.user, a.Password, a.email, a.client_id, a.owner_id, a.admin_id from user a " //
                + " where a.user = ?";
    	
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, userName);
    	
    	ResultSet rs = pstm.executeQuery();
    	
    	int idToUpdate = 0;
    	
    	String sql2 = null;
    	 
        if (rs.next()) {
            int client_id, owner_id, admin_id;
            client_id = rs.getInt("client_id");
            owner_id = rs.getInt("owner_id");
            admin_id = rs.getInt("admin_id");

            if(client_id != 0) {
            	idToUpdate = client_id;
            	sql2 = "UPDATE `mydb`.`client` SET `password` = ? WHERE (`client_id` = ?)";
            	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            	pstm2.setString(1, password);
            	pstm2.setInt(2, idToUpdate);
            	pstm2.execute();
            }
            else if(owner_id != 0) {     
            	idToUpdate = owner_id;
            	sql2 = "UPDATE `mydb`.`owner` SET `password` = ? WHERE (`owner_id` = ?)";
            	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            	pstm2.setString(1, password);
            	pstm2.setInt(2, idToUpdate);
            	pstm2.execute();
            }
            else {
            	idToUpdate = admin_id;
            	sql2 = "UPDATE `mydb`.`admin` SET `password` = ? WHERE (`admin_id` = ?)";
            	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            	pstm2.setString(1, password);
            	pstm2.setInt(2, idToUpdate);
            	pstm2.execute();
            }
           
        }

        String sql3 = "UPDATE `mydb`.`user` SET `password` = ? WHERE (`user` = ?)";
        pstm = conn.prepareStatement(sql3);
        pstm.setString(1, password);
        pstm.setString(2, userName);
        pstm.execute();
        
    }
    
    public static void changeEmail(Connection conn, String userName, String password) throws SQLException{
    	   
    	String sql1 = "Select a.user, a.Password, a.email, a.client_id, a.owner_id, a.admin_id from user a " //
                + " where a.user = ?";
    	
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, userName);
    	
    	ResultSet rs = pstm.executeQuery();
    	
    	int idToUpdate = 0;
    	
    	String sql2 = null;
    	 
        if (rs.next()) {
            int client_id, owner_id, admin_id;
            client_id = rs.getInt("client_id");
            owner_id = rs.getInt("owner_id");
            admin_id = rs.getInt("admin_id");

            if(client_id != 0) {
            	idToUpdate = client_id;
            	sql2 = "UPDATE `mydb`.`client` SET `email` = ? WHERE (`client_id` = ?)";
            	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            	pstm2.setString(1, password);
            	pstm2.setInt(2, idToUpdate);
            	pstm2.execute();
            }
            else if(owner_id != 0) {     
            	idToUpdate = owner_id;
            	sql2 = "UPDATE `mydb`.`owner` SET `email` = ? WHERE (`owner_id` = ?)";
            	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            	pstm2.setString(1, password);
            	pstm2.setInt(2, idToUpdate);
            	pstm2.execute();
            }
            else {
            	idToUpdate = admin_id;
            	sql2 = "UPDATE `mydb`.`admin` SET `email` = ? WHERE (`admin_id` = ?)";
            	PreparedStatement pstm2 = conn.prepareStatement(sql2);
            	pstm2.setString(1, password);
            	pstm2.setInt(2, idToUpdate);
            	pstm2.execute();
            }
           
        }

        String sql3 = "UPDATE `mydb`.`user` SET `email` = ? WHERE (`user` = ?)";
        pstm = conn.prepareStatement(sql3);
        pstm.setString(1, password);
        pstm.setString(2, userName);
        pstm.execute();
        
    }
    
    public static int getPropertyId(Connection conn, String name) throws SQLException{
    	String sql = "Select a.virtual_property_id from virtual_property a " //
                + " where a.name = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        int property_id = 0;
 
        if (rs.next()) {
            property_id = rs.getInt("virtual_property_id");
        }
        
        return property_id;

    }
    
    public static float getLat(Connection conn, String name) throws SQLException{
    	String sql = "Select a.lat from virtual_property a " //
                + " where a.name = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        float property_id = 0;
 
        if (rs.next()) {
            property_id = rs.getFloat("lat");
        }
        
        return property_id;

    }
    
    public static float getLng(Connection conn, String name) throws SQLException{
    	String sql = "Select a.lng from virtual_property a " //
                + " where a.name = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        float property_id = 0;
 
        if (rs.next()) {
            property_id = rs.getFloat("lng");
        }
        
        return property_id;

    }
    
    public static String getPropertyName(Connection conn, int id) throws SQLException{
    	String sql = "Select a.name from virtual_property a " //
                + " where a.virtual_property_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        String name = null;
 
        if (rs.next()) {
            name = rs.getString("name");
        }
        
        return name;

    }
    
    public static int getPropertyOwner(Connection conn, int id) throws SQLException{
    	String sql = "Select a.owner_id from virtual_property a " //
                + " where a.virtual_property_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        int owner_id = 0;
 
        if (rs.next()) {
            owner_id = rs.getInt("owner_id");
        }
        
        return owner_id;

    }
    
    public static int getApproval(Connection conn, int id) throws SQLException{
    	String sql = "Select a.approval from owner a " //
                + " where a.owner_id = ?";
    	
    	PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        int approval = 0;
 
        if (rs.next()) {
            approval = rs.getInt("approval");
        }
        
        return approval;
    }
    
    public static String getPropertyAddress(Connection conn, String name) throws SQLException{
    	String sql = "Select a.address from virtual_property a " //
                + " where a.name = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        String address = null;
        
        if (rs.next()) {
            address = rs.getString("address");
        }
        
        return address;

    }
    
    public static String getOwnerName(Connection conn, int id) throws SQLException{
    	String sql = "Select a.user from owner a " //
                + " where a.owner_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        String address = null;
        
        if (rs.next()) {
            address = rs.getString("user");
        }
        
        return address;

    }
    
    public static String getClientFirstName(Connection conn, int id) throws SQLException{
    	String sql = "Select a.first_name from client a " //
                + " where a.client_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        String address = null;
        
        if (rs.next()) {
            address = rs.getString("first_name");
        }
        
        return address;

    }
    
    public static String getClientLastName(Connection conn, int id) throws SQLException{
    	String sql = "Select a.last_name from client a " //
                + " where a.client_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        String address = null;
        
        if (rs.next()) {
            address = rs.getString("last_name");
        }
        
        return address;

    }
    
    public static String getOwnerFirstName(Connection conn, int id) throws SQLException{
    	String sql = "Select a.first_name from owner a " //
                + " where a.owner_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        String address = null;
        
        if (rs.next()) {
            address = rs.getString("first_name");
        }
        
        return address;

    }
    
    public static String getOwnerLastName(Connection conn, int id) throws SQLException{
    	String sql = "Select a.last_name from owner a " //
                + " where a.owner_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        String address = null;
        
        if (rs.next()) {
            address = rs.getString("last_name");
        }
        
        return address;

    }
    
    public static float getPropertyPrice(Connection conn, String name) throws SQLException{
    	String sql = "Select a.price from virtual_property a " //
                + " where a.name = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        float price = 0;
        
        if (rs.next()) {
            price = rs.getFloat("price");
        }
        
        return price;

    }
    
    public static int getPropertyValid(Connection conn, String name) throws SQLException{
    	String sql = "Select a.valid from virtual_property a " //
                + " where a.name = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        int price = 0;
        
        if (rs.next()) {
            price = rs.getInt("valid");
        }
        
        return price;

    }
    
    public static int getOwnerId(Connection conn, String name) throws SQLException{
    	String sql = "Select a.owner_id from owner a " //
                + " where a.user = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        int owner_id = 0;
 
        if (rs.next()) {
            owner_id = rs.getInt("owner_id");
        }
        
        return owner_id;

    }
    
    public static int getClientId(Connection conn, String name) throws SQLException{
    	String sql = "Select a.client_id from client a " //
                + " where a.user = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        int owner_id = 0;
 
        if (rs.next()) {
            owner_id = rs.getInt("client_id");
        }
        
        return owner_id;

    }
    
    public static int getAdminId(Connection conn, String name) throws SQLException{
    	String sql = "Select a.admin_id from admin a " //
                + " where a.user = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        ResultSet rs = pstm.executeQuery();
        
        int owner_id = 0;
 
        if (rs.next()) {
            owner_id = rs.getInt("admin_id");
        }
        
        return owner_id;

    }
    
    public static int getBan(Connection conn, String name, String password) throws SQLException{
    	String sql = "Select a.ban from user a " //
                + " where a.user = ? and a.password = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setString(2, password);
        ResultSet rs = pstm.executeQuery();
        
        int owner_id = 0;
 
        if (rs.next()) {
            owner_id = rs.getInt("ban");
        }
        
        return owner_id;

    }
    
    public static int getUserFromClient(Connection conn, int id) throws SQLException{
    	String sql = "Select a.user_id from user a " //
                + " where a.client_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        int owner_id = 0;
 
        if (rs.next()) {
            owner_id = rs.getInt("user_id");
        }
        
        return owner_id;

    }
    
    
    public static void addPhoto(Connection conn, int propertyId, String link) throws SQLException{
    	String sql1 = "INSERT INTO `mydb`.`photo` (`link`, `property_id`) VALUES (?, ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, link);
    	pstm.setInt(2, propertyId);
    	
    	pstm.execute();
    }
    
    public static void addPropertyReview(Connection conn, int clientId, int propertyId, String comment, int score) throws SQLException{
    	String sql1 = "INSERT INTO `mydb`.`review_property` (`client_id`, `property_id`, `comment`, `score`) VALUES (?, ?, ?, ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setInt(1, clientId);
    	pstm.setInt(2, propertyId);
    	pstm.setString(3, comment);
    	pstm.setInt(4, score);
    	
    	pstm.execute();
    }
    
    public static void addClientReview(Connection conn, int clientId, int ownerId, String comment, int score) throws SQLException{
    	String sql1 = "INSERT INTO `mydb`.`review_client` (`client_id`, `owner_id`, `comment`, `score`) VALUES (?, ?, ?, ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setInt(1, clientId);
    	pstm.setInt(2, ownerId);
    	pstm.setString(3, comment);
    	pstm.setInt(4, score);
    	
    	pstm.execute();
    }
    
    public static void updateProperty(Connection conn, int id, String name, String address, float price) throws SQLException {
    	String sql = "UPDATE `mydb`.`virtual_property` SET `name` = ?, `address` = ?, `price` = ? WHERE (`virtual_property_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setString(1, name);
    	pstmt.setString(2, address);
    	pstmt.setFloat(3, price);
    	pstmt.setInt(4, id);
    	
    	pstmt.execute();
    }
    
    public static void updateAvailability(Connection conn, int id, int available) throws SQLException {
    	String sql = "UPDATE `mydb`.`virtual_property` SET `available` = ? WHERE (`virtual_property_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, available);
    	pstmt.setInt(2, id);
    	
    	pstmt.execute();
    }
    
    public static void checkIn(Connection conn, int id) throws SQLException {
    	String sql = "UPDATE `mydb`.`booking` SET `arrived` = 1 WHERE (`booking_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, id);
    	
    	pstmt.execute();
    }
    
    public static void reviewClient(Connection conn, int id) throws SQLException {
    	String sql = "UPDATE `mydb`.`booking` SET `reviewed_client` = 1 WHERE (`booking_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, id);
    	
    	pstmt.execute();
    }
    
    public static void reviewOwner(Connection conn, int id) throws SQLException {
    	String sql = "UPDATE `mydb`.`booking` SET `reviewed_owner` = 1 WHERE (`booking_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, id);
    	
    	pstmt.execute();
    }
    
    public static void checkOut(Connection conn, int id) throws SQLException {
    	String sql = "UPDATE `mydb`.`booking` SET `fulfilled` = 1 WHERE (`booking_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, id);
    	
    	pstmt.execute();
    }
    
    public static void banUser(Connection conn, int id, int status) throws SQLException {
    	String sql = "UPDATE `mydb`.`user` SET `ban` = ? WHERE (`user_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, status);
    	pstmt.setInt(2, id);
    	
    	pstmt.execute();
    }
    
    public static void updateBooking(Connection conn, int id, int valid) throws SQLException {
    	String sql = "UPDATE `mydb`.`booking` SET `valid` = ? WHERE (`booking_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, valid);
    	pstmt.setInt(2, id);
    	
    	pstmt.execute();
    }
    
    public static void updateOwner(Connection conn, int id, int approval) throws SQLException {
    	String sql = "UPDATE `mydb`.`owner` SET `approval` = ? WHERE (`owner_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, approval);
    	pstmt.setInt(2, id);

    	pstmt.execute();
    }
    
    public static void addProperty(Connection conn, String name, String address, int ownerId, float price, int stars, float lat, float lng) throws SQLException{
    	String sql1 = "INSERT INTO `mydb`.`virtual_property` (`name`, `address`, `owner_id`, `price`, `valid`, `stars`, `lat`, `lng`) VALUES (?, ?, ?, ?, 0, ?, ?, ?)";
        
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, name);
    	pstm.setString(2, address);
    	pstm.setInt(3, ownerId);
    	pstm.setFloat(4,  price);
    	pstm.setInt(5,  stars);
    	pstm.setFloat(6, lat);
    	pstm.setFloat(7,  lng);
    	
    	pstm.execute();
    }
    
    public static void validateProperty(Connection conn, int propertyId) throws SQLException {
    	String sql = "UPDATE `mydb`.`virtual_property` SET `valid` = 1 WHERE (`virtual_property_id` = ?)";
    	PreparedStatement pstmt = conn.prepareStatement(sql);
    	pstmt.setInt(1, propertyId);

    	pstmt.execute();
    }
    
    public static void addBooking(Connection conn, Date checkIn, Date checkOut, float price, int client_id, int property_id, int valid) throws SQLException{
    	String sql1 = "INSERT INTO `mydb`.`booking` (`check_in`, `check_out`, `price`, `client_id`, `property_id`, `valid`) VALUES (?, ?, ?, ?, ?, ?)";

    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	long diffInMillies = Math.abs(checkOut.getTime() - checkIn.getTime());
    	long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    	pstm.setDate(1, new java.sql.Date (checkIn.getTime()));
    	pstm.setDate(2, new java.sql.Date (checkOut.getTime()));
    	pstm.setFloat(3, price * diff);
    	pstm.setInt(4,  client_id);
    	pstm.setInt(5,  property_id);
    	pstm.setInt(6,  valid);
    	
    	pstm.execute();
    }
    
    public static int getBookingId(Connection conn, Date checkIn, Date checkOut, float price, int client_id, int property_id) throws SQLException{
    	String sql = "Select booking_id from booking " //
                + " where check_in = ? and check_out = ? and price = ? and client_id = ? and property_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        long diffInMillies = Math.abs(checkOut.getTime() - checkIn.getTime());
    	long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        pstm.setDate(1, new java.sql.Date (checkIn.getTime()));
    	pstm.setDate(2, new java.sql.Date (checkOut.getTime()));
    	pstm.setFloat(3, price * diff);
    	pstm.setInt(4,  client_id);
    	pstm.setInt(5,  property_id);
        ResultSet rs = pstm.executeQuery();
        
        int booking_id = 0;
 
        if (rs.next()) {
           booking_id = rs.getInt("booking_id");
        }
        
        return booking_id;

    }
    
    public static int getNotificationId(Connection conn, String message) throws SQLException{
    	String sql = "Select notification_id from notification " //
                + " where message = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
    	pstm.setString(1,  message);
        ResultSet rs = pstm.executeQuery();
        
        int notification_id = 0;
 
        if (rs.next()) {
           notification_id = rs.getInt("notification_id");
        }
        
        return notification_id;

    }
    
    public static String getPropertyFromBooking(Connection conn, int id) throws SQLException{
    	String sql = "Select a.property_id from booking a " //
                + " where a.booking_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        int property_id = 0;
 
        if (rs.next()) {
            property_id = rs.getInt("property_id");
        }
        
        String sql2 = "Select a.name from virtual_property a " //
                + " where a.virtual_property_id = ?";
 
        PreparedStatement pstm2 = conn.prepareStatement(sql2);
        pstm2.setInt(1, property_id);
        ResultSet rs2 = pstm2.executeQuery();
        
        String name = null;
        
        if (rs2.next()) {
        	name = rs2.getString("name");
        }
        
        return name;

    }
    
    public static String getClientFromBooking(Connection conn, int id) throws SQLException{
    	String sql = "Select a.client_id from booking a " //
                + " where a.booking_id = ?";
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, id);
        ResultSet rs = pstm.executeQuery();
        
        int property_id = 0;
 
        if (rs.next()) {
            property_id = rs.getInt("client_id");
        }
        
        String sql2 = "Select a.user from client a " //
                + " where a.client_id = ?";
 
        PreparedStatement pstm2 = conn.prepareStatement(sql2);
        pstm2.setInt(1, property_id);
        ResultSet rs2 = pstm2.executeQuery();
        
        String name = null;
        
        if (rs2.next()) {
        	name = rs2.getString("user");
        }
        
        return name;

    }
    
    public static void deletePhoto(Connection conn, int photoId) throws SQLException {
    	String sql = "DELETE FROM `mydb`.`photo` WHERE (`photo_id` = ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	pstm.setInt(1, photoId);
    	pstm.execute();
    }
    
    public static void deleteReviewProperty(Connection conn, int id) throws SQLException {
    	String sql = "DELETE FROM `mydb`.`review_property` WHERE (`review_property_id` = ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	pstm.setInt(1, id);
    	pstm.execute();
    }
    
    public static void deleteProperty(Connection conn, int propertyId) throws SQLException {
    	String sql2 = "DELETE FROM `mydb`.`photo` WHERE (`property_id` = ?)";
    	PreparedStatement pstm2 = conn.prepareStatement(sql2);
    	pstm2.setInt(1, propertyId);
    	pstm2.execute();
    	
    	String sql = "DELETE FROM `mydb`.`virtual_property` WHERE (`virtual_property_id` = ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	pstm.setInt(1, propertyId);
    	pstm.execute();
    }
    
    public static void deleteNotification(Connection conn, int notificationId) throws SQLException {
    	String sql = "DELETE FROM `mydb`.`notification` WHERE (`notification_id` = ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	pstm.setInt(1, notificationId);
    	pstm.execute();
    }
    
    public static void deleteBooking(Connection conn, int id) throws SQLException {
    	String sql = "DELETE FROM `mydb`.`booking` WHERE (`booking_id` = ?)";
    	PreparedStatement pstm = conn.prepareStatement(sql);
    	pstm.setInt(1, id);
    	pstm.execute();
    }
    
    public static void addNotification(Connection conn, String message, int ownerId, int valid) throws SQLException {
    	String sql1 = "INSERT INTO `mydb`.`notification` (`message`, `owner_id`, `valid`) VALUES (?, ?, ?)";
        
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, message);
    	pstm.setInt(2, ownerId);
    	pstm.setInt(3, valid);
    	
    	pstm.execute();
    }
    
    public static void addNotificationClient(Connection conn, String message, int ownerId) throws SQLException {
    	String sql1 = "INSERT INTO `mydb`.`notification` (`message`, `client_id`, `valid`) VALUES (?, ?, ?)";
        
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, message);
    	pstm.setInt(2, ownerId);
    	pstm.setInt(3, 0);
    	
    	pstm.execute();
    }
    
    public static void addNotificationAdmin(Connection conn, String message, int adminId) throws SQLException {
    	String sql1 = "INSERT INTO `mydb`.`notification` (`message`, `admin_id`, `valid`) VALUES (?, ?, ?)";
        
    	PreparedStatement pstm = conn.prepareStatement(sql1);
    	pstm.setString(1, message);
    	pstm.setInt(2, adminId);
    	pstm.setInt(3, 0);
    	
    	pstm.execute();
    }

}