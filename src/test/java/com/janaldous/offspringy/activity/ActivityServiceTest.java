package com.janaldous.offspringy.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.entity.Activity;
import com.janaldous.offspringy.entity.ActivityType;
import com.janaldous.offspringy.entity.Event;

@RunWith(SpringRunner.class)
public class ActivityServiceTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public IActivityService activityService() {
            return new ActivityServiceImpl();
        }
        
        @Bean
        public ModelMapper modelMapper() {
        	return new ModelMapper();
        }
    }
	
	@Autowired
	private IActivityService activityService;
	
	@MockBean
	private ActivityRepository repository;
	
	@Test
    public void givenSearchName_whenSearchActivity_thenReturnActivities() {
		// given
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
		
		// when
		List<Activity> results = activityService.search("act", null);
        
		// then
		assertEquals(2, results.size());
		
        boolean found = results.stream()
        		.allMatch(a -> a.equals(activity2) || a.equals(activity3));
        
        assertTrue(found);
    }

	@Test
    public void givenSearchType_whenSearchActivity_thenReturnActivities() {
		// given
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

		// when
		List<Activity> results = activityService.search(null, ActivityType.MORE);
        
		// then
		
		assertEquals(2, results.size());
		
        boolean found = results.stream()
        		.allMatch(a -> a.equals(activity2) || a.equals(activity3));
        
        assertTrue(found);
    }
	
	@Test
    public void givenSearchNameAndType_whenSearchActivity_thenReturnActivities() {
		// given
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
		
		// when
		List<Activity> results = activityService.search("act", ActivityType.FREE);
        
		// then
		assertEquals(1, results.size());
		
        boolean found = results.stream()
        		.allMatch(a -> a.equals(activity3));
        
        assertTrue(found);
    }
	
	@Test
    public void givenSearchEmptyQuery_whenSearchActivity_thenReturnActivities() {
		// given
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
		
		// when
		List<Activity> results = activityService.search(null, null);
        
		// then
        assertEquals(allActivities, results);
    }
	
	@Test
    public void givenNewEvent_whenAddEvent_thenReturnUpdatedActivity() {
		// given
		Activity activity1 = Activity.builder()
				.name("swimming")
				.summary("summary 1")
				.type(ActivityType.BOOK_NOW)
				.build();
		
		Event event = Event.builder()
				.title("6 week course")
				.description("swimming for 6 weeks")
				.build();
		
        Optional<Activity> activityOptional = Optional.of(activity1);
        
		given(repository.findById(1L)).willReturn(activityOptional);
		
		Activity activityMock = Activity.builder()
				.name("swimming")
				.summary("summary 1")
				.type(ActivityType.BOOK_NOW)
				.build();
		activityMock.addEvent(event);
		given(repository.save(activity1)).willReturn(activityMock);
		
		// when
		Activity result = activityService.addEvent(1L, event);
        
		// then
		assertEquals("Event not successfully added to activity's events", 1, result.getEvents().size());
		assertTrue("Event not included in activity's events", result.getEvents().contains(event));
    }
}
