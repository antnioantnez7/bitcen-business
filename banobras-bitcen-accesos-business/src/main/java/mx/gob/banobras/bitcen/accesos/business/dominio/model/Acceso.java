package mx.gob.banobras.bitcen.accesos.business.dominio.model;
/**
 * Acceso.java:
 * 
 * Objeto de dominio para los servicios de acceso
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
public class Acceso {
    private Integer id;
    private String direccion;
    private String celular;
    private String email;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombre;
    private String contrasena;
    private String tipoUsuario;
    private String Acceso;
    private Integer activo;


}
