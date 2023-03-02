package tda.spring.back.entity;

import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AgentDataMapper {
    AgentData toEntity(AgentDataDto agentDataDto);

    AgentDataDto toDto(AgentData agentData);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AgentData partialUpdate(AgentDataDto agentDataDto, @MappingTarget AgentData agentData);

    @AfterMapping
    default void linkAgentLogin(@MappingTarget AgentData agentData) {
        AgentLogin agentLogin = agentData.getAgentLogin();
        if (agentLogin != null) {
            agentLogin.setAgentData(agentData);
        }
    }

    @AfterMapping
    default void linkLocationDatas(@MappingTarget AgentData agentData) {
        agentData.getLocationDatas().forEach(locationData -> locationData.setAgentData(agentData));
    }
}