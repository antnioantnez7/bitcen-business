package mx.gob.banobras.bitcen.catalogos.business.aplication.service;
/**
 * CatUsuariosCasoUsoServiceImpl.java:
 * 
 * Implementacion para el caso de uso del servicio de catalogo de aplicativos.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import mx.gob.banobras.bitcen.token.business.aplication.port.out.ITokenClient;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.TokenizerResponseDTO;
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.in.ICatUsuariosCasoUsoService;
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.out.ICatUsuariosPersistenceClient;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoUsuarioDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatUsuariosDTO;


@Service
public class CatUsuariosCasoUsoServiceImpl implements ICatUsuariosCasoUsoService {
	
	/** Logs de la clase */
	Logger log = LogManager.getLogger(CatUsuariosCasoUsoServiceImpl.class);

	/** Objeto de la interfaz del cliente de persistencia del Catalogo de usuarios */
	private final ICatUsuariosPersistenceClient iCatUsuariosPersistenceClient;
	
	/** Objeto de la interfaz del cliente de apitokenizer */
	private final ITokenClient iTokenClient;

	/** Constructor para inyectar las interfaces de los clientes a consumir */
	public CatUsuariosCasoUsoServiceImpl(ICatUsuariosPersistenceClient iCatUsuariosPersistenceClient, ITokenClient iTokenClient) {
		this.iCatUsuariosPersistenceClient = iCatUsuariosPersistenceClient;
		this.iTokenClient = iTokenClient;
	}

	/**
	 * Metodo para registrar un aplicativo en el Catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @param catalogoUsuarioDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatUsuariosDTO Objeto que contien la respuesta con el Catalogo de usuarios o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatUsuariosDTO guardarUsuario(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatUsuariosDTO responseCatUsuariosDTO = null;
		log.info("guardarUsuario");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatUsuariosPersistenceClient.guardarUsuario(headerDTO, catalogoUsuarioDTO);
		} else {
			responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
			responseCatUsuariosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatUsuariosDTO.setSucess(false);
			responseCatUsuariosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatUsuariosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatUsuariosDTO;
	}
	
	/**
	 * Metodo para eliminar un usuario del Catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @param identificador entero que contiene el identificador del Catalogo
	 * @return ResponseCatUsuariosDTO Objeto que contien la respuesta con el Catalogo de usuarios o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatUsuariosDTO eliminarUsuario(HeaderDTO headerDTO, int identificador) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatUsuariosDTO responseCatUsuariosDTO = null;
		log.info("eliminarUsuario");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatUsuariosPersistenceClient.eliminarUsuario(headerDTO, identificador);
		} else {
			responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
			responseCatUsuariosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatUsuariosDTO.setSucess(false);
			responseCatUsuariosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatUsuariosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatUsuariosDTO;
	}
	
	/**
	 * Metodo para consultar los aplicativos en el catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @return ResponseCatUsuariosDTO Objeto que contien la respuesta con el Catalogo de usuarios o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatUsuariosDTO consultarUsuarios(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatUsuariosDTO responseCatUsuariosDTO = null;
		log.info("consultarUsuarios");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatUsuariosPersistenceClient.consultarUsuarios(headerDTO, catalogoUsuarioDTO);
		} else {
			responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
			responseCatUsuariosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatUsuariosDTO.setSucess(false);
			responseCatUsuariosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatUsuariosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatUsuariosDTO;
	}
}
