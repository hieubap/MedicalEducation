package medical.education.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.backend.library.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
public class BillDTO extends BaseDTO {
  private Long id;

  @JsonInclude(value = Include.NON_NULL)
  private Long buyerId;

  private UserDTO buyer;

  private Long total;

  private Short status;

  private String statusName;

  private LocalDateTime date;

  @JsonInclude(value = Include.NON_NULL)
  private Long courseId;
}
