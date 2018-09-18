package com.kx.todaynews;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Description: App基础的常量，包含URL，FILE路径等可能会被修改的参数。主要是配置级别的。
 */
public class YZBaseConstants {

    public static String BASE_PATH;

    public static String BASE_FILE_PATH;

    public static final int VIDEO_LIMIT=20*1000;

    public static final String MODEL_FILE_NAME = "model.yz";

    //拍摄视频路径
    public static final String VIDEO_FOLDER = "BIMCamera";

    //可清空
    public static String getNetCacheDir() {
        String netCatchDir = BASE_PATH + File.separator + "net";
        File fileDir = new File(netCatchDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return netCatchDir;
    }


    //可清空
    public static String getUserCacheDir(String userId) {
        String userCacheDir = BASE_PATH + File.separator + userId;
        File fileDir = new File(userCacheDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return userCacheDir;
    }

    //可清空
    public static String getTopicCacheDir(String userId) {
        String topicCatchDir = getUserCacheDir(userId) + File.separator + "topic";
        File fileDir = new File(topicCatchDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return topicCatchDir;
    }

    //
    public static String getProjectFileDir(String projectId){
        String projectDir = BASE_FILE_PATH + File.separator + projectId;
        File fileDir = new File(projectDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return projectDir;
    }

    public static String getDocumentDir(String projectId) {
        String documentDir = getProjectFileDir(projectId) + File.separator + "docmuent";
        File fileDir = new File(documentDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return documentDir;
    }

    public static String getDocumentFilePath(String projectId, String fileId, String suffix){
        return getDocumentDir(projectId)+ File.separator+fileId+"."+suffix;
    }


    //模型根目录
    public static String getModelDir(String projectId){
        String modelDir = getProjectFileDir(projectId)+ File.separator+ "models";
        File fileDir = new File(modelDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return modelDir;
    }

    public static String getModelFileDir(String projectId, String modelId) {
        String modelFileDir = getModelDir(projectId)+ File.separator + modelId;
        File fileDir = new File(modelFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return modelFileDir;
    }

    public static String getModelFile(String projectId, String modelId) {
        return getModelFileDir(projectId,modelId)+ File.separator + YZBaseConstants.MODEL_FILE_NAME;
    }

    public static String getModelZipFile(String projectId, String modelId, String modelFile) {
        return getModelFileDir(projectId,modelId)+ File.separator + modelFile;
    }

    //图片
    public static String getImageFile(){
        return getImageFileDir() + File.separator + System.currentTimeMillis() + ".jpg";
    }
    //内部图片目录
    public static String getImageFileDir(){
        String imageDir = BASE_FILE_PATH + File.separator + ".images";
        File fileDir = new File(imageDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return imageDir;
    }

    //内部视频目录
    public static String getVideoFileDir(){
        String imageDir = BASE_FILE_PATH + File.separator + ".videos";
        File fileDir = new File(imageDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return imageDir;
    }

    //内部录音目录
    public static String getRecordCacheDir() {
        String recordDir = BASE_FILE_PATH + File.separator + ".record";
        File fileDir = new File(recordDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return recordDir;
    }

    public static String getVideoBimCameraFileDir() {
        String videoJCameraDir = getVideoFileDir() + File.separator + VIDEO_FOLDER;
        File fileDir = new File(videoJCameraDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return videoJCameraDir;
    }

    public static String getVideoSaveFileDir() {
        String saveVideoDir = getBaseSaveFileDir() + File.separator + "videos";
        File fileDir = new File(saveVideoDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return saveVideoDir;
    }

    public static String getImageSaveFileDir() {
        String saveImageDir = getBaseSaveFileDir() + File.separator + "images";
        File fileDir = new File(saveImageDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return saveImageDir;
    }




    public static String getBaseSaveFileDir() {
        String saveBaseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + "EBIM";
        File fileDir = new File(saveBaseDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return saveBaseDir;
    }


    public static void init(Context context) {
        if (context.getApplicationContext().getExternalCacheDir() == null) {
            BASE_PATH = context.getApplicationContext().getCacheDir().getAbsolutePath();
            BASE_FILE_PATH = context.getApplicationContext().getFilesDir().getAbsolutePath();
        } else {
            BASE_PATH = context.getApplicationContext().getExternalCacheDir().getAbsolutePath();
            BASE_FILE_PATH = context.getApplicationContext().getExternalFilesDir(null).getAbsolutePath();
        }
    }

}
