package imperio.imperio_backend.login.controller;

import imperio.imperio_backend.login.dto.LoginDTO;
import imperio.imperio_backend.login.dto.UserProfileDTO;
import imperio.imperio_backend.login.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@Valid @RequestBody LoginDTO loginDTO){
        // No try-catch needed! GlobalExceptionHandler handles any failures.
        return loginService.login(loginDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerController(@Valid @RequestBody LoginDTO loginDTO){
        return loginService.register(loginDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutController(){
        return loginService.logout();
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile(java.security.Principal principal) {
        if (principal == null) {
            // Return 401 instead of crashing with 500
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
        }

        // This calls your service method:
        // @Override public ResponseEntity<UserProfileDTO> getCurrentUser(String username)
        return loginService.getCurrentUser(principal.getName());
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(Principal principal, @RequestParam String oldPassword, @RequestParam String newPassword){
        return loginService.changePassword(principal.getName(), oldPassword, newPassword);
    }
}
