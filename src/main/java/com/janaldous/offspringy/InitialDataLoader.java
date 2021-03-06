package com.janaldous.offspringy;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.janaldous.offspringy.business.customer.data.CustomerEntity;
import com.janaldous.offspringy.business.customer.data.ICustomerRepository;
import com.janaldous.offspringy.user.data.PrivilegeRepository;
import com.janaldous.offspringy.user.data.RoleRepository;
import com.janaldous.offspringy.user.data.UserRepository;
import com.janaldous.offspringy.user.data.entity.Privilege;
import com.janaldous.offspringy.user.data.entity.Role;
import com.janaldous.offspringy.user.data.entity.User;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
  
    @Autowired
    private PrivilegeRepository privilegeRepository;
  
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ICustomerRepository customerRepository;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");
  
        List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);        
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_PROVIDER", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
 
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role userRole = roleRepository.findByName("ROLE_USER");
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("admin@test.com");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);
        
        User provider = new User();
        provider.setFirstName("John");
        provider.setLastName("Smith");
        provider.setPassword(passwordEncoder.encode("smith"));
        provider.setEmail("john@provider.com");
        provider.setRoles(Arrays.asList(adminRole));
        provider.setEnabled(true);
        userRepository.save(provider);
        
        User normalUser = new User();
        normalUser.setFirstName("John");
        normalUser.setLastName("Doe");
        normalUser.setPassword(passwordEncoder.encode("doe"));
        normalUser.setEmail("john@doe.com");
        normalUser.setRoles(Arrays.asList(userRole));
        normalUser.setEnabled(true);
        userRepository.save(normalUser);
        
        userRepository.findAll().forEach(System.out::println);
        
        CustomerEntity customer = new CustomerEntity();
        customer.setUser(normalUser);
        customer.setName(normalUser.getFirstName());
        customerRepository.save(customer);
        
        alreadySetup = true;
    }
 
    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
  
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
 
    @Transactional
    private Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
