package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;

import java.util.List;

public interface NoteService {
    public List<Note> findAllNotes();
    public Note findNoteById(String id);
    public List<Note> findNoteByPatientId(long patientId);
    public Note addWithPatientId(Note note);
    public boolean updateNote(Note note);
    public boolean deleteNote(Note note);
}
