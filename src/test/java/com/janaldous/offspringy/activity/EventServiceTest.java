package com.janaldous.offspringy.activity;

import static org.mockito.BDDMockito.given;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.activity.data.EventRepository;
import com.janaldous.offspringy.activity.data.entity.Event;
import com.janaldous.offspringy.user.UpdateBookedEventException;
import com.janaldous.offspringy.user.data.entity.User;

@RunWith(SpringRunner.class)
public class EventServiceTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public IEventService eventService() {
            return new EventServiceImpl();
        }
        
        @Bean
        public ModelMapper modelMapper() {
        	return new ModelMapper();
        }
    }
	
	@Autowired
	private IEventService eventService;
	
	@MockBean
	private EventRepository repository;
	
	@Test(expected = UpdateBookedEventException.class)
    public void givenBookedEvent_whenUpdateEvent_thenThrowError() {
		// given
		Set<User> attendees = new HashSet<>();
		attendees.add(User.builder().firstName("dog").email("dog@gmail.com").password("holdmypoodle").build());
		attendees.add(User.builder().firstName("cat").email("cat@gmail.com").password("holdmypoodle").build());
		
        Event event = Event.builder()
        		.id(1L)
        		.title("Swimming class 1")
        		.description("first class")
        		.attendees(attendees)
        		.build();
        
		given(repository.findById(1L)).willReturn(Optional.of(event));
		
		// when
		eventService.update(event);
    }
}
