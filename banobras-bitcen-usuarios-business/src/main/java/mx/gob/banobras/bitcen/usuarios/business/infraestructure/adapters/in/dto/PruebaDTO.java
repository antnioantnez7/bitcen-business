package mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDTO {

	private Integer statusCode;
	private String message;
    private boolean success;
    private Integer responseType ;
	//private Object contenido;
    
	private List<Usuario1DTO> contenido;
	
	
	
}
