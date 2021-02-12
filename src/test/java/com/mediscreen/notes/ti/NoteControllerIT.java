package com.mediscreen.notes.ti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@DataMongoTest
public class NoteControllerIT {
    private static final Logger logger = LogManager.getLogger(NoteControllerIT.class);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private NoteService noteService;

    private Note note;
    private List<Note> noteList;// = new ArrayList<>();


    String noteIdConst = "abc123";
    Long notePatientIdConst = 123l;
    String noteTextConst = "AZERTYIOP";
    LocalDate noteDate;
    String inexistingNoteIdConst = "zzz999";
    Long incorrectNotePatientIdConst = 999l;
    String noteDateConst ="01/01/2021";
    LocalDate noteLocalDateConst;
    String noteNewTextConst = "QWERTYIOP";

    @BeforeEach
    public void setup() throws Exception {
        mongoTemplate.dropCollection(Note.class);
        noteList = new ArrayList<>();
        noteDate = LocalDate.now();
        note = new Note(noteIdConst,notePatientIdConst,noteTextConst,noteDate);
        noteService.addWithPatientId(note);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        noteLocalDateConst = LocalDate.parse(noteDateConst,df);

    }

    /*---------------------------  GET all notes -----------------------------*/
    @Test
    public void listNotess_existingNotes_noteListIsDone() throws Exception {
        //Given

        //WHEN THEN

        //String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");
        mockMvc.perform(get("/patHistories"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    /*---------------------------  GET note by id note -----------------------------*/
    @Test
    public void getNoteById_existingNotes_noteIsDone() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = noteTempList.get(0);
        //WHEN THEN
        mockMvc.perform(get("/patHistory/"+noteTemp.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getNoteById_inexistingNotes_erreurIsDone() throws Exception {
        //Given
        //WHEN THEN
        mockMvc.perform(get("/patHistory/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    /*---------------------------  GET note by patient id -----------------------------*/
    @Test
    public void getNoteByPatientId_existingPatientId_noteIsDone() throws Exception {
        //Given
        //WHEN THEN
        mockMvc.perform(get("/patientpatHistories/"+notePatientIdConst))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getNoteByPatientId_inexistingPatientId_erreurIsDone() throws Exception {
        //Given
        //WHEN THEN
        mockMvc.perform(get("/patientpatHistories/0"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    /*---------------------------  POST note -----------------------------*/
    @Test
    public void addNote_newNoteToInsert_noteIsDone() throws Exception {
        //Given
        //NoteDto noteTemp = new NoteDto(notePatientIdConst,noteTextConst,noteLocalDateConst);
        //NoteDto noteTemp = new NoteDto();
        Note noteTemp = new Note();
        noteTemp.setPatientId(notePatientIdConst);
        noteTemp.setTextNote(noteTextConst);
        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }
    @Test
    public void addNote_incorrectNoteToInsert_noteIsDone() throws Exception {
        //Given
        //NoteDto noteTemp = new NoteDto(notePatientIdConst,noteTextConst,noteLocalDateConst);
        //NoteDto noteTemp = new NoteDto();
        Note noteTemp = new Note();
        noteTemp.setId(noteIdConst);
        noteTemp.setPatientId(notePatientIdConst);
        noteTemp.setTextNote(noteTextConst);
        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());

    }
    /*---------------------------  PUT note -----------------------------*/
    @Test
    public void updateNote_existingNoteAndPatient_noteIsUpdated() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = new Note();
        noteTemp.setId(noteTempList.get(0).getId());
        noteTemp.setPatientId(noteTempList.get(0).getPatientId());
        noteTemp.setTextNote(noteNewTextConst);
        //WHEN THEN

        mockMvc.perform(put("/patHistory")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    @Test
    public void updateNote_inexistingNoteAndPatient_errorIsReturn() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = new Note();
        noteTemp.setId(inexistingNoteIdConst);
        noteTemp.setPatientId(noteTempList.get(0).getPatientId());
        noteTemp.setTextNote(noteNewTextConst);
        //WHEN THEN

        mockMvc.perform(put("/patHistory")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());
    }
    @Test
    public void updateNote_existingNoteAndInexistingPatient_errorIsReturn() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = new Note();
        noteTemp.setId(noteTempList.get(0).getId());
        noteTemp.setPatientId(incorrectNotePatientIdConst);
        noteTemp.setTextNote(noteNewTextConst);
        //WHEN THEN

        mockMvc.perform(put("/patHistory")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());
    }


    /*---------------------------  DELETE note -----------------------------*/
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteNote_existingNoteId_noteIsDeleted() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = new Note();
        noteTemp.setId(noteTempList.get(0).getId());
        noteTemp.setPatientId(noteTempList.get(0).getPatientId());
        noteTemp.setTextNote(noteTempList.get(0).getTextNote());

        //WHEN THEN
        mockMvc.perform(delete("/patHistory")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteNote_inexistingNoteId_error() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = new Note();
        noteTemp.setId(inexistingNoteIdConst);
        noteTemp.setPatientId(noteTempList.get(0).getPatientId());
        noteTemp.setTextNote(noteTempList.get(0).getTextNote());

        //WHEN THEN
        mockMvc.perform(delete("/patHistory")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void deleteNote_incorrectNoteId_error() throws Exception {
        //Given
        List<Note> noteTempList = noteService.findNoteByPatientId(note.getPatientId());
        Note noteTemp = new Note();
        //noteTemp.setId(inexistingNoteIdConst);
        noteTemp.setPatientId(noteTempList.get(0).getPatientId());
        noteTemp.setTextNote(noteTempList.get(0).getTextNote());

        //WHEN THEN
        mockMvc.perform(delete("/patHistory")
                .content(asJsonString(noteTemp))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotAcceptable());
    }
    /*---------------------------------------- Utility -------------------------------*/
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
