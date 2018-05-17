/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognizant.bibliotecadigital.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Diego
 */
public class StorageService {
    private static String UPLOADED_FOLDER = "C:\\files\\";
	// private static final String UPLOADED_FOLDER = "C:\\Users\\Diego\\Git\\photos\\src\\main\\resources\\files\\";
	
	private static StorageService instance;
	
	private StorageService() {}
	
	public static synchronized StorageService getInstance() {
		if(instance == null){
	        synchronized (StorageService.class) {
	            if(instance == null){
	                instance = new StorageService();
	            }
	        }
	    }
	    return instance;
	}
	
	public String store(MultipartFile file) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyhhmmssSSS");

		String ext = ".undefined";
		int last = file.getOriginalFilename().lastIndexOf(".");
		if (last > 0) {
			ext = file.getOriginalFilename().substring(last);
		}

		String newFilename = "";
		Path path;
		do {
			newFilename = simpleDateFormat.format(new Date()) + '-' + RandomStringUtils.randomAlphanumeric(8) + ext;
			path = Paths.get(UPLOADED_FOLDER + newFilename);
		} while (Files.exists(path));
		
		try {
			byte[] bytes = file.getBytes();
			Files.write(path, bytes);
		} catch (IOException ioe) {
			Logger.getLogger(StorageService.class.getName()).log(Level.SEVERE, ioe.getMessage(), ioe);
		}
		return newFilename;
	}
	
	public Resource load(String filename) {
		Path file = Paths.get(UPLOADED_FOLDER + filename);
		Resource resource = null;
		try {
			resource = new UrlResource(file.toUri());	
		} catch (MalformedURLException ex) {
			Logger.getLogger(StorageService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		return resource;
	}
    
    public void delete(String filename) {
        Path file = Paths.get(UPLOADED_FOLDER + filename);
        try {
            Files.delete(file);
        } catch (IOException ex) {
            Logger.getLogger(StorageService.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
