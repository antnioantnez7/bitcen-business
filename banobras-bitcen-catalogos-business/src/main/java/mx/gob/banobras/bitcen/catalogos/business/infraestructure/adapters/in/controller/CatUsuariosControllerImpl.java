package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.controller;
/**
 * CatUsuariosControllerImpl.java:
 * 
 * Implementacion controller que expone los servicios del catalogo de usuarios.
 * 
 * @author Antonio Antunez Barbosa
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.in.ICatUsuariosCasoUsoService;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoUsuarioDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatUsuariosDTO;

@CrossOrigin(originPatterns = {"*"})
@RestController
public class CatUsuariosControllerImpl implements ICatUsuariosController{

	/** Logs de la clase */
	Logger log = LogManager.getLogger(CatUsuariosControllerImpl.class);
	
	/** Objeto para instanciar el caso de uso de catalogo **/
	private final ICatUsuariosCasoUsoService iCatUsuariosCasoUsoService;

	/** Constructor para inyectar los objetos */
	public CatUsuariosControllerImpl(ICatUsuariosCasoUsoService iCatUsuariosCasoUsoService) {
		this.iCatUsuariosCasoUsoService = iCatUsuariosCasoUsoService;
	}
	
	/**
	 * Metodo para guardar un usuario en el catalogo.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatUsuariosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatUsuariosDTO> guardarUsuario(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo:guardarUsuario");
    	ResponseCatUsuariosDTO responseCatUsuariosDTO = iCatUsuariosCasoUsoService.guardarUsuario(headerDTO, catalogoUsuarioDTO);
        return new ResponseEntity<>(responseCatUsuariosDTO, HttpStatus.valueOf(responseCatUsuariosDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para eliminar un usuario del catalogo.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatUsuariosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatUsuariosDTO> eliminarUsuario(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody int identificador) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo:eliminarUsuario");
    	ResponseCatUsuariosDTO responseCatUsuariosDTO = iCatUsuariosCasoUsoService.eliminarUsuario(headerDTO, identificador);
        return new ResponseEntity<>(responseCatUsuariosDTO, HttpStatus.valueOf(responseCatUsuariosDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para consultar los usuarios en el catalogo
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatUsuariosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatUsuariosDTO> consultarUsuarios(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo:consultarUsuarios");
    	ResponseCatUsuariosDTO responseCatUsuariosDTO = iCatUsuariosCasoUsoService.consultarUsuarios(headerDTO, catalogoUsuarioDTO);
        return new ResponseEntity<>(responseCatUsuariosDTO, HttpStatus.valueOf(responseCatUsuariosDTO.getStatusCode()));
    }
}
