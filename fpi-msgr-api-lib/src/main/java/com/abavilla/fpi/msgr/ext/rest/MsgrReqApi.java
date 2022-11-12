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

package com.abavilla.fpi.msgr.ext.rest;

import java.time.temporal.ChronoUnit;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.exceptions.AuthApiSvcEx;
import com.abavilla.fpi.fw.exceptions.handler.ApiRepoExHandler;
import com.abavilla.fpi.fw.rest.IApi;
import com.abavilla.fpi.login.ext.rest.AppToAppPreAuth;
import com.abavilla.fpi.meta.ext.dto.msgr.MsgrReqReply;
import com.abavilla.fpi.msgr.ext.dto.MsgrMsgReqDto;
import io.smallrye.faulttolerance.api.ExponentialBackoff;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "msgr-api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterClientHeaders(AppToAppPreAuth.class)
@RegisterProvider(value = ApiRepoExHandler.class)
@Retry(maxRetries = 8, retryOn = AuthApiSvcEx.class, delay = 3,
  delayUnit = ChronoUnit.SECONDS, jitter = 1500L)
@ExponentialBackoff(maxDelay = 25, maxDelayUnit = ChronoUnit.SECONDS)
public interface MsgrReqApi extends IApi {

  @POST
  Uni<RespDto<MsgrReqReply>> sendMsg(
    MsgrMsgReqDto msgReq, @HeaderParam("X-FPI-User") String fpiUser);

  @POST
  @Path("typing/{recipient}/{isTyping}")
  Uni<RespDto<MsgrReqReply>> toggleTyping(
    @PathParam("recipient") String recipient, @PathParam("isTyping") Boolean isTypingParam);

}
