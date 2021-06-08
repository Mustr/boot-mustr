package com.mustr.document;

import java.io.File;

import org.jodconverter.core.DocumentConverter;
import org.jodconverter.core.document.DefaultDocumentFormatRegistry;
import org.jodconverter.core.office.OfficeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mustr.BootMustrApplicationTests;

public class LibreOfficeTest extends BootMustrApplicationTests {

    @Autowired
    private DocumentConverter documentConverter;
    
    @Test
    public void testConvert() {
        File source = new File("E:/TEST/word/V10.doc");
        File target = new File("E:/TEST/word/temp/V10.pdf");
        try {
            documentConverter.convert(source)
            .to(target)
            .as(DefaultDocumentFormatRegistry.PDF)
            .execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }
}
