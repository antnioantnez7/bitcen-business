package mx.gob.banobras.bitcen.operaciones.business.aplication.service;
/**
 * OperacionCasoUsoServiceImpl.java:
 * 
 * Implementacion para el caso de uso del servicio de operacion.
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
import mx.gob.banobras.bitcen.operaciones.business.aplication.port.in.IOperacionCasoUsoService;
import mx.gob.banobras.bitcen.operaciones.business.aplication.port.out.IOperacionPersistenceClient;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraOperacionDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.ResponseOperacionDTO;

@Service
public class OperacionCasoUsoServiceImpl implements IOperacionCasoUsoService {
	
	/** Logs de la clase */
	Logger log = LogManager.getLogger(OperacionCasoUsoServiceImpl.class);

	/** Objeto de la interfaz del cliente de persistencia de operacion  */
	private final IOperacionPersistenceClient iOperacionPersistenceClient;
	/** Objeto de la interfaz del cliente de apitokenizer */
	private final ITokenClient iTokenClient;

	/** Constructor para inyectar las interfaces de los clientes a consumir */
	public OperacionCasoUsoServiceImpl(IOperacionPersistenceClient iOperacionPersistenceClient, ITokenClient iTokenClient) {
		this.iOperacionPersistenceClient = iOperacionPersistenceClient;
		this.iTokenClient = iTokenClient;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de operaciones.
	 * 
	 * @param headerDTO Header de entrada.
	 * @param bitacoraOperacionDTO Objeto que contiene los datos de la bitacora de operaciones
	 * @return ResponseOperacionDTO Objeto que contien la respuesta con la bitacora de operaciones o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseOperacionDTO registrar(HeaderDTO headerDTO, BitacoraOperacionDTO bitacoraOperacionDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseOperacionDTO responseOperacionDTO = null;
		log.info("registrar");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iOperacionPersistenceClient.registrar(headerDTO, bitacoraOperacionDTO);
		} else {
			responseOperacionDTO = new ResponseOperacionDTO();
			responseOperacionDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseOperacionDTO.setSucess(false);
			responseOperacionDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseOperacionDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseOperacionDTO;
	}
	
	/**
	 * Metodo para consultar movimientos de la bitacora de operaciones.
	 * 
	 * @param headerDTO Header de entrada.
	 * @param bitacoraConsultaDTO Objeto que contiene los datos de la bitacora de operaciones
	 * @return ResponseOperacionDTO Objeto que contien la respuesta con la bitacora de operaciones o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseOperacionDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseOperacionDTO responseOperacionDTO = null;
		log.info("consultar");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iOperacionPersistenceClient.consultar(headerDTO, bitacoraConsultaDTO);
		} else {
			responseOperacionDTO = new ResponseOperacionDTO();
			responseOperacionDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseOperacionDTO.setSucess(false);
			responseOperacionDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseOperacionDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseOperacionDTO;
	}
}
