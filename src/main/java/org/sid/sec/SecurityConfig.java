package org.sid.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private DataSource dataSource;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// specifier d'une maniere statique les utilisateurs qui ont les droits d'accees à utiliser cete application.
		//inMemoryAuthentication method cad les utilisateurs sont stockés dans la memoire just pour le test et pas dans la base de données.
		BCryptPasswordEncoder bcyt= getBCPE();
		System.out.println(bcyt.encode("1234"));
		/* 
		 * auth.inMemoryAuthentication().withUser("admin").password(bcyt.encode("1234"))
		 * .roles("ADMIN", "USER");
		 * auth.inMemoryAuthentication().withUser("user").password(bcyt.encode("1234")).
		 * roles("USER"); auth.inMemoryAuthentication().passwordEncoder(new
		 * BCryptPasswordEncoder());
		 */
		auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username as principal, password as credencials, active from users where username=?")
		.authoritiesByUsernameQuery("select username as principal, roles as role from users_roles where username=? ")
		.rolePrefix("ROLE_")
		.passwordEncoder(getBCPE()); //pour comparer le password qui se trouve dans la base avec le password qui vient l'utilisateur à  inserer. alors on les comparés avec le bycrypt password encoder.
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.formLogin().loginPage("/login");
		 http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		 http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
		 http.exceptionHandling().accessDeniedPage("/403");
	}

	@Bean
	BCryptPasswordEncoder getBCPE() {
		return new BCryptPasswordEncoder();
	}
}
