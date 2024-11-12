package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto;
/**
 * CatalogoDTO.java:
 * 
 * Objeto DTO que contien los datos de Catalogo.
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
public class CatalogoDTO {
	/** Id del Catalogo */
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
    /** Tipo de Catalogo  */
    private String tipoUsuario;
    /** Catalogo de catalogo */
    private String Catalogo;
    /** Si esta hablitado igual 1 y  deshabilitado igual 0 */
    private Integer activo;

}