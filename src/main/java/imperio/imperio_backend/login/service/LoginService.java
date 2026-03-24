package imperio.imperio_backend.login.service;

import imperio.imperio_backend.login.dto.LoginDTO;
import imperio.imperio_backend.login.dto.UserProfileDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;


public interface LoginService {
    ResponseEntity<Map<String, Object>> login(LoginDTO loginDTO);

    ResponseEntity<Map<String, String>> register(LoginDTO loginDTO);

    ResponseEntity<UserProfileDTO> getCurrentUser(String username);

    ResponseEntity<Map<String, String>> logout();

    ResponseEntity<Map<String, String>> changePassword(String username, String oldPassword, String newPassword);
}
