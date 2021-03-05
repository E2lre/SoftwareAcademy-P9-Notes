package com.mediscreen.notes.dao;

import com.mediscreen.notes.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteDao extends MongoRepository<Note, String> {

    Note findNoteById (String id);
    List<Note> findNoteByPatientId (Long patientId);
    List<Note> findNotesByTextNoteContainsIgnoreCaseAndPatientId (String trigger, long patientId);


}
