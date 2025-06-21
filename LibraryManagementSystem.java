public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Create a library instance
        Library library = new Library();

        // Create some books
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee");
        Book book3 = new Book("1984", "George Orwell");
        Book book4 = new Book("Pride and Prejudice", "Jane Austen");
        Book book5 = new Book("The Catcher in the Rye", "J.D. Salinger");

        // Add books to the library
        System.out.println("=== Adding Books to Library ===");
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        // Create some members
        Member member1 = new Member("Alice Johnson", "M001");
        Member member2 = new Member("Bob Smith", "M002");
        Member member3 = new Member("Carol Davis", "M003");

        // Add members to the library
        System.out.println("\n=== Adding Members to Library ===");
        library.addMember(member1);
        library.addMember(member2);
        library.addMember(member3);

        // Display initial state
        System.out.println("\n=== Initial Library State ===");
        library.displayAllBooks();
        System.out.println();
        library.displayAllMembers();

        // Test borrowing books
        System.out.println("\n=== Testing Book Borrowing ===");
        try {
            library.borrowBook("The Great Gatsby", "M001");
            library.borrowBook("1984", member2);
            library.borrowBook("Pride and Prejudice", "M003");
        } catch (BookNotFoundException | BookNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display books after borrowing
        System.out.println("\n=== Library State After Borrowing ===");
        library.displayAllBooks();

        // Test exception handling - trying to borrow an unavailable book
        System.out.println("\n=== Testing Exception Handling ===");
        try {
            library.borrowBook("The Great Gatsby", "M002"); // Should throw exception
        } catch (BookNotFoundException | BookNotAvailableException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        // Test borrowing a non-existent book
        try {
            library.borrowBook("Non-existent Book", "M001"); // Should throw exception
        } catch (BookNotFoundException | BookNotAvailableException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        // Display borrowed books by members (Bonus feature)
        System.out.println("\n=== Displaying Borrowed Books by Members ===");
        library.displayBorrowedBooksByMember("M001");
        library.displayBorrowedBooksByMember(member2);
        library.displayBorrowedBooksByMember("M003");

        // Test returning books
        System.out.println("\n=== Testing Book Returns ===");
        try {
            library.returnBook("The Great Gatsby", "M001");
            library.returnBook("1984", member2);
        } catch (BookNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display final state
        System.out.println("\n=== Final Library State ===");
        library.displayAllBooks();
        
        System.out.println("\n=== Available Books ===");
        library.displayAvailableBooks();

        // Display remaining borrowed books
        System.out.println("\n=== Remaining Borrowed Books ===");
        library.displayBorrowedBooksByMember("M001");
        library.displayBorrowedBooksByMember("M002");
        library.displayBorrowedBooksByMember("M003");

        // Test edge case - trying to return a book not borrowed by the member
        System.out.println("\n=== Testing Edge Cases ===");
        try {
            library.returnBook("To Kill a Mockingbird", "M001"); // Should throw exception
        } catch (BookNotFoundException e) {
            System.out.println("Expected Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Expected Error: " + e.getMessage());
        }

        // Demonstrate more borrowing
        System.out.println("\n=== Additional Borrowing Test ===");
        try {
            library.borrowBook("The Great Gatsby", member1); // Now available again
            library.borrowBook("To Kill a Mockingbird", "M002");
        } catch (BookNotFoundException | BookNotAvailableException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Final display of borrowed books
        System.out.println("\n=== Final Borrowed Books Status ===");
        for (Member member : library.getAllMembers()) {
            library.displayBorrowedBooksByMember(member);
        }
    }
} 