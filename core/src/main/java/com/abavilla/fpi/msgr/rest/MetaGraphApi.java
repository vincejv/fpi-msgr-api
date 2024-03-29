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

package com.abavilla.fpi.msgr.rest;

import com.abavilla.fpi.fw.exceptions.handler.ApiRepoExHandler;
import com.abavilla.fpi.fw.rest.IApi;
import com.abavilla.fpi.meta.ext.dto.ProfileReqReply;
import com.abavilla.fpi.meta.ext.dto.msgr.MsgrReqReply;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@RegisterRestClient(configKey = "meta-graph-api")
@Produces(MediaType.APPLICATION_JSON)
@RegisterProvider(value = ApiRepoExHandler.class)
public interface MetaGraphApi extends IApi {

  @POST
  @Path("v15.0/{pageId}/messages")
  Uni<RestResponse<MsgrReqReply>> sendMsgrMsg(
      @PathParam("pageId") String pageId,
      @QueryParam("recipient") String recipient,
      @QueryParam("messaging_type") String type,
      @QueryParam("message") String messageNode,
      @QueryParam("access_token") String token
  );

  @POST
  @Path("v15.0/{pageId}/messages")
  Uni<RestResponse<MsgrReqReply>> sendTypingIndicator(
    @PathParam("pageId") String pageId,
    @QueryParam("recipient") String recipient,
    @QueryParam("sender_action") String senderAction,
    @QueryParam("messaging_type") String type,
    @QueryParam("access_token") String token
  );

  @GET
  @Path("{profileId}")
  Uni<RestResponse<ProfileReqReply>> getProfile(
      @PathParam("profileId") String profileId,
      @QueryParam("fields") String fields,
      @QueryParam("access_token") String token
  );
}
