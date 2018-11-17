package site.lhearen.ajava.spring.demos.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;


public class TarSomeFiles {
    public static void main(String... args) {
        System.out.println(Paths.get("").toAbsolutePath());
        List<FileVo> fileVoList = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            fileVoList.add(new FileVo("test_file" + i, "Hello world"));
        }
        File file = tarFlatFolder(fileVoList);
        System.out.println(file.getAbsolutePath());
    }

    private static File tarFlatFolder(List<FileVo> fileVoList) {
        File tmpFile = new File("/tmp/" + System.nanoTime());
        tmpFile.mkdirs();
        File tarFile = new File(tmpFile.getAbsolutePath()+"/first.tar");
        try {
            try (OutputStream outputStream = new FileOutputStream(tarFile);
                 ArchiveOutputStream archiveOutputStream = new ArchiveStreamFactory()
                         .createArchiveOutputStream(ArchiveStreamFactory.TAR, outputStream)) {
                for (FileVo fileVo : fileVoList) {
                    String filePath = "/tmp/" + fileVo.name;
                    Files.write(Paths.get(filePath), Arrays.asList(fileVo.content));
                    File inputFile = new File(filePath);
                    TarArchiveEntry tar_file = new TarArchiveEntry(inputFile);
                    tar_file.setSize(inputFile.length());
                    archiveOutputStream.putArchiveEntry(tar_file);
                    IOUtils.copy(new FileInputStream(inputFile), archiveOutputStream);
                    archiveOutputStream.closeArchiveEntry();
                }
                archiveOutputStream.finish();
            }
        } catch (IOException | ArchiveException ignored) {
            ignored.printStackTrace();
        }
        return tarFile;
    }
}

class FileVo {
    String name;
    String content;
    public FileVo(String theName, String content) {
        this.name = theName;
        this.content = content;
    }
}
