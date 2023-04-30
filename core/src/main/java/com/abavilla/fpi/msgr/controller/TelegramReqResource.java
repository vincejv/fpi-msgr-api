/******************************************************************************
 * FPI Application - Abavilla                                                 *
 * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *                                                                            *
 * This program is free software: you can redistribute it and/or modify       *
 * it under the terms of the GNU General Public License as published by       *
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * This program is distributed in the hope that it will be useful,            *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU General Public License for more details.                               *
 *                                                                            *
 * You should have received a copy of the GNU General Public License          *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 ******************************************************************************/

package com.abavilla.fpi.msgr.controller;

import com.abavilla.fpi.fw.dto.IDto;
import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.exceptions.FPISvcEx;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.msgr.config.TelegramApiKeyConfig;
import com.abavilla.fpi.msgr.entity.TelegramLog;
import com.abavilla.fpi.msgr.mapper.TelegramMsgReqMapper;
import com.abavilla.fpi.msgr.repo.TelegramLogRepo;
import com.abavilla.fpi.msgr.service.TelegramReqSvc;
import com.pengrad.telegrambot.response.SendResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/fpi/telegram")
public class TelegramReqResource
  extends MsgReqResource<SendResponse, TelegramLog, TelegramLogRepo, TelegramMsgReqMapper,
  TelegramApiKeyConfig, TelegramReqSvc> {

  @POST
  @Path("typing/{recipient}")
  public Uni<RespDto<IDto>> toggleTyping(
    @PathParam("recipient") String recipient) {
    return service.toggleTyping(recipient).map(svcResp -> {
      var resp = new RespDto<>();
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus("Toggled typing indicator for %s".formatted(recipient));
      return resp;
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @ServerExceptionMapper
  protected RestResponse<RespDto<IDto>> mapException(FPISvcEx x) {
    return super.mapException(x);
  }
}
