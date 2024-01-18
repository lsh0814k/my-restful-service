package fem.myrestfulservice.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@JsonFilter("UserInfo")
@NoArgsConstructor
public class AdminUser {
    private Long id;
    @Min(value = 2, message = "name은 2글자 이상 입력해주세요.")
    private String name;
    @Past(message = "등록일은 미래 날짜를 입력할 수 없습니다.")
    private LocalDateTime joinDate;

    private String password;
    private String ssn;


}
