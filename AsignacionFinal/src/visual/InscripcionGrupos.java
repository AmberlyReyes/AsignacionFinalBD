package visual;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import Conexion.Conexion;

public class InscripcionGrupos extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable estudiantesTable;
    private JTable gruposDisponiblesTable;
    private JTable gruposInscritosTable;
    private DefaultTableModel estudiantesModel;
    private DefaultTableModel gruposDisponiblesModel;
    private DefaultTableModel gruposInscritosModel;
    private JTextArea horarioTextArea;

    public InscripcionGrupos() {
        setTitle("Inscripción de Grupos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(1, 1));

        String[] estudiantesColumnNames = {"ID Estudiante", "Nombre"};
        estudiantesModel = new DefaultTableModel(estudiantesColumnNames, 0);
        estudiantesTable = new JTable(estudiantesModel);
        cargarEstudiantes();

        panelSuperior.add(new JScrollPane(estudiantesTable));
        add(panelSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridLayout(1, 2));

        String[] gruposColumnNames = {"ID Grupo", "Nombre", "Cupo", "Horario"};
        gruposDisponiblesModel = new DefaultTableModel(gruposColumnNames, 0);
        gruposDisponiblesTable = new JTable(gruposDisponiblesModel);
        cargarGruposDisponibles();

        gruposInscritosModel = new DefaultTableModel(gruposColumnNames, 0);
        gruposInscritosTable = new JTable(gruposInscritosModel);

        panelCentral.add(new JScrollPane(gruposDisponiblesTable));
        panelCentral.add(new JScrollPane(gruposInscritosTable));

        add(panelCentral, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BorderLayout());

        JButton inscribirButton = new JButton("Inscribir");
        inscribirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inscribirGrupo();
            }
        });

        JButton eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarGrupo();
            }
        });

        panelInferior.add(inscribirButton, BorderLayout.WEST);
        panelInferior.add(eliminarButton, BorderLayout.EAST);

        horarioTextArea = new JTextArea(5, 40);
        panelInferior.add(new JScrollPane(horarioTextArea), BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarEstudiantes() {
        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT IdEstudiante, Nombre FROM Estudiante")) {
            while (resultSet.next()) {
                int idEstudiante = resultSet.getInt("IdEstudiante");
                String nombre = resultSet.getString("Nombre");
                estudiantesModel.addRow(new Object[]{idEstudiante, nombre});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarGruposDisponibles() {
        try (Connection connection = Conexion.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT G.IdGrupo, A.Nombre, G.Cupo, G.Horario\r\n" + 
                     "FROM Grupo G , Asignatura A\r\n" + 
                     "WHERE G.IdAsignatura=A.IdAsignatura")) {
            while (resultSet.next()) {
                int idGrupo = resultSet.getInt("IdGrupo");
                String nombre = resultSet.getString("Nombre");
                int cupo = resultSet.getInt("Cupo");
                String horario = resultSet.getString("Horario");
                gruposDisponiblesModel.addRow(new Object[]{idGrupo, nombre, cupo, horario});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void inscribirGrupo() {
        int selectedEstudianteRow = estudiantesTable.getSelectedRow();
        int selectedGrupoRow = gruposDisponiblesTable.getSelectedRow();

        if (selectedEstudianteRow != -1 && selectedGrupoRow != -1) {
            int idEstudiante = (int) estudiantesTable.getValueAt(selectedEstudianteRow, 0);
            int idGrupo = (int) gruposDisponiblesTable.getValueAt(selectedGrupoRow, 0);
            String nombreGrupo = (String) gruposDisponiblesTable.getValueAt(selectedGrupoRow, 1);
            int cupoGrupo = (int) gruposDisponiblesTable.getValueAt(selectedGrupoRow, 2);
            String horarioGrupo = (String) gruposDisponiblesTable.getValueAt(selectedGrupoRow, 3);

            try (Connection connection = Conexion.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO Inscripcion (IdEstudiante, IdGrupo) VALUES (?, ?)")) {
                statement.setInt(1, idEstudiante);
                statement.setInt(2, idGrupo);
                statement.executeUpdate();

                gruposInscritosModel.addRow(new Object[]{idGrupo, nombreGrupo, cupoGrupo, horarioGrupo});
                gruposDisponiblesModel.removeRow(selectedGrupoRow);

                mostrarHorario(idGrupo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void eliminarGrupo() {
        int selectedEstudianteRow = estudiantesTable.getSelectedRow();
        int selectedGrupoRow = gruposInscritosTable.getSelectedRow();

        if (selectedEstudianteRow != -1 && selectedGrupoRow != -1) {
            int idEstudiante = (int) estudiantesTable.getValueAt(selectedEstudianteRow, 0);
            int idGrupo = (int) gruposInscritosTable.getValueAt(selectedGrupoRow, 0);
            String nombreGrupo = (String) gruposInscritosTable.getValueAt(selectedGrupoRow, 1);
            int cupoGrupo = (int) gruposInscritosTable.getValueAt(selectedGrupoRow, 2);
            String horarioGrupo = (String) gruposInscritosTable.getValueAt(selectedGrupoRow, 3);

            try (Connection connection = Conexion.getConnection();
                 PreparedStatement statement = connection.prepareStatement("DELETE FROM Inscripcion WHERE IdGrupo = ? AND IdEstudiante = ?")) {
                statement.setInt(1, idGrupo);
                statement.setInt(2, idEstudiante);
                statement.executeUpdate();

                gruposDisponiblesModel.addRow(new Object[]{idGrupo, nombreGrupo, cupoGrupo, horarioGrupo});
                gruposInscritosModel.removeRow(selectedGrupoRow);

                horarioTextArea.setText("");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void mostrarHorario(int idGrupo) {
        try (Connection connection = Conexion.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT Horario FROM Horario WHERE IdGrupo = ?")) {
            statement.setInt(1, idGrupo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                horarioTextArea.setText(resultSet.getString("Horario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InscripcionGrupos().setVisible(true);
            }
        });
    }
}