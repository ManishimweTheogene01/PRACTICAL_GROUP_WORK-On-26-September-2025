/**1. Manishimwe Theogene 223007186
 * 2. Mbabazi Henriette 223005178
 * 3. Uwinema Costasie 223009865**/
package com.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB{
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/SCMS","root","");
	}
}
