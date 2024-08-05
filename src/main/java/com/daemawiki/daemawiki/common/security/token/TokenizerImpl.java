package com.daemawiki.daemawiki.common.security.token;

import com.daemawiki.daemawiki.common.security.SecurityProperties;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.repository.UserRepository;
import dev.paseto.jpaseto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenizerImpl implements Tokenizer, TokenUtils {

    @Override
    public Mono<String> createToken(String user) {
        return Mono.just(tokenize(user));
    }

    @Override
    public Mono<String> reissue(String token) {
        return parseToken(removePrefix(token))
                .map(paseto -> tokenize(paseto.getClaims().getSubject())) // 아직 유효할 때
                .onErrorResume(ExpiredPasetoException.class, this::handleExpiredToken)
                .onErrorMap(e -> new RuntimeException());
    }

    @Override
    public Mono<Authentication> getAuthentication(String token) {
        return parseToken(token)
                .flatMap(paseto -> createAuthenticatedUserBySubject(paseto.getClaims().getSubject()));
    }

    @Override
    public String removePrefix(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(TOKEN_BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
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

    private Mono<Paseto> parseToken(String token) {
        return Mono.fromCallable(() -> {
            var parser = Pasetos.parserBuilder()
                    .setSharedSecret(secretKey)
                    .build();

            var tokenWithPrefix = token.startsWith(securityProperties.prefix())
                    ? token
                    : addPasetoPrefix(token);

            var paseto = parser.parse(tokenWithPrefix);

            if (!validateIssuer(paseto)) {
                throw new RuntimeException("invalid 토큰");
            }

            return paseto;
        });
    }

    private String addPasetoPrefix(String token) {
        return securityProperties.prefix() + token;
    }

    private boolean validateIssuer(Paseto paseto) {
        return securityProperties.issuer()
                .equals(paseto.getClaims().getIssuer());
    }

    private String tokenize(String user) {
        Instant now = Instant.now();
        Instant expiration = now.plus(securityProperties.expiration(), ChronoUnit.HOURS);

        return Pasetos.V2.LOCAL.builder()
                .setSharedSecret(secretKey)
                .setSubject(user)
                .setIssuedAt(now)
                .setIssuer(securityProperties.issuer())
                .setExpiration(expiration)
                .setIssuer(securityProperties.issuer())
                .claim("nbf", now)
                .compact();
    }

    private Mono<String> handleExpiredToken(ExpiredPasetoException e) {
        var claims = e.getPaseto().getClaims();

        var user = claims.getSubject();
        var expiration = claims.getExpiration();
        var now = new Date();

        if (now.before(getDatePlusHours(Date.from(expiration), 30))) {
            return Mono.just(tokenize(user));
        }
        return Mono.error(new RuntimeException()); // 만료된 지 2시간이 넘었을 때
    }

    private static Date getDatePlusHours(Date date, int minute) {
        var cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    private static final String TOKEN_BEARER_PREFIX = "Bearer ";

    private final SecurityProperties securityProperties;
    private final UserRepository userRepository;
    private final SecretKey secretKey;
}