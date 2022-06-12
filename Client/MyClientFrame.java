package com.callbackcops;

import java.util.*;
import java.util.concurrent.TimeUnit;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import javax.swing.SwingConstants;

import java.awt.Color;

import java.rmi.*;

public class MyClientFrame extends JFrame {
	JLabel feedback, updnote; //Labels for showing some messages

	JLabel idlabel, namelabel, deptlabel, gendlabel, agelabel, adylabel;//labels for textfields

	JTextField idfield, namefield, deptfield, gendfield, agefield, adyfield;//textfields to take input

	SDB sd;//SDB interface type variable for remote method invocation

	JButton insertb, deleteb, updateb, retrieveb, retrieveAllB, deleteAllB;//buttons for database operations

	boolean datafound = false;//a flag variable to know, data found or not to update

	MyClientFrame() throws Exception{//constructor of our GUI class

		try{
			sd=(SDB) Naming.lookup("rmi://localhost:9000/callbackcats");//binding for remote method invocation

			ImageIcon logo = new ImageIcon("pokemon.png");//the logo for our app

			Border redborder = BorderFactory.createLineBorder(Color.red, 2);//a red border to show around messaga(s)
			//Border blackborder = BorderFactory.createLineBorder(Color.black, 1);
			Border greenborder = BorderFactory.createLineBorder(Color.green, 2);//a green border to show around messaga(s)

			idlabel = new JLabel("ID:");
			idlabel.setBounds(200, 50-40, 120,20);
			namelabel = new JLabel("Name:");
			namelabel.setBounds(200, 100-40, 120,20);
			deptlabel = new JLabel("Department:\n");
			deptlabel.setBounds(200, 150-40, 120,20);
			gendlabel = new JLabel("Gender:\n");
			gendlabel.setBounds(200, 200-40, 120,20);
			agelabel = new JLabel("Age:\n");
			agelabel.setBounds(200, 250-40, 120,20);
			adylabel = new JLabel("Admission Year:\n");
			adylabel.setBounds(200, 300-40, 120,20);
			
			idfield = new JTextField();
			idfield.setBounds(330, 50-40, 150,20);
			namefield = new JTextField();
			namefield.setBounds(330, 100-40, 150,20);
			deptfield = new JTextField();
			deptfield.setBounds(330, 150-40, 150,20);
			gendfield = new JTextField();
			gendfield.setBounds(330, 200-40, 150,20);
			agefield = new JTextField();
			agefield.setBounds(330, 250-40, 150,20);
			adyfield = new JTextField();
			adyfield.setBounds(330, 300-40, 150,20);

			String[] header = {"ID", "Name", "Department", "Gender", "Age", "Admission Year"};//header for table
			String[][] rec = new String[10000][6];
			for(int i = 0; i<10000;i++){
				for(int j=0; j<6; j++){
					rec[i][j]="";
				}
			}

			DefaultTableModel tableModel = new DefaultTableModel(rec, header);
			JTable table = new JTable(tableModel);

			feedback = new JLabel("Welcome to SSDB Students Database Client", SwingConstants.CENTER);
			feedback.setBounds(115, 400, 460, 40);
			
			updnote = new JLabel("Updatation Procedure: At First Enter Original Data. Then Click \"Update\" Button.", SwingConstants.CENTER);
			updnote.setBounds(50, 455, 590, 40);
			updnote.setBorder(redborder);

        	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);
			table.getColumnModel().getColumn(5).setPreferredWidth(200);

			insertb = new JButton();
			insertb.setBounds(115,325-20, 100, 20);
			insertb.setText("Insert");
			insertb.addActionListener(e ->{
				try{
					if(idfield.getText().equals("") || namefield.getText().equals("") || deptfield.getText().equals("") || gendfield.getText().equals("") || agefield.getText().equals("") || adyfield.getText().equals("")){
						feedback.setText("Enter Data in All Fields for Insertion");
						feedback.setBorder(redborder);
					} else{
						feedback.setText("");
						feedback.setBorder(null);
						String sqlq = "INSERT INTO student values ("+Integer.valueOf(idfield.getText())+", '"+namefield.getText()+"', '"+deptfield.getText()+"', '"+gendfield.getText()+"', "+Integer.valueOf(agefield.getText())+", "+Integer.valueOf(adyfield.getText())+");";
						System.out.println(sqlq);
						int numrows = sd.getNumUpdRows(sqlq);
						
						System.out.println("Number of Inserted Rows: "+numrows);
						if(numrows==1){
							JOptionPane success = new JOptionPane("1 row was inserted.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
							JDialog dlg = success.createDialog("Success");
							new Thread(new Runnable(){
								public void run(){
									try{
										Thread.sleep(1250);
										dlg.dispose();
									}catch(Exception exc){
										System.out.println("Found Exception "+exc);
									}
								}
							}).start();
							dlg.setVisible(true);
						}else if(numrows>1){
							JOptionPane success = new JOptionPane(numrows+" rows were inserted.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
							JDialog dlg = success.createDialog("Success");
							new Thread(new Runnable(){
								public void run(){
									try{
										Thread.sleep(1250);
										dlg.dispose();
									}catch(Exception exc){
										System.out.println("Found Exception "+exc);
									}
								}
							}).start();
							dlg.setVisible(true);
						}else{
							JOptionPane success = new JOptionPane("No row was inserted. Please, enter correct data.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
							JDialog dlg = success.createDialog("Failure");
							
							dlg.setVisible(true);
						}
					}
				}catch(Exception err){
					System.out.println("Found Exception "+err);
				}
			});

			deleteb = new JButton();
			deleteb.setBounds(235,325-20, 100, 20);
			deleteb.setText("Delete");
			deleteb.addActionListener(e ->{
				try{
					if(idfield.getText().equals("") && namefield.getText().equals("") && deptfield.getText().equals("") && gendfield.getText().equals("") && agefield.getText().equals("") && adyfield.getText().equals("")){
						feedback.setText("Enter Data in At Least One Field for Deletion");
						feedback.setBorder(redborder);
					} else{
						feedback.setText("");
						feedback.setBorder(null);

						int selectedOption = JOptionPane.showConfirmDialog(null,
							"Are you sure?",
							"Choose",
							JOptionPane.YES_NO_OPTION);
						if(selectedOption == JOptionPane.YES_OPTION){
							String sqlq;
							sqlq = "DELETE FROM student where ";
							if(!idfield.getText().equals("")) sqlq+="id="+idfield.getText()+" ";
							if(!namefield.getText().equals("")){
								if(!idfield.getText().equals("")) sqlq+="AND ";
								sqlq+="name='"+ namefield.getText()+"' ";
							}
							if(!deptfield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("")) sqlq+="AND ";
								sqlq+="department='"+ deptfield.getText()+"' ";
							}
							if(!gendfield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("")) sqlq+="AND ";
								sqlq+="gender='"+ gendfield.getText()+"' ";
							}
							if(!agefield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("")) sqlq+="AND ";
								sqlq+="age="+ agefield.getText()+" ";
							}
							if(!adyfield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("") || !adyfield.getText().equals("")) sqlq+="AND ";
								sqlq+="ad_year="+ adyfield.getText();
							}
							sqlq+=";";
							System.out.println(sqlq);
							int numrows =sd.getNumUpdRows(sqlq);
						
							System.out.println("Number of Deleted Rows: "+numrows);
							if(numrows==1){
								JOptionPane success = new JOptionPane("1 row was deleted.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
								JDialog dlg = success.createDialog("Success");
								new Thread(new Runnable(){
									public void run(){
										try{
											Thread.sleep(1250);
											dlg.dispose();
										}catch(Exception exc){
											System.out.println("Found Exception "+exc);
										}
									}
								}).start();
								dlg.setVisible(true);
							}else if(numrows>1){
								JOptionPane success = new JOptionPane(numrows+" rows were deleted.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
								JDialog dlg = success.createDialog("Success");
								new Thread(new Runnable(){
									public void run(){
										try{
											Thread.sleep(1250);
											dlg.dispose();
										}catch(Exception exc){
											System.out.println("Found Exception "+exc);
										}
									}
								}).start();
								dlg.setVisible(true);
							}else{
								JOptionPane success = new JOptionPane("No row was deleted. Please, enter correct data.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
								JDialog dlg = success.createDialog("Failure");
							
								dlg.setVisible(true);
							}
						}
					}	
				}catch(Exception err){
					System.out.println("Found Exception "+err);
				}
			});

			updateb = new JButton();
			updateb.setBounds(355,325-20, 100, 20);
			updateb.setText("Update");
			String[] whrdata = {"", "", "", "", "", ""};
			updateb.addActionListener(e -> {
				String sqlq;
				try{
					if(idfield.getText().equals("") && namefield.getText().equals("") && deptfield.getText().equals("") && gendfield.getText().equals("") && agefield.getText().equals("") && adyfield.getText().equals("")){
						feedback.setText("Enter Data in At Least One Field for Updatation");
						feedback.setBorder(redborder);
					} else{
						feedback.setText("");
						feedback.setBorder(null);
						if(datafound==false){
							sqlq = "SELECT * FROM student where ";
							if(!idfield.getText().equals("")){
								sqlq+="id="+idfield.getText()+" ";
								whrdata[0] = idfield.getText();
							}
							if(!namefield.getText().equals("")){
								if(!idfield.getText().equals("")) sqlq+="AND ";
								sqlq+="name='"+ namefield.getText()+"' ";
								whrdata[1] = namefield.getText();
							}
							if(!deptfield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("")) sqlq+="AND ";
								sqlq+="department='"+ deptfield.getText()+"' ";
								whrdata[2] = deptfield.getText();
							}
							if(!gendfield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("")) sqlq+="AND ";
								sqlq+="gender='"+ gendfield.getText()+"' ";
								whrdata[3] = gendfield.getText();
							}
							if(!agefield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("")) sqlq+="AND ";
								sqlq+="age="+ agefield.getText()+" ";
								whrdata[4] = agefield.getText();
							}
							if(!adyfield.getText().equals("")){
								if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("") || !adyfield.getText().equals("")) sqlq+="AND ";
								sqlq+="ad_year="+ adyfield.getText();
								whrdata[5] = adyfield.getText();
							}
							sqlq+=";";

							System.out.println(sqlq);
							List<Student> list=sd.getResults(sqlq);
							System.out.println(list);
							if(list!=null){// || list!=[]){
								datafound=true;
								updnote.setText("** Now, Enter New Data and Click \"Update\" Button. **");
								updnote.setBorder(greenborder);
								JOptionPane success = new JOptionPane("Please, enter new data and click \"Update\" button.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
								JDialog dlg = success.createDialog("Original Data Matched");
								new Thread(new Runnable(){
									public void run(){
										try{
											Thread.sleep(1500);
											dlg.dispose();
										}catch(Exception exc){
											System.out.println("Found Exception "+exc);
										}
									}
								}).start();
								dlg.setVisible(true);
							}else{
								updnote.setText("Original Data Mismatch");
								new Thread(new Runnable(){
									public void run(){
										try{
											Thread.sleep(1500);
											updnote.setText("Updatation Procedure: At First Enter Original Data. Then Click \"Update\" Button.");
										}catch(Exception exc){
											System.out.println("Found Exception "+exc);
										}
									}
								}).start();
							}
						} else {

							//while(true){
								sqlq = "UPDATE student set ";
								if(!idfield.getText().equals("")) sqlq+="id="+idfield.getText();
								if(!namefield.getText().equals("")){
									if(!idfield.getText().equals("")) sqlq+=", ";
									sqlq+="name='"+ namefield.getText()+"'";
								}
								if(!deptfield.getText().equals("")){
									if(!idfield.getText().equals("") || !namefield.getText().equals("")) sqlq+=", ";
									sqlq+="department='"+ deptfield.getText()+"'";
								}
								if(!gendfield.getText().equals("")){
									if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("")) sqlq+=", ";
									sqlq+="gender='"+ gendfield.getText()+"'";
								}
								if(!agefield.getText().equals("")){
									if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("")) sqlq+=", ";
									sqlq+="age="+ agefield.getText();
								}
								if(!adyfield.getText().equals("")){
									if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("") || !adyfield.getText().equals("")) sqlq+=", ";
									sqlq+="ad_year="+ adyfield.getText();
								}
								sqlq+=" WHERE ";

								if(!whrdata[0].equals("")) sqlq+="id="+whrdata[0]+" ";
							if(!whrdata[1].equals("")){
								if(!whrdata[0].equals("")) sqlq+="AND ";
								sqlq+="name='"+ whrdata[1]+"' ";
							}
							if(!whrdata[2].equals("")){
								if(!whrdata[0].equals("") || !whrdata[1].equals("")) sqlq+="AND ";
								sqlq+="department='"+ whrdata[2]+"' ";
							}
							if(!whrdata[3].equals("")){
								if(!whrdata[0].equals("") || !whrdata[1].equals("") || !whrdata[2].equals("")) sqlq+="AND ";
								sqlq+="gender='"+ whrdata[3]+"' ";
							}
							if(!whrdata[4].equals("")){
								if(!whrdata[0].equals("") || !whrdata[1].equals("") || !whrdata[2].equals("") || !whrdata[3].equals("")) sqlq+="AND ";
								sqlq+="age="+ whrdata[4]+" ";
							}
							if(!whrdata[5].equals("")){
								if(!whrdata[0].equals("") || !whrdata[1].equals("") || !whrdata[2].equals("") || !whrdata[3].equals("") || !whrdata[5].equals("")) sqlq+="AND ";
								sqlq+="ad_year="+ whrdata[5];
							}
							sqlq+=";";

								System.out.println(sqlq);
								int numrows=sd.getNumUpdRows(sqlq);

								System.out.println("Number of Updated Rows: "+numrows);
								if(numrows==1){
									datafound=false;
									JOptionPane success = new JOptionPane("1 row was updated.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
									JDialog dlg = success.createDialog("Success");
									new Thread(new Runnable(){
										public void run(){
											try{
												Thread.sleep(1250);
												dlg.dispose();
											}catch(Exception exc){
												System.out.println("Found Exception "+exc);
											}
										}
									}).start();
									dlg.setVisible(true);
									for(String s:whrdata){
										s="";
									}
								}else if(numrows>1){
									datafound=false;
									JOptionPane success = new JOptionPane(numrows+" rows were updated.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
									JDialog dlg = success.createDialog("Success");
									new Thread(new Runnable(){
										public void run(){
											try{
												Thread.sleep(1250);
												dlg.dispose();
											}catch(Exception exc){
												System.out.println("Found Exception "+exc);
											}
										}
									}).start();
									dlg.setVisible(true);
									for(String s:whrdata){
										s="";
									}
								}else{
									JOptionPane success = new JOptionPane("No row was updated. Please, enter correct data.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
									JDialog dlg = success.createDialog("Failure");
								
									dlg.setVisible(true);
								}
							updnote.setText("Updatation Procedure: At First Enter Original Data. Then Click \"Update\" Button.");
							updnote.setBorder(redborder);
						}
					}
				}catch(Exception err){
					System.out.println("Found Exception "+err);
				}
			});

			retrieveb = new JButton();
			retrieveb.setBounds(475,325-20, 100, 20);
			retrieveb.setText("Retrieve");
			retrieveb.addActionListener(e ->{

				feedback.setText("");
				feedback.setBorder(null);
				String sqlq;

				try{
					if(idfield.getText().equals("") && namefield.getText().equals("") && deptfield.getText().equals("") && gendfield.getText().equals("") && agefield.getText().equals("") && adyfield.getText().equals("")){
						sqlq = "SELECT * FROM student;";
					} else{
						sqlq = "SELECT * FROM student where ";
						if(!idfield.getText().equals("")) sqlq+="id="+idfield.getText()+" ";
						if(!namefield.getText().equals("")){
							if(!idfield.getText().equals("")) sqlq+="AND ";
							sqlq+="name='"+ namefield.getText()+"' ";
						}
						if(!deptfield.getText().equals("")){
							if(!idfield.getText().equals("") || !namefield.getText().equals("")) sqlq+="AND ";
							sqlq+="department='"+ deptfield.getText()+"' ";
						}
						if(!gendfield.getText().equals("")){
							if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("")) sqlq+="AND ";
							sqlq+="gender='"+ gendfield.getText()+"' ";
						}
						if(!agefield.getText().equals("")){
							if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("")) sqlq+="AND ";
							sqlq+="age="+ agefield.getText()+" ";
						}
						if(!adyfield.getText().equals("")){
							if(!idfield.getText().equals("") || !namefield.getText().equals("") || !deptfield.getText().equals("") || !gendfield.getText().equals("") || !adyfield.getText().equals("")) sqlq+="AND ";
							sqlq+="ad_year="+ adyfield.getText();
						}
						sqlq+=";";
					}

					System.out.println(sqlq);

					List<Student> list=sd.getResults(sqlq);

					if(list==null){
						System.out.println("Remote Method Returned null.\n");
						JOptionPane success = new JOptionPane("No row was retrieved. Please, enter correct data.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
						JDialog dlg = success.createDialog("Failure");
							
						dlg.setVisible(true);
					}
					else{
						int i=0;
						
						tableModel.setRowCount(0);
						for(Student s:list){
							tableModel.setRowCount(i+1);
						table.setValueAt(String.valueOf(s.getid()), i, 0);
						table.setValueAt(s.getname(), i, 1);
						table.setValueAt(s.getdepartment(), i, 2);
						table.setValueAt(s.getgender(), i, 3);
						table.setValueAt(String.valueOf(s.getage()), i, 4);
							table.setValueAt(String.valueOf(s.getad_year()), i, 5);
							i++;
						}
					}
				}catch(Exception err){
					System.out.println("Found Exception "+err);
				}
			});
			
			retrieveAllB = new JButton();
			retrieveAllB.setBounds(185, 335, 150, 20);
			retrieveAllB.setText("Retrieve All");
			retrieveAllB.addActionListener(e ->{

				feedback.setText("");
				feedback.setBorder(null);
				
				String sqlq;
				
				try{
					sqlq = "SELECT * FROM student;";

					System.out.println(sqlq);
					
					List<Student> list=sd.getResults(sqlq);

					if(list==null){
						System.out.println("Remote Method Returned null.\n");
						JOptionPane success = new JOptionPane("No row was found in database. Please, insert data.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
						JDialog dlg = success.createDialog("Failure");
							
						dlg.setVisible(true);
					}
					else{
						int i=0;
						
						tableModel.setRowCount(0);
						for(Student s:list){
							tableModel.setRowCount(i+1);
							table.setValueAt(String.valueOf(s.getid()), i, 0);
							table.setValueAt(s.getname(), i, 1);
							table.setValueAt(s.getdepartment(), i, 2);
							table.setValueAt(s.getgender(), i, 3);
							table.setValueAt(String.valueOf(s.getage()), i, 4);
							table.setValueAt(String.valueOf(s.getad_year()), i, 5);
							i++;
						}
					}
				}catch(Exception err){
					System.out.println("Found Exception "+err);
				}
			});

			deleteAllB = new JButton();
			//deleteAllB.setBounds(265, 365, 150, 20);
			deleteAllB.setBounds(355, 335, 150, 20);
			deleteAllB.setText("Delete All");
			deleteAllB.addActionListener(e ->{

				feedback.setText("");
				feedback.setBorder(null);

				int selectedOption = JOptionPane.showConfirmDialog(null,
					"Are you sure you want to parmanently delete all data in the database?",
					"Choose",
					JOptionPane.YES_NO_OPTION);
				if(selectedOption == JOptionPane.YES_OPTION){
					String sqlq;
					try{
						sqlq = "DELETE FROM student;";

						System.out.println(sqlq);

						int numrows =sd.getNumUpdRows(sqlq);

						System.out.println("Number of Deleted Rows: "+numrows);
						if(numrows==1){
							JOptionPane success = new JOptionPane("1 row was deleted.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
							JDialog dlg = success.createDialog("Success");
							new Thread(new Runnable(){
								public void run(){
									try{
										Thread.sleep(1250);
										dlg.dispose();
									}catch(Exception exc){
										System.out.println("Found Exception "+exc);
									}
								}
							}).start();
							dlg.setVisible(true);
						}else if(numrows>1){
							JOptionPane success = new JOptionPane(numrows+" rows were deleted.", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, logo);
							JDialog dlg = success.createDialog("Success");
							new Thread(new Runnable(){
								public void run(){
									try{
										Thread.sleep(1250);
										dlg.dispose();
									}catch(Exception exc){
										System.out.println("Found Exception "+exc);
									}
								}
							}).start();
							dlg.setVisible(true);
						}else{
							JOptionPane success = new JOptionPane("No row was deleted. Please review the database by using \"Retrieve All\" button.", JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
							JDialog dlg = success.createDialog("Failure");
							
							dlg.setVisible(true);
						}
					
						tableModel.setRowCount(0);
					}catch(Exception err){
						System.out.println("Found Exception "+err);
					}
				}
			});

			JPanel container = new JPanel();
			JPanel panelOne = new JPanel();
			JPanel panelTwo = new JPanel();

			panelOne.setLayout(null);

			panelOne.add(idlabel);
			panelOne.add(idfield);
			panelOne.add(namelabel);
			panelOne.add(namefield);
			panelOne.add(deptlabel);
			panelOne.add(deptfield);
			panelOne.add(gendlabel);
			panelOne.add(gendfield);
			panelOne.add(agelabel);
			panelOne.add(agefield);
			panelOne.add(adylabel);
			panelOne.add(adyfield);

			panelOne.add(insertb);
			panelOne.add(deleteb);
			panelOne.add(updateb);
			panelOne.add(retrieveb);

			panelOne.add(retrieveAllB);
			panelOne.add(deleteAllB);

			panelOne.add(feedback);
			panelOne.add(updnote);

			panelTwo.add(new JScrollPane(table));

			container.setLayout(new GridLayout(1, 2));
			container.add(panelOne);
			container.add(panelTwo);

			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("SSDB");
			this.setSize(1300, 560);
			this.setLocationRelativeTo(null);
			this.setLayout(new BorderLayout());

			this.setIconImage(logo.getImage());
			this.add(container);
			this.setVisible(true);

		}catch(Exception e){
			System.out.println("Found Exception "+ e);
		}
	}
}