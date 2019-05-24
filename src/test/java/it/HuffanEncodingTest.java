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

    private static String sourceFile = "src/test/resources/it/decde/text.txt";
    private static String codeFile = "src/test/resources/it/decode/dec_tab-mada.txt";
    private static String compressedFile = "src/test/resources/it/decode/output-mada.dat";
    private static String decompressedFile = "src/test/resources/it/decode/text.txt";
    private static String decompressedExpectedFile = "src/test/resources/it/decode/decompressedExpected.txt";

    @Test
    public void testDecodingEncodedFromExerciseSheet() throws IOException {
        // Given
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
        String codeFileExpected = "src/test/resources/it/encode/dec_tab-expected.txt";

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

}
