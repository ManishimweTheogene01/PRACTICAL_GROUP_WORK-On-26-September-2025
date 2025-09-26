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

public class MembersPanel extends JPanel implements ActionListener{

	//Components
	JTextField idTxt=new JTextField();
	JTextField nameTxt=new JTextField();
	JTextField dscTxt=new JTextField();
	JTextField ageTxt=new JTextField();
	JTextField psTxt=new JTextField();
	JTextField createTxt=new JTextField();
	JTextField fuseridTxt=new JTextField();



	//Button
	JButton addBtn=new JButton("ADD MEMBER");
	JButton loadBtn=new JButton("LOAD MEMBERS");
	JButton updateBtn=new JButton("UPDATE MEMBER");
	JButton deleteBtn=new JButton("REMOVE MEMBER");
	JButton selectBtn=new JButton("SELECT MEMBER ID BEFORE UPDATE OR DELETE MEMBER ID");

	//Table
	JTable table;
	DefaultTableModel model;

	//Constructor
	public MembersPanel(){
		setLayout(null);
		String[] labels={"MemberID","Name","Description","Age","Position","CreatedAt","UserID"};
		String [] cols={"MemberID","Name","Description","Age","Position","CreatedAt","UserID"};
		model=new DefaultTableModel(labels,0);
		table=new JTable(model);
		JScrollPane sp=new JScrollPane(table);
		sp.setBounds(20,250,800,300);
		int y=-10;
		y+=30;
		addField("MemberID",idTxt,y);
		y+=30;
		addField("Name",nameTxt,y);
		y+=30;
		addField("Description",dscTxt,y);
		y+=30;
		addField("Age",ageTxt,y);
		y+=30;
		addField("Position",psTxt,y);
		y+=30;
		addField("UserID",fuseridTxt,y);

		addButtosn();
		add(sp);

	}



	private void addButtosn() {
		addBtn.setBounds(300,20,150,30);
		updateBtn.setBounds(300,60,150,30);
		deleteBtn.setBounds(300,100,150,30);
		loadBtn.setBounds(300,140,150,30);
		selectBtn.setBounds(20,200,430,30);

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
				sql = "INSERT Members(Name, Description, Age, Position, UserID) VALUES(?,?,?,?,?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, dscTxt.getText());
				ps.setString(3, ageTxt.getText());
				ps.setString(4, psTxt.getText());
				ps.setString(5, fuseridTxt.getText());
				ps.executeUpdate();
				System.out.println("User inserted successfully!");
			}

			else if (e.getSource() == updateBtn) {
				sql = "UPDATE users SET Name=?, Description=?, Age=?,Position WHERE MemberID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, dscTxt.getText());
				ps.setString(3, ageTxt.getText());
				ps.setString(4, psTxt.getText());
				ps.setString(5, fuseridTxt.getText());
				ps.setInt(6, Integer.parseInt(idTxt.getText())); // use ID field
				ps.executeUpdate();
				System.out.println("User updated successfully!");
			}

			else if (e.getSource() == deleteBtn) {
				sql = "DELETE FROM Members WHERE MemberID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText())); // only ID needed
				ps.executeUpdate();
				System.out.println("User deleted successfully!");
			}

			else if (e.getSource() == loadBtn) {
				model.setRowCount(0);
				sql = "SELECT * FROM Members";
				ResultSet rs = con.createStatement().executeQuery(sql);
				while (rs.next()) {
					model.addRow(new Object[]{
							rs.getInt("MemberID"),
							rs.getString("Name"),
							rs.getString("Description"),
							rs.getString("Age"),
							rs.getString("Position"),
							rs.getTimestamp("CreatedAt"),
							rs.getInt("UserID"),
					});
				}
			}

			else if (e.getSource() == selectBtn) {
				model.setRowCount(0);
				sql = "SELECT * FROM Members WHERE MemberID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText())); // fix placeholder
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					model.addRow(new Object[]{
							rs.getInt("MemberID"),
							rs.getString("Name"),
							rs.getString("Description"),
							rs.getString("Age"),
							rs.getString("Position"),
							rs.getTimestamp("CreatedAt"),
							rs.getInt("UserID"),
					});
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}}
