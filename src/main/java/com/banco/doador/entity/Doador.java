package com.banco.doador.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
@Table (name = "doador")
public class Doador {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String rg;

    @JsonProperty ("data_nasc")
    @Column(name = "data_nasc")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate datanasc;

    private String sexo;
    private String mae;
    private String pai;
    private String email;
    private String cep;
    private String endereco;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String estado;
    @JsonProperty("telefone_fixo")
    @Column(name = "telefone_fixo")
    private String telefonefixo;
    private String celular;
    private Double altura;
    private Double peso;
    @JsonProperty("tipo_sanguineo")
    @Column(name = "tipo_sanguineo")
    @NotBlank (message = "Tipo sanguíneo é obrigatório")
    @jakarta.validation.constraints.Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "Tipo sanguíneo inválido")


    private String tiposanguineo;

    // Getters e Setters

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getNome () {
        return nome;
    }

    public void setNome (String nome) {
        this.nome = nome;
    }

    public String getCpf () {
        return cpf;
    }

    public void setCpf (String cpf) {
        this.cpf = cpf;
    }

    public String getRg () {
        return rg;
    }

    public void setRg (String rg) {
        this.rg = rg;
    }

    public LocalDate getDatanasc () {
        return datanasc;
    }

    public void setDatanasc (LocalDate datanasc) {
        this.datanasc = datanasc;
    }

    public String getSexo () {
        return sexo;
    }

    public void setSexo (String sexo) {
        this.sexo = sexo;
    }

    public String getMae () {
        return mae;
    }

    public void setMae (String mae) {
        this.mae = mae;
    }

    public String getPai () {
        return pai;
    }

    public void setPai (String pai) {
        this.pai = pai;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getCep () {
        return cep;
    }

    public void setCep (String cep) {
        this.cep = cep;
    }

    public String getEndereco () {
        return endereco;
    }

    public void setEndereco (String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero () {
        return numero;
    }

    public void setNumero (Integer numero) {
        this.numero = numero;
    }

    public String getBairro () {
        return bairro;
    }

    public void setBairro (String bairro) {
        this.bairro = bairro;
    }

    public String getCidade () {
        return cidade;
    }

    public void setCidade (String cidade) {
        this.cidade = cidade;
    }

    public String getEstado () {
        return estado;
    }

    public void setEstado (String estado) {
        this.estado = estado;
    }

    public String getTelefonefixo () {
        return telefonefixo;
    }

    public void setTelefonefixo (String telefonefixo) {
        this.telefonefixo = telefonefixo;
    }

    public String getCelular () {
        return celular;
    }

    public void setCelular (String celular) {
        this.celular = celular;
    }

    public Double getAltura () {
        return altura;
    }

    public void setAltura (Double altura) {
        this.altura = altura;
    }

    public Double getPeso () {
        return peso;
    }

    public void setPeso (Double peso) {
        this.peso = peso;
    }

    public String getTiposanguineo () {
        return tiposanguineo;
    }

    public void setTiposanguineo (String tiposanguineo) {
        this.tiposanguineo = tiposanguineo;
    }
}
