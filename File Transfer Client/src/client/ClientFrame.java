/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;
import javax.swing.*;
import java.awt.*;
import java.io.File;
/**
 *
 * @author ASUS
 */

public class ClientFrame extends JFrame {
    private JTextField ipField, portField, fileField;
    private JButton browseButton, sendButton;
    private JTextArea logArea;
    private File selectedFile;

    public ClientFrame() {
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(3, 2));
        ipField = new JTextField("127.0.0.1");
        portField = new JTextField("9000");
        fileField = new JTextField();
        fileField.setEditable(false);
        browseButton = new JButton("Browse...");
        sendButton = new JButton("Send File");
        logArea = new JTextArea();
        logArea.setEditable(false);

        topPanel.add(new JLabel("Server IP:"));
        topPanel.add(ipField);
        topPanel.add(new JLabel("Port:"));
        topPanel.add(portField);
        topPanel.add(fileField);
        topPanel.add(browseButton);

        add(topPanel, BorderLayout.NORTH);
        add(sendButton, BorderLayout.CENTER);
        add(new JScrollPane(logArea), BorderLayout.SOUTH);

        browseButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                selectedFile = fc.getSelectedFile();
                fileField.setText(selectedFile.getAbsolutePath());
            }
        });

        sendButton.addActionListener(e -> {
            if (selectedFile == null) {
                logArea.append("Please select a file first.\n");
                return;
            }
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());
            new Thread(() -> Client.sendFile(ip, port, selectedFile, msg -> SwingUtilities.invokeLater(() -> logArea.append(msg + "\n")))).start();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientFrame().setVisible(true));
    }
}

