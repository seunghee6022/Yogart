package com.ssafy.yogart.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.yogart.user.model.UserFile;

@Transactional
@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Integer> {

	@Query(value="SELECT * FROM user_file ORDER BY file_create_date DESC",
			nativeQuery = true)
	List<UserFile> getUsers();

	@Query(value="SELECT * FROM user_file WHERE user_file_email = :userEmail ",
			nativeQuery = true)
	List<UserFile> getImageName(@Param("userEmail") String userEmail);
	
	@Modifying
	@Query(value="DELETE FROM user_file WHERE user_file_email = :userEmail ",
			nativeQuery = true)
	void deleteByUserFileEmail(@Param("userEmail") String userEmail);
}
