package com.app.library.payload.request;

import com.app.library.model.BookItem;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class RentBookRequest {

    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @NotNull
    private BookItem bookItem;
    @NotNull
    private Long userId;

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public Long getUserId() {
        return userId;
    }
}
