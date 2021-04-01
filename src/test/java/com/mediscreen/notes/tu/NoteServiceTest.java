package com.mediscreen.notes.tu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NoteServiceTest {
    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteDao noteDao;

    private Note note;

    String noteIdConst = "abc123";
    Long notePatientIdConst = 123l;
    String noteTextConst = "AZERTYIOP";
    String noteTextConst2 = "QWERTYIOP";
    LocalDate noteDate;
    String inexistingNoteIdConst = "zzz999";
    Long incorrectNotePatientIdConst = 999l;



    @BeforeEach
    public void setUpEach() {
        note = new Note();
        note.setId(noteIdConst);
        note.setPatientId(notePatientIdConst);
        note.setTextNote(noteTextConst);
        noteDate = LocalDate.now();
        note.setDateNote(noteDate);
    }

    /*------------------------ findAll ---------------------------------*/

    @Test
    public void findAllNotes_WhenDBISNotEmpty_NoteLisIsReturn(){
        //GIVEN
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        Mockito.when(noteDao.findAll()).thenReturn(noteList);

        //WHEN
        List<Note> noteResultList =  noteService.findAllNotes();
        //THEN
        assertThat(noteResultList).isNotEmpty();
    }


    @Test
    public void findNoteById_existingNoteId_noteIsReturn(){
        //GIVEN
        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);

        //WHEN
        Note noteResult =  noteService.findNoteById(noteIdConst);
        //THEN
        assertThat(noteResult).isNotNull();
        assertThat(noteResult.getTextNote()).isEqualTo(noteTextConst);
    }
    @Test
    public void findNoteById_inexistingNoteId_errorIsReturn(){
        //GIVEN
        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(null);

        //WHEN
        Note noteResult =  noteService.findNoteById(inexistingNoteIdConst);
        //THEN
        assertThat(noteResult).isNull();
    }
    /*------------------------ findNoteByPatientId ---------------------------------*/
    @Test
    public void findNoteByPatientId_existingPatientId_noteListIsReturn(){
        //GIVEN
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        Mockito.when(noteDao.findNoteByPatientId(anyLong())).thenReturn(noteList);

        //WHEN
        List<Note> noteListResult =  noteService.findNoteByPatientId(notePatientIdConst);
        //THEN
        assertThat(noteListResult).isNotNull();
        assertThat(noteListResult.size()).isEqualTo(noteList.size());
    }

    @Test
    public void findNoteByPatientId_inexistingPatientId_errorIsReturn(){
        //GIVEN
        Mockito.when(noteDao.findNoteByPatientId(anyLong())).thenReturn(null);

        //WHEN
        List<Note> noteListResult =  noteService.findNoteByPatientId(notePatientIdConst);
        //THEN
        assertThat(noteListResult).isNull();
    }
    /*------------------------ getScoreByTriggers ---------------------------------*/
    @Test
    public void getScoreByTriggers_existingPatientId2Risk_2IsReturn(){
        //GIVEN
        List<String> triggers = new ArrayList<>();
        triggers.add(noteTextConst);
        triggers.add(noteTextConst2);
        List<Note> noteList = new ArrayList<>();
        noteList.add(note);
        Mockito.when(noteDao.findNotesByTextNoteContainsIgnoreCaseAndPatientId(anyString(),anyLong())).thenReturn(noteList);

        //WHEN
        int result =  noteService.getScoreByTriggers(notePatientIdConst,triggers);
        //THEN
        assertThat(result).isEqualTo(2);
    }
    @Test
    public void getScoreByTriggers_existingPatientIdNoRiskFound_0IsReturn(){
        //GIVEN
        List<String> triggers = new ArrayList<>();
        triggers.add(noteTextConst);
        triggers.add(noteTextConst2);
        List<Note> noteList = new ArrayList<>();

        Mockito.when(noteDao.findNotesByTextNoteContainsIgnoreCaseAndPatientId(anyString(),anyLong())).thenReturn(noteList);

        //WHEN
        int result =  noteService.getScoreByTriggers(notePatientIdConst,triggers);
        //THEN
        assertThat(result).isEqualTo(0);
    }
    @Test
    public void getScoreByTriggers_inexistingPatientId2Risk_0IsReturn(){
        //GIVEN
        List<String> triggers = new ArrayList<>();
        triggers.add(noteTextConst);
        triggers.add(noteTextConst2);
        List<Note> noteList = new ArrayList<>();

        Mockito.when(noteDao.findNotesByTextNoteContainsIgnoreCaseAndPatientId(anyString(),anyLong())).thenReturn(noteList);

        //WHEN
        int result =  noteService.getScoreByTriggers(notePatientIdConst,triggers);
        //THEN
        assertThat(result).isEqualTo(0);
    }
    /*------------------------ addWithPatientId ---------------------------------*/
    @Test
    public void addWithPatientId_inexistingNotePatientIdGiven_noteIsCreated(){
        //GIVEN
        Mockito.when(noteDao.insert(any(Note.class))).thenReturn(note);

        //WHEN
        Note noteResult =  noteService.addWithPatientId(note);
        //THEN
        assertThat(noteResult).isNotNull();
        assertThat(noteResult.getTextNote()).isEqualTo(note.getTextNote());
    }

    /*------------------------ updateNote ---------------------------------*/
    @Test
    public void updateNote_existingNotePatientIdAndCorrectNoteIdGiven_noteIsUpdated(){
        //GIVEN
        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);
        Mockito.when(noteDao.save(any(Note.class))).thenReturn(note);

        //WHEN
        boolean result =  noteService.updateNote(note);
        //THEN
        assertThat(result).isTrue();
    }
    @Test
    public void updateNote_inexistingNotePatientIdAndCorrectNoteIdGiven_errorISReturn(){
        //GIVEN
        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(null);
        Mockito.when(noteDao.save(any(Note.class))).thenReturn(note);

        //WHEN
        boolean result =  noteService.updateNote(note);
        //THEN
        assertThat(result).isFalse();
    }
    @Test
    public void updateNote_existingNotePatientIdAndIncorrectNoteIdGiven_errorISReturn(){
        //GIVEN
        Note incorrectNote = new Note(noteIdConst,incorrectNotePatientIdConst,noteTextConst,noteDate);

        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(incorrectNote);
        Mockito.when(noteDao.save(any(Note.class))).thenReturn(note);

        //WHEN
        boolean result =  noteService.updateNote(note);
        //THEN
        assertThat(result).isFalse();
    }

    /*------------------------ deleteNote ---------------------------------*/
    @Test
    public void deleteNote_existingNotePatientIdAndCorrectNoteIdGiven_noteIsUpdated(){
        //GIVEN
        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(note);
        Mockito.doNothing().when(noteDao).delete(any(Note.class));

        //WHEN
        boolean result =  noteService.deleteNote(note);
        //THEN
        assertThat(result).isTrue();
    }
    @Test
    public void deleteNote_inexistingNotePatientIdAndCorrectNoteIdGiven_errorISReturn(){
        //GIVEN
        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(null);
        Mockito.doNothing().when(noteDao).delete(any(Note.class));

        //WHEN
        boolean result =  noteService.deleteNote(note);
        //THEN
        assertThat(result).isFalse();
    }
    @Test
    public void deleteNote_existingNotePatientIdAndIncorrectNoteIdGiven_errorISReturn(){
        //GIVEN
        Note incorrectNote = new Note(noteIdConst,incorrectNotePatientIdConst,noteTextConst,noteDate);

        Mockito.when(noteDao.findNoteById(anyString())).thenReturn(incorrectNote);
        Mockito.doNothing().when(noteDao).delete(any(Note.class));

        //WHEN
        boolean result =  noteService.deleteNote(note);
        //THEN
        assertThat(result).isFalse();
    }


}
