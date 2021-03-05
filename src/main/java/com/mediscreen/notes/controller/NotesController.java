package com.mediscreen.notes.controller;

import com.mediscreen.notes.controller.exception.NoteCanNotBeDeletedException;
import com.mediscreen.notes.controller.exception.NoteCanNotBeSavedException;
import com.mediscreen.notes.controller.exception.NoteCanNotbeAddedException;
import com.mediscreen.notes.controller.exception.NoteIdNotFoundException;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.model.external.Patient;
import com.mediscreen.notes.proxies.PatientProxy;
import com.mediscreen.notes.service.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class NotesController {
    private static final Logger logger = LogManager.getLogger(NotesController.class);
    @Autowired
    private NoteService noteService;

    @Autowired
    private PatientProxy patientProxy;
    /*---------------------------  GET all notes -----------------------------*/

    /**
     * Find all notes in data base
     * @return list of notes
     */
    @GetMapping(value = "patHistories")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getAllNotes()  {
        logger.info("getAllNotes start/finish");
        return noteService.findAllNotes();
    }

    /*---------------------------  GET note by id note -----------------------------*/

    /**
     * GET note by id note
     * @param id note id
     * @return note
     * @throws NoteIdNotFoundException Error if note id doesn't exist
     */
    @GetMapping(value = "patHistory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNoteById(@PathVariable String id) throws NoteIdNotFoundException {
        logger.info("getNoteById start");
        Note finalResult = null;
        String errorMessage = null;
        finalResult = noteService.findNoteById(id);
        if (finalResult ==null) {
            errorMessage = "The note id " + id + " doesn't exist";
            logger.error(errorMessage);
            throw new NoteIdNotFoundException(errorMessage);
        }
        logger.info("getNoteById finish");
        return finalResult;
    }

    /*---------------------------  GET note by patient id -----------------------------*/

    /**
     * GET note by patient id
     * @param patientId patient id
     * @return list of note
     * @throws NoteIdNotFoundException Error if patient id doesn't exist
     */
    @GetMapping(value = "patientpatHistories/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getNoteByPatientId(@PathVariable Long patientId) throws NoteIdNotFoundException {
        logger.info("getNoteByPatientId start");
        List<Note> finalResult = null;
        String errorMessage = null;
        finalResult = noteService.findNoteByPatientId(patientId);
        if (finalResult.size() == 0) {
            errorMessage = "no note for patient " + patientId.toString() + " exist";
            logger.error(errorMessage);
            throw new NoteIdNotFoundException(errorMessage);
        }
        logger.info("getNoteByPatientId finish");
        return finalResult;
    }
    /*---------------------------  GET score not by trigger list -----------------------------*/
//TODO javadoc + controle sur patientID + exception

    //@GetMapping(value = "patHistories/triggers/{patientId}")
    @RequestMapping(value = "patHistories/triggers/{patientId}")
    @ResponseStatus(HttpStatus.OK)
    public int getScoreByPatientIdAndTriggers(@PathVariable Long patientId,@RequestBody List<String> triggers) {
        logger.info("getScoreByTriggers start");
        int finalResult = 0;
        finalResult = noteService.getScoreByTriggers(patientId,triggers);

        logger.info("getScoreByTriggers finish");
        return finalResult;
    }

    /*---------------------------  POST note -----------------------------*/

    /**
     * Add a note
     * @param note note to be added. noteId must be null
     * @return message with noteId
     * @throws NoteCanNotbeAddedException Error if not added
     */
    @PostMapping(value = "patHistory/add")
    @ResponseStatus(HttpStatus.CREATED)
    public String addNote(@RequestBody Note note) throws NoteCanNotbeAddedException {
        String finalResult = null;
        String errorMessage = null;
        logger.info("addNote start");

        //check if patient ID exist
        try {
            if (note.getPatientId() != null ) {
                Patient patient=patientProxy.getPatientById(note.getPatientId());
                if (patient != null){
                    if (note.getId()==null) {
                        Note result = noteService.addWithPatientId(note);
                        if (result != null) {
                            finalResult = "The note id " + result.getId() + " for the patientId " + result.getPatientId().toString() + " has been created";
                        } else {
                            errorMessage = "The note for patient " + note.getPatientId().toString() + " not created for the text " + note.getTextNote();
                        }
                    } else {
                        errorMessage = "id is autogenerate, don't give a value for adding in data base";
                    }
                } else {
                    errorMessage = "patient id" + note.getPatientId() + "doesn't exist";
                }
            } else {
                errorMessage = "patient id must not be null";
            }
        } catch (Exception e) {
            errorMessage = "Error to get patient id" + note.getPatientId() ;
            logger.error(errorMessage);
            throw new NoteCanNotbeAddedException(errorMessage);
        }


        if (finalResult ==null) {
            logger.error(errorMessage);
            throw new NoteCanNotbeAddedException(errorMessage);
        }
        return finalResult;
    }

    /*---------------------------  PUT note -----------------------------*/

    /**
     * Update a note
     * @param note note to be updated
     * @return message with noteId updated
     * @throws NoteCanNotBeSavedException Error if update is incorrect
     */
    @PutMapping(value = "patHistory")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String saveNote(@RequestBody Note note) throws NoteCanNotBeSavedException {
        String finalResult = null;
        String errorMessage = null;
        logger.info("saveNote start");
        if (note.getId()!=null) {
          if (noteService.updateNote(note)){
              finalResult = "Note id " + note.getId() +" is updated";
              logger.info(finalResult);
          } else {
              errorMessage = "Note id " + note.getId() +" can not be updated";
              logger.error(errorMessage);
          }
        } else {
            errorMessage = "note id must not be null";
        }
        if (finalResult ==null) {
            logger.error(errorMessage);
            throw new NoteCanNotBeSavedException(errorMessage);
        }
        return finalResult;
    }

    /*---------------------------  DELETE note -----------------------------*/

    /**
     * Delete a note
     * @param note note to be deleted
     * @return message with noteId delete
     * @throws NoteCanNotBeDeletedException Error if delete is impossible
     */
    @DeleteMapping(value = "patHistory")
    @ResponseStatus(HttpStatus.OK)
    public String deleteNote(@RequestBody Note note) throws NoteCanNotBeDeletedException {
        String finalResult = null;
        String errorMessage = null;
        logger.info("saveNote start");
        if (note.getId()!=null) {
            if (noteService.deleteNote(note)){
                finalResult = "Note id " + note.getId() +" is deleted";
                logger.info(finalResult);
            } else {
                errorMessage = "Note id " + note.getId() +" can not be deleted";
                logger.error(errorMessage);
            }
        } else {
            errorMessage = "note id must not be null";
        }
        if (finalResult ==null) {
            logger.error(errorMessage);
            throw new NoteCanNotBeDeletedException(errorMessage);
        }
        return finalResult;
    }
}
