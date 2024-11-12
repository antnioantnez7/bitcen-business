package mx.gob.banobras.bitcen.accesos.business.aplication.service;
/**
 * AccesoCasoUsoServiceImpl.java:
 * 
 * Implementacion para el caso de uso del servicio de acceso.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import mx.gob.banobras.bitcen.token.business.aplication.port.out.ITokenClient;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.TokenizerResponseDTO;
import mx.gob.banobras.bitcen.accesos.business.aplication.port.in.IAccesoCasoUsoService;
import mx.gob.banobras.bitcen.accesos.business.aplication.port.out.IAccesoPersistenceClient;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraAccesoDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.ResponseAccesoDTO;

@Service
public class AccesoCasoUsoServiceImpl implements IAccesoCasoUsoService {
	
	/** Logs de la clase */
	Logger log = LogManager.getLogger(AccesoCasoUsoServiceImpl.class);

	/** Objeto de la interfaz del cliente de persistencia de Acceso  */
	private final IAccesoPersistenceClient iAccesoPersistenceClient;
	/** Objeto de la interfaz del cliente de apitokenizer */
	private final ITokenClient iTokenClient;

	/** Constructor para inyectar las interfaces de los clientes a consumir */
	public AccesoCasoUsoServiceImpl(IAccesoPersistenceClient iAccesoPersistenceClient, ITokenClient iTokenClient) {
		this.iAccesoPersistenceClient = iAccesoPersistenceClient;
		this.iTokenClient = iTokenClient;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de accesos.
	 * 
	 * @param headerDTO Header de entrada.
	 * @param bitacoraAccesoDTO Objeto que contiene los datos de la bitacora de accesos
	 * @return ResponseAccesoDTO Objeto que contien la respuesta con la bitacora de accesos o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseAccesoDTO registrar(HeaderDTO headerDTO, BitacoraAccesoDTO bitacoraAccesoDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseAccesoDTO responseAccesoDTO = null;
		log.info("registrar");
		log.info(headerDTO.getFunctionalId().toUpperCase()+"/"+bitacoraAccesoDTO.getEstatusOperacion().toUpperCase()+":coindice 1:"+headerDTO.getFunctionalId().toUpperCase().contains("Login".toUpperCase()));
		log.info("coincide2:"+bitacoraAccesoDTO.getEstatusOperacion().toUpperCase().contains("I".toUpperCase()));
		if(headerDTO.getFunctionalId().toUpperCase().contains("Login".toUpperCase()) && bitacoraAccesoDTO.getEstatusOperacion().toUpperCase().contains("I".toUpperCase())) {
			tokenizerResponseDTO = new TokenizerResponseDTO();
			tokenizerResponseDTO.setStatusCode(200);
			log.info("salto tokenizer por funcional-id:LOGIN y estatusOperacion=I");
		} else {
			tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		}
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iAccesoPersistenceClient.registrar(headerDTO, bitacoraAccesoDTO);
		} else {
			responseAccesoDTO = new ResponseAccesoDTO();
			responseAccesoDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseAccesoDTO.setSucess(false);
			responseAccesoDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseAccesoDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseAccesoDTO;
	}
	
	/**
	 * Metodo para consultar movimientos de la bitacora de accesos.
	 * 
	 * @param headerDTO Header de entrada.
	 * @param bitacoraConsultaDTO Objeto que contiene los datos de la bitacora de accesos
	 * @return ResponseAccesoDTO Objeto que contien la respuesta con la bitacora de accesos o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseAccesoDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseAccesoDTO responseAccesoDTO = null;
		log.info("consultar");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iAccesoPersistenceClient.consultar(headerDTO, bitacoraConsultaDTO);
		} else {
			responseAccesoDTO = new ResponseAccesoDTO();
			responseAccesoDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseAccesoDTO.setSucess(false);
			responseAccesoDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseAccesoDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseAccesoDTO;
	}
}
