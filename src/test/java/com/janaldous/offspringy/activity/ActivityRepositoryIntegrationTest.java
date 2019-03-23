package com.janaldous.offspringy.activity;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.janaldous.offspringy.model.Activity;
import com.janaldous.offspringy.model.ActivityType;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ActivityRepositoryIntegrationTest {

	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private ActivityRepository activityRepository;
    
    @Test
    public void whenFindByActivityName_thenReturnActivity() {
        // given
        Activity activity = new Activity("activity 1");
        entityManager.persist(activity);
        
        Activity activity2 = new Activity("activity 2");
        entityManager.persist(activity2);
        
        entityManager.flush();
     
        // when
        List<Activity> results = activityRepository.findByName("activity 1");
     
        // then
        boolean found = results.stream()
        		.allMatch(a -> a.getName().contains("activity 1"));
        
        assertTrue(found);
    }
    
    @Test
    public void whenFindByActivityType_thenReturnActivity() {
        // given
        Activity activity = Activity.builder().name("activity 1").type(ActivityType.BOOK_NOW).build();
        entityManager.persist(activity);
        
        Activity activity2 = Activity.builder().name("activity 2").type(ActivityType.MORE).build();
        entityManager.persist(activity2);
        
        entityManager.flush();
     
        // when
        List<Activity> results = activityRepository.findByType(ActivityType.BOOK_NOW);
     
        // then
        boolean found = results.stream()
        		.allMatch(a -> a.getType().equals(ActivityType.BOOK_NOW));
        
        assertTrue(found);
    }
}
