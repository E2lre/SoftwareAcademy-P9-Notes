package com.mediscreen.notes.controller;

import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NotesController {
    private static final Logger logger = LogManager.getLogger(NotesController.class);
    @Autowired
    private NoteService noteService;
    /*---------------------------  GET by id note -----------------------------*/
    @GetMapping(value = "note/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable long id)  {
        logger.info("listNotes start/finish");
        return noteService.findNoteById(id);
    }
    /*---------------------------  POST Patient -----------------------------*/
    @PostMapping(value = "note")
    @ResponseStatus(HttpStatus.CREATED)
    public Note addNote(@RequestBody Note note) {
        Note finalResult;
        logger.info("addNote start");
        Note result = noteService.addWithPatientId(note);
        //TODO ajouter les controles
/*        if (result) {
            finalResult = "The patient " + patient.getId() + " has been created";
        } else {
            finalResult = "The patient " + patient.getId() + " already exist";
            logger.warn(finalResult);
            throw new PatientCanNotbeAddedException("The patient " + patient.getId() + " already exist");
        }*/
        return result;
    }
}
