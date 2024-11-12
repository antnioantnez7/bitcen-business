package mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.out.client;
/**
 * UsuarioPersistenceClientImpl.java:
 * 
 * Componete para consumir el servicio de usuarios de la capa de persistencia.
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
import mx.gob.banobras.bitcen.usuarios.business.aplication.port.out.IUsuarioPersistenceClient;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraUsuarioDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.ResponseUsuarioDTO;

@Component
public class UsuarioPersistenceClientImpl implements IUsuarioPersistenceClient {

	/** Logs de la clase */
	Logger log = LogManager.getLogger(UsuarioPersistenceClientImpl.class);

	/** Variable que contiene la URL del servicio de usuarios de persistencia **/
	@Value("${app.url.persistence.usuarios}")
	
	String urlPersistenceUsuarios;

	/** Objeto para obtner el cliente de conexion **/
	private final HttpClientFactory httpClientFactory;

	/**
	 * Constructor para el cliente de conexion 
	 * @param httpClientFactory
	 */
	public UsuarioPersistenceClientImpl(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}

	/**
	 * Metodo para registrar movimientos en la bitacora de usuarios.
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param BitacoraAccesoDTO  Datos del modelo a guardar
	 * 
	 * @return ResponseUsuarioDTO
	 */
	@Override
	public ResponseUsuarioDTO registrar(HeaderDTO headerDTO, BitacoraUsuarioDTO bitacoraUsuarioDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseUsuarioDTO responseUsuarioDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente registrar");
		String urlCatalogoFindById = urlPersistenceUsuarios + "/persistence/usuarios/registrar";
		log.info(urlCatalogoFindById);
		try {
			client = httpClientFactory.getHttpClient(urlCatalogoFindById);
			HttpPost httpPost = new HttpPost(urlCatalogoFindById);
			/** se setea el tipo de contenido */
			httpPost.setHeader("Content-Type", "application/json");
			/** convierte a JSON el objeto */
			log.info(bitacoraUsuarioDTO.toString());
			entityBody = new StringEntity(bitacoraUsuarioDTO.toJson(), ContentType.APPLICATION_JSON);
		    httpPost.setEntity(entityBody);
		    
			/** Ejecuta la peticion */
		    response = client.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respondeBody = EntityUtils.toString(entity);
					responseUsuarioDTO = gson.fromJson(respondeBody, ResponseUsuarioDTO.class);
					log.info(responseUsuarioDTO);
					if (responseUsuarioDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_USER.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseUsuarioDTO = new ResponseUsuarioDTO();
						responseUsuarioDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseUsuarioDTO.setMessage(errorMessageDTO.getMessage());
						responseUsuarioDTO.setSucess(false);
					} else if(responseUsuarioDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseUsuarioDTO.setStatusCode(responseUsuarioDTO.getStatusCode());
						responseUsuarioDTO.setMessage(responseUsuarioDTO.getMessage());
						responseUsuarioDTO.setSucess(false);
					} else { 
						responseUsuarioDTO.setMessage("");
						responseUsuarioDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_USER.getName());
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
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_USER.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseUsuarioDTO = new ResponseUsuarioDTO();
			responseUsuarioDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseUsuarioDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente registrar");
		return responseUsuarioDTO;
	}
	
	/**
	 * Metodo para consultar movimientos de la bitacora de usuarios.
	 * 
	 * @param headerDTO Datos de los Headers de la peticion.
	 * @param BitacoraAccesoDTO  Objeto que contiene los parametros de busqueda de la bitacora de usuarios
	 * 
	 * @return ResponseUsuarioDTO
	 */
	@Override
	public ResponseUsuarioDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception {
		Gson gson = new Gson();
		String respondeBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		ResponseUsuarioDTO responseUsuarioDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		StringEntity entityBody = null;
		log.info("Inicia Cliente consultar");
		String urlCatalogoFindById = urlPersistenceUsuarios + "/persistence/usuarios/consultar";
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
					responseUsuarioDTO = gson.fromJson(respondeBody, ResponseUsuarioDTO.class);
					log.info(responseUsuarioDTO);
					if (responseUsuarioDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(respondeBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_USER.getName());
						errorMessageDTO.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						responseUsuarioDTO = new ResponseUsuarioDTO();
						responseUsuarioDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						responseUsuarioDTO.setMessage(errorMessageDTO.getMessage());
						responseUsuarioDTO.setSucess(false);
					} else if(responseUsuarioDTO.getStatusCode() != 200) {
						/** Respuesta del servicio */
						responseUsuarioDTO.setStatusCode(responseUsuarioDTO.getStatusCode());
						responseUsuarioDTO.setMessage(responseUsuarioDTO.getMessage());
						responseUsuarioDTO.setSucess(false);
					} else { 
						responseUsuarioDTO.setMessage("");
						responseUsuarioDTO.setSucess(true);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_USER.getName());
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
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_NO_AVAILABLE_USER.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			responseUsuarioDTO = new ResponseUsuarioDTO();
			responseUsuarioDTO.setStatusCode(errorMessageDTO.getStatusCode());
			responseUsuarioDTO.setMessage(errorMessageDTO.getMessage());
		} finally {
			try {
				client.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza Cliente consultar");
		return responseUsuarioDTO;
	}
}
