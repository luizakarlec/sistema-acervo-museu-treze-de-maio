package org.example.museutrezemaio.service;

import org.example.museutrezemaio.model.Usuario;
import org.example.museutrezemaio.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void salvar(Usuario usuario){
        boolean emailEmUso = usuario.getIdUsuario() == 0
                ? usuarioRepository.existsByEmail(usuario.getEmail())
                : usuarioRepository.existsByEmailAndIdUsuarioNot(usuario.getEmail(), usuario.getIdUsuario());

        if (emailEmUso) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public List<Usuario> listaUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> BuscarId(Long id){
        return usuarioRepository.findById(id);
    }

}
