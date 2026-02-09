package challenge.forohub.api.domain.usuario;

import challenge.forohub.api.domain.ValidacionException;
import challenge.forohub.api.domain.perfil.Perfil;
import challenge.forohub.api.domain.perfil.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Long PERFIL_USER_ID = 1L;

    public Usuario registrar(DatosRegistroUsuario datos) {
        if (usuarioRepository.findByCorreoElectronico(datos.correoElectronico()) != null) {
            throw new ValidacionException("El correo electrónico ya está registrado");
        }

        String contrasenaEncriptada = passwordEncoder.encode(datos.contrasena());

        Usuario usuario = new Usuario(datos.nombre(), datos.correoElectronico(), contrasenaEncriptada);

        Perfil perfilUser = perfilRepository.findById(PERFIL_USER_ID)
                .orElseThrow(() -> new ValidacionException("Perfil USER no encontrado"));

        usuario.agregarPerfil(perfilUser);

        return usuarioRepository.save(usuario);
    }
}
