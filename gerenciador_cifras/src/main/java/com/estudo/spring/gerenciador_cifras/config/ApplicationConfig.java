package com.estudo.spring.gerenciador_cifras.config;

import com.estudo.spring.gerenciador_cifras.repository.*;
import com.estudo.spring.gerenciador_cifras.repository.inmemory.*;
import com.estudo.spring.gerenciador_cifras.service.*;
import org.springframework.context.annotation.*;

@Configuration
public class ApplicationConfig {
    @Bean @Profile("!mongodb") public UsuarioRepository usuarioRepository() { return new InMemoryUsuarioRepository(); }
    @Bean @Profile("!mongodb") public CifraRepository cifraRepository() { return new InMemoryCifraRepository(); }
    @Bean @Profile("!mongodb") public PlaylistRepository playlistRepository() { return new InMemoryPlaylistRepository(); }
    @Bean public CifraService cifraService(CifraRepository c, UsuarioRepository u) { return new CifraService(c, u); }
    @Bean public PlaylistService playlistService(PlaylistRepository p, CifraRepository c, UsuarioRepository u) { return new PlaylistService(p, c, u); }
    @Bean public UsuarioService usuarioService(UsuarioRepository u, CifraService c, PlaylistService p) { return new UsuarioService(u, c, p); }
}
