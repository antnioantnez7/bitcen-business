package mx.gob.banobras.bitcen.catalogos.business.aplication.service;
/**
 * CatAplicativosCasoUsoServiceImpl.java:
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
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.in.ICatAplicativosCasoUsoService;
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.out.ICatAplicativosPersistenceClient;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoAplicativoDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatAplicativosDTO;


@Service
public class CatAplicativosCasoUsoServiceImpl implements ICatAplicativosCasoUsoService {
	
	/** Logs de la clase */
	Logger log = LogManager.getLogger(CatAplicativosCasoUsoServiceImpl.class);

	/** Objeto de la interfaz del cliente de persistencia del Catalogo de aplicativos  */
	private final ICatAplicativosPersistenceClient iCatAplicativosPersistenceClient;
	
	/** Objeto de la interfaz del cliente de apitokenizer */
	private final ITokenClient iTokenClient;

	/** Constructor para inyectar las interfaces de los clientes a consumir */
	public CatAplicativosCasoUsoServiceImpl(ICatAplicativosPersistenceClient iCatAplicativosPersistenceClient, ITokenClient iTokenClient) {
		this.iCatAplicativosPersistenceClient = iCatAplicativosPersistenceClient;
		this.iTokenClient = iTokenClient;
	}

	/**
	 * Metodo para registrar un aplicativo en el Catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @param catalogoAplicativoDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatAplicativosDTO Objeto que contien la respuesta con el Catalogo de aplicativos o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatAplicativosDTO registrarAplicativo(HeaderDTO headerDTO, CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		log.info("registrarAplicativo");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatAplicativosPersistenceClient.registrarAplicativo(headerDTO, catalogoAplicativoDTO);
		} else {
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatAplicativosDTO.setSucess(false);
			responseCatAplicativosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatAplicativosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatAplicativosDTO;
	}

	/**
	 * Metodo para actualizar un aplicativo en el Catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @param catalogoAplicativoDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatAplicativosDTO Objeto que contien la respuesta con el Catalogo de aplicativos o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatAplicativosDTO actualizarAplicativo(HeaderDTO headerDTO, CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		log.info("actualizarAplicativo");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatAplicativosPersistenceClient.actualizarAplicativo(headerDTO, catalogoAplicativoDTO);
		} else {
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatAplicativosDTO.setSucess(false);
			responseCatAplicativosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatAplicativosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatAplicativosDTO;
	}
	
	/**
	 * Metodo para eliminar un aplicativo del Catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @param aplicativoId cadena que contiene el identificador del Catalogo
	 * @return ResponseCatAplicativosDTO Objeto que contien la respuesta con el Catalogo de aplicativos o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatAplicativosDTO eliminarAplicativo(HeaderDTO headerDTO, String aplicativoId) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		log.info("actualizarAplicativo");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatAplicativosPersistenceClient.eliminarAplicativo(headerDTO, aplicativoId);
		} else {
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatAplicativosDTO.setSucess(false);
			responseCatAplicativosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatAplicativosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatAplicativosDTO;
	}
	
	/**
	 * Metodo para consultar los aplicativos en el catalogo
	 * 
	 * @param headerDTO Header de entrada.
	 * @return ResponseCatAplicativosDTO Objeto que contien la respuesta con el Catalogo de aplicativos o mensajes de error.
	 * @throws Exception
	 */
	@Override
	public ResponseCatAplicativosDTO consultarAplicativos(HeaderDTO headerDTO) throws Exception {
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		log.info("actualizarAplicativo");
		tokenizerResponseDTO = iTokenClient.validToken(headerDTO);
		log.info(tokenizerResponseDTO.getStatusCode());
		if (tokenizerResponseDTO.getStatusCode() == 200) {
			return this.iCatAplicativosPersistenceClient.consultarAplicativos(headerDTO);
		} else {
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(tokenizerResponseDTO.getStatusCode());
			responseCatAplicativosDTO.setSucess(false);
			responseCatAplicativosDTO.setMessage(tokenizerResponseDTO.getErrorMessageDTO().getMessage());
			responseCatAplicativosDTO.setResponseType(tokenizerResponseDTO.getStatusCode());
		}
		return responseCatAplicativosDTO;
	}
}
