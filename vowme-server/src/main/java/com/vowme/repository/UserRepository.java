package com.vowme.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vowme.model.User;
import com.vowme.util.helper.MutliModel;


/**
 * The Interface UserRepository.
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Gets the volunteers.
	 *
	 * @param pageable
	 *            the pageable
	 * @return the volunteers
	 */
	@Query("SELECT distinct u FROM User u JOIN u.userInfo i WHERE i.isOrganizer != 1")
	Page<User> getVolunteers(Pageable pageable);

	/**
	 * Gets the volunteers.
	 *
	 * @return the volunteers
	 */
	@Query("SELECT distinct u FROM User u JOIN u.userInfo i WHERE i.isOrganizer != 1")
	List<User> getVolunteers();

	/**
	 * Find team by owner id.
	 *
	 * @param userId
	 *            the user id
	 * @return the list
	 */
	@Query("SELECT distinct t.user FROM User u JOIN u.cause c JOIN c.teams t WHERE u.id = ?1 and t.user != u")
	List<User> findTeamByOwnerId(Long userId);

	/**
	 * Gets the volunteers by user id.
	 *
	 * @param userId the user id
	 * @param pageable the pageable
	 * @return the volunteers by user id
	 */
	@Query("SELECT distinct p.user FROM User u JOIN u.cause c JOIN c.participates p WHERE u.id = ?1")
	Page<User> getVolunteersByUserId(Long userId, Pageable pageable);

	User findByEmail(String email);
	
	User findById(Long id);

	@Query("SELECT distinct new com.vowme.util.helper.MutliModel(u.id,u.firstname,u.lastname,u.username,u.email,u.cnic,c.name,c.id) FROM User u JOIN u.approvals a JOIN a.cause c JOIN c.user cu WHERE cu.id = ?1 and cu.id != u.id and a.isApproved NOT in (0,1)")
	Page<MutliModel> getPendingVolunteersByUserId(Long userId, Pageable pageable);

	@Query("SELECT distinct u FROM User u JOIN u.userInfo ui where ui.isOrganizer = 1")
	List<User> getAllOrganizer();
	
	User findByUsername(String username);
	
	@Query("SELECT distinct u FROM User u where u.provider = ?1 and u.providerKey = ?2")
	User findByProvider(String provider, Long providerkey);
}
