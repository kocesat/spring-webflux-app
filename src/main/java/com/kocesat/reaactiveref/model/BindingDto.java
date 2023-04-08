package com.kocesat.reaactiveref.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.kocesat.reaactiveref.util.web.deserialize.CustomLocalDateTimeDeserializer;
import com.kocesat.reaactiveref.util.web.serialize.CustomLocalDateSerializer;
import com.kocesat.reaactiveref.util.web.serialize.CustomLocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BindingDto {
  @Builder.Default
  private Integer page = 1;
  @Builder.Default
  private Integer pageSize = 20;
  @JsonSerialize(using = CustomLocalDateSerializer.class)
  private LocalDate requestDate;
  @JsonSerialize(using = CustomLocalDateTimeSerializer.class)
  @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
  private LocalDateTime requestTime;
}
