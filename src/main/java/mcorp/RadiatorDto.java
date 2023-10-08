package mcorp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RadiatorDto {
    private String currentHeatingSetpoint;
    private Integer localTemperature;

}
