package com.worksap.morphling.raptor.dump.thread.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.worksap.morphling.raptor.dump.thread.service.DumpService;
import com.worksap.morphling.raptor.dump.thread.service.S3DownLoadService;
import com.worksap.morphling.raptor.dump.thread.service.UserDumpService;
import com.worksap.morphling.raptor.dump.thread.util.ThreadUtils;
import com.worksap.morphling.raptor.scheduler.dao.QrtzJobHistoryRepository;
import com.worksap.morphling.raptor.scheduler.entity.QrtzJobHistory;
import com.worksap.morphling.raptor.scheduler.hook.PostExecutionHook;
import com.worksap.morphling.raptor.websocket.WebSocketService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenerateExecutorPostHook extends PostExecutionHook {
    private static final String DUMP_FILE_NAME = "dumpFileName";
    private static final String COMPRESSED_FORMAT_SUFFIX = ".tar";

    @Autowired
    S3DownLoadService s3DownLoadService;

    @Autowired
    DumpService dumpService;

    @Autowired
    WebSocketService webSocketService;

    @Autowired
    UserDumpService userDumpService;

    @Autowired
    private QrtzJobHistoryRepository qrtzJobHistoryRepository;

    @Override
    public void execute(JobExecutionContext context, QrtzJobHistory history) {

        if (QrtzJobHistory.JobStatusEnum.SUCCEED.toString().equals(history.getStatus())) {
            String parameters = history.getParameters();
            JsonParser jsonParser = new JsonParser();
            JsonObject tempResluts = (JsonObject) jsonParser.parse(parameters);

            String fileName = tempResluts.get(DUMP_FILE_NAME).getAsString().concat(COMPRESSED_FORMAT_SUFFIX);
            File downloadFile = s3DownLoadService.download(fileName, true);

            try {
                File outputFile = new File("/tmp/dumpTar" + String.valueOf(System.nanoTime()));
                outputFile.mkdirs();
                List<Long> dumpIds = unTar(downloadFile, outputFile).stream()
                        .map(file -> dumpService.parseFile(file, history.getCreatedBy(), false))
                        .collect(Collectors.toList());

                outputFile.delete();
                webSocketService.sendThreadDumpResultId(history.getJobName(), dumpIds);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
            value = "PATH_TRAVERSAL_IN",
            justification = "Specified by System Only")
    private List<File> unTar(final File inputFile, final File outputDir) throws IOException, ArchiveException {

        log.info("Untaring {} to dir {}.", inputFile.getAbsolutePath(), outputDir.getAbsolutePath());

        final List<File> untaredFiles = new LinkedList<File>();
        try (InputStream is = new FileInputStream(inputFile);
             TarArchiveInputStream debInputStream = (TarArchiveInputStream)
                     new ArchiveStreamFactory().createArchiveInputStream("tar", is)) {
            TarArchiveEntry entry = null;
            while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
                String fileName = ThreadUtils.getLast(Arrays.asList(entry.getName().split("/")));
                final File outputFile = new File(outputDir, fileName);
                if (entry.isDirectory()) {
                    log.info("Attempting to write output directory {}.", outputFile.getAbsolutePath());
                    if (!outputFile.exists()) {
                        log.info("Attempting to create output directory {}.", outputFile.getAbsolutePath());
                        if (!outputFile.mkdirs()) {
                            throw new IllegalStateException(String.format("Couldn't create directory %s.",
                                    outputFile.getAbsolutePath()));
                        }
                    }
                } else {
                    log.info("Creating output file {}.", outputFile.getAbsolutePath());
                    final OutputStream outputFileStream = new FileOutputStream(outputFile);
                    IOUtils.copy(debInputStream, outputFileStream);
                    outputFileStream.close();
                }
                untaredFiles.add(outputFile);
            }
        }

        return untaredFiles;
    }
}
