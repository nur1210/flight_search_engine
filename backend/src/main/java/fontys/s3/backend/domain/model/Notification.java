package fontys.s3.backend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String date;
    private String title;
    private String text;
    private String to;
    private Map<String, String> queryParam;
}
