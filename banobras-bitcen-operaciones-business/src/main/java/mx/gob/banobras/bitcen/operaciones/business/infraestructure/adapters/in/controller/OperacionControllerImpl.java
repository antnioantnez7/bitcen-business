package mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.controller;
/**
 * OperacionControllerImpl.java:
 * 
 * Implementacion controller que expone los servicios de operacion.
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
import mx.gob.banobras.bitcen.operaciones.business.aplication.port.in.IOperacionCasoUsoService;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraOperacionDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.ResponseOperacionDTO;


@CrossOrigin(originPatterns = {"*"})
@RestController
public class OperacionControllerImpl implements IOperacionController{

	/** Logs de la clase */
	Logger log = LogManager.getLogger(OperacionControllerImpl.class);
	
	/** Objeto para instanciar el caso de uso de operacion **/
	private final IOperacionCasoUsoService iOperacionCasoUsoService;

	/** Constructor para inyectar los objetos */
	public OperacionControllerImpl(IOperacionCasoUsoService iOperacionCasoUsoService) {
		this.iOperacionCasoUsoService = iOperacionCasoUsoService;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de operaciones.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseOperacionDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseOperacionDTO> registrar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraOperacionDTO bitacoraOperacionDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio operacion:registrar");
    	ResponseOperacionDTO responseOperacionDTO = iOperacionCasoUsoService.registrar(headerDTO, bitacoraOperacionDTO);
        return new ResponseEntity<>(responseOperacionDTO, HttpStatus.valueOf(responseOperacionDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para consultar movimientos de la bitacora de operaciones.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseOperacionDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseOperacionDTO> consultar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio operacion:consultar");
    	ResponseOperacionDTO responseOperacionDTO = iOperacionCasoUsoService.consultar(headerDTO, bitacoraConsultaDTO);
        return new ResponseEntity<>(responseOperacionDTO, HttpStatus.valueOf(responseOperacionDTO.getStatusCode()));
    }
}
