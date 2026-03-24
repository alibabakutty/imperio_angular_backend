package imperio.imperio_backend.login.service.serviceImpl;

import imperio.imperio_backend.exception.duplicateUserException.DuplicateUserException;
import imperio.imperio_backend.exception.invalidCredentialsException.InvalidCredentialsException;
import imperio.imperio_backend.exception.userNotFoundException.UserNotFoundException;
import imperio.imperio_backend.login.dto.LoginDTO;
import imperio.imperio_backend.login.dto.UserProfileDTO;
import imperio.imperio_backend.login.mapper.LoginMapper;
import imperio.imperio_backend.login.module.LoginModule;
import imperio.imperio_backend.login.repository.LoginRepository;
import imperio.imperio_backend.login.service.LoginService;
import imperio.imperio_backend.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginServiceImpl implements LoginService {

    private final JwtUtils jwtUtils;
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginMapper loginMapper;

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginDTO loginDTO) {
        validateLoginInput(loginDTO);

        LoginModule user = loginRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(user.getUsername());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(Map.of(
                        "token", jwtCookie.getValue(),
                        "username", user.getUsername(),
                        "role", user.getRole(),
                        "message", "Login successful"
                ));
    }

    @Override
    public ResponseEntity<Map<String, String>> register(LoginDTO loginDTO) {
        validateRegistrationInput(loginDTO);

        if (loginRepository.existsByUsername(loginDTO.getUsername())) {
            throw new DuplicateUserException("Username '" + loginDTO.getUsername() + "' is already taken");
        }

        if (loginRepository.existsByEmail(loginDTO.getEmail())) {
            throw new DuplicateUserException("Email already registered");
        }

        // Use mapper to create entity from DTO
        LoginModule user = loginMapper.toEntity(loginDTO);

        // Encode password before saving (security best practice)
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        // Default role if not provided
        if (user.getRole() == null) user.setRole("USER");

        loginRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    @Override
    public ResponseEntity<UserProfileDTO> getCurrentUser(String username) {
        LoginModule user = loginRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        return ResponseEntity.ok(loginMapper.toProfileDto(user));
    }

    @Override
    public ResponseEntity<Map<String, String>> logout() {
        ResponseCookie cleanCookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cleanCookie.toString())
                .body(Map.of("message", "Logout successful"));
    }

    @Override
    public ResponseEntity<Map<String, String>> changePassword(String username, String oldPassword, String newPassword) {
        // 1. Validation (Senior Move: use .isBlank() for cleaner code)
        if (isInvalid(oldPassword)) throw new IllegalArgumentException("Old password required");
        if (isInvalid(newPassword)) throw new IllegalArgumentException("New password required");

        // 2. Fetch User
        LoginModule user = loginRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        // 3. Verify current password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            // This will be caught by GlobalExceptionHandler and return 401
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        // 4. Update and Save
        user.setPassword(passwordEncoder.encode(newPassword));
        loginRepository.save(user);

        // 5. Clean Return
        return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
    }

    // Private Helpers
    private void validateLoginInput(LoginDTO dto) {
        if (dto == null) throw new IllegalArgumentException("Data cannot be null");
        if (isInvalid(dto.getUsername())) throw new IllegalArgumentException("Username is required");
        if (isInvalid(dto.getPassword())) throw new IllegalArgumentException("Password is required");
    }

    private void validateRegistrationInput(LoginDTO dto) {
        validateLoginInput(dto);
        if (isInvalid(dto.getEmail())) throw new IllegalArgumentException("Email is required");
        if (!dto.getEmail().contains("@")) throw new IllegalArgumentException("Invalid email format");
    }

    private boolean isInvalid(String str) {
        return str == null || str.isBlank();
    }
}
