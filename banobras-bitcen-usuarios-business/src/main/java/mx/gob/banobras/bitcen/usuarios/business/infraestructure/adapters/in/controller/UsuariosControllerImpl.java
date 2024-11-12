package mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.controller;
/**
 * UsuariosControllerImpl.java:
 * 
 * Implementacion controller que expone los servicios de usuarios.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.usuarios.business.aplication.port.in.IUsuarioCasoUsoService;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraUsuarioDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.ResponseUsuarioDTO;

@CrossOrigin(originPatterns = {"*"})
@RestController
public class UsuariosControllerImpl implements IUsuariosController{

	/** Logs de la clase */
	Logger log = LogManager.getLogger(UsuariosControllerImpl.class);
	
	/** Objeto para instanciar el caso de uso de usuarios **/
	private final IUsuarioCasoUsoService iUsuarioCasoUsoService;

	/** Constructor para inyectar los objetos */
	public UsuariosControllerImpl(IUsuarioCasoUsoService iUsuarioCasoUsoService) {
		this.iUsuarioCasoUsoService = iUsuarioCasoUsoService;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de usuarios.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseUsuarioDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseUsuarioDTO> registrar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraUsuarioDTO bitacoraUsuarioDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio usuarios:registrar");
    	ResponseUsuarioDTO responseUsuarioDTO = iUsuarioCasoUsoService.registrar(headerDTO, bitacoraUsuarioDTO);
        return new ResponseEntity<>(responseUsuarioDTO, HttpStatus.valueOf(responseUsuarioDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para consultar movimientos de la bitacora de usuarios.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseUsuarioDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseUsuarioDTO> consultar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio usuarios:consultar");
    	ResponseUsuarioDTO responseUsuarioDTO = iUsuarioCasoUsoService.consultar(headerDTO, bitacoraConsultaDTO);
        return new ResponseEntity<>(responseUsuarioDTO, HttpStatus.valueOf(responseUsuarioDTO.getStatusCode()));
    }
}
