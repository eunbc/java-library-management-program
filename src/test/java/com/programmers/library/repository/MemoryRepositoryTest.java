package com.programmers.library.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.programmers.library.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class MemoryRepositoryTest {

	private MemoryRepository repository;

	@BeforeEach
	public void setUp() {
		repository = new MemoryRepository();
	}

	@Test
	public void testSave() {
		//given
		Book book = new Book("Test Book", "Test Author", 111L);

		//when
		Book savedBook = repository.save(book);

		//then
		assertNotNull(savedBook.getId());
		assertEquals("Test Book", savedBook.getTitle());
	}

	@Test
	public void testFindAll() {
		Book book1 = new Book("Test Book1", "Test Author", 111L);
		Book book2 = new Book("Test Book2", "Test Author", 111L);
		repository.save(book1);
		repository.save(book2);

		List<Book> allBooks = repository.findAll();

		assertEquals(2, allBooks.size());
	}

	@Test
	public void testFindById() {
		Book book = new Book("Test Book", "Test Author", 111L);
		Book savedBook = repository.save(book);

		Optional<Book> foundBook = repository.findById(savedBook.getId());

		assertTrue(foundBook.isPresent());
		assertEquals(savedBook.getId(), foundBook.get().getId());
	}

	@Test
	public void testFindByTitleLike() {
		Book book1 = new Book("Java Programming", "Test Author", 111L);
		Book book2 = new Book("Python Programming", "Test Author", 111L);
		repository.save(book1);
		repository.save(book2);

		List<Book> javaBooks = repository.findByTitleLike("Java");

		assertEquals(1, javaBooks.size());
		assertEquals("Java Programming", javaBooks.get(0).getTitle());
	}

	@Test
	public void testDeleteById() {
		Book book = new Book("Test Book", "Test Author", 111L);
		Book savedBook = repository.save(book);

		repository.deleteById(savedBook.getId());

		Optional<Book> foundBook = repository.findById(savedBook.getId());
		assertFalse(foundBook.isPresent());
	}
}