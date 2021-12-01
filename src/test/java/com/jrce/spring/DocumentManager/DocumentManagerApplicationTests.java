package com.jrce.spring.DocumentManager;

import com.jrce.spring.DocumentManager.dao.DocumentRepository;
import com.jrce.spring.DocumentManager.domain.Document;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.postgresql.hostchooser.HostRequirement.primary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DocumentManagerApplicationTests {
    
        @Autowired
        private DocumentRepository repo;
        
        @Autowired
        private TestEntityManager entityManager;

	@Test
	void testInsertDocument() throws IOException {
            File file = new File("/home/jramon208/Documentos/sätze");
            
            Document document = new Document();
            
            document.setName(file.getName());
            
            byte[] bytes = Files.readAllBytes(file.toPath());
            document.setContent(bytes);
            long fileSize = bytes.length;
            document.setSize(fileSize);
            
            Document savedDoc = repo.save(document);
            
            Document existDoc = entityManager.find(Document.class, savedDoc.getId());
            
            assertThat(existDoc.getSize()).isEqualTo(fileSize);
            
	}

}
