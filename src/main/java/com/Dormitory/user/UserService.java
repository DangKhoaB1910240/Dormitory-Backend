package com.Dormitory.user;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Dormitory.config.JwtGenerator;
import com.Dormitory.exception.UsernameAlreadyExistsException;
import com.Dormitory.login.LoginDTO;
import com.Dormitory.role.Role;
import com.Dormitory.role.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private JwtGenerator jwtGenerator;

    @Autowired //tiêm phụ thuộc vào
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    // @Transactional //Đánh dấu là 1 giao dịch, nếu có vấn đề nó rollback hết
    // public void register(User user) {

    //     // Nếu tài khoản tồn tại thì ném ra exception
    //     if(userRepository.existsByUsername(user.getUsername())) {
    //         throw new UsernameAlreadyExistsException("Account already exists", BAD_REQUEST);
    //     }

    //     //Tìm role
    //     Role role = roleRepository.findByName("CUSTOMER");

    //     // Set role
    //     user.setRoles(Collections.singletonList(role));
        
    //     // Mã hóa mật khẩu
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));

    //     // Save Mật khẩu
    //     userRepository.save(user);
    // }

    public String login(LoginDTO loginDTO) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtGenerator.generateToken(authentication);

        return token;
    }

}
