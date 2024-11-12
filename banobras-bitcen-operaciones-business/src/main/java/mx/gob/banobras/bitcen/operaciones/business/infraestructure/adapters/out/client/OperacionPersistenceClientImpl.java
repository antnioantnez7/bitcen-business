package mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.out.client;
/**
 * OperacionPersistenceClientImpl.java:
 * 
 * Componete para consumir el servicio de operacion de la capa de persistencia.
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
import mx.gob.banobras.bitcen.operaciones.business.aplication.port.out.IOperacionPersistenceClient;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraOperacionDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.ResponseOperacionDTO;

@Component
public class OperacionPersistenceClientImpl implements IOperacionPersistenceClient {

	/** Logs de la clase */
	Logger log = LogManager.getLogger(OperacionPersistenceClientImpl.class);

	/** Variable que contiene la URL del servicio de operacion de persistencia **/
	@Value("${app.url.persistence.operaciones}")
	String urlPersistenceOperaciones;

	/** Objeto para obtner el cliente de conexion **/
	private final HttpClientFactory httpClientFactory;

	/**
	 * Constructor para el cliente de conexion 
	 * @param httpClientFactory
	 */
	public OperacionPersistenceClientImpl(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de operaciones.
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param BitacoraAccesoDTO  Datos del Catalogo a guardar
	 * 
	 * @return ResponseOperacionDTO
	 */
	@Override
	public ResponseOperacionDTO registrar(HeaderDTO headerDTO, BitacoraOperacionDTO bitacoraOperacionDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseOperacionDTO responseOperacionDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente registrar");
		String urlCatalogoFindById = urlPersistenceOperaciones + "/persistence/operaciones/registrar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(bitacoraOperacionDTO.toString());
			entityBody = new StringEntity(bitacoraOperacionDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseOperacionDTO = gson.fromJson(respondeBody, ResponseOperacionDTO.class);
					log.info(responseOperacionDTO);
					if (responseOperacionDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_OPERATIONS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseOperacionDTO = new ResponseOperacionDTO();
						responseOperacionDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseOperacionDTO.setMessage(errorMessageDTO.getMessage());
						responseOperacionDTO.setSucess(false);
					} else if(responseOperacionDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseOperacionDTO.setStatusCode(responseOperacionDTO.getStatusCode());
						responseOperacionDTO.setMessage(responseOperacionDTO.getMessage());
						responseOperacionDTO.setSucess(false);
					} else { 
						responseOperacionDTO.setMessage("");
						responseOperacionDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_OPERATIONS.getName());
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
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_OPERATIONS.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseOperacionDTO = new ResponseOperacionDTO();
			responseOperacionDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseOperacionDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente registrar");
		return responseOperacionDTO;
	}
	
	/**
	 * Metodo para consultar movimientos de la bitacora de operaciones.
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param BitacoraAccesoDTO  Objeto que contiene los parametros de busqueda de la bitacora de operaciones
	 * 
	 * @return ResponseOperacionDTO
	 */
	@Override
	public ResponseOperacionDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseOperacionDTO responseOperacionDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente consultar");
		String urlCatalogoFindById = urlPersistenceOperaciones + "/persistence/operaciones/consultar";
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
					responseOperacionDTO = gson.fromJson(respondeBody, ResponseOperacionDTO.class);
					log.info(responseOperacionDTO);
					if (responseOperacionDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_OPERATIONS.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseOperacionDTO = new ResponseOperacionDTO();
						responseOperacionDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseOperacionDTO.setMessage(errorMessageDTO.getMessage());
						responseOperacionDTO.setSucess(false);
					} else if(responseOperacionDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseOperacionDTO.setStatusCode(responseOperacionDTO.getStatusCode());
						responseOperacionDTO.setMessage(responseOperacionDTO.getMessage());
						responseOperacionDTO.setSucess(false);
					} else { 
						responseOperacionDTO.setMessage("");
						responseOperacionDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_OPERATIONS.getName());
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
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_OPERATIONS.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseOperacionDTO = new ResponseOperacionDTO();
			responseOperacionDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseOperacionDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente consultar");
		return responseOperacionDTO;
	}
}
