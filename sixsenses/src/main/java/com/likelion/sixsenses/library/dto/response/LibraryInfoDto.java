package com.likelion.sixsenses.library.dto.response;

import com.likelion.sixsenses.library.entity.Library;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LibraryInfoDto {
    private String libraryImg;
    private String libraryName;
    private String libraryAddress;
    private String locationX;
    private String locationY;

    @Builder
    public LibraryInfoDto(Library library){
        this.libraryImg = library.getLibraryImg();
        this.libraryName = library.getLibraryName();
        this.libraryAddress = library.getLibraryAddress();
        this.locationX = library.getLocationX();
        this.locationY = library.getLocationY();
    }

    public static LibraryInfoDto from(Library library){
        return LibraryInfoDto.builder().library(library).build();
    }
}
