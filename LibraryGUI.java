import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class LibraryGUI extends JFrame {
    private Library library;
    private JTabbedPane tabbedPane;
    
    // Components for Books tab
    private JTextField bookTitleField, bookAuthorField, bookSearchField;
    private JTable booksTable;
    private DefaultTableModel booksTableModel;
    private JLabel bookCountLabel;
    
    // Components for Members tab
    private JTextField memberNameField, memberIdField, memberSearchField;
    private JTable membersTable;
    private DefaultTableModel membersTableModel;
    private JLabel memberCountLabel;
    
    // Components for Borrow/Return tab
    private JComboBox<String> borrowBookCombo, borrowMemberCombo;
    private JComboBox<String> returnBookCombo, returnMemberCombo;
    private JTextArea transactionHistoryArea;
    
    // Components for View tab
    private JComboBox<String> viewMemberCombo;
    private JTextArea borrowedBooksArea;
    private JLabel borrowedCountLabel;

    // Color scheme
    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);
    private static final Color SECONDARY_COLOR = new Color(33, 150, 243);
    private static final Color SUCCESS_COLOR = new Color(76, 175, 80);
    private static final Color WARNING_COLOR = new Color(255, 193, 7);
    private static final Color ERROR_COLOR = new Color(244, 67, 54);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color BORDER_COLOR = new Color(224, 224, 224);

    public LibraryGUI() {
        library = new Library();
        initializeGUI();
        addSampleData();
        setupKeyboardShortcuts();
    }

    private void initializeGUI() {
        setTitle("📚 Library Management System - Enhanced GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 600));

        // Use default modern look and feel

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        tabbedPane.addTab("📚 Books Library", createEnhancedBooksPanel());
        tabbedPane.addTab("👥 Members", createEnhancedMembersPanel());
        tabbedPane.addTab("🔄 Transactions", createEnhancedBorrowReturnPanel());
        tabbedPane.addTab("📊 Member Activity", createEnhancedViewPanel());

        add(tabbedPane);
        
        // Add status bar
        JPanel statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private JPanel createEnhancedBooksPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header panel with title and stats
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("📚 Books Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        bookCountLabel = new JLabel("0 books in library");
        bookCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bookCountLabel.setForeground(Color.GRAY);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(bookCountLabel, BorderLayout.EAST);

        // Input panel with modern design
        JPanel inputPanel = createStyledPanel("Add New Book");
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        // Title field
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createStyledLabel("Book Title:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        bookTitleField = createStyledTextField(25);
        bookTitleField.setToolTipText("Enter the book title");
        inputPanel.add(bookTitleField, gbc);

        // Author field
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        inputPanel.add(createStyledLabel("Author:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        bookAuthorField = createStyledTextField(25);
        bookAuthorField.setToolTipText("Enter the author's name");
        inputPanel.add(bookAuthorField, gbc);

        // Add button
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        JButton addBookButton = createStyledButton("📖 Add Book", SUCCESS_COLOR);
        addBookButton.addActionListener(e -> addBook());
        inputPanel.add(addBookButton, gbc);

        // Clear button
        gbc.gridx = 2;
        JButton clearButton = createStyledButton("🗑️ Clear", Color.GRAY);
        clearButton.addActionListener(e -> clearBookFields());
        inputPanel.add(clearButton, gbc);

        // Search panel
        JPanel searchPanel = createStyledPanel("Search Books");
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel searchLabel = createStyledLabel("🔍 Search:");
        bookSearchField = createStyledTextField(20);
        bookSearchField.setToolTipText("Search by title or author");
        bookSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterBooksTable();
            }
        });
        
        searchPanel.add(searchLabel);
        searchPanel.add(bookSearchField);

        // Table with enhanced design
        String[] columns = {"Title", "Author", "Status", "Actions"};
        booksTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        booksTable = new JTable(booksTableModel);
        setupEnhancedTable(booksTable);
        
        // Add double-click listener for quick actions
        booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = booksTable.getSelectedRow();
                    if (row != -1) {
                        String title = (String) booksTable.getValueAt(row, 0);
                        showBookDetails(title);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(booksTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);

        // Layout
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createEnhancedMembersPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("👥 Members Management");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        memberCountLabel = new JLabel("0 members registered");
        memberCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        memberCountLabel.setForeground(Color.GRAY);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(memberCountLabel, BorderLayout.EAST);

        // Input panel
        JPanel inputPanel = createStyledPanel("Register New Member");
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(createStyledLabel("Member Name:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        memberNameField = createStyledTextField(25);
        memberNameField.setToolTipText("Enter member's full name");
        inputPanel.add(memberNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        inputPanel.add(createStyledLabel("Member ID:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        memberIdField = createStyledTextField(25);
        memberIdField.setToolTipText("Enter unique member ID (e.g., M001)");
        inputPanel.add(memberIdField, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 1;
        JButton addMemberButton = createStyledButton("👤 Add Member", SECONDARY_COLOR);
        addMemberButton.addActionListener(e -> addMember());
        inputPanel.add(addMemberButton, gbc);

        gbc.gridx = 2;
        JButton clearMemberButton = createStyledButton("🗑️ Clear", Color.GRAY);
        clearMemberButton.addActionListener(e -> clearMemberFields());
        inputPanel.add(clearMemberButton, gbc);

        // Search panel
        JPanel searchPanel = createStyledPanel("Search Members");
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        memberSearchField = createStyledTextField(20);
        memberSearchField.setToolTipText("Search by name or ID");
        memberSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterMembersTable();
            }
        });
        
        searchPanel.add(createStyledLabel("🔍 Search:"));
        searchPanel.add(memberSearchField);

        // Table
        String[] columns = {"Name", "Member ID", "Books Borrowed", "Status"};
        membersTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        membersTable = new JTable(membersTableModel);
        setupEnhancedTable(membersTable);

        JScrollPane scrollPane = new JScrollPane(membersTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createEnhancedBorrowReturnPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JLabel titleLabel = new JLabel("🔄 Book Transactions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);

        // Create two side-by-side panels
        JPanel transactionPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        transactionPanel.setBackground(Color.WHITE);

        // Borrow panel
        JPanel borrowPanel = createStyledPanel("📖 Borrow Book");
        borrowPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        borrowPanel.add(createStyledLabel("Select Book:"), gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        borrowBookCombo = createStyledComboBox();
        borrowBookCombo.setToolTipText("Choose an available book");
        borrowPanel.add(borrowBookCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        borrowPanel.add(createStyledLabel("Select Member:"), gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        borrowMemberCombo = createStyledComboBox();
        borrowMemberCombo.setToolTipText("Choose a registered member");
        borrowPanel.add(borrowMemberCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JButton borrowButton = createStyledButton("📚 Borrow Book", WARNING_COLOR);
        borrowButton.addActionListener(e -> borrowBookEnhanced());
        borrowPanel.add(borrowButton, gbc);

        // Return panel
        JPanel returnPanel = createStyledPanel("📤 Return Book");
        returnPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        returnPanel.add(createStyledLabel("Select Member:"), gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        returnMemberCombo = createStyledComboBox();
        returnMemberCombo.setToolTipText("Choose member who borrowed books");
        returnMemberCombo.addActionListener(e -> updateReturnBookCombo());
        returnPanel.add(returnMemberCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        returnPanel.add(createStyledLabel("Select Book:"), gbc);
        gbc.gridx = 0; gbc.gridy = 3;
        returnBookCombo = createStyledComboBox();
        returnBookCombo.setToolTipText("Choose book to return");
        returnPanel.add(returnBookCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JButton returnButton = createStyledButton("📥 Return Book", SUCCESS_COLOR);
        returnButton.addActionListener(e -> returnBookEnhanced());
        returnPanel.add(returnButton, gbc);

        transactionPanel.add(borrowPanel);
        transactionPanel.add(returnPanel);

        // Transaction history
        JPanel historyPanel = createStyledPanel("📊 Recent Transactions");
        transactionHistoryArea = new JTextArea(8, 50);
        transactionHistoryArea.setEditable(false);
        transactionHistoryArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        transactionHistoryArea.setBackground(LIGHT_GRAY);
        transactionHistoryArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane historyScroll = new JScrollPane(transactionHistoryArea);
        historyScroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        historyPanel.add(historyScroll, BorderLayout.CENTER);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(transactionPanel, BorderLayout.CENTER);
        mainPanel.add(historyPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private JPanel createEnhancedViewPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("📊 Member Activity Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        
        borrowedCountLabel = new JLabel("Select a member to view activity");
        borrowedCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        borrowedCountLabel.setForeground(Color.GRAY);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(borrowedCountLabel, BorderLayout.EAST);

        // Selection panel
        JPanel selectionPanel = createStyledPanel("Select Member");
        selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        selectionPanel.add(createStyledLabel("👤 Member:"));
        viewMemberCombo = createStyledComboBox();
        viewMemberCombo.setPreferredSize(new Dimension(200, 35));
        viewMemberCombo.setToolTipText("Select a member to view their borrowed books");
        selectionPanel.add(viewMemberCombo);
        
        JButton viewButton = createStyledButton("📋 View Activity", PRIMARY_COLOR);
        viewButton.addActionListener(e -> viewBorrowedBooksEnhanced());
        selectionPanel.add(viewButton);

        JButton refreshButton = createStyledButton("🔄 Refresh", Color.GRAY);
        refreshButton.addActionListener(e -> refreshAllData());
        selectionPanel.add(refreshButton);

        // Display area
        borrowedBooksArea = new JTextArea(20, 50);
        borrowedBooksArea.setEditable(false);
        borrowedBooksArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        borrowedBooksArea.setBackground(LIGHT_GRAY);
        borrowedBooksArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        borrowedBooksArea.setText("📋 Select a member from the dropdown above to view their borrowed books and activity.");
        
        JScrollPane scrollPane = new JScrollPane(borrowedBooksArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scrollPane.getViewport().setBackground(LIGHT_GRAY);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(selectionPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        return mainPanel;
    }

    // Helper methods for styled components
    private JPanel createStyledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1), 
            title, 
            0, 0, 
            new Font("Segoe UI", Font.BOLD, 14), 
            PRIMARY_COLOR
        ));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    private JTextField createStyledTextField(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setBackground(Color.WHITE);
        return field;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private JComboBox<String> createStyledComboBox() {
        JComboBox<String> combo = new JComboBox<>();
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setPreferredSize(new Dimension(250, 35));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    private void setupEnhancedTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(232, 245, 253));
        table.setSelectionForeground(Color.BLACK);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(LIGHT_GRAY);
        table.getTableHeader().setForeground(Color.DARK_GRAY);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        
        // Center align status column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        if (table.getColumnCount() > 2) {
            table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        }
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(LIGHT_GRAY);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel statusLabel = new JLabel("📚 Library Management System - Ready");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.GRAY);
        statusBar.add(statusLabel);
        
        return statusBar;
    }

    // Enhanced functionality methods
    private void addBook() {
        String title = bookTitleField.getText().trim();
        String author = bookAuthorField.getText().trim();

        if (title.isEmpty() || author.isEmpty()) {
            showStyledMessage("Please fill in both title and author fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate books
        for (Book book : library.getAllBooks()) {
            if (book.getTitle().equalsIgnoreCase(title) && book.getAuthor().equalsIgnoreCase(author)) {
                showStyledMessage("This book already exists in the library!", "Duplicate Book", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Book book = new Book(title, author);
        library.addBook(book);
        updateAllTables();
        clearBookFields();
        
        showStyledMessage("Book '" + title + "' added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        addTransactionHistory("📖 Added book: " + title + " by " + author);
    }

    private void addMember() {
        String name = memberNameField.getText().trim();
        String memberId = memberIdField.getText().trim();

        if (name.isEmpty() || memberId.isEmpty()) {
            showStyledMessage("Please fill in both name and member ID fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check for duplicate member ID
        for (Member member : library.getAllMembers()) {
            if (member.getMemberId().equals(memberId)) {
                showStyledMessage("Member ID '" + memberId + "' already exists!", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Member member = new Member(name, memberId);
        library.addMember(member);
        updateAllTables();
        clearMemberFields();
        
        showStyledMessage("Member '" + name + "' registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        addTransactionHistory("👤 Registered member: " + name + " (ID: " + memberId + ")");
    }

    private void borrowBookEnhanced() {
        String selectedBook = (String) borrowBookCombo.getSelectedItem();
        String selectedMember = (String) borrowMemberCombo.getSelectedItem();

        if (selectedBook == null || selectedMember == null) {
            showStyledMessage("Please select both a book and a member.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String bookTitle = selectedBook.split(" - ")[0];
            String memberId = selectedMember.split(" - ")[1];
            
            library.borrowBook(bookTitle, memberId);
            updateAllTables();
            
            showStyledMessage("Book borrowed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            addTransactionHistory("📚 " + selectedMember.split(" - ")[0] + " borrowed: " + bookTitle);
        } catch (Exception e) {
            showStyledMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnBookEnhanced() {
        String selectedMember = (String) returnMemberCombo.getSelectedItem();
        String selectedBook = (String) returnBookCombo.getSelectedItem();

        if (selectedMember == null || selectedBook == null) {
            showStyledMessage("Please select both a member and a book.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String memberId = selectedMember.split(" - ")[1];
            String bookTitle = selectedBook;
            
            library.returnBook(bookTitle, memberId);
            updateAllTables();
            
            showStyledMessage("Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            addTransactionHistory("📥 " + selectedMember.split(" - ")[0] + " returned: " + bookTitle);
        } catch (Exception e) {
            showStyledMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewBorrowedBooksEnhanced() {
        String selectedMember = (String) viewMemberCombo.getSelectedItem();

        if (selectedMember == null) {
            showStyledMessage("Please select a member.", "Selection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String memberId = selectedMember.split(" - ")[1];
        Member member = null;
        
        for (Member m : library.getAllMembers()) {
            if (m.getMemberId().equals(memberId)) {
                member = m;
                break;
            }
        }

        if (member == null) {
            borrowedBooksArea.setText("❌ Member not found.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("👤 Member: ").append(member.getName()).append(" (ID: ").append(member.getMemberId()).append(")\n");
        sb.append("═".repeat(60)).append("\n\n");

        if (member.getBorrowedBooks().isEmpty()) {
            sb.append("📭 No books currently borrowed.\n\n");
            sb.append("ℹ️ This member has a clean record with no outstanding books.");
            borrowedCountLabel.setText("0 books borrowed");
        } else {
            sb.append("📚 Currently Borrowed Books (").append(member.getBorrowedBooks().size()).append("):\n\n");
            int count = 1;
            for (Book book : member.getBorrowedBooks()) {
                sb.append(String.format("%d. 📖 %s\n", count++, book.getTitle()));
                sb.append(String.format("   ✍️ Author: %s\n\n", book.getAuthor()));
            }
            borrowedCountLabel.setText(member.getBorrowedBooks().size() + " books borrowed");
        }

        borrowedBooksArea.setText(sb.toString());
        borrowedBooksArea.setCaretPosition(0); // Scroll to top
    }

    // Utility methods
    private void clearBookFields() {
        bookTitleField.setText("");
        bookAuthorField.setText("");
        bookTitleField.requestFocus();
    }

    private void clearMemberFields() {
        memberNameField.setText("");
        memberIdField.setText("");
        memberNameField.requestFocus();
    }

    private void filterBooksTable() {
        String searchText = bookSearchField.getText().toLowerCase();
        booksTableModel.setRowCount(0);
        
        for (Book book : library.getAllBooks()) {
            if (book.getTitle().toLowerCase().contains(searchText) || 
                book.getAuthor().toLowerCase().contains(searchText)) {
                String status = book.isAvailable() ? "✅ Available" : "❌ Borrowed";
                booksTableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), status, "📋"});
            }
        }
    }

    private void filterMembersTable() {
        String searchText = memberSearchField.getText().toLowerCase();
        membersTableModel.setRowCount(0);
        
        for (Member member : library.getAllMembers()) {
            if (member.getName().toLowerCase().contains(searchText) || 
                member.getMemberId().toLowerCase().contains(searchText)) {
                int borrowedCount = member.getBorrowedBooks().size();
                String status = borrowedCount > 0 ? "📚 Active" : "💤 Inactive";
                membersTableModel.addRow(new Object[]{
                    member.getName(), 
                    member.getMemberId(), 
                    borrowedCount, 
                    status
                });
            }
        }
    }

    private void updateAllTables() {
        updateBooksTable();
        updateMembersTable();
        updateComboBoxes();
        updateCounts();
    }

    private void updateBooksTable() {
        booksTableModel.setRowCount(0);
        for (Book book : library.getAllBooks()) {
            String status = book.isAvailable() ? "✅ Available" : "❌ Borrowed";
            booksTableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), status, "📋"});
        }
    }

    private void updateMembersTable() {
        membersTableModel.setRowCount(0);
        for (Member member : library.getAllMembers()) {
            int borrowedCount = member.getBorrowedBooks().size();
            String status = borrowedCount > 0 ? "📚 Active" : "💤 Inactive";
            membersTableModel.addRow(new Object[]{
                member.getName(), 
                member.getMemberId(), 
                borrowedCount, 
                status
            });
        }
    }

    private void updateComboBoxes() {
        // Update borrow book combo
        borrowBookCombo.removeAllItems();
        for (Book book : library.getAllBooks()) {
            if (book.isAvailable()) {
                borrowBookCombo.addItem(book.getTitle() + " - " + book.getAuthor());
            }
        }

        // Update member combos
        borrowMemberCombo.removeAllItems();
        returnMemberCombo.removeAllItems();
        viewMemberCombo.removeAllItems();
        
        for (Member member : library.getAllMembers()) {
            String memberInfo = member.getName() + " - " + member.getMemberId();
            borrowMemberCombo.addItem(memberInfo);
            viewMemberCombo.addItem(memberInfo);
            
            if (!member.getBorrowedBooks().isEmpty()) {
                returnMemberCombo.addItem(memberInfo);
            }
        }
    }

    private void updateReturnBookCombo() {
        returnBookCombo.removeAllItems();
        String selectedMember = (String) returnMemberCombo.getSelectedItem();
        
        if (selectedMember != null) {
            String memberId = selectedMember.split(" - ")[1];
            for (Member member : library.getAllMembers()) {
                if (member.getMemberId().equals(memberId)) {
                    for (Book book : member.getBorrowedBooks()) {
                        returnBookCombo.addItem(book.getTitle());
                    }
                    break;
                }
            }
        }
    }

    private void updateCounts() {
        int totalBooks = library.getAllBooks().size();
        int availableBooks = 0;
        for (Book book : library.getAllBooks()) {
            if (book.isAvailable()) availableBooks++;
        }
        
        bookCountLabel.setText(totalBooks + " books total, " + availableBooks + " available");
        memberCountLabel.setText(library.getAllMembers().size() + " members registered");
    }

    private void addTransactionHistory(String transaction) {
        String timestamp = java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")
        );
        String entry = "[" + timestamp + "] " + transaction + "\n";
        transactionHistoryArea.insert(entry, 0);
        
        // Keep only last 50 transactions
        String text = transactionHistoryArea.getText();
        String[] lines = text.split("\n");
        if (lines.length > 50) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 50; i++) {
                sb.append(lines[i]).append("\n");
            }
            transactionHistoryArea.setText(sb.toString());
        }
    }

    private void showBookDetails(String title) {
        for (Book book : library.getAllBooks()) {
            if (book.getTitle().equals(title)) {
                String details = String.format(
                    "📖 Book Details\n\n" +
                    "Title: %s\n" +
                    "Author: %s\n" +
                    "Status: %s\n\n" +
                    "Double-click on books to view details.",
                    book.getTitle(),
                    book.getAuthor(),
                    book.isAvailable() ? "Available" : "Currently Borrowed"
                );
                showStyledMessage(details, "Book Information", JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
    }

    private void refreshAllData() {
        updateAllTables();
        bookSearchField.setText("");
        memberSearchField.setText("");
        showStyledMessage("All data refreshed successfully!", "Refresh Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setupKeyboardShortcuts() {
        // Ctrl+N for new book
        KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlN, "newBook");
        getRootPane().getActionMap().put("newBook", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(0);
                bookTitleField.requestFocus();
            }
        });

        // Ctrl+M for new member
        KeyStroke ctrlM = KeyStroke.getKeyStroke(KeyEvent.VK_M, KeyEvent.CTRL_DOWN_MASK);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlM, "newMember");
        getRootPane().getActionMap().put("newMember", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(1);
                memberNameField.requestFocus();
            }
        });

        // F5 for refresh
        KeyStroke f5 = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(f5, "refresh");
        getRootPane().getActionMap().put("refresh", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshAllData();
            }
        });
    }

    private void showStyledMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void addSampleData() {
        // Add sample books
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        library.addBook(new Book("1984", "George Orwell"));
        library.addBook(new Book("Pride and Prejudice", "Jane Austen"));
        library.addBook(new Book("The Catcher in the Rye", "J.D. Salinger"));
        library.addBook(new Book("Lord of the Rings", "J.R.R. Tolkien"));
        library.addBook(new Book("Harry Potter", "J.K. Rowling"));

        // Add sample members
        library.addMember(new Member("Alice Johnson", "M001"));
        library.addMember(new Member("Bob Smith", "M002"));
        library.addMember(new Member("Carol Davis", "M003"));
        library.addMember(new Member("David Brown", "M004"));

        updateAllTables();
        addTransactionHistory("🚀 System initialized with sample data");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI();
        });
    }
} 