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

package com.abavilla.fpi.msgr.controller;

import com.abavilla.fpi.fw.controller.AbsBaseResource;
import com.abavilla.fpi.fw.dto.IDto;
import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.entity.AbsItem;
import com.abavilla.fpi.fw.mapper.IMapper;
import com.abavilla.fpi.fw.repo.AbsMongoRepo;
import com.abavilla.fpi.fw.util.DateUtil;
import com.abavilla.fpi.fw.util.FWConst;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import com.abavilla.fpi.msgr.service.MsgReqSvc;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;

public abstract class MsgReqResource<Q, I extends AbsItem, R extends AbsMongoRepo<I>, M extends IMapper, K, S extends
  MsgReqSvc<Q, I, R, M, K>> extends AbsBaseResource<MsgrMsgReqDto, I, S> {

  @POST
  public Uni<RespDto<IDto>> sendMsg(
    MsgrMsgReqDto msgReq, @HeaderParam("X-FPI-User") String fpiUser) {
    return service.postMsg(msgReq, fpiUser).map(svcResp -> {
      var resp = new RespDto<>();
      resp.setTimestamp(DateUtil.nowAsStr());
      resp.setStatus(FWConst.SUCCESS);
      return resp;
    });
  }

}
