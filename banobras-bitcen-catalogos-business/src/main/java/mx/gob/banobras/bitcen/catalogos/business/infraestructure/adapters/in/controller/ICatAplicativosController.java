package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoAplicativoDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatAplicativosDTO;

/**
 * ICatAplicativosController.java:
 * 
 * Interfaz controller que expone los servicios de catalogo de aplicativos.
 * 
 * @author Antonio Antunez Barbosa
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

@Tag(name = "Bitacora-Catalogos-Aplicativos", description = "Servicios de la funcionalidad de administraci&oacute;n de catalogos de aplicativos.")
@RequestMapping("/bitacora/catalogos")
public interface ICatAplicativosController {
	/**
	 * Metodo para registrar un aplicativo en el Catalogo
	 * 
	 * @param credentials    - Credenciales e usuario encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * @throws manda una excepci&oacute;n si ocurre un error.
	 * 
	 */
	@Operation(summary = "Servicio para agregar un registro en el catálogo de aplicativos.", description = "Servicio para agregar un registro en el catálogo de aplicativos.")
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
    @PostMapping("/aplicativos/registrar")
    public ResponseEntity<ResponseCatAplicativosDTO> registrarAplicativo(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception;
	
	/**
	 * Metodo para actualizar un aplicativo en el Catalogo
	 * 
	 * @param credentials    - Credenciales e usuario encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * @throws manda una excepci&oacute;n si ocurre un error.
	 * 
	 */
	@Operation(summary = "Servicio para actualizar un registro en el catálogo de aplicativos.", description = "Servicio para actualizar un registro en el catálogo de aplicativos.")
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
    @PostMapping("/aplicativos/actualizar")
    public ResponseEntity<ResponseCatAplicativosDTO> actualizarAplicativo(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception;
	
	/**
	 * Metodo para actualizar un aplicativo en el Catalogo
	 * 
	 * @param credentials    - Credenciales e usuario encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * @throws manda una excepci&oacute;n si ocurre un error.
	 * 
	 */
	@Operation(summary = "Servicio para eliminar un registro del catálogo de aplicativos.", description = "Servicio para eliminar un registro del catálogo de aplicativos.")
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
    @DeleteMapping("/aplicativos/eliminar/{aplicativoId}")
    public ResponseEntity<ResponseCatAplicativosDTO> eliminarAplicativo(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@PathVariable String aplicativoId) throws Exception;
	
	/**
	 * Metodo para consultar los aplicativo del Catalogo
	 * 
	 * @param credentials    - Credenciales e usuario encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * @throws manda una excepci&oacute;n si ocurre un error.
	 * 
	 */
	@Operation(summary = "Servicio para consultar los registros del catálogo de aplicativos.", description = "Servicio para consultar los registros del catálogo de aplicativos.")
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
    @GetMapping("/aplicativos/consultar")
    public ResponseEntity<ResponseCatAplicativosDTO> consultarAplicativos(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId) throws Exception;
}
