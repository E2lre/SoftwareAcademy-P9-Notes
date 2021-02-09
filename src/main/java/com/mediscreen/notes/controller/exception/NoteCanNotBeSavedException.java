package com.mediscreen.notes.controller.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_MODIFIED)
public class NoteCanNotBeSavedException extends Exception {
    private static final Logger logger = LogManager.getLogger(NoteCanNotBeSavedException.class);
    public NoteCanNotBeSavedException(String s) {
        super(s);
        logger.error(s);
    }
}
