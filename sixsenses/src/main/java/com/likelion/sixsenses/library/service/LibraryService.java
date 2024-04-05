package com.likelion.sixsenses.library.service;

import com.likelion.sixsenses.library.dto.request.UserLocationInfo;
import com.likelion.sixsenses.library.dto.response.LibraryInfoDto;
import com.likelion.sixsenses.library.entity.Library;
import com.likelion.sixsenses.library.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryInfoDto getLibrary(long libraryId){
        Library library = libraryRepository.findById(libraryId).orElseThrow();
        return LibraryInfoDto.from(library);
    }

    public List<LibraryInfoDto> findNearLibrary(String bookISBN, UserLocationInfo userLocationInfo){
        List<Library> libraries = libraryRepository.findAllNearLibrary(bookISBN);

        Collections.sort(libraries, (a, b) -> Double.compare(getDistance(userLocationInfo, a), getDistance(userLocationInfo,b)));

        return libraries.stream().map(library -> LibraryInfoDto.from(library)).toList();
    }
    public double getDistance(UserLocationInfo userLocationInfo, Library library){
        double userLocationX = Double.parseDouble(userLocationInfo.getLocationX());
        double userLocationY = Double.parseDouble(userLocationInfo.getLocationY());
        double libraryLocationX = Double.parseDouble(library.getLocationX());
        double libraryLocationY = Double.parseDouble(library.getLocationY());

        return Math.sqrt(Math.pow(userLocationX - libraryLocationX, 2) + Math.pow(userLocationY - libraryLocationY, 2));
    }
}
