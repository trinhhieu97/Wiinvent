package wiinvent.com.vn.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiinvent.com.vn.dto.request.LoginRequest;
import wiinvent.com.vn.dto.request.RegisterRequest;
import wiinvent.com.vn.dto.response.AccessTokenResponse;
import wiinvent.com.vn.dto.common.ResponseData;
import wiinvent.com.vn.service.base.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Register a new user",
            description = "Accepts username and password in the request body and registers a new user."
    )
    @PostMapping("/register")
    public ResponseData<AccessTokenResponse> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @Operation(
            summary = "Login user",
            description = "Accepts username and password in the request body and logs in the user."
    )
    @PostMapping("/login")
    public ResponseData<AccessTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
