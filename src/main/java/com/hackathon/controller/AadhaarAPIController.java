package com.hackathon.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import com.hackathon.model.ImageModel;
import com.hackathon.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "aadhaar")
public class AadhaarAPIController
{
    @PostMapping("/upload")

    public ResponseEntity<String> uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(), compressBytes(file.getBytes()));
        return new ResponseEntity("Image Uploaded Successfully!!",HttpStatus.OK);
    }
        // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }
    @GetMapping("/userDetails")
    public ResponseEntity<User> getUserDetails() throws IOException{
        User user = getValues();
        return new ResponseEntity(user,HttpStatus.OK);
    }
    private User getValues(){
        User user =new User();
        user.setFirstName("Archana");
        user.setLastName("Jhunjhunwala");
        user.setMiddleName("Ruban");
        user.setGender("Female");
        user.setNationality("Indian");
        user.setFatherName("VK Jhunjhunwala");
        user.setAddress("DB Hackathon Pune-412207");
        user.setPhoneNumber("8981316278");
        user.setAadhaarNumber("968668675621");
        user.setDob("29/12/2000");
        return user;
    }
}

