package com.lenda.marc.repository;

import com.lenda.marc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitle(String title);
}
