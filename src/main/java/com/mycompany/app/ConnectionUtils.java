/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author son
 */
public class ConnectionUtils {
     public Connection getMyConnection() throws SQLException,
          ClassNotFoundException {
      // Sử dụng Oracle.
      // Bạn có thể thay thế bởi Database nào đó.
      return MySQLConnUtils.getMySQLConnection();
  }
 
  //
  // Test Connection ...
  //
//  public static void main(String[] args) throws SQLException,
//          ClassNotFoundException {
// 
//      System.out.println("Get connection ... ");
// 
//      // Lấy ra đối tượng Connection kết nối vào database.
//      Connection conn = ConnectionUtils.getMyConnection();
// 
//      System.out.println("Get connection " + conn);
// 
//      System.out.println("Done!");
//  }

  
}