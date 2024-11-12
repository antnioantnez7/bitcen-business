package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.out.client;
/**
 * CatalogoPersistenceClientImpl.java:
 * 
 * Componete para consumir el servicio de catalogo de la capa de persistencia.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import java.util.Date;

import org.apache.hc.client5.http.HttpHostConnectException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import mx.gob.banobras.bitcen.common.infraestructure.adapters.out.client.HttpClientFactory;
import mx.gob.banobras.bitcen.common.infraestructure.adapters.out.dto.ErrorMessageDTO;
import mx.gob.banobras.bitcen.common.infraestructure.adapters.out.dto.HttpErrorExceptionDTO;
import mx.gob.banobras.bitcen.common.util.ConstantBitcen;
import mx.gob.banobras.bitcen.common.util.ErrorDetail;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.out.ICatUsuariosPersistenceClient;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoUsuarioDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatUsuariosDTO;

@Component
public class CatUsuariosPersistenceClientImpl implements ICatUsuariosPersistenceClient {

	/** Logs de la clase */
	Logger log = LogManager.getLogger(CatUsuariosPersistenceClientImpl.class);

	/** Variable que contiene la URL del servicio de catalogo de persistencia **/
	@Value("${app.url.persistence.catalogos}")
	String urlPersistenceCatalogos;

	/** Objeto para obtner el cliente de conexion **/
	private final HttpClientFactory httpClientFactory;

	/**
	 * Constructor para el cliente de conexion 
	 * @param httpClientFactory
	 */
	public CatUsuariosPersistenceClientImpl(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * Metodo para registrar un usuario en el Catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param CatalogoUsuarioDTO  Datos del Catalogo a guardar
	 * 
	 * @return ResponseCatUsuariosDTO
	 */
	@Override
	public ResponseCatUsuariosDTO guardarUsuario(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatUsuariosDTO responseCatUsuariosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente guardarUsuario");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/usuarios/guardar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(catalogoUsuarioDTO.toString());
			entityBody = new StringEntity(catalogoUsuarioDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseCatUsuariosDTO = gson.fromJson(respondeBody, ResponseCatUsuariosDTO.class);
					log.info(responseCatUsuariosDTO);
					if (responseCatUsuariosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
						responseCatUsuariosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatUsuariosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatUsuariosDTO.setSucess(false);
					} else if(responseCatUsuariosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatUsuariosDTO.setStatusCode(responseCatUsuariosDTO.getStatusCode());
						responseCatUsuariosDTO.setMessage(responseCatUsuariosDTO.getMessage());
						responseCatUsuariosDTO.setSucess(false);
					} else { 
						responseCatUsuariosDTO.setMessage("");
						responseCatUsuariosDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
			errorMessageDTO.setTimestamp(new Date());
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
			responseCatUsuariosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatUsuariosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente guardarUsuario");
		return responseCatUsuariosDTO;
	}
	
	/**
	 * Metodo para eliminar un usuario  del catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param identificador entero que contiene el identificador del Catalogo
	 * 
	 * @return ResponseCatUsuariosDTO
	 */
	@Override
	public ResponseCatUsuariosDTO eliminarUsuario(HeaderDTO headerDTO, int identificador) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatUsuariosDTO responseCatUsuariosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		log.info("Inicia Cliente eliminarUsuario");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/usuarios/eliminar/" + identificador;
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpDelete httpDelete = new HttpDelete(urlCatalogoFindById);
			httpDelete.setHeader("Content-Type", "application/json");
			response = client.execute(httpDelete);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseCatUsuariosDTO = gson.fromJson(respondeBody, ResponseCatUsuariosDTO.class);
					log.info(responseCatUsuariosDTO);
					if (responseCatUsuariosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
						responseCatUsuariosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatUsuariosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatUsuariosDTO.setSucess(false);
					} else if(responseCatUsuariosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatUsuariosDTO.setStatusCode(responseCatUsuariosDTO.getStatusCode());
						responseCatUsuariosDTO.setMessage(responseCatUsuariosDTO.getMessage());
						responseCatUsuariosDTO.setSucess(false);
					} else { 
						responseCatUsuariosDTO.setMessage("");
						responseCatUsuariosDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
			errorMessageDTO.setTimestamp(new Date());
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
			responseCatUsuariosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatUsuariosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente eliminarUsuario");
		return responseCatUsuariosDTO;
	}
	
	/**
	 * Metodo para consultar los usuarios en el catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * 
	 * @return ResponseCatUsuariosDTO
	 */
	@Override
	public ResponseCatUsuariosDTO consultarUsuarios(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatUsuariosDTO responseCatUsuariosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente consultarUsuarios");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/usuarios/consultar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(catalogoUsuarioDTO.toString());
			entityBody = new StringEntity(catalogoUsuarioDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseCatUsuariosDTO = gson.fromJson(respondeBody, ResponseCatUsuariosDTO.class);
					log.info(responseCatUsuariosDTO);
					if (responseCatUsuariosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
						responseCatUsuariosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatUsuariosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatUsuariosDTO.setSucess(false);
					} else if(responseCatUsuariosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatUsuariosDTO.setStatusCode(responseCatUsuariosDTO.getStatusCode());
						responseCatUsuariosDTO.setMessage(responseCatUsuariosDTO.getMessage());
						responseCatUsuariosDTO.setSucess(false);
					} else { 
						responseCatUsuariosDTO.setMessage("");
						responseCatUsuariosDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
			errorMessageDTO.setTimestamp(new Date());
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseCatUsuariosDTO = new ResponseCatUsuariosDTO();
			responseCatUsuariosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatUsuariosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente consultarUsuarios");
		return responseCatUsuariosDTO;
	}
}
