package com.mediscreen.notes.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NotesController {
    private static final Logger logger = LogManager.getLogger(NotesController.class);
    /*---------------------------  GET Find All -----------------------------*/
    @GetMapping(value = "notes")
    @ResponseStatus(HttpStatus.OK)
    public boolean listNotes()  {    // TODO A CHANGER
        logger.info("listNotes start/finish");
        return true;

    }
}
