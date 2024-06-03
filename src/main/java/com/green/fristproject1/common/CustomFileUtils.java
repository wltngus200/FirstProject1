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

    public void deleteFolder(String shortPath){ //파일이 위치한 전체 경로를 받음
        String fullPath=String.format("%s/%s", uploadPath, shortPath);
        File file=new File(fullPath); //그 값을 담은 파일 객체 생성
        if(file.exists() && file.isDirectory()){ //그게 존재하고, 폴더라면
            File[] list=file.listFiles(); //리스트에 값을 넣어
            for(File f:list){ //File이 든 리스트 길이만큼 반복
                if(f.isDirectory()){ //안에 든 값중 1개가 폴더면
                    deleteFolder(f.getAbsolutePath()); //다시 이 메소드를 호출
                }else{
                    f.delete(); //사진파일이면 지워
                }
            }file.delete(); //다 끝나면 빈 폴더도 지워
        }
    }
}
