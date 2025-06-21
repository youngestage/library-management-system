import java.util.ArrayList;
import java.util.List;

public class Member {
    private String name;
    private String memberId;
    private List<Book> borrowedBooks;

    // Constructor
    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getMemberId() {
        return memberId;
    }

    public List<Book> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks); // Return a copy to maintain encapsulation
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    // Method to add a borrowed book
    public void addBorrowedBook(Book book) {
        borrowedBooks.add(book);
    }

    // Method to remove a returned book
    public void removeBorrowedBook(Book book) {
        borrowedBooks.remove(book);
    }

    // Method to check if member has borrowed a specific book
    public boolean hasBorrowedBook(Book book) {
        return borrowedBooks.contains(book);
    }

    // Method to display all borrowed books
    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println(name + " has not borrowed any books.");
        } else {
            System.out.println(name + "'s borrowed books:");
            for (Book book : borrowedBooks) {
                System.out.println("- " + book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", memberId='" + memberId + '\'' +
                ", borrowedBooks=" + borrowedBooks.size() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Member member = (Member) obj;
        return memberId.equals(member.memberId);
    }
} 