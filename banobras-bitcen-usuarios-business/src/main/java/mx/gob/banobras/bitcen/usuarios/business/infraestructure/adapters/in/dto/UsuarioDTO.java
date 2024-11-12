package mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto;
/**
 * UsuarioDTO.java:
 * 
 * Objeto DTO que contien los del producto.
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
public class UsuarioDTO {
	/** Id del usuario */
	private Integer id;
	/** Direccion o Domicilio */
    private String direccion;
    /** Numero de celular */
    private String celular;
    /** Correo electronico */
    private String email;
    /** Apellido Paterno */
    private String apellidoPaterno;
    /** Apellido Materno */
    private String apellidoMaterno;
    /** Nombre completo */
    private String nombre;
    /** Contrasena o password */
    private String contrasena;
    /** Tipo de usuario  */
    private String tipoUsuario;
    /** Usuario de acceso */
    private String usuario;
    /** Si esta hablitado igual 1 y  deshabilitado igual 0 */
    private Integer activo;

}