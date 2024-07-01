package com.daemawiki.daemawiki.global.security.token;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import com.daemawiki.daemawiki.global.security.SecurityProperties;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenizerImpl implements Tokenizer {
    private final SecurityProperties securityProperties;
    private final UserRepository userRepository;

    @Override
    public Mono<Tuple2<String, LocalDateTime>> createToken(String user) {
        return Mono.just(tokenize(user));
    }

    @Override
    public Mono<Authentication> getAuthentication(String token) {
        return parseClaims(token)
                .flatMap(claims -> createAuthenticatedUserBySubject(claims.getSubject()));
    }

    @Override
    public Mono<Tuple2<String, LocalDateTime>> reissue(String token) {
        return parseClaims(token)
                .filter(this::validateIssuer)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 잘못 된 토큰
                .map(claims -> tokenize(claims.getSubject())) // 아직 유효할 때
                .onErrorResume(ExpiredJwtException.class, this::handleExpiredToken)
                .onErrorMap(e -> new RuntimeException("Token reissue failed", e));
    }

    private Mono<Authentication> createAuthenticatedUserBySubject(String subject) {
        return userRepository.findByEmail(subject)
                .switchIfEmpty(Mono.error(new RuntimeException()))
                .map(user -> createAuthenticatedUser(subject, user))
                .map(TokenizerImpl::createUserNamePasswordAuthenticationToken);
    }

    private static UsernamePasswordAuthenticationToken createUserNamePasswordAuthenticationToken(User user) {
        return new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities());
    }

    private static User createAuthenticatedUser(String subject, UserEntity user) {
        return new User(subject, "", List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
    }

    private Mono<Claims> parseClaims(String token) {
        return Mono.fromCallable(() -> Jwts.parser()
                .setSigningKey(securityProperties.secret())
                .parseClaimsJws(token)
                .getBody());
    }

    private boolean validateIssuer(Claims claims) {
        return securityProperties.issuer()
                .equals(claims.getIssuer());
    }

    private Tuple2<String, LocalDateTime> tokenize(String user) {
        var now = LocalDateTime.now();
        var nowDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        var expirationDate = Date.from(now.plusHours(securityProperties.expiration()).atZone(ZoneId.systemDefault()).toInstant());

        String token = Jwts.builder()
                .setSubject(user)
                .setIssuer(securityProperties.issuer())
                .setIssuedAt(nowDate)
                .setNotBefore(nowDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, securityProperties.secret())
                .compact();

        return Tuples.of(token, now);
    }

    private Mono<Tuple2<String, LocalDateTime>> handleExpiredToken(ExpiredJwtException e) {
        var user = e.getClaims().getSubject();
        var expiration = e.getClaims().getExpiration();
        var now = new Date();

        if (now.before(getDatePlusHours(expiration, 2))) {
            return Mono.just(tokenize(user));
        }
        return Mono.error(new RuntimeException()); // 만료된 지 2시간이 넘었을 때
    }

    private static Date getDatePlusHours(Date date, int hours) {
        var cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal.getTime();
    }
}
