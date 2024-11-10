package dental.epms.service;

import dental.epms.dto.LoginDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);
    String generateToken(UserDetails userDetails, LoginDto loginDto);
    boolean isTokenValid(String token, UserDetails userDetails);
    void deleteByUserId(Long userId);

    Long returnIdByToken(String token);
}
