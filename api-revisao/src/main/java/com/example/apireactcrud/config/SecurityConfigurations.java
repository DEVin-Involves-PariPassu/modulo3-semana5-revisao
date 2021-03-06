package com.example.apireactcrud.config;

import com.example.apireactcrud.repository.UsuarioRepository;
import com.example.apireactcrud.service.AutenticacaoService;
import com.example.apireactcrud.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    private final AutenticacaoService autenticacaoService;

    public SecurityConfigurations(UsuarioRepository usuarioRepository, TokenService tokenService, AutenticacaoService autenticacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenService = tokenService;
        this.autenticacaoService = autenticacaoService;
    }


    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(autenticacaoService).passwordEncoder(encoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll() //qualquer combina????o de requisi????o vai ser permitida
                .antMatchers(HttpMethod.POST, "/adduser").permitAll()
//                .antMatchers(HttpMethod.GET,"/home").permitAll()
                .anyRequest().authenticated()//demais request precisam de autentica????o
                .and().csrf().disable()//desabilita sess??es
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//o sistema n??o lembra quem enviou a ultima requisi????o
                .and()
                .addFilterBefore(
                    new AutenticacaoTokenFilter(tokenService, usuarioRepository), //servi??o que filtra requisi????es que tem o token v??lido
                        UsernamePasswordAuthenticationFilter.class //o tipo de autentica????o
                ); //adicionar um filtro customizado antes de todos os outros
    }

    @Override
    public void configure(WebSecurity web) throws Exception{

    }


}
