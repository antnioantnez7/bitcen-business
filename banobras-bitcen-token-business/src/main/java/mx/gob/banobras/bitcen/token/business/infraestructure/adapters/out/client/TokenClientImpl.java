package mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.client;
/**
 * TokenClientImpl.java:
 * 
 * Implementacio para consumir los servicios del Api Tokenizer
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */


import org.apache.hc.client5.http.HttpHostConnectException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
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
import mx.gob.banobras.bitcen.token.business.aplication.port.out.ITokenClient;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.TokenizerResponseDTO;
import java.util.Date;

@Component
public class TokenClientImpl implements ITokenClient {
	
	/** Trazas de la aplicación */
	Logger log = LogManager.getLogger(TokenClientImpl.class);

	/** Variable que contiene el nombre de usuario de conexion en ldap */
	@Value("${app.url.token.valid}")
	String urlTokenValid;
	
	/** Objeto para obtner el cliente de conexion **/
	private final HttpClientFactory httpClientFactory;

	/**
	 * Constructor para el cliente de conexion 
	 * @param httpClientFactory
	 */
	public TokenClientImpl(HttpClientFactory httpClientFactory) {
		this.httpClientFactory = httpClientFactory;
	}
	

	/**
	 * Metodo para validar el token.
	 * 
	 * @param HeadetDTO componente que contiene los datos del token.
	 * @return HttpResponse<String> regresa un objeto con los datos del token
	 *         validado
	 * @throws Exception
	 */
	@Override
	public TokenizerResponseDTO validToken(HeaderDTO headerDTO)
			throws Exception {
		Gson gson = new Gson();
		String responseBody = null;
		CloseableHttpClient client = null;
		CloseableHttpResponse response = null;
		TokenizerResponseDTO tokenizerResponseDTO = null;
		ErrorMessageDTO errorMessageDTO = null;
		log.info("Inicia cliente validar token");
		log.info(urlTokenValid);

		try {
			log.info("Inicia rest cliente LDAP");
			client = httpClientFactory.getHttpClient(urlTokenValid);
			
			HttpPost httpPost = new HttpPost(urlTokenValid);
			httpPost.setHeader("Content-Type", "application/json");
			httpPost.setHeader("credentials", headerDTO.getCredentials());
			httpPost.setHeader("token-auth", headerDTO.getTokenAuth());
			httpPost.setHeader("app-name", headerDTO.getAppName());
			httpPost.setHeader("consumer-id", headerDTO.getConsumerId());
			httpPost.setHeader("functional-id", headerDTO.getFunctionalId());
			httpPost.setHeader("transaction-id", headerDTO.getTransactionId());

			response = client.execute(httpPost);
			try {
				HttpEntity entity =  response.getEntity();
				if (entity != null) {
					responseBody = EntityUtils.toString(entity);
					tokenizerResponseDTO = gson.fromJson(responseBody, TokenizerResponseDTO.class);
					
					if (tokenizerResponseDTO.getStatusCode() == null) {
						HttpErrorExceptionDTO httpErrorExceptionDTO = gson.fromJson(responseBody,
								HttpErrorExceptionDTO.class);
						log.info(httpErrorExceptionDTO.getStatus());
						errorMessageDTO = new ErrorMessageDTO();
						errorMessageDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						errorMessageDTO.setTimestamp(new Date());
						errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_TOKEN_NO_AVAILABLE.getName());
						errorMessageDTO
								.setDetail(httpErrorExceptionDTO.getPath() + " - " + httpErrorExceptionDTO.getError());
						/** Respuesta del servicio */
						tokenizerResponseDTO = new TokenizerResponseDTO();
						tokenizerResponseDTO.setStatusCode(Integer.parseInt(httpErrorExceptionDTO.getStatus()));
						tokenizerResponseDTO.setErrorMessageDTO(errorMessageDTO);
					}
				}else {
					throw new HttpHostConnectException(ConstantBitcen.MSG_SERVICE_TOKEN_NO_AVAILABLE.getName());
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
			client.close();
		} catch (Exception e) {
			log.error(e);
			errorMessageDTO = new ErrorMessageDTO();
			errorMessageDTO.setStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());
			errorMessageDTO.setTimestamp(new Date());
			errorMessageDTO.setMessage(ConstantBitcen.MSG_SERVICE_TOKEN_NO_AVAILABLE.getName());
			errorMessageDTO.setDetail(ErrorDetail.getDetail(e));
			/** Respuesta del servicio */
			tokenizerResponseDTO = new TokenizerResponseDTO();
			tokenizerResponseDTO.setStatusCode(errorMessageDTO.getStatusCode());
			tokenizerResponseDTO.setErrorMessageDTO(errorMessageDTO);
		} finally {
			try {
				client.close();	
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		log.info("Finaliza validar token");
		return tokenizerResponseDTO;
	}

	


}
