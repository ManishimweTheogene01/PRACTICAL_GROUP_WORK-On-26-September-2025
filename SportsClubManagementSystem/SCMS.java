/**1. Manishimwe Theogene 223007186
 * 2. Mbabazi Henriette 223005178
 * 3. Uwinema Costasie 223009865**/
package com.form;

import java.awt.BorderLayout;

import javax.swing.*;

public class SCMS extends JFrame{

	JTabbedPane tabs=new JTabbedPane();

	public SCMS(String role, int userid){
		setTitle("Sports Club Management System");
		setSize(90,60);
		setLayout(new BorderLayout());
		if(role.equalsIgnoreCase("admin")){
			tabs.add("users",new UserPanel());
			tabs.add("Members",new MembersPanel());
			tabs.add("Events",new EventsPanel());
			tabs.add("Programs",new ProgramsPanel());
			tabs.add("Payments",new PaymentsPanel());
			tabs.add("Facilities",new FacilitiesPanel());
		}
		else if(role.equalsIgnoreCase("coach")){
			tabs.add("Members",new MembersPanel());
			tabs.add("Events",new EventsPanel());
			tabs.add("Programs",new ProgramsPanel());
			tabs.add("Facilities",new FacilitiesPanel());
		}
		else if(role.equalsIgnoreCase("member")){
			tabs.add("Events",new EventsPanel());
			tabs.add("Programs",new ProgramsPanel());
			tabs.add("Payments",new PaymentsPanel());
			tabs.add("Facilities",new FacilitiesPanel());
		}
		add(tabs,BorderLayout.CENTER);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
