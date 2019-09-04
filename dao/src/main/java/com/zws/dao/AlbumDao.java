package com.zws.dao;

import com.zws.model.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import  com.zws.vo.AlbumResult;


public interface AlbumDao extends JpaRepository<Album, Long>, JpaSpecificationExecutor{



    @Query(value = "select new com.zws.vo.AlbumResult(a.id, c.name, c.head )  from Album a join Account c  on  c.id = a.accountId ")
    Page<AlbumResult> findAllPage(Pageable pageable);


//    @Modifying
//    @Query("delete from album v where v.id=:id")
//    void deleteById(@Param("id") Long id);

}
