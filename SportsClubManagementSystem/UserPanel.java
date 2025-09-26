/**1. Manishimwe Theogene 223007186
 * 2. Mbabazi Henriette 223005178
 * 3. Uwinema Costasie 223009865**/
package com.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.util.DB;

public class UserPanel extends JPanel implements ActionListener{

	//Components
	JTextField idTxt=new JTextField();
	JTextField nameTxt=new JTextField();
	JPasswordField passTxtTxt=new JPasswordField();
	JTextField roleTxt=new JTextField();
	JTextField emailTxt=new JTextField();
	JTextField createTxt=new JTextField();



	//Button
	JButton addBtn=new JButton("ADD USER");
	JButton loadBtn=new JButton("LOAD USERS");
	JButton updateBtn=new JButton("UPDATE USER");
	JButton deleteBtn=new JButton("REMOVE USER");
	JButton selectBtn=new JButton("SELECT USER ID BEFORE UPDATE OR DELETE USER");

	//Table
	JTable table;
	DefaultTableModel model;

	//Constructor
	public UserPanel(){
		setLayout(null);
		String[] labels={"ID","Username","PasswordHash","Role","Email","Date and Time"};
		String [] cols={"UserID","Username","PasswordHash","Role","Email","CreatedAt"};
		model=new DefaultTableModel(labels,0);
		table=new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(20,250,800,300);
		int y=-10;
		y+=30;
		addField("UserID",idTxt,y);
		y+=30;
		addField("Username",nameTxt,y);
		y+=30;
		addField("Password",passTxtTxt,y);
		y+=30;
		addField("Role",roleTxt,y);
		y+=30;
		addField("Email",emailTxt,y);

		addButtosn();
		add(sp);

	}



	private void addButtosn() {
		addBtn.setBounds(300,20,150,30);
		updateBtn.setBounds(300,60,150,30);
		deleteBtn.setBounds(300,100,150,30);
		loadBtn.setBounds(300,140,150,30);
		selectBtn.setBounds(20,180,430,30);

		add(addBtn);
		add(updateBtn);
		add(deleteBtn);
		add(loadBtn);
		add(selectBtn);

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		loadBtn.addActionListener(this);
		selectBtn.addActionListener(this);

		/** JLabel l=new JLabel(lbl);
		l.setBounds(20,y,80,25);
		txt.setBounds(100,y,150,25);
		add(l);
		add(txt); **/

	}
	private void addField(String lbl,JComponent txt, int y){
		JLabel l=new JLabel(lbl);
		l.setBounds(20,y,80,25);
		txt.setBounds(100,y,150,25);
		add(l);
		add(txt);

	}



	public void actionPerformed(ActionEvent e) {
		try (Connection con = DB.getConnection()) {
			String sql = null;

			if (e.getSource() == addBtn) {
				sql = "INSERT INTO users(Username,PasswordHash,Role,Email) VALUES(?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, new String(passTxtTxt.getPassword()));
				ps.setString(3, roleTxt.getText());
				ps.setString(4, emailTxt.getText());
				ps.executeUpdate();
				System.out.println("User inserted successfully!");
			}

			else if (e.getSource() == updateBtn) {
				sql = "UPDATE users SET Username=?, Role=?, Email=? WHERE UserID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, roleTxt.getText());
				ps.setString(3, emailTxt.getText());
				ps.setInt(4, Integer.parseInt(idTxt.getText())); // use ID field
				ps.executeUpdate();
				System.out.println("User updated successfully!");
			}

			else if (e.getSource() == deleteBtn) {
				sql = "DELETE FROM users WHERE UserID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText())); // only ID needed
				ps.executeUpdate();
				System.out.println("User deleted successfully!");
			}

			else if (e.getSource() == loadBtn) {
				model.setRowCount(0);
				sql = "SELECT * FROM users";
				ResultSet rs = con.createStatement().executeQuery(sql);
				while (rs.next()) {
					model.addRow(new Object[]{
							rs.getInt("UserID"),
							rs.getString("Username"),
							rs.getString("PasswordHash"),
							rs.getString("Role"),
							rs.getString("Email"),
							rs.getTimestamp("CreatedAt")
					});
				}
			}

			else if (e.getSource() == selectBtn) {
				model.setRowCount(0);
				sql = "SELECT * FROM users WHERE UserID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText())); // fix placeholder
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					model.addRow(new Object[]{
							rs.getInt("UserID"),
							rs.getString("Username"),
							rs.getString("PasswordHash"),
							rs.getString("Role"),
							rs.getString("Email"),
							rs.getTimestamp("CreatedAt")
					});
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}}
