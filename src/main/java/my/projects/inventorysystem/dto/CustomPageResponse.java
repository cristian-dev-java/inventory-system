package my.projects.inventorysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que sirve para emvolver el contenido util sobre la paginación
 *
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResponse<T> {

    private long totalElements;
    private int totalPage;
    private boolean lastPage;
    private T data;

}
