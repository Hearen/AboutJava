package site.lhearen.ajava.spring.demos.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

public class TarFolder {
    public static void main(String... args) {
        File folder = new File("/tmp/prometheus/rules/jillk_develop_multi-tenant-single");
        tarToFlatFolder(folder);
    }


    public static File tarToFlatFolder(File folder) {
        File tarFile = new File("first.tar");
        try {
            try (OutputStream outputStream = new FileOutputStream(tarFile);
                 ArchiveOutputStream archiveOutputStream = new ArchiveStreamFactory()
                         .createArchiveOutputStream(ArchiveStreamFactory.TAR, outputStream);
                 Stream<Path> paths = Files.walk(Paths.get(folder.getAbsolutePath()))) {
                paths.forEach(filePath -> {
                    try {
                        if (Files.isRegularFile(filePath)) {
                            File inputFile = new File(filePath.toAbsolutePath().toString());
                            File tmpFile = new File(inputFile.getName());
                            Files.copy(inputFile.toPath(), tmpFile.toPath());
                            TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(tmpFile);
                            tarArchiveEntry.setSize(tmpFile.length());
                            archiveOutputStream.putArchiveEntry(tarArchiveEntry);
                            IOUtils.copy(new FileInputStream(tmpFile), archiveOutputStream);
                            archiveOutputStream.closeArchiveEntry();
                            tmpFile.delete();
                        }
                    } catch (IOException ignored) {
                        ignored.printStackTrace();
                    }
                });
                archiveOutputStream.finish();
            }
        } catch (IOException | ArchiveException ignored) {
            ignored.printStackTrace();
        }
        return tarFile;
    }
}
