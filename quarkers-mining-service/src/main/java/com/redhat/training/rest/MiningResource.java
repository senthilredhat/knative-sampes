package com.redhat.training.rest;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.training.model.Bitmine;
import com.redhat.training.model.BitmineStatus;
import com.redhat.training.service.MiningService;

@Path("/")
public class MiningResource {

        private static final Logger LOGGER = Logger.getLogger("mining-resource");

        @Inject
        private MiningService miningService;

        @POST
        @Path("/")
        @Consumes(javax.ws.rs.core.MediaType.MEDIA_TYPE_WILDCARD)
        public Response process(@Context HttpHeaders httpHeaders, String bitmineJson) {
        	
        		ObjectMapper mapper = new ObjectMapper();
        		
        		Bitmine bitmine;
				try {
					bitmine = mapper.readValue(bitmineJson, Bitmine.class);
					miningService.process(bitmine);
	                logHeaders(httpHeaders);
	                LOGGER.info("Processed Bitmine:" + bitmine);
	                try {
						Thread.sleep(60000, 0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	                miningService.store(bitmine);
	                LOGGER.info("Bitmine is stored in the DB");
				} catch (JsonMappingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                return Response.status(Status.OK).entity("{\"status\":\"successful\"}")
                                .build();
        }

        private void logHeaders(HttpHeaders httpHeaders) {
                LOGGER.info("Cloud Event:");

                LOGGER.info("ce-id=" + httpHeaders.getHeaderString("ce-id"));
                LOGGER.info(
                                "ce-source=" + httpHeaders.getHeaderString("ce-source"));
                LOGGER.info("ce-specversion="
                                + httpHeaders.getHeaderString("ce-specversion"));
                LOGGER.info("ce-time=" + httpHeaders.getHeaderString("ce-time"));
                LOGGER.info("ce-type=" + httpHeaders.getHeaderString("ce-type"));
                LOGGER.info(
                                "content-type=" + httpHeaders.getHeaderString("content-type"));
                LOGGER.info("content-length="
                                + httpHeaders.getHeaderString("content-length"));
        }

}
