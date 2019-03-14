package com.janaldous.jugtours.activity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.jugtours.model.Activity;

@RestController
@RequestMapping("/api")
@Api(value="activity", description="Operations pertaining to activity")
public class ActivityController {
	private final Logger log = LoggerFactory.getLogger(ActivityController.class);
	@Autowired
    private ActivityRepository activityRepository;
    
	@ApiOperation(value = "View a list of available activities", response = Collection.class)
	@GetMapping("/activity")
    Collection<Activity> activities() {
        return activityRepository.findAll();
    }
	
	@ApiOperation(value = "View details of an activity", response = ResponseEntity.class)
	@GetMapping("/activity/{id}")
    ResponseEntity<?> getActivityDetail(@PathVariable Long id) {
        Optional<Activity> activity = activityRepository.findById(id);
        return activity.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@ApiOperation(value = "Create an activity", response = ResponseEntity.class)
	@PostMapping("/activity")
    ResponseEntity<Activity> createActivity(@Valid @RequestBody Activity activity) throws URISyntaxException {
        log.info("Request to create activity: {}", activity);
        Activity result = activityRepository.save(activity);
        return ResponseEntity.created(new URI("/api/activity/" + result.getId()))
                .body(result);
    }
	
	@ApiOperation(value = "Update an activity", response = ResponseEntity.class)
	@PutMapping("/activity")
    ResponseEntity<Activity> updateActivity(@Valid @RequestBody Activity activity) {
        log.info("Request to update activity: {}", activity);
        Activity result = activityRepository.save(activity);
        return ResponseEntity.ok().body(result);
    }
	
	@ApiOperation(value = "Delete an activity", response = ResponseEntity.class)
	@DeleteMapping("/activity/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        log.info("Request to delete activity: {}", id);
        activityRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}