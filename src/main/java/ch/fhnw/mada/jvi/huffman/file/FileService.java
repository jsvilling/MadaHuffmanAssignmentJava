package ch.fhnw.mada.jvi.huffman.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {

    private final String sourceFile;
    private final String codeFile;
    private final String compressedFile;
    private final String decompressedFile;

    public FileService() {
        this.sourceFile = "text.txt";
        this.codeFile = "dec_tab-mada.txt";
        this.compressedFile = "output-mada.dat";
        this.decompressedFile = "decompressed.txt";
    }

    public FileService(String sourceFile, String codeFile, String compressedFile, String decompressedFile) {
        this.sourceFile = sourceFile;
        this.codeFile = codeFile;
        this.compressedFile = compressedFile;
        this.decompressedFile = decompressedFile;
    }

    public String readDecodedFile() {
        return readTextFile(sourceFile);
    }

    public byte[] readEncodedFile() {
        return readBinaryFile(new File(compressedFile));
    }

    public String readCodeFile() {
        return readTextFile(codeFile);
    }

    public void writeCompressedFile(byte[] compressed) {
        writeBinaryFile(compressed, compressedFile);
    }

    public void writeCodeFile(String text) {
        writeTextFile(text, codeFile);
    }

    public void writeDecodedFile(String text) {
        writeTextFile(text, decompressedFile);
    }

    private String readTextFile(String fileName) {
        try {
            return Files.readString(Paths.get(fileName));
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