package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto;
/**
 * ResponseCatalogoDTO.java:
 * 
 * Objeto que contiene los datos de entrada del catalogo de aplicativos. 
 *  
 * @author Antonio Antunez Barbosa
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogoUsuarioDTO {
	/** Identificador del catálogo de usuarios */
	@Schema(example = "SIGEVI")
	private int identificador;
	/** Identificador de usuario a nivel institucional */
	@Schema(example = "antnioantnz7")
	private String usuario;
	/** Apellido paterno del usuario */
	@Schema(example = "Antunez")
	private String paterno;
	/** Apellido materno del usaurio */
	@Schema(example = "Barbosa")
	private String materno; 
	/** Nombre del usuario */
	@Schema(example = "Antonio")
	private String nombre;
	/** Indica si el usuario tiene una sesión activa en el aplicativo */
	@Schema(example = "N")
	private String sesionActiva;
	/** Indica si el usuario tiene bloqueo en el aplicativo */
	@Schema(example = "N")
	private String usuarioBloqueado;
	/** Contabiliza los intentos fallidos */
	private int intentosFallidos;
	/** Usuario que realiza el registro */
	@Schema(example = "1")
	private int usuarioRegistro;
	/** Fecha hora en que se realiza el registro */
	private LocalDateTime fechaRegistro;
	/** Usuario que realiza una modificación */
	@Schema(example = "1")
	private int usuarioModifica;
	/** Fecha hora en que se realiza una modificacion */
	private LocalDateTime fechaModifica;
	/** Propiedad para convertir el modelo a formato Json*/
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("identificador").append('"').append(":").append('"').append(this.identificador).append('"').append(",");
		sb.append('"').append("usuario").append('"').append(":").append('"').append(this.usuario).append('"').append(",");
		sb.append('"').append("paterno").append('"').append(":").append('"').append(this.paterno).append('"').append(",");
		sb.append('"').append("materno").append('"').append(":").append('"').append(this.materno).append('"').append(",");
		sb.append('"').append("nombre").append('"').append(":").append('"').append(this.nombre).append('"').append(",");
		sb.append('"').append("sesionActiva").append('"').append(":").append('"').append(this.sesionActiva).append('"').append(",");
		sb.append('"').append("usuarioBloqueado").append('"').append(":").append('"').append(this.usuarioBloqueado).append('"').append(",");
		sb.append('"').append("intentosFallidos").append('"').append(":").append('"').append(this.intentosFallidos).append('"').append(",");
		sb.append('"').append("usuarioRegistro").append('"').append(":").append('"').append(this.usuarioRegistro).append('"').append(",");
		sb.append('"').append("fechaRegistro").append('"').append(":").append('"').append(this.fechaRegistro).append('"').append(",");
		sb.append('"').append("usuarioModifica").append('"').append(":").append('"').append(this.usuarioModifica).append('"').append(",");
		sb.append('"').append("fechaModifica").append('"').append(":").append('"').append(this.fechaModifica).append('"').append("");
		sb.append("}");
		return sb.toString();
	}
}
