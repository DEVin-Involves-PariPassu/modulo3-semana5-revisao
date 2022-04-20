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
                .antMatchers(HttpMethod.POST, "/auth").permitAll() //qualquer combinação de requisição vai ser permitida
                .antMatchers(HttpMethod.POST, "/adduser").permitAll()
//                .antMatchers(HttpMethod.GET,"/home").permitAll()
                .anyRequest().authenticated()//demais request precisam de autenticação
                .and().csrf().disable()//desabilita sessões
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//o sistema não lembra quem enviou a ultima requisição
                .and()
                .addFilterBefore(
                    new AutenticacaoTokenFilter(tokenService, usuarioRepository), //serviço que filtra requisições que tem o token válido
                        UsernamePasswordAuthenticationFilter.class //o tipo de autenticação
                ); //adicionar um filtro customizado antes de todos os outros
    }

    @Override
    public void configure(WebSecurity web) throws Exception{

    }


}
