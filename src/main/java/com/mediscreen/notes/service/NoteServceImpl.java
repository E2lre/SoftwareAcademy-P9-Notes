package com.mediscreen.notes.service;

import com.mediscreen.notes.controller.NotesController;
import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteServceImpl implements NoteService {
    private static final Logger logger = LogManager.getLogger(NoteServceImpl.class);
    @Autowired
    private NoteDao noteDao;

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public List<Note> findAllNotes() {
        List<Note> resulListNote = noteDao.findAll();
        return resulListNote;
    }

    @Override
    public Note findNoteById(String id) {
        return noteDao.findNoteById(id);
    }

    @Override
    public List<Note> findNoteByPatientId(long patientId) {
        return noteDao.findNoteByPatientId(patientId);
    }

    @Override
    public int getScoreByTriggers(long patientId,List<String> triggers) {
        int result = 0;
        for (String trigger: triggers) {
            result = result + noteDao.findNotesByTextNoteContainsIgnoreCaseAndPatientId(trigger,patientId).size();
        }
        return result;
    }


    @Override
    public Note addWithPatientId(Note note) {
        //TODO Ajouter les controles
        Note noteToInsert = new Note();
        //noteToInsert.setDateNote(note.getDateNote()); //DateNote is current day
        noteToInsert.setDateNote(LocalDate.now()); // Date is the corrent day, input value is ignored
        noteToInsert.setPatientId(note.getPatientId());
        noteToInsert.setTextNote(note.getTextNote());
        //return noteDao.insert(note);
        return noteDao.insert(noteToInsert);
    }

    @Override
    public boolean updateNote(Note note) {
        boolean result = false;
        Note resultNote = noteDao.findNoteById(note.getId());
        if ((resultNote != null) && (resultNote.getPatientId().equals(note.getPatientId())))  {
            note.setDateNote(LocalDate.now()); // Date is the current day, input value is ignored
            noteDao.save(note);
            result = true;
            logger.info("Note id " + note.getId() + " is updated");
        } else {
            logger.info("Note id " + note.getId() + " doesn't exist or patient id " + note.getPatientId() + " is incorrect");
        }
        return result;
    }

    @Override
    public boolean deleteNote(Note note) {
        boolean result = false;
        Note resultNote = noteDao.findNoteById(note.getId());
        if ((resultNote != null) && (resultNote.getPatientId().equals(note.getPatientId())))  {
            noteDao.delete(note);
            result = true;
            logger.info("Note id " + note.getId() + " is deleted");
        } else {
            logger.info("Note id " + note.getId() + " doesn't exist or patient id " + note.getPatientId() + " is incorrect");
        }
        return result;
    }
}
