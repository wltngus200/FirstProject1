package com.green.fristproject1.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class CustomFileUtils {
    private final String uploadPath;

    public CustomFileUtils(@Value("${file.directory}") String uploadPath){
        this.uploadPath = uploadPath;
    }

    public String makeRandomFileName(){
        return String.format("%s", UUID.randomUUID());}
    public String makeRandomFileName(String fileName){
        return String.format("%s%s", makeRandomFileName(), getTmp(fileName));}
    public String makeRandomFileName(MultipartFile pic){
        if(pic==null||pic.isEmpty()){ return null; }
        return makeRandomFileName(pic.getOriginalFilename());
    }

    public String getTmp(String fileName){
        int idx=fileName.lastIndexOf(".");
        String tmp=fileName.substring(idx);
        return tmp;
    }

    public String makeFolder(String path){
        File folder=new File(uploadPath, path);
        folder.mkdirs();
        return folder.getAbsolutePath();
    }
    public void transfer(String target, MultipartFile mf) throws Exception {
        File file=new File(uploadPath, target);
        mf.transferTo(file);
    }

}
