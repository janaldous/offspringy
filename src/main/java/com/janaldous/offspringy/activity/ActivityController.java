package com.janaldous.offspringy.activity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.janaldous.offspringy.activity.dto.ActivityDto;
import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;

@RestController
@RequestMapping("/api")
@Api(value="activity")
public class ActivityController {
	
	private final Logger log = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
    private IActivityService activityService;
	
	@Autowired
	private ModelMapper modelMapper;
    
	@ApiOperation(value = "View a list of available activities", response = Collection.class)
	@GetMapping("/activity")
    Collection<ActivityDto> activities(
    		@RequestParam(required = false) String name, 
    		@RequestParam(required = false) ActivityType type
    		) {
        return activityService.search(name, type)
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
    }
	
	@ApiOperation(value = "View details of an activity", response = ResponseEntity.class)
	@GetMapping("/activity/{id}")
    ResponseEntity<?> getActivityDetail(@PathVariable Long id) {
        Optional<Activity> activity = activityService.findActivity(id);
        
        return activity.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
	
	@ApiOperation(value = "Create an activity", response = ResponseEntity.class)
	@PostMapping("/activity")
    ResponseEntity<ActivityDto> createActivity(@Valid @RequestBody ActivityDto activity) throws URISyntaxException {
		log.info("Request to create activity: {}", activity);
        ActivityDto result = convertToDto(activityService.save(convertToEntity(activity)));
        
        return ResponseEntity.created(new URI("/api/activity/" + result.getId()))
                .body(result);
    }
	
	@ApiOperation(value = "Update an activity", response = ResponseEntity.class)
	@PutMapping("/activity/{id}")
    ResponseEntity<ActivityDto> updateActivity(@PathVariable Long id,
    		@Valid @RequestBody ActivityDto activity) {
		log.info("Request to update activity: {}", activity);
		return activityService.findActivity(id).map(a -> {
			a.setName(activity.getName());
			a.setProvider(activity.getProvider());
			a.setSummary(activity.getSummary());
			a.setType(activity.getType());
			ActivityDto result = convertToDto(activityService.save(convertToEntity(activity)));
			return ResponseEntity.ok().body(result);
		})
		.orElseGet(() -> {
			activity.setId(id);
			ActivityDto result = convertToDto(activityService.save(convertToEntity(activity)));
			return ResponseEntity.ok().body(result);
		});
    }
	
	@ApiOperation(value = "Delete an activity", response = ResponseEntity.class)
	@DeleteMapping("/activity/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
		log.info("Request to delete activity: {}", id);
		if (!activityService.hasActivity(id)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
        activityService.deleteById(id);
        
        return ResponseEntity.ok().build();
    }
	
	private ActivityDto convertToDto(Activity activity) {
		ActivityDto activityDto = modelMapper.map(activity, ActivityDto.class);
	    return activityDto;
	}
	
	private Activity convertToEntity(ActivityDto activityDto) {
		Activity activity = modelMapper.map(activityDto, Activity.class);
	    return activity;
	}
}
