package com.mediscreen.notes.service;

import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServceImpl implements NoteService {
    @Autowired
    private NoteDao noteDao;

    @Override
    public Note findNoteById(Long id) {
        return noteDao.findNoteById(id);
    }

    @Override
    public List<Note> findNoteByPatientId(long patientId) {
        return noteDao.findNoteByPatientId(patientId);
    }

    @Override
    public Note addWithPatientId(Note note) {
        //TODO Ajouter les controles
        return noteDao.save(note);
    }
}
