package com.mediscreen.notes.controller.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoteIdNotFoundException extends Exception {
    private static final Logger logger = LogManager.getLogger(NoteIdNotFoundException.class);
    public NoteIdNotFoundException(String s) {
        super(s);
        logger.error(s);
    }
}
