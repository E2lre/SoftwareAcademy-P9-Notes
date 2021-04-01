package com.mediscreen.notes.tu;

import com.mediscreen.notes.dao.NoteDao;
import com.mediscreen.notes.model.Note;
import com.mediscreen.notes.service.NoteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest

public class NoteServiceTestV2 {
    private static final Logger logger = LogManager.getLogger(NoteServiceTestV2.class);

    @Test
    public void Mafonction2test (){
        boolean b = true;
        assertThat(b).isTrue();
    }

}
