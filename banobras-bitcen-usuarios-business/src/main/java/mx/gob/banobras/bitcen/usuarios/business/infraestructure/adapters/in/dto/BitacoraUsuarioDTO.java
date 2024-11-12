package mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto;
/**
 * BitacoraOperacionDTO.java:
 * 
 * Objeto que contiene los datos de entrada de la bitacora de operaciones. 
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
public class BitacoraUsuarioDTO {
	/** Identificador de bitácora de Usuarios, llave primaria. */
	public int identificador;
	/** Acrónimo e identificador de aplicativo. */
	@Schema(example = "MAC")
	public String aplicativoId;
	/** Capa desde donde se envía el registro a Bitácora (Front, Negocio, Persistencia). */
	@Schema(example = "Business")
	public String capa;
	/** Nombre del método desde donde se envía el registro a Bitácora. */
	@Schema(example = "altaUsuario")
	public String metodo;
	/** Descripción del proceso ejecutado (Usuario, Cuenta, Permiso/Rol). */
	@Schema(example = "Usuarios")
	public String proceso;
	/** Descripción del subproceso ejecutado (Alta, Baja, Activación, etc.). */
	@Schema(example = "creacionUsuario")
	public String subproceso;
	/** Detalle de la operación o información referente a la misma. */
	@Schema(example = "Alta de usuario")
	public String detalleOperacion;
	/** Identificador de la sesión en la que se está realizando la operación. */
	@Schema(example = "9680e51f-4766-4124-a3ff-02e9c3a5f9d6")
	public String transaccionId;
	/** IP del equipo donde se requiere el acceso. */
	@Schema(example = "127.0.0.1")
	public String ipEquipo;
	/** Fecha hora de la transacción. */
	public LocalDateTime fechaHoraOperacion;
	/** Usuario que opera. */
	@Schema(example = "andres")
	public String usuarioOperador;
	/** Nombre completo del usuario operador. */
	@Schema(example = "Andres Gonz")
	public String nombreOperador;
	/** Expediente del usuario operador. */
	@Schema(example = "12345")
	public String expedienteOperador;
	/** RFC del usuario operador. */
	@Schema(example = "AAGG12345678")
	public String rfcOperador;
	/** Área del usuario operador. */
	@Schema(example = "Administracion")
	public String areaOperador;
	/** Puesto del usuario operador. */
	@Schema(example = "Administrador")
	public String puestoOperador;
	/** Usuario del cual se está realizando la transacción. */
	@Schema(example = "ricardo")
	public String usuario;
	/** Nombre completo del usuario de la transacción. */
	@Schema(example = "Ricardo Loaiza")
	public String nombreUsuario;
	/** Expediente del usuario de la transacción. */
	@Schema(example = "56789")
	public String expedienteUsuario;
	/** RFC del usuario de la transacción. */
	@Schema(example = "RRLL12345678")
	public String rfcUsuario;
	/** Área del usuario de la transacción. */
	@Schema(example = "Contabilidad")
	public String areaUsuario;
	/** Puesto del usuario de la transacción. */
	@Schema(example = "Contador")
	public String puestoUsuario;
	/** Estatus (Correcto, Incorrecto). */
	@Schema(example = "I")
	public String estatusOperacion;
	/** Mensaje de la transacción realizada. */
	@Schema(example = "Incorrecto")
	public String respuestaOperacion;
	/** Propiedad para convertir el modelo a formato Json*/
	public String toJson() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append('"').append("identificador").append('"').append(":").append('"').append(this.identificador).append('"').append(",");
		sb.append('"').append("aplicativoId").append('"').append(":").append('"').append(this.aplicativoId).append('"').append(",");
		sb.append('"').append("capa").append('"').append(":").append('"').append(this.capa).append('"').append(",");
		sb.append('"').append("metodo").append('"').append(":").append('"').append(this.metodo).append('"').append(",");
		sb.append('"').append("proceso").append('"').append(":").append('"').append(this.proceso).append('"').append(",");
		sb.append('"').append("subproceso").append('"').append(":").append('"').append(this.subproceso).append('"').append(",");
		sb.append('"').append("detalleOperacion").append('"').append(":").append('"').append(this.detalleOperacion).append('"').append(",");		
		sb.append('"').append("transaccionId").append('"').append(":").append('"').append(this.transaccionId).append('"').append(",");
		sb.append('"').append("ipEquipo").append('"').append(":").append('"').append(this.ipEquipo).append('"').append(",");
		sb.append('"').append("fechaHoraOperacion").append('"').append(":").append('"').append(this.fechaHoraOperacion).append('"').append(",");
		sb.append('"').append("usuarioOperador").append('"').append(":").append('"').append(this.usuarioOperador).append('"').append(",");
		sb.append('"').append("nombreOperador").append('"').append(":").append('"').append(this.nombreOperador).append('"').append(",");
		sb.append('"').append("expedienteOperador").append('"').append(":").append('"').append(this.expedienteOperador).append('"').append(",");
		sb.append('"').append("rfcOperador").append('"').append(":").append('"').append(this.rfcOperador).append('"').append(",");
		sb.append('"').append("areaOperador").append('"').append(":").append('"').append(this.areaOperador).append('"').append(",");
		sb.append('"').append("puestoOperador").append('"').append(":").append('"').append(this.puestoOperador).append('"').append(",");
		sb.append('"').append("usuario").append('"').append(":").append('"').append(this.usuario).append('"').append(",");
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
