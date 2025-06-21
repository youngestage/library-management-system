import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;

    // Constructor
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    // Method to add a book to the library
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    // Method to add a member to the library
    public void addMember(Member member) {
        members.add(member);
        System.out.println("Member added: " + member.getName());
    }

    // Method to find a book by title
    private Book findBookByTitle(String title) throws BookNotFoundException {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        throw new BookNotFoundException("Book with title '" + title + "' not found in the library.");
    }

    // Method to find a member by ID
    private Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    // Method to borrow a book
    public void borrowBook(String title, String memberId) throws BookNotFoundException, BookNotAvailableException {
        Book book = findBookByTitle(title);
        Member member = findMemberById(memberId);

        if (member == null) {
            throw new IllegalArgumentException("Member with ID '" + memberId + "' not found.");
        }

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book '" + title + "' is currently not available for borrowing.");
        }

        book.borrowBook();
        member.addBorrowedBook(book);
        System.out.println(member.getName() + " successfully borrowed '" + title + "'");
    }

    // Overloaded method to borrow a book with Member object
    public void borrowBook(String title, Member member) throws BookNotFoundException, BookNotAvailableException {
        Book book = findBookByTitle(title);

        if (!book.isAvailable()) {
            throw new BookNotAvailableException("Book '" + title + "' is currently not available for borrowing.");
        }

        book.borrowBook();
        member.addBorrowedBook(book);
        System.out.println(member.getName() + " successfully borrowed '" + title + "'");
    }

    // Method to return a book
    public void returnBook(String title, String memberId) throws BookNotFoundException {
        Book book = findBookByTitle(title);
        Member member = findMemberById(memberId);

        if (member == null) {
            throw new IllegalArgumentException("Member with ID '" + memberId + "' not found.");
        }

        if (!member.hasBorrowedBook(book)) {
            throw new IllegalArgumentException("Member '" + member.getName() + "' has not borrowed the book '" + title + "'");
        }

        book.returnBook();
        member.removeBorrowedBook(book);
        System.out.println(member.getName() + " successfully returned '" + title + "'");
    }

    // Overloaded method to return a book with Member object
    public void returnBook(String title, Member member) throws BookNotFoundException {
        Book book = findBookByTitle(title);

        if (!member.hasBorrowedBook(book)) {
            throw new IllegalArgumentException("Member '" + member.getName() + "' has not borrowed the book '" + title + "'");
        }

        book.returnBook();
        member.removeBorrowedBook(book);
        System.out.println(member.getName() + " successfully returned '" + title + "'");
    }

    // Method to display all books in the library
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("Library Books:");
            for (Book book : books) {
                String status = book.isAvailable() ? "Available" : "Borrowed";
                System.out.println("- " + book.getTitle() + " by " + book.getAuthor() + " [" + status + "]");
            }
        }
    }

    // Method to display all available books
    public void displayAvailableBooks() {
        System.out.println("Available Books:");
        boolean hasAvailable = false;
        for (Book book : books) {
            if (book.isAvailable()) {
                System.out.println("- " + book.getTitle() + " by " + book.getAuthor());
                hasAvailable = true;
            }
        }
        if (!hasAvailable) {
            System.out.println("No books are currently available.");
        }
    }

    // Method to display all members
    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members in the library.");
        } else {
            System.out.println("Library Members:");
            for (Member member : members) {
                System.out.println("- " + member.getName() + " (ID: " + member.getMemberId() + 
                                   ", Borrowed: " + member.getBorrowedBooks().size() + " books)");
            }
        }
    }

    // Bonus: Method to display all books currently borrowed by a specific member
    public void displayBorrowedBooksByMember(String memberId) {
        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member with ID '" + memberId + "' not found.");
        } else {
            member.displayBorrowedBooks();
        }
    }

    // Overloaded method with Member object
    public void displayBorrowedBooksByMember(Member member) {
        member.displayBorrowedBooks();
    }

    // Getters for accessing collections (returning copies to maintain encapsulation)
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members);
    }
} 