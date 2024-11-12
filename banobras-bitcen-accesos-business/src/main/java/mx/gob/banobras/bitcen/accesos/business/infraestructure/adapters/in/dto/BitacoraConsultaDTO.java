package mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto;
/**
 * BitacoraAccesoDTO.java:
 * 
 * Objeto que contiene los datos de entrada de consulta de la bitacora. 
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
public class BitacoraConsultaDTO {
	/** Acrónimo e identificador de aplicativo. */
	@Schema(example = "MAC")
	private String aplicativoId;
	/** Fecha hora inicio de la transacción. */
	private LocalDateTime fechaHoraIni;
	/** Fecha hora fin de la transacción. */
	private LocalDateTime fechaHoraFin;
	/** Valor booleano para bitacora historica o no. */
	@Schema(example = "false")
	private boolean historico;
	/** Propiedad para convertir el modelo a formato Json*/
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("aplicativoId").append('"').append(":").append('"').append(this.aplicativoId).append('"').append(",");
		sb.append('"').append("fechaHoraIni").append('"').append(":").append('"').append(this.fechaHoraIni).append('"').append(",");
		sb.append('"').append("fechaHoraFin").append('"').append(":").append('"').append(this.fechaHoraFin).append('"').append(",");
		sb.append('"').append("historico").append('"').append(":").append("").append(this.historico).append("").append("");
		sb.append("}");
		return sb.toString();
	}
}
