package com.jrce.spring.DocumentManager;

import com.jrce.spring.DocumentManager.dao.DocumentRepository;
import com.jrce.spring.DocumentManager.domain.Document;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DocumentManagerApplicationTests {

    @Autowired
    private DocumentRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Rollback(false)
    void testInsertDocument() throws IOException {
        File file = new File("C:\\ejemplos\\nuevo.txt");
        byte[] bytes = Files.readAllBytes(file.toPath());
        long fileSize = bytes.length;
        
        System.out.println("fileSize = " + fileSize);

        Document document = new Document();
        document.setName(file.getName());
        document.setContent(bytes);
        document.setSize(fileSize);
        document.setUploadTime(new Date());

        Document savedDoc = repo.save(document);
        Document existDoc = entityManager.find(Document.class, savedDoc.getId());

        assertThat(existDoc.getSize()).isEqualTo(fileSize);
    }

}
