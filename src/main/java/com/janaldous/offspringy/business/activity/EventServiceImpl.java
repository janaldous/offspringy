package com.janaldous.offspringy.business.activity;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.janaldous.offspringy.business.activity.data.EventRepository;
import com.janaldous.offspringy.business.activity.data.entity.Event;
import com.janaldous.offspringy.user.UpdateBookedEventException;
import com.janaldous.offspringy.web.dto.EventPatchDto;

@Service
class EventServiceImpl implements IEventBusiness {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Event getEvent(Long eventId) throws EventNotFoundException {
		Optional<Event> eventOptional = eventRepository.findById(eventId);
		if (eventOptional.isPresent()) {
			return eventOptional.get();
		} else {
			throw new EventNotFoundException();
		}
	}

//	@Override
//	public Event add(@Valid Event event) {
//		if (event.getId() != null) {
//			throw new IllegalArgumentException("Event already exists");
//		}
//		
//		return eventRepository.save(event);
//	}

	@Override
	public Event update(@Valid Event event) {
		if (eventRepository.findById(event.getId()).isPresent()
				&& event.getAttendees().size() > 0) {
			throw new UpdateBookedEventException();
		}
		
		return eventRepository.save(event);
	}

	@Override
	public Event patch(Long eventId, EventPatchDto eventDto) {
		Event event = eventRepository.findById(eventId).get();
		modelMapper.typeMap(EventPatchDto.class, Event.class).setPropertyCondition(Conditions.isNotNull());
		modelMapper.map(eventDto, event);
		return eventRepository.save(event);
	}

	@Override
	public boolean exists(Long id) {
		return eventRepository.findById(id).isPresent();
	}
}
