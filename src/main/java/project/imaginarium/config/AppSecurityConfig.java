package project.imaginarium.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/styles/*", "/images/*", "/js/*", "/bootstrap4/*")
                .permitAll()
                .antMatchers("/plugins/easing/*","/plugins/font-awesome-4.7.0/*","/plugins/font-awesome-4.7.0/css/*","/plugins/font-awesome-4.7.0/fonts/*","/plugins/font-awesome-4.7.0/less/*","/plugins/font-awesome-4.7.0/scss/*", "/plugins/greensock/*","/plugins/Isotope/isotope.pkgd.min.js", "/plugins/magnific-popup/*", "/plugins/OwlCarousel2-2.2.1/*")
                .permitAll()
                .antMatchers("/plugins/parallax-js-master/*","/plugins/progressbar/progressbar.min.js", "/plugins/scrollmagic/*","/images/*", "/styles/*", "/styles/bootstrap4/*")
                .permitAll()
                .antMatchers("/", "/about", "/contacts", "/users/register/user", "/users/register/partner", "/blog","/resources/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/users/login").permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout().logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/unauthorized");
    }
}
