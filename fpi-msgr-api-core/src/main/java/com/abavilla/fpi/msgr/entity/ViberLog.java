/*************************************************************************
 * FPI Application - Abavilla                                            *
 * Copyright (C) 2022  Vince Jerald Villamora                            *
 *                                                                       *
 * This program is free software: you can redistribute it and/or modify  *
 * it under the terms of the GNU General Public License as published by  *
 * the Free Software Foundation, either version 3 of the License, or     *
 * (at your option) any later version.                                   *
 *                                                                       *
 * This program is distributed in the hope that it will be useful,       *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 * GNU General Public License for more details.                          *
 *                                                                       *
 * You should have received a copy of the GNU General Public License     *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.*
 *************************************************************************/

package com.abavilla.fpi.msgr.entity;

import com.abavilla.fpi.fw.entity.mongo.AbsMongoItem;
import com.abavilla.fpi.viber.ext.dto.Location;
import com.abavilla.fpi.viber.ext.dto.Message;
import com.abavilla.fpi.viber.ext.dto.Sender;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@BsonDiscriminator
@MongoEntity(collection="viber_log")
public class ViberLog extends AbsMongoItem {

  private String text;
  private Message.Type type;
  private String trackingData;
  private String media;
  private Location location;
  private String receiver;
  private Sender sender;
  private Integer minApiVersion;
  private Integer status;
  private String statusMsg;
  private String msgToken;
  private String chatHostname;
  private Integer billingStatus;
  private String fpiUser;
  private String fpiSystem;

}
