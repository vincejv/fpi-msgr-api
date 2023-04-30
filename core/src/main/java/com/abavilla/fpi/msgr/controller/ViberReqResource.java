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
import com.abavilla.fpi.msgr.config.ViberApiKeyConfig;
import com.abavilla.fpi.msgr.entity.ViberLog;
import com.abavilla.fpi.msgr.mapper.ViberMsgReqMapper;
import com.abavilla.fpi.msgr.repo.ViberLogRepo;
import com.abavilla.fpi.msgr.service.ViberReqSvc;
import com.abavilla.fpi.viber.ext.dto.SendResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Path("/fpi/viber")
public class ViberReqResource
  extends MsgReqResource<SendResponse, ViberLog, ViberLogRepo, ViberMsgReqMapper,
  ViberApiKeyConfig, ViberReqSvc> {

  @GET
  @Path("u/{id}")
  public Uni<RespDto<SendResponse>> getUserDtls(
    @PathParam("id") String userId, @HeaderParam("X-FPI-User") String fpiUser
  ) {
    return service.getUserDtls(userId).map(svcResp -> {
      var resp = new RespDto<SendResponse>();
      resp.setResp(svcResp);
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus("Retrieved viber user details");
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
