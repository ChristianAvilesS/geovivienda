package com.geovivienda.geovivienda.dtos;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatRequestDTO {
    private List<ChatMessageDTO> messages;
}
