package com.zws.service;


import com.zws.model.Album;
import com.zws.vo.AlbumResult;
import org.springframework.data.domain.Page;

import java.util.Optional;

import  java.util.Map;

public interface AlbumService {


/**
* @param id
* @return
*/

Optional<Album> findById(Long id);


/**
* @param album
* @param page
* @param pageSize
* @return
*/

Page<AlbumResult> findAll(Album album , Integer page, Integer pageSize);


/**
* @param album
* @return
*/

Album insertByAlbum(Album album);


/**
 *
 * @param album
  * @return
 */
 Optional<Album>  updateByAlbum(Long id, Album album);

 /**
  *
  * @param id
  */
 void deleteById(Long id);

}
