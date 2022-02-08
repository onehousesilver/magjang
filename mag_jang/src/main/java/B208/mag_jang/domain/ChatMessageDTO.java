package B208.mag_jang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
@Getter
@Setter
@AllArgsConstructor
public class ChatMessageDTO {
    private String roomId;
    private String writer;
    private String reader;
    private String message;

    @Override
    public String toString() {
        return "ChatMessageDTO{" +
                "roomId='" + roomId + '\'' +
                ", writer='" + writer + '\'' +
                ", reader='" + reader + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
