package web_demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
	@Autowired
	private DataSource securityDataSource;
//
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(securityDataSource);
	}
//		// add our users for in memory authentication
//
//		auth.inMemoryAuthentication()
//			.withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN", "EMPLOYEE")
//			.and()
//			.withUser("employee").password(passwordEncoder().encode("empPass")).roles("EMPLOYEE");

	@Bean
	public CorsConfigurationSource corsConfigurationSource(){
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("http://localhost:8080");
		configuration.addAllowedOrigin("http://localhost:8081");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.addExposedHeader("Authorization");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors()
				.and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/demo/add").permitAll()
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
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll();
//			.and()
//			.exceptionHandling().accessDeniedPage("/access-denied");

	}
//
//	@Bean
//	public PasswordEncoder passwordEncoder (){
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		return  passwordEncoder;
//	}

}






