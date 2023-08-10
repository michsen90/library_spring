package com.app.library.service;

import com.app.library.model.RentBook;
import com.app.library.payload.request.RentBookRequest;
import com.app.library.repository.RentBookRepository;
import com.app.library.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RentBookService {

    private RentBookRepository rentBookRepository;
    private UserRepository userRepository;

    RentBookService(final RentBookRepository rentBookRepository, final UserRepository userRepository) {
        this.rentBookRepository = rentBookRepository;
        this.userRepository = userRepository;
    }

    public RentBook makeRentBook(RentBook rentBook){
        RentBook rentedBook = rentBookRepository.save(rentBook);
        return rentedBook;
    }
}
