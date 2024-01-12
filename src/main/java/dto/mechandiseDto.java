package dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class mechandiseDto {

 private String MR_id;
 private String name;
 private String qty_on_hand;
 private String price;


 //public List<mechandiseDto> getAllmerchandises() {
 //}
}
