package com.generation.artativa.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.artativa.model.UserLogin;
import com.generation.artativa.model.Usuario;
import com.generation.artativa.repository.UsuarioRepository;
import com.generation.artativa.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins ="*", allowedHeaders = "*")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/all")
	public ResponseEntity<List<Usuario>> getAll(){
		return ResponseEntity.ok(usuarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> getById(@PathVariable Long id){
		return usuarioRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UserLogin> login(@RequestBody Optional<UserLogin> usuarioLogin){
		return usuarioService.logarUsuario(usuarioLogin)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario){
        return usuarioService.cadastrarUsuario(usuario)
                .map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }
	
	@PutMapping("/editar")
	public ResponseEntity<Usuario> atualizarUsuario(@Valid @RequestBody Usuario usuario){
		return usuarioService.atualizarUsuario(usuario)
				.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
				.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletarUsuario(@PathVariable Long id){
		return usuarioRepository.findById(id)
				.map(resp -> {
					usuarioRepository.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
}
