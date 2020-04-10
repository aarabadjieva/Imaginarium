package project.imaginarium.service.services.impl;

import org.springframework.stereotype.Service;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.service.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void seedRolesInDB() {
        roleRepository.saveAndFlush(new Role("ADMIN"));
        roleRepository.saveAndFlush(new Role("CLIENT"));
        roleRepository.saveAndFlush(new Role("GUIDE"));
        roleRepository.saveAndFlush(new Role("PARTNER"));
    }
}
