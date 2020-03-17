package com.robi.foodiy.controller.etc;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/etc")
public class ImageController {

    private static Logger logger = LoggerFactory.getLogger(ImageController.class);

    private Environment env;

    @GetMapping(value="/img/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("name") String name) {
        InputStream is = null;
        byte[] rtByte = null;
        
        try {
            String a = env.getProperty("foodiy.records.img.basedir");
            is = getClass().getResourceAsStream(a + "/" + name); // 파일을 못 가져온다... 권한문제인가? @@
            rtByte = IOUtils.toByteArray(is);
        }
        catch (IOException e) {
            logger.error("Exception!", e);
        }

        return rtByte;
    }
}