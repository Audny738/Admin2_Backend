package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.entity.Job;
import com.gate_software.ams_backend.repository.JobRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @PostMapping("/")
    @Operation(summary = "Create a Job", description = "Create a new job.")
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) {
        Job createdJob = jobRepository.save(job);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @GetMapping("/")
    @Operation(summary = "Get All Jobs", description = "Get a list of all jobs.")
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{jobId}")
    @Operation(summary = "Get a Job by ID", description = "Get a job by its ID.")
    @Parameters({
            @Parameter(name = "jobId", description = "ID of the job to retrieve", required = true)
    })
    public ResponseEntity<Job> getJobById(@PathVariable Integer jobId) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        return jobOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{jobId}")
    @Operation(summary = "Update a Job", description = "Update an existing job.")
    @Parameters({
            @Parameter(name = "jobId", description = "ID of the job to update", required = true)
    })
    public ResponseEntity<Job> updateJob(@PathVariable Integer jobId, @Valid @RequestBody Job updatedJob) {
        if (jobRepository.existsById(jobId)) {
            updatedJob.setId(jobId);
            Job savedJob = jobRepository.save(updatedJob);
            return ResponseEntity.ok(savedJob);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{jobId}")
    @Operation(summary = "Delete a Job", description = "Delete an existing job.")
    @Parameters({
            @Parameter(name = "jobId", description = "ID of the job to delete", required = true)
    })
    public ResponseEntity<Void> deleteJob(@PathVariable Integer jobId) {
        if (jobRepository.existsById(jobId)) {
            jobRepository.deleteById(jobId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
