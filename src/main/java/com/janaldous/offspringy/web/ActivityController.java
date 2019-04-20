package com.janaldous.offspringy.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.janaldous.offspringy.activity.EventNotFound;
import com.janaldous.offspringy.activity.data.entity.ActivityType;
import com.janaldous.offspringy.web.dto.ActivityDto;
import com.janaldous.offspringy.web.serviceadapters.ActivityServiceAdapter;

@RestController
@RequestMapping("/api")
@Api(value="activity")
public class ActivityController {

	private final Logger log = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	private ActivityServiceAdapter activityService;

	@Autowired
	private ModelMapper modelMapper;
	
	@ApiOperation(value = "View a list of available activities", response = Collection.class)
	@GetMapping("/activity")
	public Collection<ActivityDto> activities(
			@RequestParam(required = false) String name, 
			@RequestParam(required = false) ActivityType type
			) {
		return activityService.search(name, type);
	}

	@ApiOperation(value = "View details of an activity", response = ResponseEntity.class)
	@GetMapping("/activity/{id}")
	public ResponseEntity<?> getActivityDetail(@PathVariable Long id) {
		Optional<ActivityDto> activityDto = activityService.findActivity(id);

		return activityDto.map(response -> ResponseEntity.ok().body(response))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@ApiOperation(value = "Create an activity", response = ResponseEntity.class)
	@PostMapping("/activity")
	public ResponseEntity<ActivityDto> createActivity(@Valid @RequestBody ActivityDto activity) throws URISyntaxException {
		log.info("Request to create activity: {}", activity);
		ActivityDto result = activityService.create(activity);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(result);
	}

	@ApiOperation(value = "Update an activity", response = ResponseEntity.class)
	@PutMapping("/activity/{id}")
	public ResponseEntity<ActivityDto> putActivity(@PathVariable Long id,
			@Valid @RequestBody ActivityDto activity) throws EventNotFound {
		log.info("Request to update activity: {}", activity);
		Optional<ActivityDto> activityOptional = activityService.findActivity(id);
		
		if(activityOptional.isPresent()) {
			ActivityDto result = activityService.update(activity);
			return ResponseEntity.ok()
					.body(result);
		} else {
			activity.setId(id);
			ActivityDto result = activityService.create(activity);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(result);
		}
	}

	@ApiOperation(value = "Delete an activity", response = ResponseEntity.class)
	@DeleteMapping("/activity/{id}")
	public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
		log.info("Request to delete activity: {}", id);
		if (!activityService.findActivity(id).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		activityService.deleteById(id);

		return ResponseEntity.ok().build();
	}
}