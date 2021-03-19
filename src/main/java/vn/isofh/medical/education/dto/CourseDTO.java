package vn.isofh.medical.education.dto;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.isofh.common.dto.BaseDTO;
import vn.isofh.medical.education.dao.model.SubjectEntity;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class CourseDTO extends BaseDTO {

  private String name;

  private String value;

  private String thoiGianHoc;

  private String details;

  private List<SubjectDTO> subjects;

}
