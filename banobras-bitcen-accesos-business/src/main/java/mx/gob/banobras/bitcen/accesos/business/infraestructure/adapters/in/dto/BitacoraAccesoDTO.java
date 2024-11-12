package mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto;
/**
 * BitacoraAccesoDTO.java:
 * 
 * Objeto que contiene los datos de entrada de la bitacora de accesos. 
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
public class BitacoraAccesoDTO {
	/** Identificador de bitácora de Accesos, llave primaria. */
	private int identificador;
	/** Acrónimo e identificador de aplicativo. */
	@Schema(example = "MAC")
	private String aplicativoId;
	/** Capa desde donde se envía el registro a Bitácora (Front, Negocio, Persistencia). */
	@Schema(example = "Business")
	private String capa;
	/** Nombre del método desde donde se envía el registro a Bitácora. */
	@Schema(example = "Login")
	private String metodo;
	/** Descripción de la actividad realizada (Ingreso de usuario). */
	@Schema(example = "Iniciar sesion")
	private String actividad;
	/** Identificador de la sesión en la que se está realizando la operación. */
	@Schema(example = "9680e51f-4766-4124-a3ff-02e9c3a5f9d6")
	private String transaccionId;
	/** IP del equipo donde se requiere el acceso. */
	@Schema(example = "127.0.0.1")
	private String ipEquipo;
	/** Fecha hora de la transacción. */
	private LocalDateTime fechaHoraAcceso;
	/** Usuario que opera. */
	@Schema(example = "andres")
	private String usuarioAcceso;
	/** Nombre completo del usuario. */
	@Schema(example = "Andres Gonz")
	private String nombreUsuario;
	/** Expediente del usuario. */
	@Schema(example = "12345")
	private String expedienteUsuario;
	/** RFC del usuario. */
	@Schema(example = "AAGG12345678")
	private String rfcUsuario;
	/** Área del usuario. */
	@Schema(example = "Administracion")
	private String areaUsuario;
	/** Puesto del usuario. */
	@Schema(example = "Administrador")
	private String puestoUsuario;
	/** Estatus de la transacción. */
	@Schema(example = "C")
	private String estatusOperacion;
	/** Mensaje de la transacción realizada. */
	@Schema(example = "Exito")
	private String respuestaOperacion;
	/** Propiedad para convertir el modelo a formato Json*/
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("identificador").append('"').append(":").append('"').append(this.identificador).append('"').append(",");
		sb.append('"').append("aplicativoId").append('"').append(":").append('"').append(this.aplicativoId).append('"').append(",");
		sb.append('"').append("capa").append('"').append(":").append('"').append(this.capa).append('"').append(",");
		sb.append('"').append("metodo").append('"').append(":").append('"').append(this.metodo).append('"').append(",");
		sb.append('"').append("actividad").append('"').append(":").append('"').append(this.actividad).append('"').append(",");
		sb.append('"').append("transaccionId").append('"').append(":").append('"').append(this.transaccionId).append('"').append(",");
		sb.append('"').append("ipEquipo").append('"').append(":").append('"').append(this.ipEquipo).append('"').append(",");
		sb.append('"').append("fechaHoraAcceso").append('"').append(":").append('"').append(this.fechaHoraAcceso).append('"').append(",");
		sb.append('"').append("usuarioAcceso").append('"').append(":").append('"').append(this.usuarioAcceso).append('"').append(",");
		sb.append('"').append("nombreUsuario").append('"').append(":").append('"').append(this.nombreUsuario).append('"').append(",");
		sb.append('"').append("expedienteUsuario").append('"').append(":").append('"').append(this.expedienteUsuario).append('"').append(",");
		sb.append('"').append("rfcUsuario").append('"').append(":").append('"').append(this.rfcUsuario).append('"').append(",");
		sb.append('"').append("areaUsuario").append('"').append(":").append('"').append(this.areaUsuario).append('"').append(",");
		sb.append('"').append("puestoUsuario").append('"').append(":").append('"').append(this.puestoUsuario).append('"').append(",");
		sb.append('"').append("estatusOperacion").append('"').append(":").append('"').append(this.estatusOperacion).append('"').append(",");
		sb.append('"').append("respuestaOperacion").append('"').append(":").append('"').append(this.respuestaOperacion).append('"').append("");
		sb.append("}");
		return sb.toString();
	}
}
