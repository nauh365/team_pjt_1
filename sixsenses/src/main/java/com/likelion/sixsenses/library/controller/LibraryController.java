package com.likelion.sixsenses.library.controller;

import com.likelion.sixsenses.library.dto.request.UserLocationInfo;
import com.likelion.sixsenses.library.dto.response.LibraryInfoDto;
import com.likelion.sixsenses.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;
    @GetMapping("/map")
    public String getLibrary(){
        return "near-library.html";
    }

    @PostMapping("/{bookISBN}")
    @ResponseBody
    public List<LibraryInfoDto> findLibrary(@PathVariable("bookISBN") String bookISBN, @RequestBody UserLocationInfo userLocationInfo){
        return libraryService.findNearLibrary(bookISBN, userLocationInfo);
    }

    @GetMapping("/{ISBN}")
    public String getNearLibrary(@PathVariable("ISBN") String isbn){
        return "near-library.html";
    }
}
