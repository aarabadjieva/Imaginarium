package project.imaginarium.service.services.impl;

import com.project.imaginarium.ImaginariumApplicationTests;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.service.services.RoleService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class RoleServiceImplTest extends ImaginariumApplicationTests {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService service;


    @Test
    void allRoles() {
        List<Role> roles = new ArrayList<>();
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        assertEquals(service.allRoles(), roles);
    }

    @Test
    void findRoleByName() {
        Role role = new Role("ADMIN");
        Mockito.when(roleRepository.findByAuthority(role.getAuthority())).thenReturn(role);
        assertEquals(service.findRoleByName(role.getAuthority()), role);
    }
}