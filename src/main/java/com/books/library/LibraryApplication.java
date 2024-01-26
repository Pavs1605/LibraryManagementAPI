package com.books.library;

import com.books.library.model.Book;
import com.books.library.model.BorrowingRecord;
import com.books.library.model.Patron;
import com.books.library.model.enums.BookState;
import com.books.library.repository.BookRepository;
import com.books.library.repository.BorrowingRecordRepository;
import com.books.library.repository.PatronRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
@Api( tags="Library Management API", description = "Gives list of API's needed to manage library")
public class LibraryApplication {
		@Autowired
	BorrowingRecordRepository borrowingRecordRepository;

		@Autowired
	PatronRepository patronRepository;

		@Autowired
	BookRepository bookRepository;
	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Bean
		public CommandLineRunner initialCreate() {
		return (args) -> {
			Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925L, "978-3-16-148410-0", 123456L, null);
			Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960L, "978-0-06-112008-4", 789012L, "Mockingbird");
			Book book3 = new Book("1984", "George Orwell", 1949L, "978-0-452-28423-4", 456789L, "Dystopian classic");
			Book book4 = new Book("The Great Gatsby 2", "F. Scott Walter", 1925L, "978-0-7432-7356-5", 987654L, "Jazz Age novel");
			Book book5 = new Book("Pride and Prejudice", "Jane Austen", 1813L, "978-0-14-143951-8", 567890L, "Classic romance");
			Book book6 = new Book("The Catcher in the Rye", "J.D. Salinger", 1951L, "978-0-316-76948-0", 234567L, "Coming of age novel");
			Book book7 = new Book("Moby-Dick", "Herman Melville", 1851L, "978-0-06-287067-1", 123456L, "Whaling adventure");
			Book book8 = new Book("The Hobbit", "J.R.R. Tolkien", 1937L, "978-0-618-00221-4", 876543L, "Fantasy adventure");
			Book book9 = new Book("Jane Eyre", "Charlotte Brontë", 1847L, "978-0-14-243720-9", 345678L, "Gothic romance");
			Book book10 = new Book("Brave New World", "Aldous Huxley", 1932L, "978-0-06-085052-4", 890123L, "Dystopian novel");
			Book book11 = new Book("The Odyssey", "Homer", 1997L, "1221-2331-111", 012345L, "Epic poem");
			bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10, book11));


			Patron patron1 = new Patron("John Doe", "+123456789", "john.doe@example.com", "123 Main Street, City");
			Patron patron2 = new Patron("Alice Smith", "+987654321", "alice.smith@example.com", "456 Oak Avenue, Town");
			Patron patron3 = new Patron("Bob Johnson", "+1122334455", "bob.johnson@example.com", "789 Pine Road, Village");
			Patron patron4 = new Patron("Eva Brown", "+999888777", "eva.brown@example.com", "101 Elm Street, Suburb");
			Patron patron5 = new Patron("Michael White", "+777666555", "michael.white@example.com", "202 Cedar Lane, District");
			Patron patron6 = new Patron("Samantha Black", "+444333222", "samantha.black@example.com", "303 Birch Boulevard, County");
			Patron patron7 = new Patron("Charlie Green", "+555444333", "charlie.green@example.com", "404 Maple Court, State");
			Patron patron8 = new Patron("Olivia Grey", "+123123123", "olivia.grey@example.com", "505 Redwood Drive, Province");
			Patron patron9 = new Patron("Daniel Gold", "+789789789", "daniel.gold@example.com", "606 Spruce Lane, Territory");
			Patron patron10 = new Patron("Sophia Silver", "+456456456", "sophia.silver@example.com", "707 Sycamore Street, Region");
			patronRepository.saveAll(Arrays.asList(patron1, patron2, patron3, patron4, patron5, patron6, patron7, patron8, patron9, patron10));

			BorrowingRecord record1 = new BorrowingRecord(book1, patron1, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 17), BookState.RETURNED);
			BorrowingRecord record2 = new BorrowingRecord(book1, patron1, LocalDate.of(2024, 1, 20), null, BookState.BORROWED);
			BorrowingRecord record3 = new BorrowingRecord(book2, patron2, LocalDate.of(2024, 1, 12), null, BookState.BORROWED);
			BorrowingRecord record4 = new BorrowingRecord(book3, patron3, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 22), BookState.RETURNED);
			BorrowingRecord record5 = new BorrowingRecord(book4, patron3, LocalDate.of(2024, 1, 25), null, BookState.BORROWED);
			BorrowingRecord record6 = new BorrowingRecord(book5, patron4, LocalDate.of(2024, 1, 18), LocalDate.of(2024, 1, 18), BookState.RETURNED);
			BorrowingRecord record7 = new BorrowingRecord(book6, patron5, LocalDate.of(2024, 1, 20), null, BookState.BORROWED);

			BorrowingRecord record8 = new BorrowingRecord(book7, patron6, LocalDate.of(2024, 1, 22), LocalDate.of(2024, 1, 29), BookState.RETURNED);
			BorrowingRecord record9 = new BorrowingRecord(book8, patron6, LocalDate.of(2024, 1, 25), null, BookState.BORROWED);
			BorrowingRecord record10 = new BorrowingRecord(book9, patron7, LocalDate.of(2024, 1, 10), LocalDate.of(2024, 1, 17), BookState.RETURNED);
			BorrowingRecord record11 = new BorrowingRecord(book10, patron7, LocalDate.of(2024, 1, 20), LocalDate.of(2024, 1, 27), BookState.RETURNED);
			BorrowingRecord record12 = new BorrowingRecord(book11, patron7, LocalDate.of(2024, 1, 30), null, BookState.BORROWED);
			//BorrowingRecord record13 = new BorrowingRecord(book11, patron8, LocalDate.of(2023, 12, 15), null, BookState.BORROWED);
			BorrowingRecord record15 = new BorrowingRecord(book2, patron10, LocalDate.of(2024, 1, 26), LocalDate.of(2024, 1, 26), BookState.RETURNED);
			borrowingRecordRepository.saveAll(Arrays.asList(record1, record2, record3, record4, record5, record6, record7, record8, record9, record10, record11, record12, record15));

		};
	}


}
