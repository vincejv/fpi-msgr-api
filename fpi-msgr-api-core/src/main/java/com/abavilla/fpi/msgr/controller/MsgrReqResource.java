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

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.abavilla.fpi.fw.controller.AbsBaseResource;
import com.abavilla.fpi.fw.dto.IDto;
import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.exceptions.FPISvcEx;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.fw.util.FWConst;
import com.abavilla.fpi.meta.ext.dto.msgr.MsgrReqReply;
import com.abavilla.fpi.msgr.entity.MsgrLog;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import com.abavilla.fpi.msgr.service.MsgrReqSvc;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/fpi/msgr")
public class MsgrReqResource extends AbsBaseResource<MsgrMsgReqDto, MsgrLog, MsgrReqSvc> {

  @POST
  public Uni<RespDto<MsgrReqReply>> sendMsg(
    MsgrMsgReqDto msgReq, @HeaderParam("X-FPI-System") String appSource) {
    return service.postMsg(msgReq, appSource).map(svcResp -> {
      var resp = new RespDto<MsgrReqReply>();
      resp.setResp(svcResp);
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus(FWConst.SUCCESS);
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
