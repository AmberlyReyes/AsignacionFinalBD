package visual;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import Conexion.Conexion;

import java.util.ArrayList;

import Logico.*;

public class InscripcionGruposGUI extends JDialog {
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JComboBox<Estudiante> cbxEstudiantes;
    private JComboBox<Grupo> cbxGruposDisponibles;
    private JList<Grupo> listGruposInscritos;
    private DefaultListModel<Grupo> listModelGruposInscritos;
    private JTextArea textAreaHorario;
    private Conexion conexion;

    public static void main(String[] args) {
        try {
            InscripcionGruposGUI dialog = new InscripcionGruposGUI();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InscripcionGruposGUI() {
        setTitle("Inscripción de Grupos");
        setBounds(100, 100, 600, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);

        // Conectar a la base de datos
        conexion = new Conexion();
        
        JPanel panelSeleccion = new JPanel();
        panelSeleccion.setBorder(new TitledBorder(null, "Selección", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelSeleccion.setBounds(10, 11, 564, 90);
        contentPanel.add(panelSeleccion);
        panelSeleccion.setLayout(new GridLayout(2, 2, 10, 10));

        cbxEstudiantes = new JComboBox<>();
        cargarEstudiantes();
        cbxEstudiantes.addActionListener(new EstudianteSelectionListener());

        cbxGruposDisponibles = new JComboBox<>();
        cargarGruposDisponibles();
        cbxGruposDisponibles.addActionListener(new GrupoSelectionListener());

        panelSeleccion.add(new JLabel("Seleccionar Estudiante:"));
        panelSeleccion.add(cbxEstudiantes);
        panelSeleccion.add(new JLabel("Seleccionar Grupo Disponible:"));
        panelSeleccion.add(cbxGruposDisponibles);

        JPanel panelGruposInscritos = new JPanel();
        panelGruposInscritos.setBorder(new TitledBorder(null, "Grupos Inscritos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panelGruposInscritos.setBounds(10, 112, 564, 150);
        contentPanel.add(panelGruposInscritos);
        panelGruposInscritos.setLayout(new BorderLayout(0, 0));

        listModelGruposInscritos = new DefaultListModel<>();
        listGruposInscritos = new JList<>(listModelGruposInscritos);
        listGruposInscritos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listGruposInscritos.addListSelectionListener(e -> mostrarHorarioGrupoSeleccionado());
        JScrollPane scrollPaneGruposInscritos = new JScrollPane(listGruposInscritos);
        panelGruposInscritos.add(scrollPaneGruposInscritos, BorderLayout.CENTER);

        textAreaHorario = new JTextArea();
        textAreaHorario.setEditable(false);
        JScrollPane scrollPaneHorario = new JScrollPane(textAreaHorario);
        scrollPaneHorario.setBorder(new TitledBorder(null, "Horario del Grupo Seleccionado", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        scrollPaneHorario.setBounds(10, 273, 564, 77);
        contentPanel.add(scrollPaneHorario);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(panelBotones, BorderLayout.SOUTH);

        JButton btnAgregarGrupo = new JButton("Agregar Grupo");
        btnAgregarGrupo.addActionListener(new AgregarGrupoListener());
        panelBotones.add(btnAgregarGrupo);

        JButton btnEliminarGrupo = new JButton("Eliminar Grupo");
        btnEliminarGrupo.addActionListener(new EliminarGrupoListener());
        panelBotones.add(btnEliminarGrupo);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        panelBotones.add(btnCancelar);
    }

    private void cargarEstudiantes() {
        ArrayList<Estudiante> estudiantes = conexion.obtenerEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            cbxEstudiantes.addItem(estudiante);
        }
    }

    private void cargarGruposDisponibles() {
        ArrayList<Grupo> grupos = conexion.obtenerGruposDisponibles();
        for (Grupo grupo : grupos) {
            cbxGruposDisponibles.addItem(grupo);
        }
    }

    /*private void mostrarHorarioGrupoSeleccionado() {
        Grupo grupoSeleccionado = listGruposInscritos.getSelectedValue();
        if (grupoSeleccionado != null) {
            Horario horarios = conexion.obtenerHorarioGrupo(grupoSeleccionado);
            StringBuilder horario = new StringBuilder();
            for (Horario horarioGrupo : horarios) {
                horario.append("Día: ").append(horarioGrupo.getNumDia())
                        .append(", Inicio: ").append(horarioGrupo.getFechaHoraInicio())
                        .append(", Fin: ").append(horarioGrupo.getFechaHoraFin()).append("\n");
            }
            textAreaHorario.setText(horario.toString());
        } else {
            textAreaHorario.setText("");
        }
    }*/
    
    private void mostrarHorarioGrupoSeleccionado() {
        Grupo grupoSeleccionado = listGruposInscritos.getSelectedValue();
        if (grupoSeleccionado != null) {
            Horario horario = conexion.obtenerHorarioGrupo(grupoSeleccionado);
            StringBuilder horarioTexto = new StringBuilder();
            horarioTexto.append("Día: ").append(horario.getNumDia())
                        .append(", Inicio: ").append(horario.getFechaHoraInicio())
                        .append(", Fin: ").append(horario.getFechaHoraFin()).append("\n");
            textAreaHorario.setText(horarioTexto.toString());
        } else {
            textAreaHorario.setText("");
        }
    }

    private class EstudianteSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Estudiante estudianteSeleccionado = (Estudiante) cbxEstudiantes.getSelectedItem();
            listModelGruposInscritos.clear();
            if (estudianteSeleccionado != null) {
                ArrayList<Grupo> gruposInscritos = conexion.obtenerGruposInscritos(estudianteSeleccionado);
                for (Grupo grupo : gruposInscritos) {
                    listModelGruposInscritos.addElement(grupo);
                }
            }
        }
    }

    private class GrupoSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            mostrarHorarioGrupoSeleccionado();
        }
    }

    private class AgregarGrupoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Estudiante estudianteSeleccionado = (Estudiante) cbxEstudiantes.getSelectedItem();
            Grupo grupoSeleccionado = (Grupo) cbxGruposDisponibles.getSelectedItem();
            if (estudianteSeleccionado != null && grupoSeleccionado != null) {
                boolean exito = conexion.agregarGrupoInscrito(estudianteSeleccionado, grupoSeleccionado);
                if (exito) {
                    listModelGruposInscritos.addElement(grupoSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo inscribir el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class EliminarGrupoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Estudiante estudianteSeleccionado = (Estudiante) cbxEstudiantes.getSelectedItem();
            Grupo grupoSeleccionado = listGruposInscritos.getSelectedValue();
            if (estudianteSeleccionado != null && grupoSeleccionado != null) {
                boolean exito = conexion.eliminarGrupoInscrito(estudianteSeleccionado, grupoSeleccionado);
                if (exito) {
                    listModelGruposInscritos.removeElement(grupoSeleccionado);
                    textAreaHorario.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el grupo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
