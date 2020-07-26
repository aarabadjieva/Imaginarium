package project.imaginarium.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Configuration
public class AppBeanConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public List<String> countries(){
       return Arrays.stream(Locale.getISOCountries())
                .map(c -> {
                    Locale locale = new Locale("en", c);
                    return locale.getDisplayName().substring(locale.getDisplayName().indexOf('(') + 1, locale.getDisplayName().indexOf(')'));
                })
                .sorted()
                .collect(Collectors.toList());
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new AuthenticationSuccessHandler();
    }
}
