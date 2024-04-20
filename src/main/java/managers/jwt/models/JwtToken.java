package managers.jwt.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class JwtToken {
    private String token;

    private long expiresAt;
}
