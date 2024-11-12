package mx.gob.banobras.bitcen.accesos.business.aplication.port.in;
/**
 * IAccesoCasoUsoService.java:
 * 
 * Interface para el caso de uso del servicio de accesos.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraAccesoDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.accesos.business.infraestructure.adapters.in.dto.ResponseAccesoDTO;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;

public interface IAccesoCasoUsoService {
	/**
	 * Metodo para registrar movimientos en la bitacora de accesos.
	 * 
	 * @param headerDTO Header de entrada
	 * @param bitacoraAccesoDTO Objeto que contiene los datos de la bitacora de accesos
	 * @return ResponseAccesoDTO
	 * @throws Exception
	 */
	ResponseAccesoDTO registrar(HeaderDTO headerDTO, BitacoraAccesoDTO bitacoraAccesoDTO) throws Exception;
	/**
	 * Metodo para consultar movimientos de la bitacora de accesos.
	 * 
	 * @param headerDTO Header de entrada
	 * @param bitacoraConsultaDTO Objeto que contiene los parametros de busqueda de la bitacora de accesos
	 * @return ResponseAccesoDTO
	 * @throws Exception
	 */
	ResponseAccesoDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception;
}
