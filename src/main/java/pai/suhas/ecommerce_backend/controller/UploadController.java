package pai.suhas.ecommerce_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pai.suhas.ecommerce_backend.service.UploadService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController
{
    private final UploadService uploadService;

    public UploadController(UploadService uploadService)
    {
        this.uploadService = uploadService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file) throws IOException
    {
        String imageUrl = uploadService.uploadImage(file);

        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);

        return ResponseEntity.ok(response);
    }
}