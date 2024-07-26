package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JMenuBar;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class PrincipalVisual extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalVisual frame = new PrincipalVisual();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalVisual() {
		setBackground(Color.WHITE);
		setFont(new Font("Segoe UI Historic", Font.BOLD, 12));
		setTitle("Welcome !");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 549);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setForeground(Color.WHITE);
		menuBar.setFont(new Font("Segoe UI", Font.BOLD, 12));
		setJMenuBar(menuBar);
		
		JMenu mnAsignar = new JMenu("Inscribir Grupo");
		mnAsignar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		mnAsignar.setForeground(new Color(0, 153, 255));
		menuBar.add(mnAsignar);
		
		JMenuItem mntInscribe = new JMenuItem("Inscribir Estudiante");
		mntInscribe.setForeground(new Color(0, 51, 0));
		mnAsignar.add(mntInscribe);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelFondo = new JPanel();
		contentPane.add(panelFondo, BorderLayout.CENTER);
		panelFondo.setLayout(new BorderLayout(0, 0));
	}

}
