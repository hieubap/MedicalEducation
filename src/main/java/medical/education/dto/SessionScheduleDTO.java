package medical.education.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import spring.backend.library.dto.BaseDTO;

import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
public class SessionScheduleDTO extends BaseDTO {
    private Long id;

    @DateTimeFormat(pattern = "hh:mm")
    private Time startTime;

    @DateTimeFormat(pattern = "hh:mm")
    private Time endTime;
}
