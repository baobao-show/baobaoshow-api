package com.zws.service;

import com.zws.dao.AlbumDao;
import com.zws.model.Album;
import com.zws.utils.PageUtil;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import  com.zws.vo.AlbumResult;

@Service
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    AlbumDao albumDao;

    @Override
    @Cacheable(value = "album", key = "'id_'+#id", unless = "#result eq null")
    public Optional<Album> findById(Long id) {

        return albumDao.findById(id);
    }


    @Override
    public Album insertByAlbum(Album album) {
        return albumDao.save(album);
    }


    @Override
    public void deleteById(Long id) {

        albumDao.deleteById(id);
    }


    @Override
    public Optional<Album> updateByAlbum(Long id, Album album) {
        albumDao.save(album);
        return albumDao.findById(id);
    }

    @Override
    //@Cacheable(value = "pages", key = "'page_'+#page+'_'+#pageSize", unless = "#result eq null")
    public Page<AlbumResult> findAll(Album album, Integer page, Integer pageSize) {

        Pageable pageable = PageUtil.Pageable(page, pageSize);

        return albumDao.findAllPage(pageable);
    }


}
