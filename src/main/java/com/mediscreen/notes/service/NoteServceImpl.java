package com.mediscreen.notes.service;

import com.mediscreen.notes.controller.NotesController;
import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServceImpl implements NoteService {
    private static final Logger logger = LogManager.getLogger(NoteServceImpl.class);
    @Autowired
    private NoteDao noteDao;

    @Override
    public List<Note> findAllNotes() {
        return noteDao.findAll();
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
    public Note addWithPatientId(Note note) {
        //TODO Ajouter les controles
        Note noteToInsert = new Note();
        noteToInsert.setDateNote(note.getDateNote());
        noteToInsert.setPatientId(note.getPatientId());
        noteToInsert.setTextNote(note.getTextNote());
        return noteDao.insert(note);
    }

    @Override
    public boolean updateNote(Note note) {
        boolean result = false;
        Note resultNote = noteDao.findNoteById(note.getId());
        if ((resultNote != null) && (resultNote.getPatientId().equals(note.getPatientId())))  {
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
