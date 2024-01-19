package fem.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")
public class User {
    @Schema(title = "사용자 ID", description = "사용자 ID는 자동 생성됩니다.")
    private Long id;

    @Schema(title = "사용자 이름", description = "사용자 이름을 입력합니다.")
    @Min(value = 2, message = "name은 2글자 이상 입력해주세요.")
    private String name;

    @Schema(title = "사용자 등록일", description = "사용자 등록일을 입력할 수 있습니다. 입력하지 않으면 현재 날짜가 지정됩니다.")
    @Past(message = "등록일은 미래 날짜를 입력할 수 없습니다.")
    private LocalDateTime joinDate;

    @Schema(title = "사용자 비밀번호", description = "사용자의 비밀번호를 입력합니다.")
    private String password;

    @Schema(title = "사용자 주민번호", description = "사용자의 주민번호를 입력합니다.")
    private String ssn;
}
