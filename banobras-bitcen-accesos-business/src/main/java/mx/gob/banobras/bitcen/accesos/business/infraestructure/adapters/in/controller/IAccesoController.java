package mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.controller;
/**
 * IAccesoController.java:
 * 
 * Interfaz controller que expone los servicios de acceso.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraAccesoDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.ResponseAccesoDTO;

@Tag(name = "Bitacora-Accesos", description = "Servicios de la funcionalidad de administraci&oacute;n de acceso.")
@RequestMapping("/bitacora/accesos/")
public interface IAccesoController {
	/**
	 * Metodo para registrar movimientos en la bitacora de accesos.
	 * 
	 * @param credentials    - Credenciales e usuario encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return Metodo para registrar movimientos en la bitacora de accesos.
	 * @throws manda una excepci&oacute;n si ocurre un error.
	 * 
	 */
	@Operation(summary = "Servicio para registrar movimientos en la bitácora de accesos.", description = "Servicio para registrar movimientos en la bitácora de accesos.")
	@Parameter(name = "credentials", required = true, description = "Credenciales de usuario encriptadas.", example = "AC0A5B36D37EBF616799CD1B94FBB29C")
	@Parameter(name = "token-auth", required = true, description = "Token de autenticaci&oacute;n", example = "Bearer eyJ0eXAiOiJKV1Qi...")
	@Parameter(name = "app-name", required = true, description = "Nombre del aplicativo que consume el servicio.", example = "SICOVI")
	@Parameter(name = "consumer-id", required = true, description = "Interfaz o capa que consume el servicio.", example = "UI SICOVI")
	@Parameter(name = "functional-id", required = true, description = "Funcionlidad negocio.", example = "Login user")
	@Parameter(name = "transaction-id", required = true, description = "Identificador Unico de la transacci&oacute;n, generado por UUID.", example = "9680e51f-4766-4124-a3ff-02e9c3a5f9d6")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "400", description = "Error en la petici&oacute;n.", content = @Content)
	@ApiResponse(responseCode = "404", description = "No esta disponible el recurso.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Error interno.", content = @Content)
	@ApiResponse(responseCode = "503", description = "Service no disponible.", content = @Content)
    @PostMapping("registrar")
    public ResponseEntity<ResponseAccesoDTO> registrar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraAccesoDTO bitacoraAccesoDTO) throws Exception;
	
	/**
	 * Metodo para consultar movimientos de la bitacora de accesos.
	 * 
	 * @param credentials    - Credenciales e usuario encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return Metodo para consultar movimientos de la bitacora de accesos.
	 * @throws manda una excepci&oacute;n si ocurre un error.
	 * 
	 */
	@Operation(summary = "Servicio para consultar movimientos de la bitácora de accesos.", description = "Servicio para consultar movimientos de la bitácora de accesos.")
	@Parameter(name = "credentials", required = true, description = "Credenciales de usuario encriptadas.", example = "AC0A5B36D37EBF616799CD1B94FBB29C")
	@Parameter(name = "token-auth", required = true, description = "Token de autenticaci&oacute;n", example = "Bearer eyJ0eXAiOiJKV1Qi...")
	@Parameter(name = "app-name", required = true, description = "Nombre del aplicativo que consume el servicio.", example = "SICOVI")
	@Parameter(name = "consumer-id", required = true, description = "Interfaz o capa que consume el servicio.", example = "UI SICOVI")
	@Parameter(name = "functional-id", required = true, description = "Funcionlidad negocio.", example = "Login user")
	@Parameter(name = "transaction-id", required = true, description = "Identificador Unico de la transacci&oacute;n, generado por UUID.", example = "9680e51f-4766-4124-a3ff-02e9c3a5f9d6")
	@ApiResponse(responseCode = "200", description = "OK")
	@ApiResponse(responseCode = "400", description = "Error en la petici&oacute;n.", content = @Content)
	@ApiResponse(responseCode = "404", description = "No esta disponible el recurso.", content = @Content)
	@ApiResponse(responseCode = "500", description = "Error interno.", content = @Content)
	@ApiResponse(responseCode = "503", description = "Service no disponible.", content = @Content)
    @PostMapping("consultar")
    public ResponseEntity<ResponseAccesoDTO> consultar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception;
}
