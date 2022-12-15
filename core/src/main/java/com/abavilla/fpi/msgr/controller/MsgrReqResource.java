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

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.abavilla.fpi.fw.controller.AbsBaseResource;
import com.abavilla.fpi.fw.dto.IDto;
import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.exceptions.FPISvcEx;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.fw.util.FWConst;
import com.abavilla.fpi.meta.ext.dto.ProfileReqReply;
import com.abavilla.fpi.meta.ext.dto.msgr.MsgrReqReply;
import com.abavilla.fpi.msgr.entity.MsgrLog;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import com.abavilla.fpi.msgr.service.MsgrReqSvc;
import io.smallrye.mutiny.Uni;
import org.apache.commons.lang3.BooleanUtils;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/fpi/msgr")
public class MsgrReqResource extends AbsBaseResource<MsgrMsgReqDto, MsgrLog, MsgrReqSvc> {

  @POST
  public Uni<RespDto<MsgrReqReply>> sendMsg(
    MsgrMsgReqDto msgReq, @HeaderParam("X-FPI-User") String fpiUser) {
    return service.postMsg(msgReq, fpiUser).map(svcResp -> {
      var resp = new RespDto<MsgrReqReply>();
      resp.setResp(svcResp);
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus(FWConst.SUCCESS);
      return resp;
    });
  }

  @POST
  @Path("typing/{recipient}/{isTyping}")
  public Uni<RespDto<MsgrReqReply>> toggleTyping(
    @PathParam("recipient") String recipient,
    @PathParam("isTyping") String isTypingParam) {
    boolean isTyping = BooleanUtils.toBoolean(isTypingParam);
    return service.toggleTyping(recipient, isTyping).map(svcResp -> {
      var resp = new RespDto<MsgrReqReply>();
      resp.setResp(svcResp);
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus("%s typing indicator for %s".formatted(isTyping ? "Activated" : "Deactivated", recipient));
      return resp;
    });
  }

  @GET
  @Path("u/{id}")
  public Uni<RespDto<ProfileReqReply>> getUserDtls(
    @PathParam("id") String userId, @HeaderParam("X-FPI-User") String fpiUser
  ) {
    return service.getUserDtls(userId).map(svcResp -> {
      var resp = new RespDto<ProfileReqReply>();
      resp.setResp(svcResp);
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus("Retrieved meta user details");
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
