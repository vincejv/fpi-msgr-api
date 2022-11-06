package com.abavilla.fpi.msgr.entity;

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
@MongoEntity(collection="telegram_log")
public class TelegramLog extends AbsMongoItem {
  private Long chatId;
  private Integer messageId;
  private String msgContent;
  private Integer replyTo;
  private String fpiUser;
  private String fpiSystem;
}
