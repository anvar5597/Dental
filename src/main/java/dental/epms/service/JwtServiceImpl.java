package dental.epms.service;

import dental.epms.dto.EmployeeShortInfoDto;
import dental.epms.dto.LoginDto;
import dental.epms.entity.Employees;
import dental.epms.entity.JwtTokenEntity;
import dental.epms.repository.JwtTokenRepo;
import dental.epms.repository.EmployeeRepository;
import dental.exception.ResourceNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${token.signing.key}")
    private String jwtSigningKey;
    private final JwtTokenRepo jwtTokenRepo;
    private final EmployeeRepository empRepo;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return  Jwts.parserBuilder().setSigningKey(getSigningKey())
                    .build().parseClaimsJws(token).getBody();
        } catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException(e.getMessage());
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String generateToken(UserDetails userDetails, LoginDto loginDto) {

        String jwt = generateToken(new HashMap<>(), userDetails);
        JwtTokenEntity jwtToken = new JwtTokenEntity();
        jwtToken.setToken(jwt);
        jwtToken.setUser((Employees) userDetails);
        jwtToken.setApiType(loginDto.getApiType());
        jwtTokenRepo.save(jwtToken);

        return jwt;

    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Optional<JwtTokenEntity> optionalJwtTokenEntity = jwtTokenRepo.findByToken(token);
        if(optionalJwtTokenEntity.isEmpty())
            return false;
        final String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && username.equals(optionalJwtTokenEntity.get().getUser().getUsername());
    }

    @Override
    @Transactional
    public void deleteByUserId(Long userId) {
        jwtTokenRepo.deleteByUser(empRepo.findById(userId).get());
    }

    @Override
    public EmployeeShortInfoDto returnIdByToken(String token) {
       Optional<JwtTokenEntity> optionalJwtTokenEntity=jwtTokenRepo.findByToken(token);
       if (optionalJwtTokenEntity.isEmpty()){
           throw new ResourceNotFoundException("Bunday token yo`q");
       }
       JwtTokenEntity jwtTokenEntity = optionalJwtTokenEntity.get();
       EmployeeShortInfoDto shortInfoDto = toDto(jwtTokenEntity);

       return shortInfoDto;
    }

    EmployeeShortInfoDto toDto(JwtTokenEntity entity){
        EmployeeShortInfoDto dto = new EmployeeShortInfoDto();

        dto.setId(entity.getUser().getId());
        dto.setFirstName(entity.getUser().getFirstName());
        dto.setLastName(entity.getUser().getLastName());
        dto.setPatronymic(entity.getUser().getPatronymic());
        dto.setRole(entity.getUser().getRole());
        return dto;
    }

    @Override
    public String returnRoleByToken(String token) {
        Optional<JwtTokenEntity> optionalJwtTokenEntity=jwtTokenRepo.findByToken(token);
        if (optionalJwtTokenEntity.isEmpty()){
            throw new ResourceNotFoundException("Bunday token yo`q");
        }
        JwtTokenEntity jwtTokenEntity = optionalJwtTokenEntity.get();
        return jwtTokenEntity.getUser().getRole().toString();
    }
}
