package com.project.imaginarium.services;

import com.project.imaginarium.base.ImaginariumApplicationBaseTests;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.exeptions.NoSuchRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RoleServiceTest extends ImaginariumApplicationBaseTests {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService service;


    @Test
    void allRoles_shouldReturnAllRoles() {
        List<Role> roles = new ArrayList<>();
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        assertEquals(service.allRoles(), roles);
    }

    @Test
    void findRoleByName_shouldReturnRoleIfExists() {
        Role role = new Role("ADMIN");
        Mockito.when(roleRepository.findByAuthority(role.getAuthority())).thenReturn(Optional.of(role));
        assertEquals(service.findRoleByName(role.getAuthority()), role);
    }

    @Test
    void findRoleByName_shouldThrowIfRole_NOT_Exists() {
        Role role = new Role("ADMIN");
        Mockito.when(roleRepository.findByAuthority(role.getAuthority())).thenThrow(NoSuchRole.class);
        assertThrows(NoSuchRole.class, () -> service.findRoleByName(role.getAuthority()));
    }
}