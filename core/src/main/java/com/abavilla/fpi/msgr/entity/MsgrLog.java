package com.abavilla.fpi.msgr.entity;

import java.util.List;

import com.abavilla.fpi.fw.entity.mongo.AbsMongoItem;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@BsonDiscriminator
@MongoEntity(collection="msgr_log")
public class MsgrLog extends AbsMongoItem {
  private String metaMsgId;
  private String recipient;
  private String pageId;
  private String msgContent;
  private String replyTo;
  private MsgrErrorApiResp.ErrorField apiError;
  private List<MsgAttchmt> attachments;
  private String fpiUser;
  private String fpiSystem;
}
