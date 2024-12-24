import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser;



public class EventManagerApp {
    private JFrame frame;
    private JTextField eventNameField;
    private JDateChooser dateChooser;
    private JTextArea eventDescriptionArea;    private DefaultListModel<String> eventListModel;
    private JList<String> eventList;
    private ArrayList<Event> events;
    

    public EventManagerApp() {
        events = new ArrayList<>();
        initialize();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EventManagerApp app = new EventManagerApp();
                app.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        frame = new JFrame("Event Manager");
        frame.setBounds(100, 100, 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Top Panel for adding events
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(4, 2, 20, 10));

        topPanel.add(new JLabel("Event Name:"));
        eventNameField = new JTextField();
        topPanel.add(eventNameField);

        topPanel.add(new JLabel("Event Date (YYYY-MM-DD):"));
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd-mm-yyyy");
        topPanel.add(dateChooser);

        topPanel.add(new JLabel("Description:"));
        eventDescriptionArea = new JTextArea(2, 20);
        topPanel.add(new JScrollPane(eventDescriptionArea));

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(this::addEvent);
        topPanel.add(addButton);

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);

        // Center Panel for displaying events
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());

        eventListModel = new DefaultListModel<>();
        eventList = new JList<>(eventListModel);
        eventList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        centerPanel.add(new JScrollPane(eventList), BorderLayout.CENTER);

        JButton deleteButton = new JButton("Delete Selected Event");
        deleteButton.addActionListener(this::deleteEvent);
        centerPanel.add(deleteButton, BorderLayout.SOUTH);

        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
    }

    private void addEvent(ActionEvent e) {
        String name = eventNameField.getText();
        String date = eventDateField.getText();
        String description = eventDescriptionArea.getText();

        if (name.isEmpty() || date.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Event event = new Event(name, date, description);
        events.add(event);
        eventListModel.addElement(event.toString());

        eventNameField.setText("");
        eventDateField.setText("");
        eventDescriptionArea.setText("");
    }

    private void deleteEvent(ActionEvent e) {
        int selectedIndex = eventList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an event to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        events.remove(selectedIndex);
        eventListModel.remove(selectedIndex);
    }

    static class Event {
        private String name;
        private String date;
        private String description;

        public Event(String name, String date, String description) {
            this.name = name;
            this.date = date;
            this.description = description;
        }

        @Override
        public String toString() {
            return name + " (" + date + ")";
        }
    }
}
