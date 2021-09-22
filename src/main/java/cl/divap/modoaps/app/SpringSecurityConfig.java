package cl.divap.modoaps.app;

import cl.divap.modoaps.app.auth.handler.LoginFailedHandler;
import cl.divap.modoaps.app.auth.handler.LoginSuccessHandler;
import cl.divap.modoaps.app.models.service.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // @Autowired
    // private DataSource dataSource;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailedHandler failedHandler;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEventPublisher eventPublisher;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //
        http.authorizeRequests()
                .antMatchers("/", "/css/**", "/js/**", "/images/**", "/app/**", "/libs/**", "/index").permitAll()
                // .antMatchers("/funcionario/listar").hasAnyRole("USER")
                // .antMatchers("/funcionario/ver/**").hasAnyRole("USER")
                // .antMatchers("/funcionario/form/**").hasAnyRole("ADMIN")
                // .antMatchers("/funcionario/eliminar/**").hasAnyRole("ADMIN")
                // .antMatchers("/contrato/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .successHandler(loginSuccessHandler)
                        .failureHandler(failedHandler)
                        .loginPage("/login")
                    .permitAll()
                .and()
                    .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {

        /*
        PasswordEncoder encoder = this.passwordEncoder;
        UserBuilder users = User.builder().passwordEncoder(password -> {
            return encoder.encode(password);
        });

        builder.inMemoryAuthentication()
                .withUser(users.username("admin").password("12345").roles("ADMIN", "USER"))
                .withUser(users.username("eguzman").password("12345").roles("USER"));
         */

        // JDVC Authentication
        /*
        builder.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(passwordEncoder)
            .usersByUsernameQuery("select username, password, enabled from users where username=?")
            .authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id = u.id) where u.username=?");
         */

        // JPA
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .authenticationEventPublisher(eventPublisher);
    }
}
