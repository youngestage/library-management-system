import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryLauncher extends JFrame {
    
    public LibraryLauncher() {
        setTitle("Library Management System - Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(63, 81, 181));
        JLabel titleLabel = new JLabel("Library Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        // GUI version button
        JButton guiButton = new JButton("🖥️ Launch GUI Version");
        guiButton.setFont(new Font("Arial", Font.BOLD, 16));
        guiButton.setBackground(new Color(76, 175, 80));
        guiButton.setForeground(Color.WHITE);
        guiButton.setFocusPainted(false);
        guiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchGUI();
            }
        });
        
        // Console version button
        JButton consoleButton = new JButton("💻 Launch Console Version");
        consoleButton.setFont(new Font("Arial", Font.BOLD, 16));
        consoleButton.setBackground(new Color(33, 150, 243));
        consoleButton.setForeground(Color.WHITE);
        consoleButton.setFocusPainted(false);
        consoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                launchConsole();
            }
        });
        
        // Exit button
        JButton exitButton = new JButton("❌ Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBackground(new Color(244, 67, 54));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        buttonPanel.add(guiButton);
        buttonPanel.add(consoleButton);
        buttonPanel.add(exitButton);
        
        // Description panel
        JPanel descPanel = new JPanel();
        JLabel descLabel = new JLabel("<html><center>Choose how you want to run the Library Management System<br/>GUI version provides a user-friendly interface<br/>Console version shows all features in terminal</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        descLabel.setForeground(Color.GRAY);
        descPanel.add(descLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(descPanel, BorderLayout.SOUTH);
    }
    
    private void launchGUI() {
        this.setVisible(false);
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
        this.dispose();
    }
    
    private void launchConsole() {
        this.setVisible(false);
        this.dispose();
        LibraryManagementSystem.main(new String[0]);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryLauncher().setVisible(true);
        });
    }
} 