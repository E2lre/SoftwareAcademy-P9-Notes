package com.mediscreen.notes.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "Note")
public class Note {

    @Id
    private Long id;

    @Field(value = "Patient_Id")
    private Long patientId;

    @Field(value = "Text_Note")
    private String textNote;

    @Field(value = "Date_Note")
    private Date dateNote;

    public Note() {
        }
    public Note(Long id, Long patientId, String textNote, Date dateNote) {
        this.id = id;
        this.patientId = patientId;
        this.textNote = textNote;
        this.dateNote = dateNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getDateNote() {
        return dateNote;
    }

    public void setDateNote(Date dateNote) {
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
