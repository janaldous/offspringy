package com.janaldous.offspringy.activity;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.model.Activity;
import com.janaldous.offspringy.model.ActivityType;

@RunWith(SpringRunner.class)
public class ActivityServiceTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public IActivityService activityService() {
            return new ActivityServiceImpl();
        }
    }
	
	@Autowired
	private IActivityService activityService;
	
	@MockBean
	private ActivityRepository repository;
	
	@Test
    public void givenSearchName_whenSearchActivity_thenReturnActivities() {
		
		Activity activity1 = Activity.builder()
				.name("swimming")
				.summary("summary 1")
				.type(ActivityType.BOOK_NOW)
				.build();

        Activity activity2 = Activity.builder()
        		.name("acting")
        		.summary("summary 2")
        		.type(ActivityType.MORE)
        		.build();
        
        Activity activity3 = Activity.builder()
        		.name("Beginners acting")
        		.summary("summary 2")
        		.type(ActivityType.MORE)
        		.build();
		
        List<Activity> allActivities = Arrays.asList(activity1, activity2, activity3);
        
		given(repository.findAll()).willReturn(allActivities);
		
		List<Activity> results = activityService.search("act", null);
        
		assertEquals(2, results.size());
		
        boolean found = results.stream()
        		.allMatch(a -> a.equals(activity2) || a.equals(activity3));
        
        assertTrue(found);
        
    }

	@Test
    public void givenSearchType_whenSearchActivity_thenReturnActivities() {
		
		Activity activity1 = Activity.builder()
				.name("swimming")
				.summary("summary 1")
				.type(ActivityType.BOOK_NOW)
				.build();

        Activity activity2 = Activity.builder()
        		.name("acting")
        		.summary("summary 2")
        		.type(ActivityType.MORE)
        		.build();
        
        Activity activity3 = Activity.builder()
        		.name("Beginners acting")
        		.summary("summary 2")
        		.type(ActivityType.MORE)
        		.build();
		
        List<Activity> allActivities = Arrays.asList(activity1, activity2, activity3);
        
		given(repository.findAll()).willReturn(allActivities);
		
		List<Activity> results = activityService.search(null, ActivityType.MORE);
        
		assertEquals(2, results.size());
		
        boolean found = results.stream()
        		.allMatch(a -> a.equals(activity2) || a.equals(activity3));
        
        assertTrue(found);
        
    }
	
	@Test
    public void givenSearchNameAndType_whenSearchActivity_thenReturnActivities() {
		
		Activity activity1 = Activity.builder()
				.name("swimming")
				.summary("summary 1")
				.type(ActivityType.BOOK_NOW)
				.build();

        Activity activity2 = Activity.builder()
        		.name("acting")
        		.summary("summary 2")
        		.type(ActivityType.MORE)
        		.build();
        
        Activity activity3 = Activity.builder()
        		.name("Beginners acting")
        		.summary("summary 2")
        		.type(ActivityType.FREE)
        		.build();
		
        List<Activity> allActivities = Arrays.asList(activity1, activity2, activity3);
        
		given(repository.findAll()).willReturn(allActivities);
		
		List<Activity> results = activityService.search("act", ActivityType.FREE);
        
		assertEquals(1, results.size());
		
        boolean found = results.stream()
        		.allMatch(a -> a.equals(activity3));
        
        assertTrue(found);
        
    }
	
	@Test
    public void givenSearchEmptyQuery_whenSearchActivity_thenReturnActivities() {
		
		Activity activity1 = Activity.builder()
				.name("swimming")
				.summary("summary 1")
				.type(ActivityType.BOOK_NOW)
				.build();

        Activity activity2 = Activity.builder()
        		.name("acting")
        		.summary("summary 2")
        		.type(ActivityType.MORE)
        		.build();
        
        Activity activity3 = Activity.builder()
        		.name("Beginners acting")
        		.summary("summary 2")
        		.type(ActivityType.FREE)
        		.build();
		
        List<Activity> allActivities = Arrays.asList(activity1, activity2, activity3);
        
		given(repository.findAll()).willReturn(allActivities);
		
		List<Activity> results = activityService.search(null, null);
        
        assertEquals(allActivities, results);
        
    }
	
}
