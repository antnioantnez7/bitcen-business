package mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.out.client;
/**
 * AccesoPersistenceClientImpl.java:
 * 
 * Componete para consumir el servicio de acceso de la capa de persistencia.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import java.util.Date;

import org.apache.hc.client5.http.HttpHostConnectException;
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
import mx.gob.banobras.bitcen.accesos.business.aplication.port.out.IAccesoPersistenceClient;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraAccesoDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.ResponseAccesoDTO;

@Component
public class AccesoPersistenceClientImpl implements IAccesoPersistenceClient {

	/** Logs de la clase */
	Logger log = LogManager.getLogger(AccesoPersistenceClientImpl.class);

	/** Variable que contiene la URL del servicio de acceso de persistencia **/
	@Value("${app.url.persistence.accesos}")
	String urlPersistenceAccesos;

	/** Objeto para obtner el cliente de conexion **/
	private final HttpClientFactory httpClientFactory;

	/**
	 * Constructor para el cliente de conexion 
	 * @param httpClientFactory
	 */
	public AccesoPersistenceClientImpl(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de accesos.
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param BitacoraAccesoDTO  Datos del Catalogo a guardar
	 * 
	 * @return ResponseAccesoDTO
	 */
	@Override
	public ResponseAccesoDTO registrar(HeaderDTO headerDTO, BitacoraAccesoDTO bitacoraAccesoDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseAccesoDTO responseAccesoDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente registrar");
		String urlCatalogoFindById = urlPersistenceAccesos + "/persistence/accesos/registrar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(bitacoraAccesoDTO.toString());
			entityBody = new StringEntity(bitacoraAccesoDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					log.info(respondeBody.toString());
					responseAccesoDTO = gson.fromJson(respondeBody, ResponseAccesoDTO.class);
					log.info(responseAccesoDTO);
					if (responseAccesoDTO.getStatusCode() == null) {
						/** Respuesta del servicio */
						responseAccesoDTO.setStatusCode(responseAccesoDTO.getStatusCode());
						responseAccesoDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_ACCESS.getName());
						responseAccesoDTO.setSucess(false);
					} else if(responseAccesoDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseAccesoDTO.setStatusCode(responseAccesoDTO.getStatusCode());
						responseAccesoDTO.setMessage(responseAccesoDTO.getMessage());
						responseAccesoDTO.setSucess(false);
					} else { 
						responseAccesoDTO.setMessage("");
						responseAccesoDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_ACCESS.getName());
				}
				log.info("entity: \n");
				log.info(entity);
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
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_ACCESS.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseAccesoDTO = new ResponseAccesoDTO();
			responseAccesoDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseAccesoDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente registrar");
		return responseAccesoDTO;
	}
	
	/**
	 * Metodo para consultar movimientos de la bitacora de accesos.
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param BitacoraAccesoDTO  Objeto que contiene los parametros de busqueda de la bitacora de accesos
	 * 
	 * @return ResponseAccesoDTO
	 */
	@Override
	public ResponseAccesoDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseAccesoDTO responseAccesoDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente consultar");
		String urlCatalogoFindById = urlPersistenceAccesos + "/persistence/accesos/consultar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(bitacoraConsultaDTO.toString());
			log.info(bitacoraConsultaDTO.toJson());
			entityBody = new StringEntity(bitacoraConsultaDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseAccesoDTO = gson.fromJson(respondeBody, ResponseAccesoDTO.class);
					log.info(responseAccesoDTO);
					if (responseAccesoDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_ACCESS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseAccesoDTO = new ResponseAccesoDTO();
						responseAccesoDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseAccesoDTO.setMessage(errorMessageDTO.getMessage());
						responseAccesoDTO.setSucess(false);
					} else if(responseAccesoDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						log.info(responseAccesoDTO.getMessage());
						responseAccesoDTO.setStatusCode(responseAccesoDTO.getStatusCode());
						responseAccesoDTO.setMessage(responseAccesoDTO.getMessage());
						responseAccesoDTO.setSucess(false);
					} else { 
						responseAccesoDTO.setMessage("");
						responseAccesoDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_ACCESS.getName());
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
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseAccesoDTO = new ResponseAccesoDTO();
			responseAccesoDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseAccesoDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente consultar");
		return responseAccesoDTO;
	}
}
