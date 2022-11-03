/*
 * *****************************************************************************
 *  * FPI Application - Abavilla                                                 *
 *  * Copyright (C) 2022  Vince Jerald Villamora                                 *
 *  *                                                                            *
 *  * This program is free software: you can redistribute it and/or modify       *
 *  * it under the terms of the GNU General Public License as published by       *
 *  * the Free Software Foundation, either version 3 of the License, or          *
 *  * (at your option) any later version.                                        *
 *  *                                                                            *
 *  * This program is distributed in the hope that it will be useful,            *
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 *  * GNU General Public License for more details.                               *
 *  *                                                                            *
 *  * You should have received a copy of the GNU General Public License          *
 *  * along with this program.  If not, see <https://www.gnu.org/licenses/>.     *
 *  *****************************************************************************
 */

package com.abavilla.fpi.msgr.ext.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.abavilla.fpi.fw.dto.impl.RespDto;
import com.abavilla.fpi.fw.exceptions.handler.ApiRepoExHandler;
import com.abavilla.fpi.fw.rest.IApi;
import com.abavilla.fpi.msgr.ext.dto.SessionDto;
import com.abavilla.fpi.msgr.ext.dto.WebhookLoginDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "login-api")
@RegisterClientHeaders(AppToAppPreAuth.class)
@RegisterProvider(value = ApiRepoExHandler.class)
public interface TrustedLoginApi extends IApi {

  /**
   * Perform a trusted login through {@code /fpi/login/trusted}
   * @param login Login credentials
   * @return {@link SessionDto} object
   */
  @POST
  @Path("trusted")
  Uni<RespDto<SessionDto>> webhookAuthenticate(WebhookLoginDto login);
}