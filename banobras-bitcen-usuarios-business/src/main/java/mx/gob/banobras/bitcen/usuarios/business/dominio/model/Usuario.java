package mx.gob.banobras.bitcen.usuarios.business.dominio.model;
/**
 * Usuario.java:
 * 
 * Objeto de dominio para los servicios de usuarios
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer id;
    private String direccion;
    private String celular;
    private String email;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String contrasena;
    private String tipoUsuario;
    private String usuario;
    private Integer activo;


}
