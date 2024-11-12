package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto;
/**
 * ResponseCatalogoDTO.java:
 * 
 * Objeto que contiene los datos de respuesta de catalogo. 
 *  
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */
import lombok.Data;
import mx.gob.banobras.bitcen.common.infraestructure.adapters.out.dto.ErrorMessageDTO;


@Data
public class ResponseCatalogoDTO {
	/** Estatus de la peticion */
	private Integer statusCode;
	/** Objeto que contien los datos del Catalogo */
	private CatalogoDTO catalogoDTO;
	/** Objeto que contiene los datos de mensaje de error de la peticion */
	private ErrorMessageDTO errorMessageDTO;
}


