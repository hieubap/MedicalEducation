package vn.isofh.medical.education.dto;

import javax.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.isofh.common.dto.BaseDTO;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class SubjectDTO extends BaseDTO {

  private String value;

  private String name;

  private String type;
}
