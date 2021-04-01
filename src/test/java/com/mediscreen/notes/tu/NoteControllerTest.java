package com.mediscreen.notes.tu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.model.external.Patient;
import com.mediscreen.notes.proxies.PatientProxy;
import com.mediscreen.notes.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @MockBean
    private PatientProxy patientProxy;


    private Note note;
    private Patient patient;

    String noteIdConst = "abc123";
    Long notePatientIdConst = 123l;
    String noteTextConst = "AZERTYIOP";
    LocalDate noteDate;

    @BeforeEach
    public void setUpEach() {
        note = new Note();
        note.setPatientId(notePatientIdConst);
        note.setTextNote(noteTextConst);
        noteDate = LocalDate.now();

        patient = new Patient();
        patient.setId(notePatientIdConst);
        patient.setLastName("Testlastname");
        patient.setFirstName("TestFirstname");
    }
    /*---------------------------  POST note -----------------------------*/
    @Test
    public void addNote_newNoteToInsertExistingPatientId_errorISReturn() throws Exception {
      //Given
       Mockito.when(patientProxy.getPatientById(note.getPatientId())).thenReturn(patient);
        Mockito.when(noteService.addWithPatientId(any(Note.class))).thenReturn(note);

        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(note))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }
    @Test
    public void addNote_newNoteToInsertInexistingPatientId_errorISReturn() throws Exception {
        //Given
        Mockito.when(patientProxy.getPatientById(note.getPatientId())).thenReturn(null);
        Mockito.when(noteService.addWithPatientId(any(Note.class))).thenReturn(note);

        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(note))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());

    }
    @Test
    public void addNote_incorrectNoteToInsert_noteIsDone() throws Exception {
        //Given
        patient.setId(notePatientIdConst);
        note.setId(noteIdConst);
        Mockito.when(patientProxy.getPatientById(note.getPatientId())).thenReturn(patient);
        Mockito.when(noteService.addWithPatientId(any(Note.class))).thenReturn(note);

        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(note))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());

    }
    @Test
    public void addNote_correctNoteToInsertAndNullPatientId_noteIsDone() throws Exception {
        //Given
        note.setId(noteIdConst);
        Mockito.when(patientProxy.getPatientById(note.getPatientId())).thenReturn(patient);
        Mockito.when(noteService.addWithPatientId(any(Note.class))).thenReturn(note);

        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(note))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());

    }
    @Test
    public void addNote_errorDuringNoteCration_errorISReturn() throws Exception {
        //Given
        Mockito.when(patientProxy.getPatientById(note.getPatientId())).thenReturn(patient);
        Mockito.when(noteService.addWithPatientId(any(Note.class))).thenReturn(null);

        //WHEN THEN
        mockMvc.perform(post("/patHistory/add")
                .content(asJsonString(note))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotModified());

    }
    /*------------------------ utility ---------------------------------*/
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
