import java.util.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Main {
    private static List<Book> books = new ArrayList<>();
    private static List<Member> members = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int bookIdCounter = 1;
    private static int memberIdCounter = 1;
    private static int transactionIdCounter = 1;

    public static void main(String[] args) {
        // Add some sample data
        addSampleData();
        
        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. View All Transactions");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: bookManagement(); break;
                case 2: memberManagement(); break;
                case 3: issueBook(); break;
                case 4: returnBook(); break;
                case 5: viewAllTransactions(); break;
                case 6: System.out.println("Thank you!"); return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void bookManagement() {
        while (true) {
            System.out.println("\n--- Book Management ---");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addBook(); break;
                case 2: viewAllBooks(); break;
                case 3: searchBook(); break;
                case 4: deleteBook(); break;
                case 5: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addBook() {
        System.out.println("\n--- Add New Book ---");
        Book book = new Book();
        book.setId(bookIdCounter++);
        
        System.out.print("Enter Title: ");
        book.setTitle(scanner.nextLine());
        
        System.out.print("Enter Author: ");
        book.setAuthor(scanner.nextLine());
        
        System.out.print("Enter ISBN: ");
        book.setIsbn(scanner.nextLine());
        
        System.out.print("Enter Publisher: ");
        book.setPublisher(scanner.nextLine());
        
        System.out.print("Enter Quantity: ");
        book.setQuantity(scanner.nextInt());
        book.setAvailableQuantity(book.getQuantity());
        scanner.nextLine();
        
        books.add(book);
        System.out.println("Book added successfully! ID: " + book.getId());
    }

    private static void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }
        for (Book b : books) {
            System.out.println(b);
        }
    }

    private static void searchBook() {
        System.out.print("Enter book title to search: ");
        String title = scanner.nextLine().toLowerCase();
        boolean found = false;
        
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title)) {
                System.out.println(b);
                found = true;
            }
        }
        if (!found) System.out.println("No books found.");
    }

    private static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        books.removeIf(b -> b.getId() == id);
        System.out.println("Book deleted!");
    }

    private static void memberManagement() {
        while (true) {
            System.out.println("\n--- Member Management ---");
            System.out.println("1. Add Member");
            System.out.println("2. View All Members");
            System.out.println("3. Search Member");
            System.out.println("4. Delete Member");
            System.out.println("5. Back");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1: addMember(); break;
                case 2: viewAllMembers(); break;
                case 3: searchMember(); break;
                case 4: deleteMember(); break;
                case 5: return;
                default: System.out.println("Invalid choice!");
            }
        }
    }

    private static void addMember() {
        System.out.println("\n--- Add New Member ---");
        Member member = new Member();
        member.setId(memberIdCounter++);
        
        System.out.print("Enter Name: ");
        member.setName(scanner.nextLine());
        
        System.out.print("Enter Email: ");
        member.setEmail(scanner.nextLine());
        
        System.out.print("Enter Phone: ");
        member.setPhone(scanner.nextLine());
        
        member.setMembershipDate(LocalDate.now());
        member.setActive(true);
        
        members.add(member);
        System.out.println("Member added successfully! ID: " + member.getId());
    }

    private static void viewAllMembers() {
        System.out.println("\n--- All Members ---");
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }
        for (Member m : members) {
            System.out.println(m);
        }
    }

    private static void searchMember() {
        System.out.print("Enter member name to search: ");
        String name = scanner.nextLine().toLowerCase();
        boolean found = false;
        
        for (Member m : members) {
            if (m.getName().toLowerCase().contains(name)) {
                System.out.println(m);
                found = true;
            }
        }
        if (!found) System.out.println("No members found.");
    }

    private static void deleteMember() {
        System.out.print("Enter Member ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        members.removeIf(m -> m.getId() == id);
        System.out.println("Member deleted!");
    }

    private static void issueBook() {
        System.out.println("\n--- Issue Book ---");
        
        System.out.print("Enter Member ID: ");
        int memberId = scanner.nextInt();
        System.out.print("Enter Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();
        
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);
        
        if (member == null || book == null) {
            System.out.println("Invalid member or book ID!");
            return;
        }
        
        if (book.getAvailableQuantity() <= 0) {
            System.out.println("Book not available!");
            return;
        }
        
        Transaction t = new Transaction();
        t.setId(transactionIdCounter++);
        t.setBookId(bookId);
        t.setMemberId(memberId);
        t.setBorrowDate(LocalDate.now());
        t.setDueDate(LocalDate.now().plusDays(14));
        t.setStatus("ISSUED");
        
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        transactions.add(t);
        
        System.out.println("Book issued successfully! Transaction ID: " + t.getId());
        System.out.println("Return by: " + t.getDueDate());
    }

    private static void returnBook() {
        System.out.println("\n--- Return Book ---");
        System.out.print("Enter Transaction ID: ");
        int transId = scanner.nextInt();
        scanner.nextLine();
        
        Transaction t = findTransactionById(transId);
        if (t == null || !t.getStatus().equals("ISSUED")) {
            System.out.println("Invalid transaction!");
            return;
        }
        
        t.setReturnDate(LocalDate.now());
        t.setStatus("RETURNED");
        
        Book book = findBookById(t.getBookId());
        if (book != null) {
            book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        }
        
        // Calculate fine if late
        if (t.getReturnDate().isAfter(t.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(t.getDueDate(), t.getReturnDate());
            double fine = daysLate * 5; // ₹5 per day
            t.setFine(fine);
            System.out.println("Book returned late! Fine: ₹" + fine);
        } else {
            System.out.println("Book returned successfully!");
        }
    }

    private static void viewAllTransactions() {
        System.out.println("\n--- All Transactions ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private static Member findMemberById(int id) {
        for (Member m : members) {
            if (m.getId() == id) return m;
        }
        return null;
    }

    private static Book findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) return b;
        }
        return null;
    }

    private static Transaction findTransactionById(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    private static void addSampleData() {
        // Add sample books
        Book b1 = new Book();
        b1.setId(bookIdCounter++);
        b1.setTitle("Java Programming");
        b1.setAuthor("John Doe");
        b1.setIsbn("123-456");
        b1.setPublisher("Tech Pub");
        b1.setQuantity(5);
        b1.setAvailableQuantity(5);
        books.add(b1);
        
        Book b2 = new Book();
        b2.setId(bookIdCounter++);
        b2.setTitle("Python Basics");
        b2.setAuthor("Jane Smith");
        b2.setIsbn("789-012");
        b2.setPublisher("Code Pub");
        b2.setQuantity(3);
        b2.setAvailableQuantity(3);
        books.add(b2);
        
        // Add sample members
        Member m1 = new Member();
        m1.setId(memberIdCounter++);
        m1.setName("Alice Johnson");
        m1.setEmail("alice@email.com");
        m1.setPhone("1234567890");
        m1.setMembershipDate(LocalDate.now());
        m1.setActive(true);
        members.add(m1);
        
        Member m2 = new Member();
        m2.setId(memberIdCounter++);
        m2.setName("Bob Wilson");
        m2.setEmail("bob@email.com");
        m2.setPhone("9876543210");
        m2.setMembershipDate(LocalDate.now());
        m2.setActive(true);
        members.add(m2);
    }
}

class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int quantity;
    private int availableQuantity;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + title + " by " + author + " | Available: " + availableQuantity + "/" + quantity;
    }
}

class Member {
    private int id;
    private String name;
    private String email;
    private String phone;
    private LocalDate membershipDate;
    private boolean isActive;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public LocalDate getMembershipDate() { return membershipDate; }
    public void setMembershipDate(LocalDate membershipDate) { this.membershipDate = membershipDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    @Override
    public String toString() {
        return "ID: " + id + " | " + name + " | " + email + " | Member since: " + membershipDate;
    }
}

class Transaction {
    private int id;
    private int bookId;
    private int memberId;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double fine;
    private String status;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public double getFine() { return fine; }
    public void setFine(double fine) { this.fine = fine; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Trans ID: " + id + " | Book: " + bookId + " | Member: " + memberId + 
               " | Status: " + status + " | Fine: ₹" + fine;
    }
}