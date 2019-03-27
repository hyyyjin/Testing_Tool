package com.mir.GUI;

import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import com.mir.webserverCheck.DB.DbConnection;
//import com.mir.webserverCheck.Server.SimpleHttpServer;
import com.mysql.jdbc.Connection;
import javax.swing.JRadioButtonMenuItem;

public class ResultFrame {
	public static int port = 80;

	public int init = 0;

	public JFrame frmMirlabWebServerAuto;
	public final JSeparator separator_1 = new JSeparator();
	public static JTextArea LOG_TEXT;
	public static JTextArea TOPO_TEXT;
	public static JTextArea TOPOLOGY_TEXT;

	public static JComboBox comboBoxTestType = new JComboBox();
	public static JList metricList;
	public JScrollPane scrollPane_2;
	public JTabbedPane tabbedPane;
	public static JTable RESULT_TABLE;

	// public static ArrayList<Student> studentList = new ArrayList<Student>();

	public static void main(String[] args) {

		new ResultFrame();
	}

	public ResultFrame() {
		initialize();
	}

	public void initialize() {
		/*
		 * BUILD FRAME
		 */
		frmMirlabWebServerAuto = new JFrame();
		frmMirlabWebServerAuto.getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 12));
		frmMirlabWebServerAuto.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		frmMirlabWebServerAuto.setTitle("Hanyang MIR Lab.");
		frmMirlabWebServerAuto.setResizable(false);
		frmMirlabWebServerAuto.setBounds(new Rectangle(50, 50, 50, 50));
		frmMirlabWebServerAuto.setBounds(100, 100, 1080, 739);
		frmMirlabWebServerAuto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMirlabWebServerAuto.getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(41, 54, 992, 132);
		frmMirlabWebServerAuto.getContentPane().add(scrollPane);

		metricList = new JList();
		metricList.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		metricList.setBorder(BorderFactory.createTitledBorder("Procedure"));
		metricList.setAutoscrolls(false);
		scrollPane.setViewportView(metricList);
		metricList.setModel(new AbstractListModel() {
			String[] values = new String[] { "1.Check Web Server(IP/Port) if it is Open", "2.Check 'GET index.html'",
					"3.Check 'GET pic.jpg'", "4.Check 'GET music.mp3'", "5.'PUT Score' to Student" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		metricList.setSelectedIndex(0);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1074, 31);
		frmMirlabWebServerAuto.getContentPane().add(menuBar);
		menuBar.setToolTipText("");

		JMenu fileMB = new JMenu("File");
		fileMB.setIcon(
				new ImageIcon(ResultFrame.class.getResource("/com/alee/examples/groups/filechooser/icons/folder.png")));
		menuBar.add(fileMB);

		JMenuItem newMI = new JMenuItem("New");
		newMI.setIcon(
				new ImageIcon(ResultFrame.class.getResource("/com/alee/extended/filechooser/icons/file_icon.png")));
		fileMB.add(newMI);

		JMenuItem openMI = new JMenuItem("Open Students JSON File");
		openMI.setIcon(new ImageIcon(ResultFrame.class.getResource("/com/alee/extended/ninepatch/icons/open.png")));
		fileMB.add(openMI);

		JMenuItem saveMI = new JMenuItem("Save Students' Records");
		saveMI.setIcon(new ImageIcon(ResultFrame.class.getResource("/com/alee/extended/ninepatch/icons/saveas.png")));
		fileMB.add(saveMI);

		JMenu windowMB = new JMenu("Configuration");
		windowMB.setIcon(
				new ImageIcon(ResultFrame.class.getResource("/com/alee/examples/groups/window/icons/window.png")));
		menuBar.add(windowMB);

		JRadioButtonMenuItem confiig_webserver = new JRadioButtonMenuItem("Web Server");
		windowMB.add(confiig_webserver);

		JRadioButtonMenuItem confiig_webclient = new JRadioButtonMenuItem("Web Client");
		windowMB.add(confiig_webclient);

		JRadioButtonMenuItem confiig_udpchatting = new JRadioButtonMenuItem("UDP Chatting");
		windowMB.add(confiig_udpchatting);

		JRadioButtonMenuItem confiig_GoBackN = new JRadioButtonMenuItem("Go Back N");
		windowMB.add(confiig_GoBackN);

		JRadioButtonMenuItem confiig_selective = new JRadioButtonMenuItem("Selective Repeat");
		windowMB.add(confiig_selective);

		JRadioButtonMenuItem confiig_openfowAgent = new JRadioButtonMenuItem("Openflow Agent");
		windowMB.add(confiig_openfowAgent);

		JRadioButtonMenuItem confiig_openflowServer = new JRadioButtonMenuItem("Openflow Server");
		windowMB.add(confiig_openflowServer);

		JRadioButtonMenuItem confiig_restapi = new JRadioButtonMenuItem("rest api");
		windowMB.add(confiig_openflowServer);

		JRadioButtonMenuItem confiig_qna = new JRadioButtonMenuItem("Q&A");
		windowMB.add(confiig_qna);

		JMenu helpMB = new JMenu("Help");
		helpMB.setIcon(new ImageIcon(
				ResultFrame.class.getResource("/com/alee/examples/groups/transition/icons/toolbar/2.png")));
		menuBar.add(helpMB);

		JMenuItem aboutMI = new JMenuItem("About");
		aboutMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Hanyang MIR LAB.\n", "MIR LAB.", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		aboutMI.setIcon(new ImageIcon(ResultFrame.class.getResource("/com/alee/extended/ninepatch/icons/icon.png")));
		helpMB.add(aboutMI);

		JSeparator separator = new JSeparator();
		separator.setBounds(0, 31, 1074, 13);
		frmMirlabWebServerAuto.getContentPane().add(separator);

		comboBoxTestType.setModel(new DefaultComboBoxModel(new String[] { "Throughput", "Latency" }));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		tabbedPane.setBounds(41, 219, 992, 458);
		frmMirlabWebServerAuto.getContentPane().add(tabbedPane);
		JScrollPane scrollPane_3 = new JScrollPane();
		tabbedPane.addTab("Result",
				new ImageIcon(
						ResultFrame.class.getResource("/com/alee/managers/style/icons/component/tableHeader.png")),
				scrollPane_3, null);

		confiig_webserver.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_webserver.isSelected()) {

					confiig_webclient.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE.setModel(new DefaultTableModel(
							new Object[][] { { "", "", "", "", "", null, null, null, null, null, null, null, null },
									{ "", "", "", "", null, null, null, null, null, null, null, null },
									{ "", null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null }, },
							new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "MultiThread",
									"Status Code(200)", "Status Code(404)", "Status Code(400)", "Content-Length",
									"Content Type(html)", "Content Type(Image)", "access time", "score" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {

							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from webserver2018");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(), 4);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6), rs.getRow(), 5);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7), rs.getRow(), 6);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8), rs.getRow(), 7);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(), 8);

									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10), rs.getRow(), 9);

									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(11), rs.getRow(), 10);

									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(12), rs.getRow(), 11);

									//

									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(13), rs.getRow(), 12);

									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(14), rs.getRow(), 13);

								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					}).start();

				}

			}
		});

		confiig_webclient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_webclient.isSelected()) {
					confiig_webserver.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },
									new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1",
											"Mission2", "Mission3", "Mission4", "Time", "score" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {
							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from webclient2018");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(),
									// 4);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(), 5);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6), rs.getRow(), 6);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7), rs.getRow(), 7);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8), rs.getRow(), 8);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(), 9);
									//
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10), rs.getRow(), 10);
//									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(11), rs.getRow(), 11);
									//
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}).start();

				}

			}
		});

		confiig_udpchatting.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_udpchatting.isSelected()) {
					confiig_webclient.setSelected(false);
					confiig_webserver.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },

									new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1",
											"Mission2", "Mission3", "Mission4", "Mission5", "time", "score" }));

					// new String[] { "Student Name", "Student NO", "IP", "Port", "Socket",
					// "Mission1",
					// "Mission2", "Mission3", "Mission4", "Mission5","Time","result" }));

					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {
							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from udpchatting2018");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(),
									// 4);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(), 5);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6), rs.getRow(), 6);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7), rs.getRow(), 7);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8), rs.getRow(), 8);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(), 9);
									//
									// time score
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10), rs.getRow(), 10);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(11), rs.getRow(), 11);
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}).start();

				}

			}
		});

		confiig_GoBackN.addActionListener(new ActionListener() {

			// time stamp

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_GoBackN.isSelected()) {
					confiig_webclient.setSelected(false);
					confiig_webserver.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },
									// new String[] { "Student Name", "Student NO", "IP", "Port", "Socket",
									// "Mission1",
									// "Mission2", "Mission3", "Mission4", "", "", "" }));
									new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1",
											"Mission2", "Mission3", "Mission4", "Time", "score", "" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {
							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from gbnchatting2018");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(),
									// 4);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(), 5);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6), rs.getRow(), 6);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7), rs.getRow(), 7);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8), rs.getRow(), 8);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(),
									// 9);

									// time score
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(), 9);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10), rs.getRow(), 10);

									/////// * ÀÙ¿¡´Â µðºñ Äõ¸® , µÚ´Â ºä Ä®·³
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}).start();

				}

			}
		});

		confiig_selective.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_selective.isSelected()) {
					confiig_webclient.setSelected(false);
					confiig_webserver.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },
									new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1",
											"Mission2", "Mission3", "Mission4", "Time", "Score", "" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {
							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from srchatting2018");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(),
									// 4);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(), 5);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6), rs.getRow(), 6);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7), rs.getRow(), 7);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8), rs.getRow(), 8);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(),
									// 9);

									// time score
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(), 9);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10), rs.getRow(), 10);

								}

							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}).start();

				}

			}
		});

		confiig_openfowAgent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_openfowAgent.isSelected()) {
					confiig_webclient.setSelected(false);
					confiig_webserver.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },
									new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1",
											"Mission2", "Mission3", "Mission4", "Mission5", "Mission6", "Mission7",
											"Mission8", "Time", "score" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

				}

			}
		});

		confiig_openflowServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_openflowServer.isSelected()) {
					confiig_webclient.setSelected(false);
					confiig_webserver.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_restapi.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE.setModel(new DefaultTableModel(
							new Object[][] { { "", "", "", "", "", null, null, null, null, null, null, null, null },
									{ "", "", "", "", null, null, null, null, null, null, null, null },
									{ "", null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null },
									{ null, null, null, null, null, null, null, null, null, null, null, null }, },
							new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1", "Mission2",
									"Mission3", "Mission4", "Mission5", "Mission6", "Mission7", "Mission8" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

				}

			}
		});

		confiig_restapi.addActionListener(new ActionListener() {

			// time stamp

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_restapi.isSelected()) {
					confiig_webclient.setSelected(false);
					confiig_webserver.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_qna.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },
									// new String[] { "Student Name", "Student NO", "IP", "Port", "Socket",
									// "Mission1",
									// "Mission2", "Mission3", "Mission4", "", "", "" }));
									new String[] { "Student Name", "Student NO", "IP", "Port", "Socket", "Mission1",
											"Mission2", "Mission3", "Mission4", "Time", "score", "" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {
							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from gbnchatting2018");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(),
									// 4);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5), rs.getRow(), 5);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6), rs.getRow(), 6);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7), rs.getRow(), 7);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8), rs.getRow(), 8);
									// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(),
									// 9);

									// time score
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9), rs.getRow(), 9);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10), rs.getRow(), 10);

									/////// * ÀÙ¿¡´Â µðºñ Äõ¸® , µÚ´Â ºä Ä®·³
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}).start();

				}

			}
		});

		confiig_qna.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				if (confiig_qna.isSelected()) {
					confiig_webserver.setSelected(false);
					confiig_webclient.setSelected(false);
					confiig_udpchatting.setSelected(false);
					confiig_GoBackN.setSelected(false);
					confiig_selective.setSelected(false);
					confiig_openfowAgent.setSelected(false);
					confiig_openflowServer.setSelected(false);
					confiig_restapi.setSelected(false);

					RESULT_TABLE = new JTable();
					RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
					RESULT_TABLE.setRowHeight(26);
					RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
					RESULT_TABLE
							.setModel(new DefaultTableModel(
									new Object[][] {
											{ "", "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", "", "", "", null, null, null, null, null, null, null, null },
											{ "", null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null, null },
											{ null, null, null, null, null, null, null, null, null, null, null,
													null }, },
									new String[] { "Student number", "password", "content", "TIME" }));
					scrollPane_3.setViewportView(RESULT_TABLE);

					// SHOW THE RESULT
					new Thread(new Runnable() {
						DbConnection db;
						ResultSet rs;

						public void run() {
							try {
								db = new DbConnection();
								rs = db.stmt.executeQuery("select * from qna");
								Thread.sleep(0);
								while (rs.next()) {
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(1), rs.getRow(), 0);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2), rs.getRow(), 1);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3), rs.getRow(), 2);
									ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4), rs.getRow(), 3);

									//
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								try {
									rs.close();
									db.stmt.close();
									db.con.close();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						}
					}).start();

				}

			}
		});

		LOG_TEXT = new JTextArea();
		LOG_TEXT.setMargin(new Insets(20, 10, 20, 10));
		LOG_TEXT.setLineWrap(true);
		TOPO_TEXT = new JTextArea();
		TOPO_TEXT.setMargin(new Insets(20, 10, 20, 10));
		TOPO_TEXT.setLineWrap(true);

		tabbedPane.addTab("Log",
				new ImageIcon(
						ResultFrame.class.getResource("/com/alee/examples/groups/menubar/icons/menubar/edit.png")),
				new JScrollPane(LOG_TEXT), null);

		TOPOLOGY_TEXT = new JTextArea();
		TOPOLOGY_TEXT.setMargin(new Insets(20, 10, 20, 10));
		TOPOLOGY_TEXT.setLineWrap(true);

		tabbedPane.addTab("Undefined",
				new ImageIcon(ResultFrame.class.getResource("/com/alee/examples/content/icons/presentation.png")),
				new JScrollPane(TOPO_TEXT), null);
		// new JScrollPane(new TopoTest(12, 1)
		separator_1.setBounds(0, 687, 1074, 13);
		frmMirlabWebServerAuto.getContentPane().add(separator_1);

		JLabel lblNewLabel = new JLabel("Items");
		lblNewLabel.setIcon(new ImageIcon(
				ResultFrame.class.getResource("/com/alee/examples/groups/menubar/icons/menubar/radio1.png")));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel.setBounds(41, 41, 93, 15);
		frmMirlabWebServerAuto.getContentPane().add(lblNewLabel);
		// RESULT_TABLE = new JTable();
		// RESULT_TABLE.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		// RESULT_TABLE.setRowHeight(26);
		// RESULT_TABLE.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		// RESULT_TABLE.setModel(new DefaultTableModel(
		// new Object[][] { { "", "", "", "", "", null, null, null, null, null,
		// null, null, null },
		// { "", "", "", "", null, null, null, null, null, null, null, null },
		// { "", null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null },
		// { null, null, null, null, null, null, null, null, null, null, null,
		// null }, },
		// new String[] { "Student Name", "Student NO", "IP", "Port", "Socket",
		// "MultiThread", "Status Code(200)",
		// "Status Code(404)", "Status Code(400)", "Content-Length", "Content
		// Type(html)",
		// "Content Type(Image)" }));
		// scrollPane_3.setViewportView(RESULT_TABLE);
		//
		// LOG_TEXT = new JTextArea();
		// LOG_TEXT.setMargin(new Insets(20, 10, 20, 10));
		// LOG_TEXT.setLineWrap(true);
		// TOPO_TEXT = new JTextArea();
		// TOPO_TEXT.setMargin(new Insets(20, 10, 20, 10));
		// TOPO_TEXT.setLineWrap(true);
		//
		// tabbedPane.addTab("Log",
		// new ImageIcon(
		// ResultFrame.class.getResource("/com/alee/examples/groups/menubar/icons/menubar/edit.png")),
		// new JScrollPane(LOG_TEXT), null);
		//
		// TOPOLOGY_TEXT = new JTextArea();
		// TOPOLOGY_TEXT.setMargin(new Insets(20, 10, 20, 10));
		// TOPOLOGY_TEXT.setLineWrap(true);
		//
		// tabbedPane.addTab("Undefined",
		// new
		// ImageIcon(ResultFrame.class.getResource("/com/alee/examples/content/icons/presentation.png")),
		// new JScrollPane(TOPO_TEXT), null);
		// // new JScrollPane(new TopoTest(12, 1)
		// separator_1.setBounds(0, 687, 1074, 13);
		// frmMirlabWebServerAuto.getContentPane().add(separator_1);
		//
		// JLabel lblNewLabel = new JLabel("Items");
		// lblNewLabel.setIcon(new ImageIcon(
		// ResultFrame.class.getResource("/com/alee/examples/groups/menubar/icons/menubar/radio1.png")));
		// lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 12));
		// lblNewLabel.setBounds(41, 41, 93, 15);
		// frmMirlabWebServerAuto.getContentPane().add(lblNewLabel);

		// SHOW THE RESULT
		// new Thread(new Runnable() {
		// DbConnection db;
		// ResultSet rs;
		//
		// public void run() {
		// while (true) {
		// try {
		// db = new DbConnection();
		// rs = db.stmt.executeQuery("select * from webserver2017");
		// Thread.sleep(5000);
		// while (rs.next()) {
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(2),
		// rs.getRow(), 0);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getInt(1),
		// rs.getRow(), 1);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(3),
		// rs.getRow(), 2);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(4),
		// rs.getRow(), 3);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(5),
		// rs.getRow(), 4);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(6),
		// rs.getRow(), 5);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(7),
		// rs.getRow(), 6);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(8),
		// rs.getRow(), 7);
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(9),
		// rs.getRow(), 8);
		//
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(10),
		// rs.getRow(), 9);
		//
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(11),
		// rs.getRow(), 10);
		//
		// ResultFrame.RESULT_TABLE.getModel().setValueAt(rs.getString(12),
		// rs.getRow(), 11);
		//
		// }
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } finally {
		// try {
		// rs.close();
		// db.stmt.close();
		// db.con.close();
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }
		// }
		// }).start();

		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
