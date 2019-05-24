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

    private static String sourceFile = "src/test/resources/it/text.txt";
    private static String codeFile = "src/test/resources/it/source/dec_tab-mada.txt";
    private static String compressedFile = "src/test/resources/it/source/output-mada.dat";
    private static String decompressedFile = "src/test/resources/it/target/decompressed.txt";
    private static String decompressedExpectedFile = "src/test/resources/it/expected/decompressedExpected.txt";

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

}
