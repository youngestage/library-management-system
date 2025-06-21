# Library Management System

A comprehensive Library Management System implemented in Java using Object-Oriented Programming principles. Now available in both **Console** and **GUI** versions!

## Features

### Core Requirements ✅
- **Book Management**: Books with title, author, and availability status
- **Member Management**: Members who can borrow and return books
- **Library System**: Central system managing books and borrowing logic
- **Encapsulation**: All classes use proper getter/setter methods and private fields
- **Object Association**: Members are associated with borrowed books
- **Collections**: Uses ArrayList to manage books and borrowed books
- **Exception Handling**: Custom exceptions for unavailable and non-existent books
- **Required Methods**: `borrowBook(String title)` and `returnBook(String title)`

### Bonus Features ✅
- **Display Borrowed Books**: Method to show all books currently borrowed by a member
- **Comprehensive Error Handling**: Multiple exception types and validation
- **Overloaded Methods**: Support for both String IDs and object references
- **Rich Display Methods**: Various ways to view library state

## Class Structure

### 1. Book.java
- **Attributes**: `title`, `author`, `isAvailable`
- **Methods**: Getters, setters, `borrowBook()`, `returnBook()`, `toString()`, `equals()`
- **Encapsulation**: All fields are private with public accessors

### 2. Member.java
- **Attributes**: `name`, `memberId`, `borrowedBooks` (List)
- **Methods**: Manage borrowed books, display borrowed books, validation methods
- **Object Association**: Maintains list of borrowed Book objects

### 3. Library.java
- **Attributes**: `books` (List), `members` (List)
- **Core Methods**: 
  - `borrowBook(String title, String memberId)`
  - `returnBook(String title, String memberId)`
  - `displayBorrowedBooksByMember(String memberId)` (Bonus)
- **Additional Features**: Display methods, member management, book management

### 4. Exception Classes
- **BookNotFoundException**: Thrown when requested book doesn't exist
- **BookNotAvailableException**: Thrown when trying to borrow an unavailable book

### 5. LibraryManagementSystem.java
- **Console demo class**: Demonstrates all functionality with comprehensive testing

### 6. LibraryGUI.java 
- **GUI version**: User-friendly graphical interface with tabs for different operations
- **Features**: Add books/members, borrow/return books, view borrowed books
- **UI Elements**: Tables, forms, buttons with color-coded actions

### 7. LibraryLauncher.java
- **Launcher**: Choose between GUI and Console versions

## OOP Principles Applied

1. **Encapsulation**: 
   - Private fields with public getters/setters
   - Data validation in methods
   - Defensive copying in collections

2. **Object Association**:
   - Member class contains List of Book objects
   - Library class manages both Books and Members

3. **Exception Handling**:
   - Custom exception classes
   - Proper exception throwing and catching
   - Validation with meaningful error messages

4. **Collections**:
   - ArrayList for managing books and members
   - ArrayList for tracking borrowed books per member

## How to Run

### Option 1: Use the Launcher (Recommended)
1. **Compile all Java files**:
   ```bash
   javac *.java
   ```

2. **Run the launcher**:
   ```bash
   java LibraryLauncher
   ```
   This will open a launcher window where you can choose between:
   - 🖥️ **GUI Version**: User-friendly graphical interface
   - 💻 **Console Version**: Terminal-based demonstration

### Option 2: Run Specific Versions Directly

**GUI Version:**
```bash
java LibraryGUI
```

**Console Version:**
```bash
java LibraryManagementSystem
```

## Sample Usage

```java
// Create library and add books
Library library = new Library();
Book book = new Book("1984", "George Orwell");
library.addBook(book);

// Create and add member
Member member = new Member("John Doe", "M001");
library.addMember(member);

// Borrow a book
try {
    library.borrowBook("1984", "M001");
} catch (BookNotFoundException | BookNotAvailableException e) {
    System.out.println("Error: " + e.getMessage());
}

// Display borrowed books
library.displayBorrowedBooksByMember("M001");

// Return a book
try {
    library.returnBook("1984", "M001");
} catch (BookNotFoundException e) {
    System.out.println("Error: " + e.getMessage());
}
```

## Error Handling Examples

The system handles various error scenarios:
- Borrowing non-existent books
- Borrowing already borrowed books
- Returning books not borrowed by the member
- Invalid member IDs

## Testing

The `LibraryManagementSystem` class includes comprehensive testing that demonstrates:
- Adding books and members
- Successful borrowing and returning
- Exception handling for various error cases
- Display functionality for all features
- Edge case testing

Run the main class to see all features in action! 