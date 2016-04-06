package com.github.disparter.file_uploader.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;

import com.github.disparter.file_uploader.domain.FileInfo;

public class FileSaver {

    private Path path;
    private Path md5Path;
    private InputStream input;
    private FileInfo fileInfo;

    public static final String SERVER_LOCATION = "ngdemo_files";
    public static final int BUFFER_SIZE = 1024;

    public FileSaver(InputStream uploadedInputStream, FileInfo fileInfo) throws IOException {
        this.fileInfo = fileInfo;
        path = Paths.get(System.getProperty("user.home"), SERVER_LOCATION, this.fileInfo.getName());
        md5Path = Paths.get(System.getProperty("user.home"), SERVER_LOCATION, this.fileInfo.getName() + ".md5");
        input = uploadedInputStream;
        save();
    }

    private Date getDate(FileTime time) throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time.toMillis());
        return calendar.getTime();
    }

    private void save() throws IOException {

        Files.createDirectories(path.getParent());

        try (OutputStream outputFile = Files.newOutputStream(path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
             OutputStream md5Output = Files.newOutputStream(md5Path, StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING)) {

            byte[] data = IOUtils.toByteArray(input);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(data);
            char[] charArray = Hex.encodeHex(digest);
            
            md5Output.write(new String(charArray).getBytes("UTF-8"));
            md5Output.flush();

            IOUtils.write(data, outputFile);
            outputFile.flush();
            // Gather Information
            this.fileInfo.setType(Files.probeContentType(path));
            this.fileInfo.setSize(Files.size(path));
            this.fileInfo.setLastModified(getDate(Files.getLastModifiedTime(path)));
        } catch (IOException | NoSuchAlgorithmException e) {
            System.err.format("IOException: %s%n", e);
        }

    }
    
}
