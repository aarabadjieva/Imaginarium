package project.imaginarium.service.services;

import project.imaginarium.data.models.users.Role;

import java.util.List;

public interface RoleService {

    void seedRolesInDB();
    List<Role> allRoles();
}
