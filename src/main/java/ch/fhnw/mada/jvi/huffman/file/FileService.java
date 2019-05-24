package ch.fhnw.mada.jvi.huffman.file;

import ch.fhnw.mada.jvi.huffman.encoding.EncodingService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Service for reading and writing the files used by the {@link ch.fhnw.mada.jvi.huffman.encoding.EncodingService}
 *
 * The FileService works with the following files:
 *
 *  * <ul>
 *  *     <li>text.txt          - Textfile containing the text to be encoded</li>
 *  *     <li>dec_tab.txt       - Textfile containing the huffman code to be used</li>
 *  *     <li>output.dat        - Binary file containing the encoded text</li>
 *  *     <li>decompressed.txt  - Textfile containing the decoded output</li>
 *  * </ul>
 *
 *  By default all files are expected to be in the working directory of the project. If necessary this service can be
 *  configured with custom file paths.
 *
 *  Any exceptions when interacting with files are logged and re-thrown as RuntimeExceptions.
 */
public class FileService {

    private final String sourceFile;
    private final String codeFile;
    private final String compressedFile;
    private final String decompressedFile;

    /**
     * Dafault constructor. Expecting all files to be in the working directory.
     */
    public FileService() {
        this.sourceFile = "text.txt";
        this.codeFile = "dec_tab.txt";
        this.compressedFile = "output.dat";
        this.decompressedFile = "decompressed.txt";
    }

    /**
     * This constructor can be used to initialize the service with custom file paths.
     *
     * @param sourceFile
     * @param codeFile
     * @param compressedFile
     * @param decompressedFile
     */
    public FileService(String sourceFile, String codeFile, String compressedFile, String decompressedFile) {
        this.sourceFile = sourceFile;
        this.codeFile = codeFile;
        this.compressedFile = compressedFile;
        this.decompressedFile = decompressedFile;
    }

    /**
     * Reads the file content of a the plaintext sourceFile.
     *
     * By default the sourceFile is expected to be found in the working directory and be named text.txt
     */
    public String readDecodedFile() {
        return readTextFile(sourceFile);
    }

    /**
     * Reads the byte[] value of a binary compressedFile. The file is expected to have been encoded using the
     * {@link EncodingService#encode()} method.
     *
     * By default the compressedFile is expected to be found in the working directory and be named output.dat
     */
    public byte[] readEncodedFile() {
        return readBinaryFile(new File(compressedFile));
    }

    /**
     * Reads the file content of a the plaintext codeFile. The file is expected to contain a valid huffan code stored
     * in the format
     *
     * By default the codeFile is expected to be found in the working directory and be named dec_tab.txt
     */
    public String readCodeFile() {
        return readTextFile(codeFile);
    }

    /**
     * Writes the given byte array to the binary compressedFile.
     *
     * By default the compressedFile is expected to be found in the working directory and be named output.dat
     */
    public void writeCompressedFile(byte[] compressed) {
        writeBinaryFile(compressed, compressedFile);
    }

    /**
     * Writes a given String to the plaintext codeFile. The String is expected to be a valid representation of a
     * huffman code. following the format ASCII code of symbol1:code of symbol1-ASCII code of symbol2:code of symbol 2-...
     *
     * By default the codeFile is expected to be found in the working directory and be named dec_tab.txt
     */
    public void writeCodeFile(String text) {
        writeTextFile(text, codeFile);
    }

    /**
     * Writes a given String to the plaintext decompressedFile.
     *
     * By default the decompressedFile is expected to be found in the working direcotry and be named decompressed.txt
     */
    public void writeDecodedFile(String text) {
        writeTextFile(text, decompressedFile);
    }

    /**
     * Helper Method reading the content of a text file.
     *
     * @param fileName String representation of the path under which the source file can be found.
     */
    private String readTextFile(String fileName) {
        try {
            return Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper Method reading the content of a binary file.
     *
     * @param file The binary file from which the byte array should be read.
     */
    private byte[] readBinaryFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            fis.read(bytes);
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper Method writing to a binary file.
     *
     * @param out Byte Array which will be written to the binary file
     * @param fileName String representation of the path under which the target file can be found.
     */
    private void writeBinaryFile(byte[] out, String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            dos.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void writeTextFile(String text, String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            byte[] out = text.getBytes();
            fos.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}