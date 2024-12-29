package com.pz.ffc.newffc;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;


public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea messageTextArea;
	private JTextArea errwarnTextArea;
	private JTabbedPane tabbedPane;
	
	//private static MainFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static class SingletonHolder {
		static final MainFrame instance = new MainFrame();
	}
	
	public static MainFrame create() {
		
		return SingletonHolder.instance;
	}

	/**
	 * Create the frame.
	 */
	private MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		//createTabs();
		createMessageTextArea();
		//createErrorWarnTextArea();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setBounds(200, 150, 500, 500);
	}
	
	private void createMessageTextArea() {
		messageTextArea = new JTextArea(39, 75);
		messageTextArea.setVisible(true);
		messageTextArea.setEditable(false);
		messageTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		messageTextArea.setFont(new Font("Arial, Helvetica, sans-serif", 0, 11));
		final JScrollPane scrollPane = new JScrollPane(messageTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setSize(800, 600);
	}
	
	private void createErrorWarnTextArea() {
		errwarnTextArea = new JTextArea(39, 75);
		errwarnTextArea.setVisible(true);
		errwarnTextArea.setEditable(false);
		errwarnTextArea.setBorder(new EmptyBorder(5, 5, 5, 5));
		errwarnTextArea.setFont(new Font("Arial, Helvetica, sans-serif", 0, 11));
		JScrollPane scrollPane = new JScrollPane(messageTextArea, 22, 32);
		scrollPane.setViewportView(errwarnTextArea);
		//contentPane.add(textArea);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		//tabbedPane.setTabComponentAt(1, scrollPane);
		pack();
		setSize(800, 600);
		setLocationByPlatform(true);
		final DefaultCaret caret = (DefaultCaret) errwarnTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}
	
	private void createTabs() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setFocusable(false);
		tabbedPane.addTab("Messages", new JPanel());
		tabbedPane.addTab("Errors and Warnings", new JPanel());
		tabbedPane.setTabComponentAt(0, getLabel("Messages", "/messages.jpg"));
		tabbedPane.setTabComponentAt(1, getLabel("Errors and Warnings", "/errorsandwarnings.jpg"));
		contentPane.add(tabbedPane);
		//this.add(tabbedPane);
		//pack();
		//setLocationByPlatform(true);
	}
	
	public void addMessage(final String message) {
		final LocalDateTime ldt = LocalDateTime.now();
		messageTextArea.append(ldt.toString());
		messageTextArea.append(" : ");
		messageTextArea.append(message);
		messageTextArea.append("\n");
	}
	
	protected JLabel getLabel(String title, String icon) {
        JLabel label = new JLabel(title);
        label.setIcon(new ImageIcon(icon));
        return label;
    }
	
}
