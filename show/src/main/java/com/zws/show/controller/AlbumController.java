package com.zws.show.controller;


import com.zws.model.Album;
import com.zws.service.AlbumService;


import com.zws.vo.AlbumResult;
import io.swagger.annotations.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import  java.util.Map;

@Api(value="/api", tags="专辑接口模块")
@RestController
@RequestMapping("/api")
public class AlbumController {


    @Autowired
    AlbumService albumService;


    @ApiOperation(value="查询所有专辑功能", notes = "可以指定参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value="第几页",paramType="query",dataType = "Integer"),
            @ApiImplicitParam(name="pageSize",value="一页有几行",paramType="query",dataType = "Integer")
    })
    @PostMapping("/albums")
    public Result<Page<Album>> findAll(

            @RequestBody  @ApiParam(name="album实例对象",value="传入json格式",required=true) Album album,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<AlbumResult> albumData  =  albumService.findAll( album, page, pageSize);

        System.out.println(albumData);
        Result result = new Result(200, "查询成功", albumData);
        return  result;

    }



}

