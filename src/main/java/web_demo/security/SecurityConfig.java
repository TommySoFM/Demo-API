package web_demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
////	@Autowired
////	private DataSource securityDataSource;
//
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		auth.jdbcAuthentication().dataSource(securityDataSource);

//		// add our users for in memory authentication
//
		auth.inMemoryAuthentication()
			.withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN", "EMPLOYEE")
			.and()
			.withUser("employee").password(passwordEncoder().encode("empPass")).roles("EMPLOYEE");
	}
//
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				.csrf().disable()
				.authorizeRequests()
                .anyRequest().authenticated()
//				.and()
//				.httpBasic();
//			.antMatchers("/").hasRole("EMPLOYEE")
//			.antMatchers("/admin/**").hasRole("ADMIN")
			.and()
			.formLogin()
//				.loginPage("/login")
				.loginProcessingUrl("/authenticateUser")
				.permitAll()
			.and()
			.logout()
				.permitAll();
//			.and()
//			.exceptionHandling().accessDeniedPage("/access-denied");

	}
//
	@Bean
	public PasswordEncoder passwordEncoder (){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return  passwordEncoder;
	}

}
//
//
//
//
//
//
