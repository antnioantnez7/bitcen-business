package mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.controller;
/**
 * AccesoControllerImpl.java:
 * 
 * Implementacion controller que expone los servicios de acceso.
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
import mx.gob.banobras.bitcen.accesos.business.aplication.port.in.IAccesoCasoUsoService;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraAccesoDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.ResponseAccesoDTO;

@CrossOrigin(originPatterns = {"*"})
@RestController
public class AccesoControllerImpl implements IAccesoController{

	/** Logs de la clase */
	Logger log = LogManager.getLogger(AccesoControllerImpl.class);
	
	/** Objeto para instanciar el caso de uso de acceso **/
	private final IAccesoCasoUsoService iAccesoCasoUsoService;

	/** Constructor para inyectar los objetos */
	public AccesoControllerImpl(IAccesoCasoUsoService iAccesoCasoUsoService) {
		this.iAccesoCasoUsoService = iAccesoCasoUsoService;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de accesos.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseAccesoDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseAccesoDTO> registrar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraAccesoDTO bitacoraAccesoDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio Acceso:registrar");
    	ResponseAccesoDTO responseAccesoDTO = iAccesoCasoUsoService.registrar(headerDTO, bitacoraAccesoDTO);
    	return new ResponseEntity<>(responseAccesoDTO, HttpStatus.valueOf(responseAccesoDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para consultar movimientos de la bitacora de accesos.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseAccesoDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseAccesoDTO> consultar(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio Acceso:consultar");
    	ResponseAccesoDTO responseAccesoDTO = iAccesoCasoUsoService.consultar(headerDTO, bitacoraConsultaDTO);
    	return new ResponseEntity<>(responseAccesoDTO, HttpStatus.valueOf(responseAccesoDTO.getStatusCode()));
    }
}
