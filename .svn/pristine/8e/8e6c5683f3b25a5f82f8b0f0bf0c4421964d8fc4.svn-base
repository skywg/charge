package com.iycharge.server.admin.controller.ajax;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iycharge.server.domain.entity.Image;
import com.iycharge.server.domain.entity.content.Content;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.service.ContentService;
import com.iycharge.server.domain.service.StationService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class PhotoAjaxController {
    private final static String bucket = "youetong";
    private final static String formApiSecret = "/4flPvPlLxu8VxvjfFn6uwoBNCg=";
    private final static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StationService stationService;

    @RequestMapping("/ajax/photo-storage-settings")
    public Map<String, String> photoStorageSettings(@RequestParam(value = "directory") String directory)
            throws JsonProcessingException {
        Map<String, Object> options = new LinkedHashMap<String, Object>();
        options.put("bucket", bucket);
        options.put("save-key", genFileName(directory));
        options.put("expiration", new Date().getTime() + 120l);


        String policy = Base64.encodeBase64String(mapper.writeValueAsBytes(options));
        String signature = DigestUtils.md5Hex(policy + "&" + formApiSecret);

        Map<String, String> settings = new LinkedHashMap<String, String>();
        settings.put("policy", policy);
        settings.put("signature", signature);
        settings.put("bucket", bucket);
        settings.put("endpoint", "http://v0.api.upyun.com/" + bucket);

        return settings;
    }

    public static String genFileName(String directory) {
        String filename = UUID.randomUUID().toString();
        return String.format("/%s/%s/%s/%s/%s", directory,
                filename.substring(0, 2), filename.substring(2, 4),
                filename.substring(4, 6), filename);
    }

    @RequestMapping("/ajax/stations/images/{stationId}/{index}/actions/delete")
    public String deleteStationImages(@PathVariable("stationId") Long chargerId, @PathVariable("index") int index) {
        try {
            Station station = stationService.findById(chargerId);
            List<Image> images = station.getImages();
            images.remove(index);
            station.setImages(images);
            stationService.save(station);
            return "SUCCESS";
        }
        catch (EmptyResultDataAccessException e) {
            return "FAILED";
        }
    }
    @RequestMapping(value = "/ajax/stations/{stationId}/images/position", method = RequestMethod.POST)
    public ResponseEntity<Void> sortStationsImages(@PathVariable("stationId") Long stationId, @RequestParam("sorted") String sorted) {
        Station station = stationService.findById(stationId);

        try {
            int i = 1;
            for (String index : sorted.split(",")) {
                station.getImages().get(Integer.parseInt(index)).setPosition(i);
                i++;
            }
            stationService.save(station);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
