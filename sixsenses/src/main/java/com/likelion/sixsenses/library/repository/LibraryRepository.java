package com.likelion.sixsenses.library.repository;

import com.likelion.sixsenses.library.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Long> {
//    @Query("select li from Library li inner join Book b on li.libraryId = b.libraryId.libraryId and b.bookISBN = :bookISBN and b.quantity > 0")
//    List<Library> findAllNearLibrary(String bookISBN);

    @Query("select li from Library li inner join Book b on li.libraryId = b.libraryId.libraryId and b.bookISBN = :bookISBN and b.quantity > 0")
    List<Library> findAllNearLibrary(@Param("bookISBN") String bookISBN);
}
