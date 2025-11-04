package com.example.eduquiz.services;

import com.example.eduquiz.model.User;
import com.example.eduquiz.persistence.UserDAO;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Registra un nuevo usuario en la base de datos.
     */
    public boolean register(@NotNull String username, @NotNull String email, @NotNull String password) {
        // Verificar si el correo ya existe
        if (userDAO.getUserByEmail(email) != null) {
            System.out.println("⚠️ El correo ya está registrado: " + email);
            return false;
        }

        // Generar hash seguro
        String hash = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        User user = new User(username, email, hash);

        boolean created = userDAO.createUser(user);
        if (created) {
            System.out.println("✅ Usuario registrado correctamente: " + username);
        }
        return created;
    }

    /**
     * Inicia sesión verificando el hash de la contraseña.
     */
    public @Nullable User login(@NotNull String email, @NotNull String password) {
        User user = userDAO.getUserByEmail(email);

        if (user != null) {
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPasswordHash());
            if (result.verified) {
                System.out.println("✅ Inicio de sesión correcto: " + user.getUsername());
                return user;
            }
        }

        System.out.println("❌ Credenciales incorrectas para: " + email);
        return null;
    }

    /**
     * Incrementa el nivel del jugador en la base de datos.
     */
    public void levelUp(@NotNull User user) {
        int newLevel = user.getCurrentLevel() + 1;
        userDAO.updateCurrentLevel(user, newLevel);
        user.setCurrentLevel(newLevel);
        System.out.println("📈 Nivel aumentado a " + newLevel + " para " + user.getUsername());
    }
}

