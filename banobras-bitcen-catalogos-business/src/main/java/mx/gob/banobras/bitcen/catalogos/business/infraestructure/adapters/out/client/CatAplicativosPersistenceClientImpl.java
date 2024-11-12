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
import org.apache.hc.client5.http.classic.methods.HttpGet;
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
import mx.gob.banobras.bitcen.catalogos.business.aplication.port.out.ICatAplicativosPersistenceClient;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoAplicativoDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatAplicativosDTO;

@Component
public class CatAplicativosPersistenceClientImpl implements ICatAplicativosPersistenceClient {

	/** Logs de la clase */
	Logger log = LogManager.getLogger(CatAplicativosPersistenceClientImpl.class);

	/** Variable que contiene la URL del servicio de catalogo de persistencia **/
	@Value("${app.url.persistence.catalogos}")
	String urlPersistenceCatalogos;

	/** Objeto para obtner el cliente de conexion **/
	private final HttpClientFactory httpClientFactory;

	/**
	 * Constructor para el cliente de conexion 
	 * @param httpClientFactory
	 */
	public CatAplicativosPersistenceClientImpl(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * Metodo para registrar un aplicativo en el Catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param CatalogoAplicativoDTO  Datos del Catalogo a guardar
	 * 
	 * @return ResponseCatAplicativosDTO
	 */
	@Override
	public ResponseCatAplicativosDTO registrarAplicativo(HeaderDTO headerDTO, CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente registrarAplicativo");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/aplicativos/registrar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(catalogoAplicativoDTO.toString());
			entityBody = new StringEntity(catalogoAplicativoDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseCatAplicativosDTO = gson.fromJson(respondeBody, ResponseCatAplicativosDTO.class);
					log.info(responseCatAplicativosDTO);
					if (responseCatAplicativosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
						responseCatAplicativosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else if(responseCatAplicativosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatAplicativosDTO.setStatusCode(responseCatAplicativosDTO.getStatusCode());
						responseCatAplicativosDTO.setMessage(responseCatAplicativosDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else { 
						responseCatAplicativosDTO.setMessage("");
						responseCatAplicativosDTO.setSucess(true);
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
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente registrarAplicativo");
		return responseCatAplicativosDTO;
	}
	
	/**
	 * Metodo para actualizar un aplicativo en el Catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion
	 * @param catalogoAplicativoDTO Objeto que contiene los datos del Catalogo
	 * 
	 * @return ResponseCatAplicativosDTO
	 */
	@Override
	public ResponseCatAplicativosDTO actualizarAplicativo(HeaderDTO headerDTO, CatalogoAplicativoDTO catalogoAplicativoDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente actualizarAplicativo");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/aplicativos/actualizar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			
			/** convierte a JSON el objeto */
			log.info(catalogoAplicativoDTO.toJson());
			entityBody = new StringEntity(catalogoAplicativoDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					log.info(respondeBody);
					responseCatAplicativosDTO = gson.fromJson(respondeBody, ResponseCatAplicativosDTO.class);
					log.info(responseCatAplicativosDTO);
					if (responseCatAplicativosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.toString());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
						responseCatAplicativosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else if(responseCatAplicativosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatAplicativosDTO.setStatusCode(responseCatAplicativosDTO.getStatusCode());
						responseCatAplicativosDTO.setMessage(responseCatAplicativosDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else { 
						responseCatAplicativosDTO.setMessage("");
						responseCatAplicativosDTO.setSucess(true);
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
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente actualizarAplicativo");
		return responseCatAplicativosDTO;
	}
	
	/**
	 * Metodo para eliminar un aplicativo  del catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param aplicativoId cadena que contiene el identificador del Catalogo
	 * 
	 * @return ResponseCatAplicativosDTO
	 */
	@Override
	public ResponseCatAplicativosDTO eliminarAplicativo(HeaderDTO headerDTO, String aplicativoId) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		log.info("Inicia Cliente eliminarAplicativo");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/aplicativos/eliminar/" + aplicativoId;
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
					responseCatAplicativosDTO = gson.fromJson(respondeBody, ResponseCatAplicativosDTO.class);
					log.info(responseCatAplicativosDTO);
					if (responseCatAplicativosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
						responseCatAplicativosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else if(responseCatAplicativosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatAplicativosDTO.setStatusCode(responseCatAplicativosDTO.getStatusCode());
						responseCatAplicativosDTO.setMessage(responseCatAplicativosDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else { 
						responseCatAplicativosDTO.setMessage("");
						responseCatAplicativosDTO.setSucess(true);
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
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente eliminarAplicativo");
		return responseCatAplicativosDTO;
	}
	
	/**
	 * Metodo para consultar los aplicativos en el catalogo
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * 
	 * @return ResponseCatAplicativosDTO
	 */
	@Override
	public ResponseCatAplicativosDTO consultarAplicativos(HeaderDTO headerDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseCatAplicativosDTO responseCatAplicativosDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		log.info("Inicia Cliente consultarAplicativos");
		String urlCatalogoFindById = urlPersistenceCatalogos + "/persistence/catalogos/aplicativos/consultar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpGet httpGet = new HttpGet(urlCatalogoFindById);
			httpGet.setHeader("Content-Type", "application/json");
			response = client.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseCatAplicativosDTO = gson.fromJson(respondeBody, ResponseCatAplicativosDTO.class);
					log.info(responseCatAplicativosDTO);
					if (responseCatAplicativosDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_CATALOGS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
						responseCatAplicativosDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else if(responseCatAplicativosDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseCatAplicativosDTO.setStatusCode(responseCatAplicativosDTO.getStatusCode());
						responseCatAplicativosDTO.setMessage(responseCatAplicativosDTO.getMessage());
						responseCatAplicativosDTO.setSucess(false);
					} else { 
						responseCatAplicativosDTO.setMessage("");
						responseCatAplicativosDTO.setSucess(true);
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
			responseCatAplicativosDTO = new ResponseCatAplicativosDTO();
			responseCatAplicativosDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseCatAplicativosDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente consultarAplicativos");
		return responseCatAplicativosDTO;
	}
}
