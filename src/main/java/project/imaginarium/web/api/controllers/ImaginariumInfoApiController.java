package project.imaginarium.web.api.controllers;

import project.imaginarium.service.services.ArticlesService;
import project.imaginarium.service.services.RoleService;
import project.imaginarium.service.services.user.UserService;
import project.imaginarium.web.api.models.article.ArticleResponseModel;
import project.imaginarium.web.api.models.user.response.GuideResponseModel;
import project.imaginarium.web.api.models.user.response.PartnerResponseModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ImaginariumInfoApiController {

    private final UserService userService;
    private final RoleService roleService;
    private final ArticlesService articlesService;
    private final ModelMapper mapper;

    @GetMapping(value = "/api/about")
    public ResponseEntity<?> getAbout(){
        if (roleService.allRoles().isEmpty()){
            roleService.seedRolesInDB();
        }
        List<PartnerResponseModel> allPartners = userService.partners();
        List<GuideResponseModel> allGuides = userService.guides();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("partners", allPartners);
        result.put("guides", allGuides);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/api/blog")
    public List<ArticleResponseModel> getBlog() {
        return articlesService.findAllArticles()
                .stream()
                .map(a -> mapper.map(a, ArticleResponseModel.class))
                .collect(Collectors.toList());
    }

}
