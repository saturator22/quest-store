package com.codecool.Helper;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class MimeTypeResolverTest {

    @Test
    void getMimeType() {
        MimeTypeResolver mimeTypeResolver =
                new MimeTypeResolver(new File("src/main/resources/static/site/addclass.html"));
        String expectedType = "text/html";
        String actualType = mimeTypeResolver.getMimeType();

        assertEquals(expectedType, actualType);
    }

    @Test
    void testGetFileExtensionForHTML() {
        MimeTypeResolver mimeTypeResolver =
                new MimeTypeResolver(new File("src/main/resources/static/site/addclass.html"));
        String expectedExtension = "html";
        String actualExtension = mimeTypeResolver.getFileExtension();

        assertEquals(expectedExtension, actualExtension);
    }
}