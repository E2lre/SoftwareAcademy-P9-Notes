package com.mediscreen.notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.GeneratedValue;
import java.time.LocalDate;
import java.util.Date;

@Document(collection = "Note")
public class Note {

    @Id
    @GeneratedValue
    private String id;

    @Field(value = "Patient_Id")
    private Long patientId;

    @Field(value = "Text_Note")
    private String textNote;

    @Field(value = "Date_Note")
    private LocalDate dateNote;

    public Note() {
        }
    public Note(String id, Long patientId, String textNote, LocalDate dateNote) {
        this.id = id;
        this.patientId = patientId;
        this.textNote = textNote;
        this.dateNote = dateNote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getTextNote() {
        return textNote;
    }

    public void setTextNote(String textNote) {
        this.textNote = textNote;
    }

    public LocalDate getDateNote() {
        return dateNote;
    }

    public void setDateNote(LocalDate dateNote) {
        this.dateNote = dateNote;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", textNote=" + textNote +
                ", dateNote=" + dateNote +
                '}';
    }
}
