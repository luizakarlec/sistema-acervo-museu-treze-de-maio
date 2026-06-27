package org.example.museutrezemaio.configuration;

import org.example.museutrezemaio.model.Usuario;
import org.example.museutrezemaio.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        inicializarUsuarios();
    }

    private void inicializarUsuarios() {
        if (userRepository.findByEmail("admin@email.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setEmail("admin@email.com");
            admin.setNome("Administrador");
            admin.setSobrenome("Do Museu");
            admin.setSenha(passwordEncoder.encode("1234"));
            admin.setTipo("ADMIN");
            userRepository.save(admin);
        }

        if (userRepository.findByEmail("bibliotecario@email.com").isEmpty()) {
            Usuario user = new Usuario();
            user.setEmail("bibliotecario@email.com");
            user.setNome("Bibliotecario");
            user.setSobrenome("Do Museu");
            user.setSenha(passwordEncoder.encode("1234"));
            user.setTipo("BIBLIOTECARIO");
            userRepository.save(user);
        }
    }
}
