package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;

import java.util.List;

public interface NoteService {
    public Note findNoteById(Long id);
    public List<Note> findNoteByPatientId(long patientId);
    public Note addWithPatientId(Note note);

}
