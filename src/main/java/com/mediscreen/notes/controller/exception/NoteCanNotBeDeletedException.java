package com.mediscreen.notes.controller.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NoteCanNotBeDeletedException extends Exception {
    private static final Logger logger = LogManager.getLogger(NoteCanNotBeDeletedException.class);
    public NoteCanNotBeDeletedException(String s) {
        super(s);
        logger.error(s);
    }
}
