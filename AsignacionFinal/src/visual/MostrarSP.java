package visual;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import Logico.StudentSchedule;

public class MostrarSP extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;
	private JTextField studentIdField;
	private JTextField periodIdField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MostrarSP frame = new MostrarSP();
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
	public MostrarSP() {
		setTitle("Store Procedure");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel inputPanel = new JPanel();
		contentPane.add(inputPanel, BorderLayout.NORTH);
		inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblStudentId = new JLabel("Student ID:");
		inputPanel.add(lblStudentId);

		studentIdField = new JTextField();
		inputPanel.add(studentIdField);
		studentIdField.setColumns(10);

		JLabel lblPeriodId = new JLabel("Period ID:");
		inputPanel.add(lblPeriodId);

		periodIdField = new JTextField();
		inputPanel.add(periodIdField);
		periodIdField.setColumns(10);

		JButton btnShowSchedule = new JButton("Show Schedule");
		inputPanel.add(btnShowSchedule);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

		btnShowSchedule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSchedule();
			}
		});
	}

	private void showSchedule() {
		try {
			String studentId = studentIdField.getText();
			String periodId = periodIdField.getText();
			ResultSet rs = StudentSchedule.getSchedule(studentId, periodId);
			textArea.setText("");
			while (rs.next()) {
				textArea.append(rs.getString("HoraDia") + "\t" +
								rs.getString("Lunes") + "\t" +
								rs.getString("Martes") + "\t" +
								rs.getString("Miercoles") + "\t" +
								rs.getString("Jueves") + "\t" +
								rs.getString("Viernes") + "\t" +
								rs.getString("Sabado") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
