package com.redhat.training.rest;

import javax.ws.rs.core.UriInfo;
import javax.ws.rs.container.ContainerRequestFilter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.PreMatching;

@Provider
@PreMatching
public final class ContentFilter implements ContainerRequestFilter {
   @Override
   public void filter(ContainerRequestContext ctx) throws IOException {
      UriInfo uri = ctx.getUriInfo();
      if ((uri != null) && uri.getPath().toLowerCase().contains("filedownload")) {
          String ctp = ctx.getHeaderString("Content-Type");
          if ("*/*".equals(ctp))
             ctx.getHeaders().putSingle("Content-Type", "application/json; charset=UTF-8");
      }
   }
}