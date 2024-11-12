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
public class CatalogoAplicativoDTO {
	/** Identificador del catálogo, es un acrónimo como (MAC, SIGEVI) */
	@Schema(example = "SIGEVI")
	private String aplicativoId;
	/** Nombre completo del acrónimo */
	@Schema(example = "Sistema de Gestión de Viaticos")
	private String nombre;
	/** Identificador del catálogo de usuario (debe estar registrado) */
	@Schema(example = "1")
	private int usuarioRegistro;
	/** Fecha de registro */
	private LocalDateTime fechaRegistro;
	/** Identificador del catálogo de usuario (debe estar registrado) */
	@Schema(example = "1")
	private int usuarioModifica;
	/** Fecha de registro */
	private LocalDateTime fechaModifica;
	/** Propiedad para convertir el modelo a formato Json*/
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("aplicativoId").append('"').append(":").append('"').append(this.aplicativoId).append('"').append(",");
		sb.append('"').append("nombre").append('"').append(":").append('"').append(this.nombre).append('"').append(",");
		sb.append('"').append("usuarioRegistro").append('"').append(":").append('"').append(this.usuarioRegistro).append('"').append(",");
		sb.append('"').append("fechaRegistro").append('"').append(":").append('"').append(this.fechaRegistro).append('"').append(",");
		sb.append('"').append("usuarioModifica").append('"').append(":").append('"').append(this.usuarioModifica).append('"').append(",");
		sb.append('"').append("fechaModifica").append('"').append(":").append('"').append(this.fechaModifica).append('"').append("");
		sb.append("}");
		return sb.toString();
	}
}
