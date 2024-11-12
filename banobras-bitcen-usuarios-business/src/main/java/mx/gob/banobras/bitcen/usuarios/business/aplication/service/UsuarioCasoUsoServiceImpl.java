package mx.gob.banobras.bitcen.usuarios.business.aplication.service;
/**
 * UsuarioCasoUsoServiceImpl.java:
 * 
 * Implementacion para el caso de uso del servicio de usuarios.
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
import mx.gob.banobras.bitcen.usuarios.business.aplication.port.in.IUsuarioCasoUsoService;
import mx.gob.banobras.bitcen.usuarios.business.aplication.port.out.IUsuarioPersistenceClient;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraUsuarioDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.ResponseUsuarioDTO;

@Service
public class UsuarioCasoUsoServiceImpl implements IUsuarioCasoUsoService {
	
	/** Logs de la clase */
	Logger log = LogManager.getLogger(UsuarioCasoUsoServiceImpl.class);

	/** Objeto de la interfaz del cliente de persistencia de usuario  */
	private final IUsuarioPersistenceClient iUsuarioPersistenceClient;
	/** Objeto de la interfaz del cliente de apitokenizer */
	private final ITokenClient iTokenClient;

	/** Constructor para inyectar las interfaces de los clientes a consumir */
	public UsuarioCasoUsoServiceImpl(IUsuarioPersistenceClient iUsuarioPersistenceClient, ITokenClient iTokenClient) {
		this.iUsuarioPersistenceClient = iUsuarioPersistenceClient;
		this.iTokenClient = iTokenClient;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de usuarios.
	 * 
	 * @param headerDTO Header de entrada.
	 * @param bitacoraAccesoDTO Objeto que contiene los datos de la bitacora de usuarios
	 * @return ResponseUsuarioDTO Objeto que contien la respuesta con la bitacora de usuarios o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseUsuarioDTO registrar(HeaderDTO headerDTO, BitacoraUsuarioDTO bitacoraUsuarioDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseUsuarioDTO responseUsuarioDTO = null;
		log.info("registrar");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iUsuarioPersistenceClient.registrar(headerDTO, bitacoraUsuarioDTO);
		} else {
			responseUsuarioDTO = new ResponseUsuarioDTO();
			responseUsuarioDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseUsuarioDTO.setSucess(false);
			responseUsuarioDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseUsuarioDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseUsuarioDTO;
	}
	
	/**
	 * Metodo para consultar movimientos de la bitacora de usuarios.
	 * 
	 * @param headerDTO Header de entrada.
	 * @param bitacoraConsultaDTO Objeto que contiene los datos de la bitacora de usuarios
	 * @return ResponseUsuarioDTO Objeto que contien la respuesta con la bitacora de operaciones o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseUsuarioDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseUsuarioDTO responseUsuarioDTO = null;
		log.info("consultar");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iUsuarioPersistenceClient.consultar(headerDTO, bitacoraConsultaDTO);
		} else {
			responseUsuarioDTO = new ResponseUsuarioDTO();
			responseUsuarioDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseUsuarioDTO.setSucess(false);
			responseUsuarioDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseUsuarioDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseUsuarioDTO;
	}
}
