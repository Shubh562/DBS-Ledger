package managers.jwt;


import com.auth0.jwt.algorithms.Algorithm;

public interface AccessTokenAlgorithm {
    Algorithm getAlgorithm();
}
