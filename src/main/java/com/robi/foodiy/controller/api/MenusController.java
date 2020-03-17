package com.robi.foodiy.controller.api;

import com.robi.data.ApiResult;
import com.robi.foodiy.service.MenusService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class MenusController {

    private static Logger logger = LoggerFactory.getLogger(MenusController.class);

    private MenusService menusSvc;

    @GetMapping("/menus/page/{pageIdx}")
    public ApiResult getMenusByPage(
        @RequestHeader ("user_jwt") String userJwt,
        @PathVariable ("pageIdx")   int pageIdx)
    {
        return menusSvc.getMenusByPageIdx(userJwt, pageIdx);
    }
    
    @GetMapping("/menus/name/{menuName}/page/{pageIdx}")
    public ApiResult getMenusByName(
        @RequestHeader ("user_jwt") String userJwt,
        @PathVariable ("menuName")   String menuName,
        @PathVariable ("pageIdx")   int pageIdx)
    {
        return menusSvc.getMenusByMenuName(userJwt, menuName, pageIdx);
    }

    @GetMapping("/menus/tag/{tag}/page/{pageIdx}")
    public ApiResult getMenusByTag(
        @RequestHeader ("user_jwt") String userJwt,
        @PathVariable ("tag")       String tag,
        @PathVariable ("pageIdx")   int pageIdx)
    {
        return menusSvc.getMenusByTag(userJwt, tag, pageIdx);
    }

    @GetMapping("/menus/place/{place}/page/{pageIdx}")
    public ApiResult getMenusByPlace(
        @RequestHeader ("user_jwt") String userJwt,
        @PathVariable ("place")     String place,
        @PathVariable ("pageIdx")   int pageIdx)
    {
        return menusSvc.getMenusByPlace(userJwt, place, pageIdx);
    }

    @GetMapping("/menus/who/{who}/page/{pageIdx}")
    public ApiResult getMenusByWho(
        @RequestHeader ("user_jwt") String userJwt,
        @PathVariable ("who")       String who,
        @PathVariable ("pageIdx")   int pageIdx)
    {
        return menusSvc.getMenusByWho(userJwt, who, pageIdx);
    }
}