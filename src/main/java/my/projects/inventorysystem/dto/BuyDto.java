package my.projects.inventorysystem.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuyDto {

    private LocalDateTime date;
    private String clientIdType;
    private long clientId;
    private String clientName;
    private List<BuyDetailDto> products;

}
