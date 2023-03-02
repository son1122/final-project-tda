package tda.spring.back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A DTO for the {@link AgentData} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AgentDataDto implements Serializable {
    private AgentLogin agentLogin;
    private AgentData.Type type;
    private Long registerId;
    private String prefix;
    private String name;
    private String lastname;
    private String phone;
    private String email;
    private Long licenseId;
    private Date expireDate;
    private Set<LocationData> locationDatas = new LinkedHashSet<>();
}