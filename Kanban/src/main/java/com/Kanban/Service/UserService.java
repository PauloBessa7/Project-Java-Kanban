package com.Kanban.Service;

import com.Kanban.Configuration.SecurityConfiguration;
import com.Kanban.Configuration.SecurityConfiguration;
import com.Kanban.DTO.CreateUserDTO;
import com.Kanban.DTO.JwtTokenDTO;
import com.Kanban.DTO.LoginUserDTO;
import com.Kanban.Enums.RoleName;
import com.Kanban.Modal.Role;
import com.Kanban.Modal.User;
import com.Kanban.Modal.UserDetailsImp;
import com.Kanban.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public JwtTokenDTO authenticateUser(LoginUserDTO loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new JwtTokenDTO(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public User createUser(CreateUserDTO newUserDTO) {
        User newUser = new User();
        newUser.setEmail(newUserDTO.email());
        //Deixa a senha encriptada com BCrypt
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newUserDTO.password());

        newUser.setPassword(encodedPassword);
        switch (newUserDTO.role()){
            case "User": newUser.setRoles(List.of(Role.builder().name(RoleName.ROLE_USER).build()));
            break;
            case "Admin": newUser.setRoles(List.of(Role.builder().name(RoleName.ROLE_ADMINISTRATOR).build()));
            break;
            default: throw new RuntimeException("O novo usuario tem que ter a role User, ou Admin");
        }
        // Salva o novo usuário no banco de dados
        return userRepository.save(newUser);
    }
}
