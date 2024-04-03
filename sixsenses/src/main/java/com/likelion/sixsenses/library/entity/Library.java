package com.likelion.sixsenses.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long libraryId;

    private String libraryImg;
    private String libraryName;
    private String libraryAddress;
    private String locationX;
    private String locationY;

    public Library(long libraryId){
        this.libraryId = libraryId;
    }
}
