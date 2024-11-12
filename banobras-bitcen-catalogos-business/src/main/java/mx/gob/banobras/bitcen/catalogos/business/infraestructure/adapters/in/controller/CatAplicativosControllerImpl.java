package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.controller;
/**
 * CatAplicativosControllerImpl.java:
 * 
 * Implementacion controller que expone los servicios del catalogo de aplicativos.
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
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.in.ICatAplicativosCasoUsoService;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoAplicativoDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatAplicativosDTO;

@CrossOrigin(originPatterns = {"*"})
@RestController
public class CatAplicativosControllerImpl implements ICatAplicativosController{

	/** Logs de la clase */
	Logger log = LogManager.getLogger(CatAplicativosControllerImpl.class);
	
	/** Objeto para instanciar el caso de uso de catalogo **/
	private final ICatAplicativosCasoUsoService iCatAplicativosCasoUsoService;

	/** Constructor para inyectar los objetos */
	public CatAplicativosControllerImpl(ICatAplicativosCasoUsoService iCatAplicativosCasoUsoService) {
		this.iCatAplicativosCasoUsoService = iCatAplicativosCasoUsoService;
	}
	
	/**
	 * Metodo para registrar un aplicativo en el catalogo.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatAplicativosDTO> registrarAplicativo(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo:registrarAplicativo");
    	ResponseCatAplicativosDTO responseCatAplicativosDTO = iCatAplicativosCasoUsoService.registrarAplicativo(headerDTO, catalogoAplicativoDTO);
        return new ResponseEntity<>(responseCatAplicativosDTO, HttpStatus.valueOf(responseCatAplicativosDTO.getStatusCode()));
    }

	/**
	 * Metodo para actualizar un aplicativo en el catalogo.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatAplicativosDTO> actualizarAplicativo(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo:actualizarAplicativo");
    	ResponseCatAplicativosDTO responseCatAplicativosDTO = iCatAplicativosCasoUsoService.actualizarAplicativo(headerDTO, catalogoAplicativoDTO);
        return new ResponseEntity<>(responseCatAplicativosDTO, HttpStatus.valueOf(responseCatAplicativosDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para eliminar un aplicativo del catalogo.
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatAplicativosDTO> eliminarAplicativo(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId,
			@RequestBody String aplicativoId) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo");
    	ResponseCatAplicativosDTO responseCatAplicativosDTO = iCatAplicativosCasoUsoService.eliminarAplicativo(headerDTO, aplicativoId);
        return new ResponseEntity<>(responseCatAplicativosDTO, HttpStatus.valueOf(responseCatAplicativosDTO.getStatusCode()));
    }
	
	/**
	 * Metodo para consultar los aplicativos en el catalogo
	 * 
	 * @param credentials    - Credeniales e usurio encriptadas.
	 * @param app-name       - Nombre del aplicativo que consume el servicio
	 * @param consumer-id    - Interfaz o capa que consume el servicio.
	 * @param functional-id  - Funcionlidad negocio.
	 * @param transaction-id - Identificador Unico de la transacci&oacute;n, generado por UUID.
	 * 
	 * @return ResponseCatAplicativosDTO.
	 * 
	 */
	@Override
    public ResponseEntity<ResponseCatAplicativosDTO> consultarAplicativos(
			@RequestHeader(value = "credentials") String credentials,
			@RequestHeader(value = "token-auth") String tokenAuth,
			@RequestHeader(value = "app-name") String appName,
			@RequestHeader(value = "consumer-id") String consumerId,
			@RequestHeader(value = "functional-id") String functionalId,
			@RequestHeader(value = "transaction-id") String transactionId) throws Exception{
    	HeaderDTO headerDTO = new HeaderDTO(credentials, null, null, tokenAuth, appName, consumerId, functionalId, transactionId, null);
    	log.info("Servicio negocio catalogo:consultarAplicativos");
    	ResponseCatAplicativosDTO responseCatAplicativosDTO = iCatAplicativosCasoUsoService.consultarAplicativos(headerDTO);
        return new ResponseEntity<>(responseCatAplicativosDTO, HttpStatus.valueOf(responseCatAplicativosDTO.getStatusCode()));
    }
}
