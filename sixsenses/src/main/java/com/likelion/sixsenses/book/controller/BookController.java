package com.likelion.sixsenses.book.controller;

import com.likelion.sixsenses.book.service.BookService;
import com.likelion.sixsenses.entity.CustomUserDetails;
import com.likelion.sixsenses.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public String bookReserve(@PathVariable("bookId") long bookId, UsernamePasswordAuthenticationToken user){
        bookService.borrowBook(bookId, ((CustomUserDetails) user.getPrincipal()).getId());
        return String.format("redirect:/books/%d", bookId);
    }
}
