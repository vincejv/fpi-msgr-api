package com.abavilla.fpi.msgr.entity;

import com.abavilla.fpi.fw.entity.mongo.AbsMongoField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@RegisterForReflection
public class MsgrErrorApiResp extends AbsMongoField {

  @Data
  @EqualsAndHashCode(callSuper = true)
  @NoArgsConstructor
  @RegisterForReflection
  public static class ErrorField extends AbsMongoField {
    private String message;
    private String type;
    private Integer code;
    @JsonProperty("error_subcode")
    private Integer subCode;
    @JsonProperty("fbtrace_id")
    private String fbTraceId;
  }

  private ErrorField error;

}
