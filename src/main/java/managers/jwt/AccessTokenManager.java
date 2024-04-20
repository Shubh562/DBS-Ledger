package managers.jwt;


import managers.jwt.exceptions.JwtManagerException;

public interface AccessTokenManager<T, U, V> {
    U createToken(T payload);

    T verifyAndDecodeToken(V token) throws JwtManagerException;
}
