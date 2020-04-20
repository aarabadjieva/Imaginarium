package project.imaginarium.service.services.impl;

import org.springframework.stereotype.Service;
import project.imaginarium.data.models.users.Role;
import project.imaginarium.data.repositories.RoleRepository;
import project.imaginarium.exeptions.NoSuchRole;
import project.imaginarium.service.services.RoleService;

import java.util.List;

import static project.imaginarium.exeptions.ExceptionMessage.ROLE_NOT_FOUND_MESSAGE;

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

    @Override
    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByAuthority(name).orElseThrow(() -> new NoSuchRole(ROLE_NOT_FOUND_MESSAGE));
    }


}
