package ch.fhnw.mada.jvi.huffman.file;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {

    private static final String SOURCE_FILE = "text.txt";
    private static final String CODE_FILE = "dec_tab-mada.txt";
    private static final String COMPRESSED_FILE = "output-mada.dat";
    private static final String DECOMPRESSED_FILE = "decompressed.txt";

    public String readDecodedFile() {
        return readTextFile(SOURCE_FILE);
    }

    public byte[] readEncodedFile() {
        return readBinaryFile(new File(COMPRESSED_FILE));
    }

    public String readCodeFile() {
        return readTextFile(CODE_FILE);
    }

    public void writeCompressedFile(byte[] compressed) {
        writeBinaryFile(compressed, COMPRESSED_FILE);
    }

    public void writeCodeFile(String text) {
        writeTextFile(text, CODE_FILE);
    }

    public void writeDecodedFile(String text) {
        writeTextFile(text, DECOMPRESSED_FILE);
    }

    private String readTextFile(String fileName) {
        try {
            return Files.readString(Paths.get(fileName), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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