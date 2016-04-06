package com.github.disparter.file_uploader.service;

import java.io.IOException;
import java.io.InputStream;

import com.github.disparter.file_uploader.domain.FileInfo;
import com.github.disparter.file_uploader.helper.FileSaver;
import com.sun.jersey.core.header.FormDataContentDisposition;

public class FileService {

    

    public FileInfo save(InputStream fileInputStream, FormDataContentDisposition formData) {

        FileInfo info = new FileInfo();
        info.setName(formData.getFileName());
        info.setSize(formData.getSize());
        info.setType(formData.getType());
        info.setCreationDate(formData.getCreationDate());
        info.setLastModified(formData.getModificationDate());
        
        try {
            new FileSaver(fileInputStream, info);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return info;

    }
}
