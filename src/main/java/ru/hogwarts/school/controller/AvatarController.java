package ru.hogwarts.school.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.service.AvatarService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadAvatar(@PathVariable Long studentId,
                                       @RequestParam MultipartFile avatar) throws IOException {
        if (avatarService.uploadAvatar(studentId, avatar)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("{studentId}/avatar-from-db")
    public ResponseEntity downloadAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        if (avatar == null) {
            return ResponseEntity.notFound().build();
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
            return ResponseEntity.ok().headers(headers).body(avatar.getData());
        }
    }

    @GetMapping("{studentId}/avatar-from-disk")
    public void downloadAvatarFromDisk(@PathVariable Long studentId,
                                                 HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatarByStudentId(studentId);
        if (avatar == null) {
            response.setStatus(404);
        } else {
            Path path = Path.of(avatar.getFilePath());
            try (InputStream is = Files.newInputStream(path);
                 OutputStream os = response.getOutputStream();
            ) {
                response.setStatus(200);
                response.setContentType(avatar.getMediaType());
                response.setContentLength((int) avatar.getFileSize());
                is.transferTo(os);
            }
        }
    }
}
