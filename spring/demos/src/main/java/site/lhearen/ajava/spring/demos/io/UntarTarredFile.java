package site.lhearen.ajava.spring.demos.io;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;

public class UntarTarredFile {
    public static void main(String... args) {
        File tarredFile = new File("./test.tar");
        File outputFolder = new File("./output");
        outputFolder.mkdirs();
        unTar(tarredFile, outputFolder);
    }

    private static List<File> unTar(final File inputFile, final File outputDir) {
        final List<File> untaredFiles = new LinkedList<>();
        try {
            try (InputStream is = new FileInputStream(inputFile);
                 TarArchiveInputStream debInputStream = (TarArchiveInputStream)
                         new ArchiveStreamFactory().createArchiveInputStream("tar", is)) {
                TarArchiveEntry entry = null;
                while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
                    final File outputFile = new File(outputDir, entry.getName());
                    if (entry.isDirectory()) {
                        if (!outputFile.exists()) {
                            if (!outputFile.mkdirs()) {
                                throw new IllegalStateException(String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
                            }
                        }
                    } else {
                        final OutputStream outputFileStream = new FileOutputStream(outputFile);
                        IOUtils.copy(debInputStream, outputFileStream);
                        outputFileStream.close();
                    }
                    untaredFiles.add(outputFile);
                }

            }
        } catch (IOException | ArchiveException ignored) {
            ignored.printStackTrace();
        }
        return untaredFiles;
    }
}
