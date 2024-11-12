package mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto;
/**
 * UsuarioDTO.java:
 * 
 * Objeto DTO que contien los datos de Acceso.
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
public class AccesoDTO {
	/** Id del Acceso */
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
    /** Tipo de Acceso  */
    private String tipoUsuario;
    /** Acceso de acceso */
    private String Acceso;
    /** Si esta hablitado igual 1 y  deshabilitado igual 0 */
    private Integer activo;

}