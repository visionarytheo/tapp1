package com.tapp.tserver.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tapp.tserver.model.User;

@Repository
public class UserRepo {

    // injections
    private final NamedParameterJdbcTemplate db;

    // constructor
    public UserRepo(NamedParameterJdbcTemplate db) {
        this.db = db;
    }

    // methods
    public void upsertUser(User userFromToken) {
        String sql = """
                INSERT INTO users (first_name, last_name, email, image, last_login_at)
                VALUES(:first_name, :last_name, :email, :image, NOW())
                ON CONFLICT(email)
                DO UPDATE SET
                first_name = EXCLUDED.first_name,
                last_name = EXCLUDED.last_name,
                image = EXCLUDED.image,
                last_login_at = NOW();
                """;

        Map<String, Object> params = Map.of(
                "first_name", userFromToken.firstName(),
                "last_name", userFromToken.lastName(),
                "email", userFromToken.email(),
                "image", userFromToken.image());

        db.update(sql, params);
    }

    public Optional<User> findUserByEmail(String email) {
        String sql = """
                SELECT user_id, first_name, last_name, email, image, role, created_at, last_login_at
                FROM users
                WHERE email=:email
                """;

        return db.query(sql, Map.of("email", email), (rs, _) -> new User(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("image"),
                rs.getString("role"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("last_login_at").toInstant())).stream().findFirst();
    }

    public Optional<String> findUserRoleByEmail(String email) {
        String sql = """
                SELECT role
                FROM users
                WHERE email=:email
                """;

        return db.query(sql, Map.of("email", email), (rs, _) -> rs.getString("role")).stream().findFirst();
    }

    public List<User> findAllUsers() {
        String sql = """
                SELECT user_id, first_name, last_name, email, image, role, created_at, last_login_at
                FROM users
                ORDER BY created_at DESC
                """;

        return db.query(sql, (rs, _) -> new User(
                rs.getLong("user_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("image"),
                rs.getString("role"),
                rs.getTimestamp("created_at").toInstant(),
                rs.getTimestamp("last_login_at").toInstant()));

    }
}
