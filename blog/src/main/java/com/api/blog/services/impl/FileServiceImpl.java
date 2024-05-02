package com.api.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.api.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {

		// File name
		String fileName = file.getOriginalFilename();

		// random name
		String randomId = UUID.randomUUID().toString();
		String randomName = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

		// full path
		String filePath = path + File.separator + randomName;
		// create folder if not created
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		// copy file
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return randomName;

	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
