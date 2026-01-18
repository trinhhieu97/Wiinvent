package wiinvent.com.vn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wiinvent.com.vn.constant.ResponseCode;
import wiinvent.com.vn.dto.request.LoginRequest;
import wiinvent.com.vn.dto.request.RegisterRequest;
import wiinvent.com.vn.dto.response.AccessTokenResponse;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.exception.ApplicationException;
import wiinvent.com.vn.domain.User;
import wiinvent.com.vn.repository.UserRepository;
import wiinvent.com.vn.security.JwtTokenProvider;
import wiinvent.com.vn.service.base.AuthService;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public ResponseData<AccessTokenResponse> login(LoginRequest request) {
        authenticate(request.getUsername(), request.getPassword());

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApplicationException(ResponseCode.USER_NOT_FOUND));

        String token = generateAccessToken(user.getId(), user.getUsername());
        return new ResponseData<>(new AccessTokenResponse(user.getId(), token));
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (BadCredentialsException ex) {
            throw new ApplicationException(ResponseCode.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseData<AccessTokenResponse> register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ApplicationException(ResponseCode.USERNAME_ALREADY_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        String token = generateAccessToken(user.getId(), user.getUsername());
        return new ResponseData<>(new AccessTokenResponse(user.getId(), token));
    }

    private String generateAccessToken(String userId, String username) {
        return jwtTokenProvider.generateToken(
                username,
                userId
        );
    }
}
