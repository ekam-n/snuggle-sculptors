package main;

import javax.swing.JFrame;

/*
 * One of the main class which creates and uses the JPanel object to run and display the whole application
 */
public class BuildBearApp extends JFrame {
	
	
	private static final long serialVersionUID = 1L;

	public BuildBearApp(String title) {
		super(title);
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BuildBearPanel bpnl = new BuildBearPanel(this);
		this.add(bpnl); 
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main (String[] args){
		new BuildBearApp("BuildBearApp");
		
	}
	
}
