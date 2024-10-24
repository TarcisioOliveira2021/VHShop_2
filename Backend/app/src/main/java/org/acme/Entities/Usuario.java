package org.acme.Entities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.acme.Entities.Enums.TipoUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String senha;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario role;

    public TipoUsuario getRole() {
        return role;
    }

    public Usuario() {
    }

    public Usuario(String nome, String senha) {
        this.role = TipoUsuario.USER;
        this.nome = nome;
        this.senha = criptografarSenha(senha);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String criptografarSenha(String senha) {

        MessageDigest md;
        StringBuilder hexString = new StringBuilder();

        try {
            md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(senha.getBytes());

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha");
        }

        return hexString.toString();
    }
}
