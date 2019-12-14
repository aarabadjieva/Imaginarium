package project.imaginarium.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                    String name = locale.getDisplayName().substring(locale.getDisplayName().indexOf('(') + 1, locale.getDisplayName().indexOf(')'));
                    return name;
                })
                .sorted()
                .collect(Collectors.toList());
    }
}
