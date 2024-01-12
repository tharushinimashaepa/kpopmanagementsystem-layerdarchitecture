package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class eventDto {
    private String EV_id;
    private String name;
   private String genre_of_event;
    private String venue;
   private String  price;
}
