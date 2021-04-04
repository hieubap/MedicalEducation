package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class ThongKeLopHocDTO extends BaseDTO {

  private Long limitRegister;

  private Short status;

  private Short day;

  private Long teacherId;

  private String time;

  private Long courseId;

  private Long placeId;

  private Long soLuong;
}
