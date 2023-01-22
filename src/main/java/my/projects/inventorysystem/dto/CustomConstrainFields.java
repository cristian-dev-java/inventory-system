package my.projects.inventorysystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomConstrainFields {

    private String fieldName;
    private String fieldMessage;

}
