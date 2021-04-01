package com.mediscreen.notes.service;

import com.mediscreen.notes.model.Note;

import java.util.List;

public interface NoteService {
    /**
     * find list of all notes
     * @return list of notes
     */
    public List<Note> findAllNotes();

    /**
     * find a note by its's ID
     * @param id id of the note to find
     * @return the note ask
     */
    public Note findNoteById(String id);


    /**
     * Score calculation with list of trigger words
     * @param patientId patient id
     * @param triggers trigger lit of specific words
     * @return score
     */
    public int getScoreByTriggers(long patientId, List<String> triggers);

    /**
     * Find a list of note for a patient
     * @param patientId patient id to get the list of notes
     * @return list of note for the patient ask
     */
    public List<Note> findNoteByPatientId(long patientId);

    /**
     * add a new  note for a patient
     * @param note note for the patient. Id (of note) must be null
     * @return not inserted with id note
     */
    public Note addWithPatientId(Note note);

    /**
     * Update a note for a patient
     * @param note note to be updated
     * @return true if update is ok, false if erreor (couple idnote/idpatient not correct)
     */
    public boolean updateNote(Note note);

    /**
     * Delete an note for a patient
     * @param note Note to be deleted
     * @return  true if delete is ok
     */
    public boolean deleteNote(Note note);
}
