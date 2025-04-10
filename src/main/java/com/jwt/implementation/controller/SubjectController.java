package com.jwt.implementation.controller;

import com.jwt.implementation.model.Subject;
import com.jwt.implementation.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    private SubjectRepository subjectRepository;

    // ✅ Add multiple subjects
    @PostMapping("/create")
    public ResponseEntity<?> createSubjects(@RequestBody List<Subject> subjects) {
        List<Subject> savedSubjects = subjectRepository.saveAll(subjects);
        return ResponseEntity.ok(savedSubjects);
    }

    // ✅ Get all subjects
    @GetMapping
    public ResponseEntity<?> getAllSubjects() {
        return ResponseEntity.ok(subjectRepository.findAll());
    }

    // ✅ Update subject by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubject(@PathVariable Long id, @RequestBody Subject updatedSubject) {
        return subjectRepository.findById(id)
                .map(subject -> {
                    subject.setSubjectName(updatedSubject.getSubjectName());
                    return ResponseEntity.ok(subjectRepository.save(subject));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete subject
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return ResponseEntity.ok("Subject deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subject not found");
        }
    }
}

