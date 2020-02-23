package web_demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import web_demo.repository.UserRepository;
import web_demo.service.UserService;

import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Value("${frontend.endpoint}")
	private String frontendEndpoint;

	@Bean
	public CorsConfigurationSource corsConfigurationSource(){
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedOrigin(frontendEndpoint);
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
				.antMatchers("/user/add").permitAll()
				.antMatchers("/user/isNameUsed").permitAll()
				.antMatchers("/isSessionValid").permitAll()
                .anyRequest().authenticated()
			.and()
			.formLogin()
				.loginProcessingUrl("/authenticateUser")
				.successForwardUrl("/loginSuccess")
				.failureForwardUrl("/loginFailed")
				.permitAll()
			.and()
			.logout()
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.permitAll();
//			.and()
//				.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//			.exceptionHandling().accessDeniedPage("/accessDenied");


	}
}






