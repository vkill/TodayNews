package com.kx.todaynews.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonSerializer;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by hdk on 2017/1/11.
 */

public class RequestBodyUtils {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MULTIPART = MediaType.parse("multipart/form-data");
    private static final MediaType TEXT = MediaType.parse("text/plain");
    private static final MediaType FILE = MediaType.parse("application/otcet-stream");

    public static MultipartBody.Part getFilePart(String name, String filePath){
        if(isNull(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if(file.isFile() && file.exists()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            return MultipartBody.Part.createFormData(name, file.getName(), requestBody);
        }
        return null;
    }

    public static MultipartBody.Part getFilePart(String name, String fileName, String filePath){
        if(isNull(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if(file.isFile() && file.exists()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            return MultipartBody.Part.createFormData(name, fileName, requestBody);
        }
        return null;
    }

    public static List<MultipartBody.Part> getFilesDefaultParts(String name, List<String> filePaths){
        if(filePaths==null||filePaths.isEmpty()) {
            return null;
        }
        List<MultipartBody.Part> parts = new ArrayList<>();
        int count = 0;
        for (String filePath:filePaths){
            File file = new File(filePath);
            if(file.isFile() && file.exists()){
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData(name + count, file.getName(), requestBody);
                if (part!=null){
                    parts.add(part);
                    count++;
                }
            }
        }
        return parts;
    }

    public static List<MultipartBody.Part> getFilesParts(String name, List<Pair<String,String>> files){
        if(files==null||files.isEmpty()) {
            return null;
        }
        List<MultipartBody.Part> parts = new ArrayList<>();
        int count = 0;
        for (Pair<String,String> filePair:files){
            File file = new File(filePair.second);
            if(file.isFile() && file.exists()){
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData(name + count, filePair.first, requestBody);
                if (part!=null){
                    parts.add(part);
                    count++;
                }
            }
        }
        return parts;
    }

    public static MultipartBody.Part getStringPart(String name){
        RequestBody requestBody = RequestBody.create(TEXT, name);
        return MultipartBody.Part.create(requestBody);
    }

    public static MultipartBody.Part getStringPart(String name, String value) {
        return MultipartBody.Part.createFormData(name, value);
    }

//    public static MultipartBody.Part getObjectPart(Object object){
//        RequestBody requestBody = RequestBody.create(JSON, JsonSerializer.getInstance().serialize(object));
//        return MultipartBody.Part.create(requestBody);
//    }




    public static MultipartBody.Part imagePathtoAvatarPart(String filePath){
        if(isNull(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if(file.isFile() && file.exists()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            return MultipartBody.Part.createFormData("avatar", file.getName(), requestBody);
        }
        return null;
    }


    private static List<MultipartBody.Part> imagePathtoMultiPart(List<String> filePaths) {
        if (filePaths == null || filePaths.size() == 0) {
            return new ArrayList<>();
        }
        int count = 0;
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < filePaths.size(); i++) {
            File file = new File(filePaths.get(i));
            if (file.isFile() && file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("picture" + count, file.getName(), requestBody);
                parts.add(part);
                count++;
            }
        }
        return parts;
    }

    public static List<MultipartBody.Part> imageFiletoMultiPart(List<File> fileList) {
        if (fileList == null || fileList.size() == 0){
            return new ArrayList<>();
        }
        int count = 0;
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (file.isFile() && file.exists()) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("picture" + count, file.getName(), requestBody);
                parts.add(part);
                count++;
            }
        }
        return parts;
    }

    public static Observable<List<MultipartBody.Part>> getImageFilePartObservable(List<String> filePath) {
        return Observable.just(imagePathtoMultiPart(filePath));
    }


    public static Observable<MultipartBody.Part> getAvatarPartObservable(String avatarPath){
        return Observable.just(imagePathtoAvatarPart(avatarPath));
    }

//    public static Observable<List<MultipartBody.Part>> getZipImageFilePartObservable(Context context, List<String> filePaths) {
//        return getZipImageFileObservable(context, filePaths).map(new Func1<List<File>, List<MultipartBody.Part>>() {
//            @Override
//            public List<MultipartBody.Part> call(List<File> files) {
//                return imageFiletoMultiPart(files);
//            }
//        });
//    }

//    public static Observable<List<File>> getZipImageFileObservable(Context context, List<String> filePaths) {
//        List<File> fileList = new ArrayList<>();
//        if (filePaths != null && filePaths.size() > 0) {
//            for (String path : filePaths) {
//                File file = new File(path);
//                if (file.exists() && file.isFile()) {
//                    fileList.add(file);
//                }
//            }
//        }
//        File cacheFile = new File(YZBaseConstants.getImageSaveFileDir());
//        if (fileList.size() > 0){
//            return YZImageCompressUtils.compress(cacheFile,fileList)
//                    .putGear(YZImageCompressUtils.CUSTOM_GEAR)
//                    .setMaxSize(200)
//                    .setCompressFormat(Bitmap.CompressFormat.JPEG)
//                    .setMaxHeight(1280)
//                    .setMaxWidth(720)
//                    .asListObservable();
//        }
//        else {
//            return Observable.just(fileList);
//        }
//    }
//
//    public static Observable<List<String>> getZipImagePathsObservable(Context context, List<String> filePaths){
//        return getZipImageFileObservable(context,filePaths).map(files -> {
//            List<String> zipPaths = new ArrayList<>();
//            if (files!=null){
//                for (File file:files){
//                    zipPaths.add(file.getAbsolutePath());
//                }
//            }
//            return zipPaths;
//        });
//    }

    public static MultipartBody.Part getFilePart(File file){
        if (file==null||!file.exists()){
            return null;
        }
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        return body;
    }

    public static MultipartBody.Part getFilePart(String path){
        if (TextUtils.isEmpty(path)){
            return null;
        }
        File file = new File(path);
        return getFilePart(file);
    }

    public static MultipartBody.Part getZipFileObservable(File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return body;
    }

    public static MultipartBody.Part getZipFileObservable(String key, File file) {
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData(key, file.getName(), requestFile);
        return body;
    }

    public static MultipartBody.Part getZipFileObservable(String key, String filePath) {
        if (TextUtils.isEmpty(filePath)){
            return null;
        }
        return getZipFileObservable(key,new File(filePath));
    }

    public static RequestBody toRequestBody(String value) {
        if (isNull(value)) {
            return null;
        }
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }


    public static List<RequestBody> toRequestBodyList(List<String> values) {
        if (values == null) {
            return null;
        }
        List<RequestBody> requestBodies = new ArrayList<>();
        for (String value : values) {
            requestBodies.add(RequestBody.create(MediaType.parse("text/plain"), value));
        }
        return requestBodies;
    }

    public static RequestBody toJsonBody(Object obj) {
        return RequestBody.create(JSON, new Gson().toJson(obj));
    }

    public static RequestBody toJsonBody(Object obj, Type type) {
        return RequestBody.create(JSON, new Gson().toJson(obj,type));
    }

    public static RequestBody toJsonBody(String str) {
        return RequestBody.create(JSON, str);
    }


    private static final String TAG = RequestBodyUtils.class.getSimpleName();


    public static MultipartBody getMultipartBody(List<File> files, String fileParams, Map<String, String> keySet) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (int i = 0; i < files.size(); i++) {
            RequestBody requestBody = RequestBody.create(MULTIPART, files.get(i));
            builder.addFormDataPart(fileParams + i, files.get(i).getName(), requestBody);
        }
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static MultipartBody getMultipartBody(Map<String, String> keySet) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String str : keySet.keySet()) {
            builder.addFormDataPart(str, keySet.get(str));
        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    public static RequestBody fileToRequestBody(File file) {
        return RequestBody.create(FILE, file);
    }

    public static RequestBody stringToTextBody(String value) {
        return RequestBody.create(TEXT, value);
    }

    public static RequestBody stringToJsonBody(String jsonStr) {
        return RequestBody.create(JSON, jsonStr);
    }
    public static boolean isNull(String... obj) {
        for (String s : obj) {
            if (s == null || s.equals("")) {
                return true;
            }
        }
        return false;
    }
}
