package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final StudentRepository studentRepository;
    private final AvatarRepository avatarRepository;

    public AvatarService(StudentRepository studentRepository, AvatarRepository avatarRepository) {
        this.studentRepository = studentRepository;
        this.avatarRepository = avatarRepository;
    }

    public boolean uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("The method {} was called", "<<uploadAvatar>>");
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return false;
        }

        Path filePath = Path.of(avatarsDir + student + "." + getExtentions(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = avatarRepository.findByStudentId(studentId);
        if (avatar == null) {
            avatar = new Avatar();
        }
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        return true;
    }

    /* Получение подстроки строки (расширения файла), начиная с символа "." до конца строки,
    начиная поиск "." с конца.*/
    private String getExtentions(String fileName) {
        logger.info("The method {} was called", "<<getExtentions>>");
        return fileName.substring(fileName.lastIndexOf(".")+1);
    }

    public Avatar findAvatarByStudentId(Long studentId) {
        logger.info("The method {} was called", "<<findAvatarByStudentId>>");
        return avatarRepository.findByStudentId(studentId);
    }

    public List getAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("The method {} was called", "<<getAvatars>>");
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent()
                .stream().map(item -> item.toString()).collect(Collectors.toList());
    }
}
