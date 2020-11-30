package com.example.demo.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.example.demo.models.KhuyenMai;

@Repository
public interface KhuyenMaiRepository extends CrudRepository<KhuyenMai,Long>{
	@Query(value = "select * from KhuyenMai where isdelete=0",nativeQuery=true)
	List<KhuyenMai> getAll();
	@Query(value = "select * from KhuyenMai where id=? and isdelete=0",nativeQuery=true)
	Optional<KhuyenMai> find(long id);
	@Query(value = "select * from KhuyenMai where maloaidt=? and isdelete=0",nativeQuery=true)
	Optional<KhuyenMai> finddt(String maloaidt);
	@Query(value = "select * from KhuyenMai where idpk=? and isdelete=0",nativeQuery=true)
	Optional<KhuyenMai> findpk(long idpk);
	@Query(value = "SELECT * FROM KhuyenMai WHERE name @@ to_tsquery(?)",nativeQuery=true)
	List<KhuyenMai> findup(String name);
}
