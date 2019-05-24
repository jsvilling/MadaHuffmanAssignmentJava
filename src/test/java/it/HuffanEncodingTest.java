package it;

import ch.fhnw.mada.jvi.huffman.encoding.CodeMapService;
import ch.fhnw.mada.jvi.huffman.encoding.EncodingService;
import ch.fhnw.mada.jvi.huffman.file.FileService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class HuffanEncodingTest {


    @Test
    public void testDecodingEncodedFromExerciseSheet() throws IOException {
        // Given
        String sourceFile = "src/test/resources/it/decde/text.txt";
        String codeFile = "src/test/resources/it/decode/dec_tab-mada.txt";
        String compressedFile = "src/test/resources/it/decode/output-mada.dat";
        String decompressedFile = "src/test/resources/it/decode/text.txt";
        String decompressedExpectedFile = "src/test/resources/it/decode/decompressedExpected.txt";
        FileService fileService = new FileService(sourceFile, codeFile, compressedFile, decompressedFile);
        EncodingService encodingService = new EncodingService(fileService, new CodeMapService());

        // When
        encodingService.decode();

        // Then
        String actualDecoded = Files.readString(Paths.get(decompressedFile));
        String expectedDecoded = Files.readString(Paths.get(decompressedExpectedFile));
        assertThat(actualDecoded).isEqualTo(expectedDecoded);
    }

    @Test
    public void testEncodingFromExerciseSheet() throws IOException {
        // Given
        String sourceFile = "src/test/resources/it/encode/text.txt";
        String codeFile = "src/test/resources/it/encode/dec_tab-mada.txt";
        String compressedFile = "src/test/resources/it/encode/output-mada.dat";
        String decompressedFile = "src/test/resources/it/encode/text.txt";
        String codeFileExpected = "src/test/resources/it/encode/dec_tab-mada.txt";

        FileService fileService = new FileService(sourceFile, codeFile, compressedFile, decompressedFile);
        EncodingService encodingService = new EncodingService(fileService, new CodeMapService());

        Files.writeString(Paths.get(codeFile), "");

        // When
        encodingService.encode();

        // Then
        String actualCode = Files.readString(Paths.get(codeFile));
        String expectedCode = Files.readString(Paths.get(codeFileExpected));
        assertThat(actualCode).isEqualTo(expectedCode);
    }

    @Test
    public void testEncodingDecodingGivesOriginalText() throws IOException {
        // Given
        String sourceFile = "src/test/resources/it/cycle/text.txt";
        String codeFile = "src/test/resources/it/cycle/dec_tab-mada.txt";
        String compressedFile = "src/test/resources/it/cycle/output-mada.dat";
        String decompressedFile = "src/test/resources/it/cycle/decompressed.txt";

        Files.writeString(Paths.get(codeFile), "");
        Files.writeString(Paths.get(compressedFile), "");
        Files.writeString(Paths.get(decompressedFile), "");

        // When
        FileService fileService = new FileService(sourceFile, codeFile, compressedFile, decompressedFile);
        EncodingService encodingService = new EncodingService(fileService, new CodeMapService());

        // Then
        encodingService.encode();
        encodingService.decode();

        String actualCode = Files.readString(Paths.get(decompressedFile));
        String expectedCode = Files.readString(Paths.get(sourceFile));
        assertThat(actualCode).isEqualToNormalizingWhitespace(expectedCode);
    }
}
