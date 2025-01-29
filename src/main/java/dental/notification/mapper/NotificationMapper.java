package dental.notification.mapper;

import dental.notification.dto.NotificationRequestDto;
import dental.notification.entity.Notification;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface NotificationMapper {

    Notification toEntity(NotificationRequestDto dto);


}
