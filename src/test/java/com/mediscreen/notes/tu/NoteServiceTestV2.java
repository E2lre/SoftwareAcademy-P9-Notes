package com.mediscreen.notes.tu;

import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
//@AutoConfigureTestDatabase
//@Configuration
//@DataMongoTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class NoteServiceTestV2 {
    private static final Logger logger = LogManager.getLogger(NoteServiceTestV2.class);
    @Autowired
    private NoteService noteService;

    @MockBean
    private NoteDao noteDao;

    private Note note;

    @Test
    public void Mafonction2test (){
        boolean b = true;
        assertThat(b).isTrue();
    }

}
