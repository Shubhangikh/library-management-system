package com.utd.core.library.controller;

import com.utd.core.library.formobject.RequestForm;
import com.utd.core.library.formobject.ResponseForm;
import com.utd.core.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LibraryController {
    @Autowired
    LibraryService libraryService;
    @RequestMapping(value = "/library.action", method = RequestMethod.GET)
    public String loginPage() {
        return "library";
    }

    @RequestMapping(value = "register.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm register(@RequestBody RequestForm requestForm) {
        return libraryService.register(requestForm);
    }

    @RequestMapping(value = "searchbooks.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm searchBooks(@RequestBody RequestForm requestForm) {
        return libraryService.searchBooks(requestForm);
    }
    @RequestMapping(value = "bookcheckout.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm bookCheckOut(@RequestBody RequestForm requestForm) {
        return libraryService.bookCheckOut(requestForm);
    }
    @RequestMapping(value = "checkinbookslist.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm checkInBooksList(@RequestBody RequestForm requestForm) {
        return libraryService.checkInBooksList(requestForm);
    }
    @RequestMapping(value = "bookcheckin.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm checkInBook(@RequestBody RequestForm requestForm) {
        return libraryService.checkInBook(requestForm);
    }
    @RequestMapping(value = "updatefineamounts.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm updateFineAmounts() {
        return libraryService.updateFineAmounts();
    }
    @RequestMapping(value = "borrowerfineamounts.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm getBorrowerFineAmountsList(@RequestBody RequestForm requestForm) {
        return libraryService.getBorrowerFineAmountsList(requestForm);
    }
    @RequestMapping(value = "payfine.action", method = RequestMethod.POST)
    @ResponseBody
    public ResponseForm payFine(@RequestBody RequestForm requestForm) {
        return libraryService.payFine(requestForm);
    }


}
