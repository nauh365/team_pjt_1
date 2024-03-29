package com.likelion.sixsenses.book.controller;

import com.likelion.sixsenses.book.dto.response.BookDetailDto;
import com.likelion.sixsenses.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public String bookDetail(@PathVariable("bookId") long bookId, Model model){
        model.addAttribute("book", bookService.getBookDetail(bookId));
        return "book-detail";
    }

    @PostMapping("/reserve/{bookId}")
    public String bookReserve(@PathVariable("bookId") long bookId, Model model){
        bookService.borrowBook(bookId);
        return String.format("redirect:/books/%d", bookId);
    }
}
